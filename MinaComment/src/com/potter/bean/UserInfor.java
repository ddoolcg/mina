package com.potter.bean;

import com.j256.ormlite.field.DatabaseField;

public class UserInfor {
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(canBeNull = false, unique = true)
	private String username;
	@DatabaseField(canBeNull = false)
	private String password;
	@DatabaseField(unique = true)
	private String token;
	@DatabaseField
	private long lastLoginTime;
	@DatabaseField(foreign = true)
	private PlayerInfo playerInfo;
	private String passwordOld;

	public UserInfor() {
		super();
		lastLoginTime = 0;
		token = "";
		playerInfo = new PlayerInfo();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

}