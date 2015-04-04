package com.daogen.socialmedia;

import org.skife.jdbi.v2.DBI;

import com.daogen.socialmedia.database.wechatDAO;
import com.daogen.socialmedia.health.wechatHealthCheck;
import com.daogen.socialmedia.resources.RNResource;
import com.daogen.socialmedia.resources.wechatAuthResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.views.ViewBundle;

public class SMService extends Service<SMConfiguration> {

    public static void main(String[] args) throws Exception {
        new SMService().run(args);
    }
 
    @Override
    public void initialize(Bootstrap<SMConfiguration> bootstrap) {
        bootstrap.setName("SMService");
        bootstrap.addBundle(new ViewBundle());
    }
 
    @Override
    public void run(SMConfiguration configuration, Environment environment) throws Exception {
    	  	final String template = configuration.getTemplate();
    	    final String defaultName = configuration.getDefaultName();
    	    final String Token = configuration.getToken();
    	    final String appid = configuration.getAppid();
    	    final String appsecret = configuration.getAppsecret();
    	    final String survey = "Please enter survey feedback: score from 1 to 5";

    	    final DBIFactory factory = new DBIFactory();
    	    final DBI jdbi = factory.build(environment, configuration.getDatabaseConfiguration(), "oracle");
    	    
    	   final wechatDAO dao = jdbi.onDemand(wechatDAO.class);	   
    	   
    	    
    	    environment.addResource(new wechatAuthResource(Token, dao,appid,appsecret));
    	    environment.addResource(new RNResource(dao,appid,appsecret,survey));
    	    environment.addHealthCheck(new wechatHealthCheck(template));
    }

}
