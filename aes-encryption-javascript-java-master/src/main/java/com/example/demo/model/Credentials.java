package com.example.demo.model;

public class Credentials {

    private String userName;
    private String password;
    
    private String decryptedUserName;
    private String decryptedPassword;
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getDecryptedUserName() {
		return decryptedUserName;
	}

	public void setDecryptedUserName(String decryptedUserName) {
		this.decryptedUserName = decryptedUserName;
	}

	public String getDecryptedPassword() {
		return decryptedPassword;
	}

	public void setDecryptedPassword(String decryptedPassword) {
		this.decryptedPassword = decryptedPassword;
	}
    
    
}
