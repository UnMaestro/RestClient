package com.odfin.domain;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable{

	private String username;
	private String password;
	private Status status;
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = hashPassword(password);
		status = Status.OFFLINE;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean checkLogIn(String inPassword) {
		return hashPassword(inPassword).equals(password);
	}
	
	public String hashPassword(String rawPassword) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(rawPassword.getBytes());
			StringBuilder stringBuilder = new StringBuilder();
			
			for(byte b : hash){
				stringBuilder.append(String.format("%02x", b));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}
