package com.testritegroup.mobile.server.route.model;

public class UserImage {	
	private String id;
	
	
	
	private String userId;
	
	private String mimeType;
	private String imageFile;
	private String appId;
	private java.util.Date createDate;
	private java.util.Date modifyDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(java.util.Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getImageFile() {
		return imageFile;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	@Override
	public String toString() {
		return "UserImage [id=" + id + ", userId=" + userId + ", mimeType=" + mimeType + ", imageFile=" + imageFile
				+ ", appId=" + appId + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}
	
	

	
}
