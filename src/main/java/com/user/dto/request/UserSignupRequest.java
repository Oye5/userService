package com.user.dto.request;

public class UserSignupRequest {

	private String id;
	private String appType;

	private String email;

	private String username;

	private String password;

	private double lattitude;

	private double longitude;

	public String getId() {
		return id;
	}

	public String getAppType() {
		return appType;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public double getLattitude() {
		return lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
