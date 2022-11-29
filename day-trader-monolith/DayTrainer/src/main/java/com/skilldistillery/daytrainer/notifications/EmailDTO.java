package com.skilldistillery.daytrainer.notifications;

import lombok.Data;

public class EmailDTO {
	public String name;
	public String emailAddress;
	public String message;
	
	public EmailDTO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "personal-site\nname: " + name + "\nemail: " + emailAddress + "\n\n" + message;
	}
	
	
}
