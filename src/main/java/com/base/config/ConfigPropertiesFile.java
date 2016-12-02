package com.base.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

public class ConfigPropertiesFile {
    public static final Logger log = Logger.getLogger(ConfigPropertiesFile.class);

    /** 1表示遇到错误直接抛异常 */
    public static final int ERR_TYPE_throwException = 1;

    /** 2表示遇到错误后面的覆盖前面的配置 */
    public static final int ERR_TYPE_override = 2;

    private int errType = -1;

    private String charsetName;

    private String fileName;

    private BufferedReader bufferedReader;

    /** 元素容器，LinkedHashMap是为了保证顺序 */
    private LinkedHashMap<String, String> map;

    public ConfigPropertiesFile() {
    }

    /**
     * 初始化参数
     * @param fileName classpath下路径的配置文件名
     * @param charsetName 文件编码格式
     * @param errType 错误处理方式，参见本类的常量设置
     */
    public ConfigPropertiesFile(String fileName, String charsetName) {
        this.fileName = fileName;
        this.charsetName = charsetName;
        reload();
    }

    /**
     * 从classpath重新读取配置文件
     */
    public void reload() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(stream, charsetName));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }

        map = new LinkedHashMap<String, String>(10);
        try {
            initialMap();
        } catch (IOException ex) {
        	log.error(ex);
        }

    }

    private void initialMap() throws IOException {
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            line = line.trim();
            //#开头表示注释
            if (line.length() == 0 || line.startsWith("#")) {
                continue;
            }
            int pos = line.indexOf('=');
            if (pos == -1) {
                throw new IllegalArgumentException("invalid line:" + line);
            }
            String key = line.substring(0, pos);
            String value = line.substring(pos + 1);
//            log.info("key:" + key);///////////////
//            log.info("value:" + value);///////////////
            if (key.equals("errType")) {
                //错误处理方式不能重复设定
                if (errType != -1) {
                    throw new IllegalArgumentException("set errType should be only once");
                }
                errType = Integer.parseInt(value);
                continue;
            } else {
                //开始初始化数据的时候，必须已经设定好错误处理方式
                if (errType == -1) {
                    throw new IllegalArgumentException("errType must be setted");
                }
            }

            if (map.containsKey(key)) {
                switch (errType) {
                    case ERR_TYPE_throwException:
                        throw new IllegalArgumentException("repeat key:" + key);
                    case ERR_TYPE_override:
                        break;
                    default:
                        throw new IllegalArgumentException("invalid errType:" + errType);
                }
            }
            map.put(key, value);
        }
    }

    /**
     * 根据key来取得value
     * @param key
     * @return String
     */
    public String getString(String key) {
        return (String) map.get(key);
    }

    /**
     * 得到配置文件中所有键值对
     * @return Map
     */
    public LinkedHashMap<String, String> getMap() {
        return map;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
