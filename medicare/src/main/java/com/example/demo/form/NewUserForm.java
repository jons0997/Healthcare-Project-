package com.example.demo.form;

public class NewUserForm {
	private String username;
	private String password;
	private int id;
	private boolean valid;

	public NewUserForm() {
	}
	
	// Using for JPA/Hibernate Query.
    public NewUserForm(String username, String password, //
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
	
	public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}