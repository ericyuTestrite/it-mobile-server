package com.testritegroup.mobile.server.route.model;

public class UserAdProfile {
	 private String userId;
	 public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String displayName;
	 private String email;
	 private String department;
	 private String company;
	 private String co;
	 private String tel;
	 private String title;
	 
	 public String getUserId() {
		return userId;
	 }
	 
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
