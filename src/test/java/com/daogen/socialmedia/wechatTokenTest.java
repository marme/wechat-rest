package com.daogen.socialmedia;

import org.junit.*;

import com.daogen.socialmedia.core.wechatToken;

public class wechatTokenTest{
	
	SMConfiguration conf;
	
	@Before
	public void init()
	{
		conf = new SMConfiguration();
	}
	
	@Test
	public void testToken()
	{
		String token = wechatToken.getToken();
		
		Assert.assertEquals(false, token.isEmpty());
	}
}
