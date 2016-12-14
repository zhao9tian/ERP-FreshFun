package com.quxin.freshfun.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by qingtian on 2016/11/2.
 */
public class HttpClientUtils {

    private static HttpClient httpClient = HttpClients.createDefault();

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

    /**
     * 发送 Post请求<br/> 直接发送JSON
     *
     * @param url
     * @param
     * @return
     */
    public static String jsonToPost(String url, String data) {
        String body = null;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            configureHttpClient2(httpClientBuilder);
            httpClient = httpClientBuilder.build();
            HttpPost httppost=new HttpPost(url);
            StringEntity params =new StringEntity(data,"UTF-8");
            httppost.addHeader("content-type", "application/json");
            httppost.setEntity(params);
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity,"UTF-8");
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static void configureHttpClient2(HttpClientBuilder clientBuilder)
    {
        try
        {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager()
            {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
                {

                }
                public void checkServerTrusted(X509Certificate[] chain,  String authType) throws CertificateException
                {

                }
                public X509Certificate[] getAcceptedIssuers()
                {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            clientBuilder.setSslcontext(ctx);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
