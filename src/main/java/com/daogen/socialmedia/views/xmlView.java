package com.daogen.socialmedia.views;

import com.yammer.dropwizard.views.View;

public class xmlView extends View{

	String xml;
	
	public xmlView(String xml)
	{
		super("xml.ftl");
		this.xml = xml;
	}
	
	public String getXml()
	{
		return xml;
	}
}
