
package com.niconicocalendar;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	private int userId;
	@NotNull
	@Size(min = 1, max = 10)
	private String userName;
	
	public User() {}
	
	public User(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}