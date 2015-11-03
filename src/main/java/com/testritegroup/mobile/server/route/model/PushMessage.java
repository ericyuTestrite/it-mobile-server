package com.testritegroup.mobile.server.route.model;

import java.util.Date;

public class PushMessage {
	private Date sendDate;
	
	private String receiverUserId;
	public String getReceiverUserId() {
		return receiverUserId;
	}
	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	private String appId;
	private String message;
	private String title;//for Android
	private String platformType;
	private String category;
	private String from;
	
	// message:  (category) Title: message
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getComposedMessage(){
		StringBuilder str = new StringBuilder();
		if(category!=null && !"".equals(category)){
			str.append("(").append(getCategory()).append(")");
		}
		if(title!=null && !"".equals(title)){
			str.append(getTitle()).append(": ");
		}
		
		str.append(getMessage());
		
		return str.toString();
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
