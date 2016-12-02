package com.encryption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PasswordUtils {
    public static final Logger log = Logger.getLogger(PasswordUtils.class);

    public static final String hash(String data) {
        MessageDigest digest = null;
        if (digest == null) {
            try {
                //digest = MessageDigest.getInstance("MD5");
            	digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                log.error("Failed to load the SHA-256 MessageDigest.");
            }
        }
        // Now, compute hash.
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }

    public static final String md5(String data) {
        MessageDigest digest = null;
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            	//digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                log.error("Failed to load the MD5 MessageDigest.");
            }
        }
        // Now, compute hash.
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;

        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }
    
    /**
     * 修改密码验证值
     * @param valueRegx:属性文件中存在的name
     */
    public static void writasswordComplexity(String valueRegx)throws Exception {
        if( StringUtils.isBlank(valueRegx)) {
            valueRegx = "";
        }
        //去除.name作为默认的name
        valueRegx = valueRegx.substring(0 , valueRegx.length() -5);
        List<String> list = new ArrayList<String>();
        InputStream stream = new FileInputStream(getFilePathPassswordComplexity());
        BufferedReader buff = new BufferedReader( new InputStreamReader(stream,"utf-8") );
        for (String  line = buff.readLine(); line!= null;line = buff.readLine()) {
            if(line.startsWith("default")) {
                int pos =line.indexOf('=');
                if( pos != -1) {
                    String key = line.substring(0,pos + 1);
                    //String value = line.substring( pos+1);
                    line = key + valueRegx;
                }else {
                    line = "="+ valueRegx;
                }
            }
            list.add(line);
        }
        buff.close();
        stream.close();
        try {
            OutputStream output = new FileOutputStream(getFilePathPassswordComplexity());
            OutputStreamWriter outWriter = new OutputStreamWriter(output,  "utf-8");
            BufferedWriter writer = new BufferedWriter(outWriter);
            for (int i = 0; i < (list!= null ?list.size():0); i++) {
                String line = list.get(i);
                writer.write(line);
                writer.write('\r');
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 密码复杂验证的文件保存地址
     * @return
     */
    public static String getFilePathPassswordComplexity() {
        return new PasswordUtils().getClass().getClassLoader().getResource("passwordComplexityMan.properties").getPath();
    }
    
    public static void main(String[] args) {
//        OrgServiceImpl org = new OrgServiceImpl();
//        String valueRegx = "password.six.to.twenty.name";
//        valueRegx = valueRegx.substring(0 , valueRegx.length() -5);
        System.out.println(PasswordUtils.hash("gys123456"));
    }

}
