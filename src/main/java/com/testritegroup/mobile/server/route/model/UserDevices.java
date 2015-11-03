package com.testritegroup.mobile.server.route.model;

public class UserDevices {
	private String userId;
	private String devicePlatform;
	private String uuid;
	private String regId;
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	String appId;
	String deviceModel;
	String osVersion;

	private String browserCodeName;
	private String browserVersion;
	private String browserLanguage;
	private String browserUserAgent;
	private String browserName;
	
	public String getBrowserCodeName() {
		return browserCodeName;
	}
	public void setBrowserCodeName(String browserCodeName) {
		this.browserCodeName = browserCodeName;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getBrowserLanguage() {
		return browserLanguage;
	}
	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
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
	public String getDevicePlatform() {
		return devicePlatform;
	}
	public void setDevicePlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}

}
