package com.crawler.neturl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.UtilJSON;
import com.utils.UtilURL;


public class StartHtmlReportTest {

	private static final Logger log = LoggerFactory.getLogger(StartHtmlReportTest.class);
/*	private static final String urlPath = "http://www.neeq.cc/controller/GetDisclosureannouncementPage";
	private static final String type = "";// 公司公共【-挂牌公司=1，公司公共-两网退市=2】，业务周知=【3，4】，监管信息公开=6，审核信息公开=7，交易公开信息分两级(public_info《-等会儿处理)
	private static final String company_cd = "";// 证券代码，全部默认为""
	private static final String key = "";// 关键字
	private static final String subType = "";// 公共类型，(默认0,
												// 1=临时公共，2=定期公共，3中介机构公共，14=持续信息披露，15=首次信息披露）
	private static final String startDate = "2004-07-02";// 开始日期，
	private static final String endDate = "";// 结束日期，
	private static final String queryParams = "0";// 默认0，固定0，其他参数
	private static final String page = "";// 默认0，页未跳转页，分页数

	*/

	
	
	
	
	public static void main(String[] args) throws Exception {
		startGSGGYearReportUpload("2015-01-01");
	}
	

	
	
	
	
	
	
	
	
	
	

	/**
	 * 开始公司公共业务
	 */
	public static void startGSGGYearReportUpload(String startDate) throws Exception {
		long timeStart = System.currentTimeMillis();
		final String code = "utf-8";
		final String urlPath = "http://www.neeq.cc/controller/GetDisclosureannouncementPage";
		final String reqType = "1";// 公司公共【挂牌公司=1，两网退市=2】
		//final String startDate = StartNeeq.startDate;
		final String endDate="";
		final String queryParams = "0";
		final String suType = "2";//定期公告=2，0查询所有
		final String strKey = "2015年半年报,2015年半年度报告,2015年 半年报";
		final String companyCode="";
		
		int pageSize = 1;
		String[] split=strKey.split(",");
		for (String string : split) {
			while(true){
				try {
					List<NeeqResponsePage> list = requets(urlPath, reqType, startDate,endDate,
							queryParams,string,suType,companyCode, code,pageSize);
					if( list == null || list.size() == 0 ) break;
					//final String type = "01";
					//DBReportServiceImpl.saveNewsNotice(list, type);
					uploadFile(list);
				} catch (Exception e) {
					log.info("异常:"+e.getMessage());
				}finally{
					pageSize++;
				}
			}
		}
		// 请求数据
		log.info("耗时:" + (System.currentTimeMillis() - timeStart));
	}
	
	private static void uploadFile(List<NeeqResponsePage> list) throws Exception{
		String path = "C://Users//huage//Desktop//file";
		if( list == null || list.size()==0) return;
		 for (NeeqResponsePage page : list) {
			 List<NeeqResponse> response = page.getLists();
	        	if( response == null )continue;
	        	for (NeeqResponse n : response) {
	        		uploadFile(page.getFilePath()+n.getFilePath(),path, n.getTitleFull(),".pdf");
				}
			}
	}

	private static void uploadFile(String urlPath,String path,String titleFull,String fileSX) throws Exception{
		titleFull = titleFull.replace(":", "_");
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置超时间为3秒
		conn.setConnectTimeout(3 * 1000);
		// 防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		
		buffer = bos.toByteArray();
		// 文件保存位置
		File saveDir = new File(path);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File file = new File(saveDir + File.separator + titleFull+fileSX);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(buffer);
		if (fos != null) {
			fos.close();
		}
		if (inputStream != null) {
			inputStream.close();
		}

		System.out.println("下载已完成:"+urlPath);
	}

	/**
	 * 请求
	 * @param urlPath：url
	 * @param reqType：类型
	 * @param startDate：开始日期
	 * @param endDate：结束日期
	 * @param queryParams：其他参数
	 * @param strKey：关键字
	 * @param suType：子类型
	 * @param code：编码
	 * @return
	 * @throws Exception
	 */
	private static List<NeeqResponsePage> requets(String urlPath,
			String reqType, String startDate,String endDate, String queryParams,
			String strKey,String suType,String companyCode, String code,int pageSize )
			throws Exception {
		// 请求参数
		NeeqRequest request = new NeeqRequest();
		request.setUrlPath(urlPath);
		request.setType(reqType);
		request.setStartDate(startDate);
		request.setEndDate(endDate);
		request.setQueryParams(queryParams);
		request.setCompany_cd(companyCode);
		request.setKey(strKey);
		request.setSubType(suType);
		// System.out.println("start "+page+" page");
		request.setPage(pageSize);
		String result = UtilURL.fetch_url(request.toStringUrl(), code);
		JSONObject object = UtilJSON.getJSONObject(result);
		if (object != null){
			String disclosureInfos = object.getString("disclosureInfos");
			if (disclosureInfos == null
					|| "null".equalsIgnoreCase(disclosureInfos)) {
				return null;
			} else {
				JSONArray array = UtilJSON.getJSONArray(disclosureInfos);
				if( array != null ){
					NeeqResponsePage needPage = new NeeqResponsePage();
					needPage.setCompany_cd(object.getString("company_cd"));
					needPage.setCompany_name(object.getString("company_name"));
					needPage.setEndDate(object.getString("endDate"));
					needPage.setFilePath(object.getString("filePath"));
					needPage.setFiles(object.getString("files"));
					needPage.setKey(object.getString("key"));
					needPage.setPage(object.getInt("page"));
					needPage.setPagesize(object.getInt("pagesize"));
					
					NeeqResponsePageInfo info = new NeeqResponsePageInfo();
					String pagingInfo = object.getString("pagingInfo");
					if (pagingInfo != null) {
						JSONObject ob = UtilJSON.getJSONObject(pagingInfo);
						info.setCurrentPage(ob.getInt("currentPage"));
						info.setPageSize(ob.getInt("pageSize"));
						info.setTotalCount(ob.getInt("totalCount"));
						needPage.setPagingInfo(info);
					}
					needPage.setQueryParams(object.getString("queryParams"));
					needPage.setRemoteData(object.getString("remoteData"));
					needPage.setStartDate(object.getString("startDate"));
					needPage.setSubType(object.getString("subType"));
					needPage.setType(object.getInt("type"));
					needPage.setYear(object.getString("year"));
					List<NeeqResponse> response = getNeedFilePath(array);
					needPage.setLists(response);
					
					List<NeeqResponsePage> list = new ArrayList<NeeqResponsePage>();
					list.add(needPage);
					log.info("------------>总共：" + list.size() + "  条数据；总共" + pageSize
							+ "  页");
					return list;
				}
			}
		}
		return null;
	}

	private static List<NeeqResponse> getNeedFilePath(JSONArray array) {
		List<NeeqResponse> listFilePath = new ArrayList<NeeqResponse>();
		NeeqResponse need = null;
		if (array != null)
			for (Object object : array) {
				JSONObject ob = JSONObject.fromObject(object);
				// System.out.println(ob);
				need = new NeeqResponse();
				need.setBpmFilePath(ob.getString("BPMFilePath"));
				need.setMd5sum(ob.getString("MD5Sum"));
				need.setCode(ob.getString("code"));
				need.setCompanyCode(ob.getString("companyCode"));
				need.setCompanyName(ob.getString("companyName"));
				need.setDisclosureYear(ob.getInt("disclosureYear"));
				need.setFileExt(ob.getString("fileExt"));
				need.setFileExtText(ob.getString("fileExtText"));
				need.setFilePath(ob.getString("filePath"));
				need.setNewThreeCompany(ob.getBoolean("isNewThreeCompany"));
				need.setIsNewThreeCompanyII(ob.getInt("isNewThreeCompanyII"));
				need.setLogId(ob.getString("logId"));
				need.setPublishDate(ob.getString("publishDate"));
				need.setPublishDateString(ob.getString("publishDateString"));
				need.setPublishOrg(ob.getString("publishOrg"));
				need.setRefCode(ob.getString("refCode"));
				need.setReservedField1(ob.getString("reservedField1"));
				need.setReservedField2(ob.getString("reservedField2"));
				need.setReservedField3(ob.getString("reservedField3"));
				need.setReservedField4(ob.getString("reservedField4"));
				need.setState(ob.getInt("state"));
				need.setSubType(ob.getString("subType"));
				need.setTitle(ob.getString("title"));
				need.setTitleFull(ob.getString("titleFull"));
				need.setTitleSuffix(ob.getString("titleSuffix"));
				need.setType(ob.getString("type"));
				need.setUploadDateTime(ob.getString("uploadDateTime"));
				need.setUploadTimeString(ob.getString("uploadTimeString"));
				listFilePath.add(need);
			}
		return listFilePath;
	}
}
