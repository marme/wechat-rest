package com.daogen.socialmedia.core;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class wechatToken {
	
	public static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

	private static String appid;
	private static String appsecret;
	
	private static String token;
	private static long expires;
	
	public static void set(String appid, String appsecret)
	{
		if(appid == wechatToken.appid && appsecret == wechatToken.appsecret)
			return;
		
		wechatToken.appid = appid;
		wechatToken.appsecret = appsecret;
		wechatToken.retrieveToken();
	}
	
	public static String getToken()
	{
		long time = expires - System.currentTimeMillis();
				
		if(token == null || time <= 0)
		{
			retrieveToken();			
		}
				
		return token;
	}
	
	private static void retrieveToken()
	{
		String url = URL + "&appid=" + appid + "&secret=" + appsecret;
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri(url).build());

		String json =service.accept(MediaType.APPLICATION_JSON).get(String.class);

		
		ObjectMapper mapper = new ObjectMapper();
		 

	 
		token t;
		try {
			t = mapper.readValue(json, token.class);
			
			token=t.getAccess_token();
			expires = t.getExpires_in()*1000 + System.currentTimeMillis();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(json);
		}
		
	}
	
}
