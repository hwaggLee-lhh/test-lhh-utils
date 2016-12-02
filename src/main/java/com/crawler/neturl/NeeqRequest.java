package com.crawler.neturl;

public class NeeqRequest {

	private String urlPath="";//请求url
	private String type="";//类型
	private String company_cd="";//股票代码
	private String key="";//关键字
	private String subType="";//子类型
	private String startDate="";//开始时间
	private String endDate="";//结束时间
	private String queryParams="0";//其他参数
	private int page=1;//分页

	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public String toStringUrl(){
		StringBuilder sb = new StringBuilder();
		sb.append(urlPath);
		sb.append("?");
		sb.append("type").append("=").append(type);
		sb.append("&");
		sb.append("key").append("=").append(key);
		sb.append("&");
		sb.append("company_cd").append("=").append(company_cd);
		sb.append("&");
		sb.append("subType").append("=").append(subType);
		sb.append("&");
		sb.append("startDate").append("=").append(startDate);
		sb.append("&");
		sb.append("endDate").append("=").append(endDate);
		sb.append("&");
		sb.append("queryParams").append("=").append(queryParams);
		sb.append("&");
		sb.append("page").append("=").append(page);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new NeeqRequest().toStringUrl());
	}
}
