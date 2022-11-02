package com.skilldistillery.daytrainer.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "followed_user")
public class FollowedUser {

	@EmbeddedId
	private FollowedUserId id;

	public FollowedUser() {
		super();

	}

	public FollowedUser(FollowedUserId id) {
		super();
		this.id = id;
	}

	public FollowedUserId getId() {
		return id;
	}

	public void setId(FollowedUserId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FollowedUser [id=" + id + "]";
	}

	
}
