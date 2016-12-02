package com.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;




/**
 * 短信服务类
 *
 */
public class UtilsSmsHttp {
	private static final String apikey = "c8c221c4c55697df4f6c24de53317f24";
    private static final String url = "http://m.5c.com.cn/api/send/?";
    private static final String username = "qdyx";
    private static final String password = "7c79dd68b400e6b0c9f99f8f221dae26";

    /**
     * 短信消息
     * @param mobile
     * @param content
     * @return 0：发送失败，1：发送成功
     * @throws Exception
     */
    private int sendSms(String mobile, String content) throws Exception{
    	if(mobile == null || mobile.isEmpty()) return 0;
    	// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer(url);
		// APIKEY
		sb.append("apikey="+apikey);
		// 用户名
		sb.append("&username="+username);
		// 向StringBuffer追加密码
		sb.append("&password_md5="+password);
		// 向StringBuffer追加手机号码
		sb.append("&mobile="+mobile);
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content, "GBK"));
		// 创建url对象
		URL url = new URL(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");
		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		// 返回发送结果
		String inputline = in.readLine();
		System.out.println("发送结果:"+inputline+";发送内容:"+inputline);
		return 1;
    }
	/**
	 * 发送验证码短信(拼装第三分内容)
	 * @param mobiles 手机号数组
	 * @param content 验证码内容
	 * @throws UnsupportedEncodingException 
	 */
	public int sendSmsForVerificationCode(String mobile, String randomStr) throws Exception {
		String contents = "您的验证码为"+randomStr+"。";
		return sendSms(mobile,contents);
	}
}
