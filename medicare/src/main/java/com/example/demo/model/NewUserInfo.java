package com.example.demo.model;

public class NewUserInfo {
	private String username;
	private String password;
	private int id;

	public NewUserInfo() {
	}
	
	// Using for JPA/Hibernate Query.
    public NewUserInfo(String username, String password, //
            int id) {
        this.username = username;
        this.password = password;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}