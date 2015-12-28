package com.pega.familydoctor.data.model;

public class User {
	private int userID;
	private String userName;
	private String userPhone;

	public User(String userName, String userPhone, Boolean userRemind) {
		super();
		this.userName = userName;
		this.userPhone = userPhone;
		this.userRemind = userRemind;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Boolean getUserRemind() {
		return userRemind;
	}

	public void setUserRemind(Boolean userRemind) {
		this.userRemind = userRemind;
	}

	private Boolean userRemind;
}
