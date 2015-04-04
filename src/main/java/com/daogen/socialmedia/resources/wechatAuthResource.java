package com.daogen.socialmedia.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.daogen.socialmedia.core.wechatAuth;
import com.daogen.socialmedia.core.wechatToken;
import com.daogen.socialmedia.database.Session;
import com.daogen.socialmedia.database.wechatDAO;
import com.daogen.socialmedia.views.blankView;
import com.daogen.socialmedia.views.simpleView;
import com.yammer.metrics.annotation.Timed;

@Path("/wechat")
@Produces(MediaType.TEXT_HTML)

public class wechatAuthResource {
    
    private final String token;
    private final wechatDAO dao;
	private final String appid;
	private final String appsecret;

	private static final String MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	
    public wechatAuthResource(String token, wechatDAO dao, String appid, String appsecret) {
        this.token = token;
        this.dao = dao;
        this.appid = appid;
        this.appsecret = appsecret;
    }

    @GET
    @Timed
    public simpleView auth(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
    		@QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr) {

    	
    	if(verify(signature,timestamp,nonce))
    	{
    		return new simpleView(new wechatAuth(echostr));
    	}
    	else
    	{
    		return new simpleView(new wechatAuth("invalid signature"));
    	}
    }
    
    private boolean verify(String sig, String time, String nonce)
    {
    	List<String> list = new ArrayList<String>();
    	list.add(token);
    	list.add(time);
    	list.add(nonce);
    	Collections.sort(list);
    	
    	String x = list.get(0)+list.get(1)+list.get(2);
    	String result="";
    	try {
			java.security.MessageDigest d = java.security.MessageDigest.getInstance("SHA-1");
			d.reset();
			d.update(x.getBytes()); 
					
			byte by[]=d.digest();
			for (int i=0; i < by.length;i++) {
				result += Integer.toString( ( by[i] & 0xff ), 16);
			} 
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(sig);
    	System.out.println(result);
    	if(sig.compareTo(result) == 0)
    		return true;
    	else
    	   	return true;
    }
       
    
    @POST
    @Timed
    public blankView receive(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
    		@QueryParam("nonce") String nonce, String xml){
    	System.out.println(xml);
    	if(!verify(signature,timestamp,nonce))
    	{
    		System.out.println("invalid sig");
    		return new blankView();
    	}
    	
    	Document doc = null;
		try {
			doc = loadXMLFromString(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	doc.getDocumentElement().normalize();
    	
    	if (doc.hasChildNodes()) {
    		 
    		Map<String,String> map = getNodes(doc.getChildNodes());
    		
    		String ToUserName = "";
    		String FromUserName= "";
    		String CreateTime= "";
    		String MsgType= "";   		
    		String Content= "";
    		String MsgId= "";
    		String PicUrl= "";
    		String MediaId= "";
    		String Format= "";
    		String ThumbMediaId= "";
    		String Location_X= "";
    		String Location_Y= "";
    		String Scale= "";
    		String Label= "";
    		String Title= "";
    		String Description= "";
    		String Url= "";
    		String Event= "";
    		String EventKey= "";
    		String Ticket= "";
    		String Latitude= "";
    		String Longitude= "";
    		String Precision= "";
    		String Recognition= "";
    		
    		
    		for(Map.Entry<String, String> entry : map.entrySet())
    		{
    			String key = entry.getKey();
    			switch(key){
    				case "ToUserName": ToUserName = entry.getValue(); break;
    				case "FromUserName": FromUserName = entry.getValue(); break;
    				case "CreateTime": CreateTime = entry.getValue(); break;
    				case "MsgType": MsgType = entry.getValue(); break;    		
    				case "Content": Content = entry.getValue(); break;
    				case "MsgId": MsgId = entry.getValue(); break;
    				case "PicUrl": PicUrl = entry.getValue(); break;
    				case "MediaId": MediaId = entry.getValue(); break;
    				case "Format": Format = entry.getValue(); break;
    				case "ThumbMediaId": ThumbMediaId = entry.getValue(); break;
    				case "Location_X": Location_X = entry.getValue(); break;
    				case "Location_Y": Location_Y = entry.getValue(); break;
    				case "Scale": Scale = entry.getValue(); break;
    				case "Label": Label = entry.getValue(); break;
    				case "Title": Title = entry.getValue(); break;
    				case "Description": Description = entry.getValue(); break;
    				case "Url": Url = entry.getValue(); break;
    				case "Event": Event = entry.getValue(); break;
    				case "EventKey": EventKey = entry.getValue(); break;
    				case "Ticket": Ticket = entry.getValue(); break;
    				case "Latitude": Latitude = entry.getValue(); break;
    				case "Longitude": Longitude = entry.getValue(); break;
    				case "Precision": Precision = entry.getValue(); break;
    				case "Recognition": Recognition = entry.getValue(); break;
        		}	
    		}
    		
    		Session s = dao.getOpenSession(FromUserName);
    		
    		
    		if(s == null)
    		{
    			System.out.println("new session");
    			dao.createSession(FromUserName);
        		s = dao.getOpenSession(FromUserName);
        		
        		(new queueHandler(dao, s)).start();
    		}
    		else
    			System.out.println("session : "+ s.getId());
    		
    		if(!MediaId.isEmpty())
    		{
    			String file = MEDIA_URL + wechatToken.getToken()+"&media_id="+ MediaId;
    			
    			
    			try{
    				URL url = URI.create(file).toURL();
    				URLConnection conn = url.openConnection();
    				String head = conn.getHeaderField("Content-Disposition");
    				System.out.println(head);
    				String filename = head.substring(head.indexOf("filename=")+10, head.length()-1);
    				System.out.println(filename);
    				InputStream in = conn.getInputStream();
    			
    				Calendar c = Calendar.getInstance();
    				int year = c.get(Calendar.YEAR);
    				int month = c.get(Calendar.MONTH);
    				int date = c.get(Calendar.DATE);
    				int hour = c.get(Calendar.HOUR_OF_DAY);
    				String path = year + File.separator + month + File.separator + date + File.separator + hour + File.separator + FromUserName + File.separator;
    				Files.createDirectories(Paths.get(path));
    				System.out.println(path+filename);
    		        Files.copy(in, Paths.get(path+filename));
    		        
    		        Url = path+filename;
    		    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		if(s.getSurvey() == -1)
    		{
    			String tmp = Content.trim();
    			if(tmp.length() > 0)
    			{
    				char n = tmp.charAt(0);
    				try{
    					int survey = Integer.parseInt(Character.toString(n));
    					
    					if(survey > 0 && survey <= 5)
    					{
    						dao.setSurvey(s.getId(), survey);
    					}
    				}
    				catch(NumberFormatException e)
    				{
    					e.printStackTrace();
    				}
    			}
    		}
    		
    		Timestamp time = new Timestamp(System.currentTimeMillis());
    		
    		dao.insertMessage(ToUserName, FromUserName, time.toString(), MsgType, Content, MsgId, PicUrl, MediaId, Format, ThumbMediaId, Location_X, Location_Y, Scale, Label, Title, Description, Url, Event, EventKey, Ticket, Latitude, Longitude, Precision, Recognition, s.getId());
    		
    		wechatToken.set(appid, appsecret);
    		

    	}
    
    	return new blankView();
    }
    
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    
    public static Map<String,String> getNodes(NodeList nodeList) {
    	Map<String,String> map = new HashMap<String,String>();
        for (int count = 0; count < nodeList.getLength(); count++) {
     
        	Node tempNode = nodeList.item(count);
     
        	// make sure it's element node.
	    	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
	         		
	    		if("xml".compareToIgnoreCase(tempNode.getNodeName()) != 0)
	    			map.put(tempNode.getNodeName(), tempNode.getTextContent());
	    
	    		if (tempNode.hasChildNodes()) {  
	    			// loop again if has child nodes
	    			map.putAll(getNodes(tempNode.getChildNodes()));
	    		}
	
	    	}
     
        }
        
        return map;
     
      }
    
    
    
}