package com.skilldistillery.daytrainer.entities;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String password;

	private boolean enabled;

	private String role;

	private String email;

	private String biography;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "profile_picture")
	private String profilePicture;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Account account;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Trade> trades;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "sender")
	private List<Message> sentMessages;

	@JsonIgnore
	@OneToMany(mappedBy = "recipient")
	private List<Message> recMessages;


	public User() {

	}

	public List<Message> getSentMessages() {
		return sentMessages;
	}
	
	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<Message> getRecMessages() {
		return recMessages;
	}

	public void setRecMessages(List<Message> recMessages) {
		this.recMessages = recMessages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", enabled=" + enabled + ", role=" + role + ", email=" + email
				+ ", biography=" + biography + ", createdAt=" + createdAt + ", profilePicture=" + profilePicture
				+ ", account=" + account + "]";
	}
	
	

//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("User [id=");
//		builder.append(id);
//		builder.append(", username=");
//		builder.append(username);
//		builder.append(", firstName=");
//		builder.append(firstName);
//		builder.append(", lastName=");
//		builder.append(lastName);
//		builder.append(", password=");
//		builder.append(password);
//		builder.append(", enabled=");
//		builder.append(enabled);
//		builder.append(", role=");
//		builder.append(role);
//		builder.append(", email=");
//		builder.append(email);
//		builder.append(", biography=");
//		builder.append(biography);
//		builder.append(", createdAt=");
//		builder.append(createdAt);
//		builder.append(", profilePicture=");
//		builder.append(profilePicture);
//		builder.append(", account=");
//		builder.append(account);
//		builder.append(", trades=");
//		builder.append(trades);
//		builder.append(", comments=");
//		builder.append(comments);
//		builder.append(", getId()=");
//		builder.append(getId());
//		builder.append(", getUsername()=");
//		builder.append(getUsername());
//		builder.append(", getFirstName()=");
//		builder.append(getFirstName());
//		builder.append(", getLastName()=");
//		builder.append(getLastName());
//		builder.append(", getPassword()=");
//		builder.append(getPassword());
//		builder.append(", isEnabled()=");
//		builder.append(isEnabled());
//		builder.append(", getRole()=");
//		builder.append(getRole());
//		builder.append(", getEmail()=");
//		builder.append(getEmail());
//		builder.append(", getBiography()=");
//		builder.append(getBiography());
//		builder.append(", getCreatedAt()=");
//		builder.append(getCreatedAt());
//		builder.append(", getProfilePicture()=");
//		builder.append(getProfilePicture());
//		builder.append(", getAccount()=");
//		builder.append(getAccount());
//		builder.append(", getTrades()=");
//		builder.append(getTrades());
//		builder.append(", getComments()=");
//		builder.append(getComments());
//		builder.append(", hashCode()=");
//		builder.append(hashCode());
//		builder.append(", getClass()=");
//		builder.append(getClass());
//		builder.append(", toString()=");
//		builder.append(super.toString());
//		builder.append("]");
//		return builder.toString();
//	}

}
