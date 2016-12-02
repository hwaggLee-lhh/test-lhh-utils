package com.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;




public class UtilsSmsHttpclient implements InitializingBean {  
  
      
    private String smsUrl;  
    private String cpidName;  
    private String cpidValue;  
    private String pwdName;  
    private String pwdValue;  
    private String pidName;  
    private String pidValue;  
    private String phoneName;  
    private String msgName;  
    private int maxLength = 60; // 默认值  
      
    public void sendSms(String mobilePhone, String message) { 
    	if( mobilePhone == null || mobilePhone.isEmpty()) return;
        send(message, mobilePhone);  
          
    }  
  
    public void sendSms(String[] mobilePhones, String message){  
        if (mobilePhones == null || mobilePhones.length == 0){  
            return ;
        }  
        for (String phone : mobilePhones) {  
            sendSms(phone, message);  
        }  
          
    }  
      
  
    /** 
     * 如果超过短信的长度，则分成几条发 
     * @param content 
     * @param phoneNo 
     * @return 
     * 
     */  
    private String send(String content,String phoneNo) {  
        content = StringUtils.trimToEmpty(content);  
        phoneNo = StringUtils.trimToEmpty(phoneNo);  
          
        // 如果服务未准备好，先初始化  
        if (!isReady()) {  
            init();  
        }  
        // 初始化后，服务仍未准备好  
        if (!isReady()) { 
        	return "验证未通过：发送失败";
        }
        // 如果超过最大长度，则分成几条发送  
        int count = content.length() / maxLength;  
        int reminder = content.length() % maxLength;  
           
        if (reminder != 0 ){  
           count += 1;  
        }  
        StringBuffer result = new StringBuffer();  
        int i = 0;  
        while (count > i){  
           result.append(doSend(StringUtils.substring(content, i*maxLength, (i+1)*maxLength),phoneNo));  
           result.append(";");  
           i ++;  
        }  
        return result.toString();  
    }  
      
    private boolean isReady(){  
        return !(smsUrl == null || cpidName == null || cpidValue == null  
                || pwdName == null || pwdValue == null || pidName == null  
                || pidValue == null || phoneName == null || msgName == null || maxLength <= 0);  
    }  
    /** 
     * @param content 
     * @param phoneNo 
     * @return 
     * 
     */  
    private String doSend(String content,String phoneNo) {  
          
        // 使用httpclient模拟http请求  
        HttpClient client = new HttpClient();  
        // 设置参数编码  
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");  
          
        PostMethod method = new PostMethod(smsUrl);  
          
        method.addParameter(cpidName, cpidValue);  
        method.addParameter(pidName, pidValue);  
        method.addParameter(pwdName, pwdValue);  
        method.addParameter(phoneName, phoneNo);  
        method.addParameter(msgName, content);  
          
          
        BufferedReader br = null;  
        String reponse  = null;  
        try {  
            int returnCode = client.executeMethod(method);  
            if (returnCode != HttpStatus.SC_OK) {  
            	return "发送失败";
            }  
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));  
            reponse = br.readLine();  
        } catch (Exception e) {  
	        e.printStackTrace(); 
        } finally {  
            method.releaseConnection();  
            if (br != null) { 
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }
        }  
        return reponse;  
    }  
  
    public void afterPropertiesSet() throws Exception {  
        // 初始化  
        init();  
    }  
      
    private void init() {  
          
          
    }  
      
    private String getResponseMsg(String code){  
        String msg = "未知返回值:" + code;  
        if ("1".equals(code)) {  
            msg = "手机号码非法";  
        } else if ("2".equals(code)) {  
            msg = "用户存在于黑名单列表";  
        } else if ("3".equals(code)) {  
            msg = "接入用户名或密码错误";  
        } else if ("4".equals(code)) {  
            msg = "产品代码不存在";  
        } else if ("5".equals(code)) {  
            msg = "IP非法";  
        } else if ("6".equals(code)) {  
            msg = "源号码错误";  
        } else if ("7".equals(code)) {  
            msg = "调用网关错误";  
        } else if ("8".equals(code)) {  
            msg = "消息长度超过60";  
        } else if ("-1".equals(code)) {  
            msg = "短信内容为空";  
        } else if ("-2".equals(code)) {  
            msg = "手机号为空";  
        }else if ("-3".equals(code)) {  
            msg = "邮件服务初始化异常";  
        }else if ("-4".equals(code)) {  
            msg = "短信接口异常";  
        }  
        return msg;  
    }  
  
}  
