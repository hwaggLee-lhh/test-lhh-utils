package com.jsoup.model;

import java.util.Date;

public class NewsInfo extends HtmlModelBase{

	private String idStr;

	private String wztitle;
	
	private String wZType;
	
	private String gupiaocode;
	
	private Date fabiaodate;
	
	private byte[] content;
	
	private String laiyuan;
	
	private String author;
	
	private String netaddress;
	
	private String wzabstrace;
	
	private String wzimgurl;
	
	private String keyword;
	
	private String expandkeyword;
	
	private String showprivilege;
	
	private Integer showOrder;
	
	private String industryType;
	
	private String status;
	
	private Date dataCompiledDate;
	
	private String count;
	
	private String wZTypeDesc;
	
	private String statusDesc;
	
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getWztitle() {
		return wztitle;
	}

	public void setWztitle(String wztitle) {
		this.wztitle = wztitle;
	}

	public String getGupiaocode() {
		return gupiaocode;
	}

	public void setGupiaocode(String gupiaocode) {
		this.gupiaocode = gupiaocode;
	}

	public Date getFabiaodate() {
		return fabiaodate;
	}

	public void setFabiaodate(Date fabiaodate) {
		this.fabiaodate = fabiaodate;
	}


	public String getwZType() {
		return wZType;
	}

	public void setwZType(String wZType) {
		this.wZType = wZType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNetaddress() {
		return netaddress;
	}

	public void setNetaddress(String netaddress) {
		this.netaddress = netaddress;
	}

	public String getWzabstrace() {
		return wzabstrace;
	}

	public void setWzabstrace(String wzabstrace) {
		this.wzabstrace = wzabstrace;
	}

	public String getWzimgurl() {
		return wzimgurl;
	}

	public void setWzimgurl(String wzimgurl) {
		this.wzimgurl = wzimgurl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getExpandkeyword() {
		return expandkeyword;
	}

	public void setExpandkeyword(String expandkeyword) {
		this.expandkeyword = expandkeyword;
	}

	public String getShowprivilege() {
		return showprivilege;
	}

	public void setShowprivilege(String showprivilege) {
		this.showprivilege = showprivilege;
	}

	public String getwZTypeDesc() {
		return wZTypeDesc;
	}

	public void setwZTypeDesc(String wZTypeDesc) {
		this.wZTypeDesc = wZTypeDesc;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Date getDataCompiledDate() {
		return dataCompiledDate;
	}

	public void setDataCompiledDate(Date dataCompiledDate) {
		this.dataCompiledDate = dataCompiledDate;
	}
	
}
