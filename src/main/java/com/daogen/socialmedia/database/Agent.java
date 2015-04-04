package com.daogen.socialmedia.database;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Agent {
	private int id;
	private String login_name;
	private boolean logged_in;
	private boolean available;
	private int rating;

	public Agent(int id, String login_name, boolean logged_in,
			boolean available, int rating) {
		this.id = id;
		this.login_name = login_name;
		this.logged_in = logged_in;
		this.available = available;
		this.rating = rating;
	}
	
	@JsonProperty
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JsonProperty
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	@JsonProperty
	public boolean isLogged_in() {
		return logged_in;
	}
	public void setLogged_in(boolean logged_in) {
		this.logged_in = logged_in;
	}
	@JsonProperty
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	@JsonProperty
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
}
