package com.user.dto.request;

public class FBLoginRequest {
	private String gcmId;
	private String fbAuthToken;
	private double lattitude;
	private double longitude;

	public String getGcmId() {
		return gcmId;
	}

	public String getFbAuthToken() {
		return fbAuthToken;
	}

	public double getLattitude() {
		return lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setFbAuthToken(String fbAuthToken) {
		this.fbAuthToken = fbAuthToken;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
