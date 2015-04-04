package com.daogen.socialmedia;

import java.util.ArrayList;
import java.util.List;

import com.daogen.socialmedia.database.Agent;
import com.daogen.socialmedia.database.Message;
import com.daogen.socialmedia.database.Session;
import com.daogen.socialmedia.database.wechatDAO;

public class wechatDaoStub implements wechatDAO{

	public int id;
	public int agentid;
	public boolean sessionOpen;
	public List<Agent> agents = new ArrayList<Agent>();
	
	@Override
	public void insertMessage(String ToUserName, String FromUserName,
			String CreateTime, String MsgType, String Content, String MsgId,
			String PicUrl, String MediaId, String Format, String ThumbMediaId,
			String Location_X, String Location_Y, String Scale, String Label,
			String Title, String Description, String Url, String Event,
			String EventKey, String Ticket, String Latitude, String Longitude,
			String Precision, String Recognition, int sessionid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Message> getMessagesBySessionId(int sessionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMessagesBycustomer(String customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createSession(String customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignAgent(int id, int agentid) {
		this.id = id;
		this.agentid = agentid;
		
	}

	@Override
	public void closeSession(int id) {
		if(id == 1)
			this.sessionOpen = false;
		
	}

	@Override
	public void acceptSession(int id, String agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSurvey(int id, int survey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Session sessionFindById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session sessionFindByAgentId(int agentid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session sessionFindByCustomer(String customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getOpenSession(String customer) {
		if(customer.equals("test"))
		{
			this.sessionOpen = true;
			return new SessionStub(1, null, null, customer, customer, 0, 0);	
		}
		
		return null;
	}

	@Override
	public int getOpenCount(int agentid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Session getLastSession(String customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Session> getUnassignedSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Session> getAssaignedSession(int agentid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent findByLogin(String login_name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<Agent> getAvailable() {
		
		return agents;
	}

	@Override
	public void changeLoggedIn(int id, int logged_in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAvailable(int id, int available) {
		agents.clear();
		
	}

}
