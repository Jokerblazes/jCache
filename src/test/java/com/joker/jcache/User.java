package com.joker.jcache;

import com.joker.jcache.annoation.Key;

public class User {
	
	@Key(value="userid")
	private int userid;
	private String password;
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password + "]";
	}
	
}
