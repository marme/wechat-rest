package com.daogen.socialmedia;

import java.sql.Timestamp;

import com.daogen.socialmedia.database.Session;

public class SessionStub extends Session {

	public SessionStub(int id, Timestamp begin, Timestamp end, String customer,
			String agent, int agentid, int survey) {
		super(id, begin, end, customer, agent, agentid, survey);
		// TODO Auto-generated constructor stub
	}

}
