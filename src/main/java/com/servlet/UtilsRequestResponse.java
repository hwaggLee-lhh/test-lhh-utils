package com.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 获取request和rsponse中的信息
 * @author huage
 *
 */
@SuppressWarnings("all")
public class UtilsRequestResponse {

	public static Map<String, Object> getRequestInfo(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("HeaderNames(客户机请求头)", converEnumerationToMap(req, req.getHeaderNames()));
		map.put("AttributeNames", converEnumerationToMap(req, req.getAttributeNames()));
		map.put("ParameterNames(客户机请求参数)", converEnumerationToMap(req, req.getParameterNames()));
		map.put("Locales", converEnumerationToMap(req, req.getLocales()));
		map.put("Accept-Encoding(户端Accept-Encoding请求头)", req.getHeader("Accept-Encoding"));
		map.put("Accept-Encodings(户端Accept-Encoding请求头s)", converEnumerationToMap(req,req.getHeaders("Accept-Encoding")));
		map.put("other(获得客户机信息)", getRequestInfos(req));
		
		if(UtilsHttpRequestDevice.isMobileDevice(req)){
			map.put("text","手机端");
		}else{
			map.put("text", "网页端");
		}
		return map;
	}


	public static Map<Object,Object> converEnumerationToMap(HttpServletRequest req,Enumeration<?> ea){
		Map<Object,Object> map = new HashMap<Object, Object>();
		if( ea != null ){
			while(ea.hasMoreElements()){
				Object ob = ea.nextElement();
				String key = ob == null ? "":ob.toString();
				map.put(key, req.getHeader(key));
			}
		}
		return map;
	}
	
	public static Map<Object,Object> converEnumerationToMap(HttpServletRequest req,Map<String, String[]> paramMap ){
		//request对象封装的参数是以Map的形式存储的
		Map<Object,Object> map = new HashMap<Object, Object>();
        for(Map.Entry<String, String[]> entry :paramMap.entrySet()){
            String paramName = entry.getKey();
            String paramValue = "";
            String[] paramValueArr = entry.getValue();
            for (int i = 0; paramValueArr!=null && i < paramValueArr.length; i++) {
                if (i == paramValueArr.length-1) {
                    paramValue+=paramValueArr[i];
                }else {
                    paramValue+=paramValueArr[i]+",";
                }
            }
            map.put(paramName, paramValue);
        }
		return map;
	}

	public static Map<String, Object> getRequestInfos(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("HeaderNames", converEnumerationToMap(request, request.getHeaderNames()));
		
		/**
         * 1.获得客户机信息
         */
        map.put("getRequestURL", "得到请求的URL地址:"+request.getRequestURL().toString());//得到请求的URL地址
        map.put("getRequestURI", "请求的资源："+request.getRequestURI());//得到请求的资源requestUri);
        map.put("getQueryString", "请求的URL地址中附带的参数："+request.getQueryString());//得到请求的URL地址中附带的参数
        map.put("getRemoteAddr", "来访者的IP地址："+request.getRemoteAddr());//得到来访者的IP地址
        map.put("getRemoteHost", "来访者的主机名："+request.getRemoteHost());
        map.put("getRemotePort", "使用的端口号："+request.getRemotePort());
        map.put("getRemoteUser", "remoteUser："+request.getRemoteUser());
        map.put("getMethod", "请求使用的方法："+request.getMethod());//得到请求URL地址时使用的方法
        map.put("getPathInfo", "pathInfo："+request.getPathInfo());
        map.put("getLocalAddr", "localAddr："+request.getLocalAddr());//获取WEB服务器的IP地址
        map.put("getLocalName", "localName："+request.getLocalName());//获取WEB服务器的主机名
        
		return map;
	}
}
