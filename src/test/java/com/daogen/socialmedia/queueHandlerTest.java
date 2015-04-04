package com.daogen.socialmedia;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import com.daogen.socialmedia.database.Agent;
import com.daogen.socialmedia.database.Session;
import com.daogen.socialmedia.database.wechatDAO;
import com.daogen.socialmedia.resources.queueHandler;

public class queueHandlerTest {

	@Test
	public void test() {
		wechatDaoStub dao = new wechatDaoStub();
		
		Session s = dao.getOpenSession("test");
		dao.agents.add(new Agent(1, "agent", true, true, 0));
		queueHandler qh = new queueHandler(dao, s);
		
		
		try {
			qh.start();
			qh.join();
			Assert.assertEquals(true,dao.sessionOpen);
			
			qh = new queueHandler(dao, s);
			dao.changeAvailable(1, 0);
			qh.start();
			qh.join();
			Assert.assertEquals(false,dao.sessionOpen);
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("queueHandler interrupted");
		}
		
		
	}

}
