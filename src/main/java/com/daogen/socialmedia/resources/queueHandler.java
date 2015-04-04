package com.daogen.socialmedia.resources;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.daogen.socialmedia.core.wechatToken;
import com.daogen.socialmedia.database.Agent;
import com.daogen.socialmedia.database.Session;
import com.daogen.socialmedia.database.wechatDAO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class queueHandler extends Thread {
	
	private final wechatDAO dao;
	private final Session session;
	
	public static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	public queueHandler(wechatDAO dao, Session session) {
		super();
		this.dao = dao;
		this.session = session;
	}


	@Override
	public void run() {
		List<Agent> agents = dao.getAvailable();
		Session last = dao.getLastSession(session.getCustomer());
		
		String agent = null;
		int id = 0;
		int rating = 0;
		
		System.out.println("agents : " + agents);
		
		int lowest = -1;
		
		for(Agent a : agents)
		{
			//no agents checked yet
			if(agent == null)
			{
				
				agent = a.getLogin_name();
				id = a.getId();
				rating = a.getRating();
				lowest = dao.getOpenCount(id);			
				
				//customer has talked to someone before
				if(last != null && id == last.getAgentid())
					break;
				
			}
			else
			{
				if( last != null && a.getId() == last.getAgentid())
				{
					agent = a.getLogin_name();
					id = a.getId();
					break;
				}
				
				int count = dao.getOpenCount(a.getId());
				
				if(count < lowest)
				{
					agent = a.getLogin_name();
					id = a.getId();
					rating = a.getRating();
					lowest = count;
				}
			}
		}
		System.out.println("assigning : "+ session.getId() + " : " + agent + " : " + id);
		if( id != 0)
			dao.assignAgent(session.getId(), id);
		else
		{
			String post = MESSAGE_URL + wechatToken.getToken();
			
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource webResource = client.resource(UriBuilder.fromUri(post).build());
			String content = "Sorry there are no available agents. Please contact us again later";
			String text = "{\"touser\":\""+session.getCustomer() +"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+ content +"\"}}";
			
			System.out.println(text);
			
			ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, text);
			System.out.println("Response " + response.getEntity(String.class));
			dao.closeSession(session.getId());
		}
	}

}
