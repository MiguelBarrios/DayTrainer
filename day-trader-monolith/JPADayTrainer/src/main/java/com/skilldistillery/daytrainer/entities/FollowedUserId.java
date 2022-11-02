package com.skilldistillery.daytrainer.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FollowedUserId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "followed_user_id")
	private int followedUserId;

	public FollowedUserId() {
		super();

	}

	public FollowedUserId(int userId, int followedUserId) {
		super();
		this.userId = userId;
		this.followedUserId = followedUserId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFollowedUserId() {
		return followedUserId;
	}

	public void setFollowedUserId(int followedUserId) {
		this.followedUserId = followedUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(followedUserId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FollowedUserId other = (FollowedUserId) obj;
		return followedUserId == other.followedUserId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "FollowedUserId [userId=" + userId + ", followedUserId=" + followedUserId + "]";
	}
	
	

}
