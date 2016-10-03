package com.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3366441338924521575L;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "app_type")
	private String appType;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "creation_date")
	private Date creation_date;

	@Column(name = "last_login")
	private Date last_login;

	@Column(name = "active")
	private boolean active;

	@Column(name = "unread_notifications")
	private int unreadNotifications;

	@Column(name = "unread_messages")
	private int unreadMessages;

	public String getUserId() {
		return userId;
	}

	public String getAppType() {
		return appType;
	}

	public String getUserName() {
		return userName;
	}

	@JsonIgnore
	@JsonProperty(value = "password")
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public Date getLast_login() {
		return last_login;
	}

	public boolean isActive() {
		return active;
	}

	@JsonIgnore
	@JsonProperty(value = "unreadNotifications")
	public int getUnreadNotifications() {
		return unreadNotifications;
	}

	@JsonIgnore
	@JsonProperty(value = "unreadMessages")
	public int getUnreadMessages() {
		return unreadMessages;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setUnreadNotifications(int unreadNotifications) {
		this.unreadNotifications = unreadNotifications;
	}

	public void setUnreadMessages(int unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

}
