package com.quxin.freshfun.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by qingtian on 2016/11/2.
 */
public class HttpClientUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    /**
     * 字符编码
     */
    public final static String CHARSET = "utf-8";

    /**
     * 微信退款发送post请求
     * @return
     */
    public static String doPost(String url,String partner, String data, KeyStore keyStore){
        SSLContext sslcontext = null;
        CloseableHttpResponse response = null;
        String body = null;
        try {
            sslcontext = SSLContexts.custom()
                    //忽略对服务器端证书的校验
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    })
                    .loadKeyMaterial(keyStore, partner.toCharArray())
                    .build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httppost = new HttpPost(url);
            StringEntity params = new StringEntity(data,CHARSET);
            httppost.setEntity(params);
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (NoSuchAlgorithmException e) {
            logger.error("订单退款处理",e);
        } catch (KeyManagementException e) {
            logger.error("订单退款处理",e);
        } catch (KeyStoreException e) {
            logger.error("订单退款处理",e);
        } catch (UnrecoverableKeyException e) {
            logger.error("订单退款处理");
        } catch (ClientProtocolException e) {
            logger.error("订单退款处理",e);
        } catch (IOException e) {
            logger.error("订单退款处理",e);
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                logger.error("订单退款处理",e);
            }
        }
        return body;
    }
}
