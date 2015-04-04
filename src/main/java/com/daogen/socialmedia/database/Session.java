package com.daogen.socialmedia.database;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Session {

	private int id;
	private Timestamp begin;
	private Timestamp end;
	private String customer;
	private String agent;
	private int agentid;
	private int survey;
	
	public Session(int id, Timestamp begin, Timestamp end, String customer,
			String agent, int agentid, int survey) {
		this.id = id;
		this.begin = begin;
		this.end = end;
		this.customer = customer;
		this.agent = agent;
		this.setAgentid(agentid);
		this.survey = survey;
	}
	
	@JsonProperty
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty
	public Timestamp getBegin() {
		return begin;
	}
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}
	
	@JsonProperty
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	
	@JsonProperty
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@JsonProperty
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	@JsonProperty
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	@JsonProperty
	public int getSurvey() {
		return survey;
	}

	public void setSurvey(int survey) {
		this.survey = survey;
	}

}
