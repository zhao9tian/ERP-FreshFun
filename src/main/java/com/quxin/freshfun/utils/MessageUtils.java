package com.quxin.freshfun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * 短信发送
 */
public class MessageUtils {

	private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

	/**
	 * 订单发货发送短信
	 */
	public static void messageAtDelivery(String phoneNum , String content) throws IOException {
		if(phoneNum != null && !"".equals(phoneNum) && content != null && !"".equals(content)){
			String cont = "【悦选美食】"+content;
			String sign="";

			StringBuffer sb;
			sb = new StringBuffer("http://sms.1xinxi.cn/asmx/smsservice.aspx?");

			// 向StringBuffer追加用户名
			sb.append("name=yuexuan2016");//之前的是quxin2016

			// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
			sb.append("&pwd=140DC7BD9C90A23757B40A854800");//C877EC8499ACDA41BC44548C64AB

			// 向StringBuffer追加手机号码
			sb.append("&mobile=").append(phoneNum);

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content=").append(URLEncoder.encode(cont, "UTF-8"));
			//追加发送时间，可为空，为空为及时发送
			sb.append("&stime=");

			//加签名
			sb.append("&sign=").append(URLEncoder.encode(sign, "UTF-8"));
			//type为固定值pt  extno为扩展码，必须为数字 可为空
			sb.append("&type=pt&extno=");
			// 创建url对象
			HttpURLConnection connection = null;
			try{
				URL url = new URL(sb.toString());

				// 打开url连接
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(1000);
				connection.setReadTimeout(1000);
				// 设置url请求方式 ‘get’ 或者 ‘post’
				connection.setRequestMethod("POST");

				// 发送
				url.openStream();
				System.out.println("发短信了:"+new Date());
			}catch (Exception e){
				logger.error(content+"短信发送失败");
			}finally {
				assert connection != null;
				connection.disconnect();
			}

		}else{
			logger.error("发送短信失败,请检查手机号和内容!");
		}

	}



	public static void main(String[] args) throws IOException {
		messageAtDelivery("13120976976","您购买的甜蜜整颗心的星空糖已经发货啦。\n" +
                "发货单号是圆通速递1234567890" );
	}
}
