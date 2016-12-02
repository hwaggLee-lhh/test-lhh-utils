package com.crawler.neturl;

import java.util.List;

public class NeeqResponsePage {
	
	private String company_cd;
	private String company_name;
	private List<NeeqResponse> lists;
	private String filePath;
	private String endDate;
	private String files;
	private String key;
	private int page;
	private int pagesize;
	private NeeqResponsePageInfo pagingInfo;
	private String queryParams;
	private String remoteData;
	private String startDate;
	private String subType;
	private int type;
	private String year;
	public String getCompany_cd() {
		return company_cd;
	}
	public void setCompany_cd(String company_cd) {
		this.company_cd = company_cd;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public List<NeeqResponse> getLists() {
		return lists;
	}
	public void setLists(List<NeeqResponse> lists) {
		this.lists = lists;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public NeeqResponsePageInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(NeeqResponsePageInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
	public String getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}
	public String getRemoteData() {
		return remoteData;
	}
	public void setRemoteData(String remoteData) {
		this.remoteData = remoteData;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
