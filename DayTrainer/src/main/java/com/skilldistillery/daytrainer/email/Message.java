package com.skilldistillery.daytrainer.email;

public class Message {

	public String name;
	
	public String email;
	
	public String message;
	
	public Message() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [name=" + name + ", email=" + email + ", message=" + message + "]";
	}
	
	
}
