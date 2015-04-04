package com.daogen.socialmedia.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.daogen.socialmedia.core.wechatToken;
import com.daogen.socialmedia.database.Agent;
import com.daogen.socialmedia.database.MediaId;
import com.daogen.socialmedia.database.Message;
import com.daogen.socialmedia.database.Session;
import com.daogen.socialmedia.database.wechatDAO;
import com.daogen.socialmedia.views.blankView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.yammer.metrics.annotation.Timed;



@Path("/rightnow")
public class RNResource {

	private final wechatDAO dao;
	private final String appid;
	private final String appsecret;
	private final String survey;
	
	private static final String URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	
	public RNResource(wechatDAO dao,String appid, String appsecret, String survey) {
		this.dao = dao;
		this.appid = appid;
		this.appsecret = appsecret;
		this.survey = survey;
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/login")
	public Agent login(@QueryParam("login") String login)
	{
		
		Agent agent = dao.findByLogin(login);
		
		if(agent == null)
			throw new NotFoundException("login:"+login+" not found");
		
		dao.changeLoggedIn(agent.getId(), 1);
		dao.changeAvailable(agent.getId(), 0);
		
		return agent;
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Agent logout(@QueryParam("login") String login)
	{
		Agent agent = dao.findByLogin(login);
		
		if(agent == null)
			throw new NotFoundException("login:"+login+" not found");
		
		dao.changeLoggedIn(agent.getId(), 0);
		dao.changeAvailable(agent.getId(), 0);
		
		return agent;
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/available")
	public Agent logout(@QueryParam("login") String login, @QueryParam("available") int available)
	{
		Agent agent = dao.findByLogin(login);
		
		dao.changeAvailable(agent.getId(), available);
		
		return agent;
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/queue")
	public List<Session> queue(@QueryParam("login") int login)
	{		
		List<Session> sessions = dao.getAssaignedSession(login);
			
		return sessions;	
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/accept")
	public List<Message> acceptSession(@QueryParam("login") int login, @QueryParam("session") int session)
	{
		Agent agent = dao.findById(login);
		
		dao.acceptSession(session, agent.getLogin_name());
		
		
		return dao.getMessagesBySessionId(session);
	}
	
	@GET
    @Timed
	@Path("/reject")
	public blankView rejectSession(@QueryParam("session") int session)
	{
		Session s = dao.sessionFindById(session);
	
		(new queueHandler(dao, s)).start();
		
		return new blankView();
	}
	
	@GET
    @Timed
	@Path("/close")
	public blankView closeSession(@QueryParam("session") int session)
	{		
		dao.closeSession(session);
		
		
		return new blankView();
	}
	
	@GET
    @Timed
    @Produces(value = MediaType.APPLICATION_JSON)
	@Path("/retrieve")
	public List<Message> retrieveSession( @QueryParam("session") int session)
	{
		Session s = dao.sessionFindById(session);
		
		return dao.getMessagesBycustomer(s.getCustomer());
		
		//return dao.getMessagesBySessionId(session);
	}
	
	@POST
    @Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/send")
	public blankView sendSession( @QueryParam("session") int session, String m)
	{
		ObjectMapper mapper = new ObjectMapper();
		Message message = null;
		try {
			message = mapper.readValue(m, Message.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Session s = dao.sessionFindById(session);
		
		dao.insertMessage(message.getToUserName(), 
				s.getAgent(), 
				timestamp.toString(), 
				"text", 
				message.getContent(), 
				message.getMsgId(), 
				message.getPicUrl(), 
				message.getMediaId(), 
				message.getFormat(), 
				message.getThumbMediaId(), 
				message.getLocation_X(), 
				message.getLocation_Y(), 
				message.getScale(), 
				message.getLabel(), 
				message.getTitle(),
				message.getDescription(), 
				message.getUrl(), 
				message.getEvent(), 
				message.getEventKey(), 
				message.getTicket(), 
				message.getLatitude(), 
				message.getLongitude(), 
				message.getPrecision(), 
				message.getRecognition(), 
				session);
		
		wechatToken.set(appid, appsecret);
		
		
		String post = URL + wechatToken.getToken();
						
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(UriBuilder.fromUri(post).build());
				
		String text = "{\"touser\":\""+message.getToUserName() +"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+ message.getContent() +"\"}}";
		
		System.out.println(text);
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, text);
		System.out.println("Response " + response.getEntity(String.class));
		
		return new blankView();
	}
	
	@POST
    @Timed
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/sendmedia")
	public blankView sendMediaSession(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("session") int session,
			@FormDataParam("msgtype") String msgtype,
			@FormDataParam("touser") String touser) throws IOException
	{
		System.out.println("Start upload");
		System.out.println(session);
		System.out.println(msgtype);
		System.out.println(touser);
		
		
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.DATE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		String path = year + File.separator + month + File.separator + date + File.separator + hour + File.separator + touser + File.separator;
		Files.createDirectories(Paths.get(path));
		
		
		String uploadedFileLocation = path+fileDetail.getFileName();
		
		 
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
 
		String output = "File uploaded to : " + uploadedFileLocation;
		System.out.println(output);
	
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Session s = dao.sessionFindById(session);
		
		dao.insertMessage(touser, 
				s.getAgent(), 
				timestamp.toString(), 
				msgtype, 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"",
				"", 
				uploadedFileLocation, 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				session);
		
		System.out.println("send to wechat");
		wechatToken.set(appid, appsecret);
		String token = wechatToken.getToken();
		String res = uploadFile(token, uploadedFileLocation, msgtype);
		
		System.out.println(res);
		ObjectMapper mapper = new ObjectMapper();
		MediaId mediaid = null;
		try {
			mediaid = mapper.readValue(res, MediaId.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mediaid.getErrcode() != null && !mediaid.getErrcode().isEmpty())
		{
			System.out.println("Error: " + mediaid.getErrcode() + " = " + mediaid.getErrmsg());
			throw new WebApplicationException(new IOException(mediaid.getErrmsg()));
		}
		
		
		

	
		String post = URL + token;
						
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(UriBuilder.fromUri(post).build());
				
		String text = "{\"touser\":\""+ touser +"\",\"msgtype\":\""+msgtype+"\",\""+msgtype+"\":{\"media_id\":\""+ mediaid.getMedia_id()+"\"}}";
		
		System.out.println(text);
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, text);
		System.out.println("Response " + response.getEntity(String.class));
		
		System.out.println("finish upload");
		return new blankView();
	}
	
	
	@GET
    @Timed
	@Path("/getmedia")
	public Response getMedia(@QueryParam("file") String file)
	{
		System.out.println(file);
		File f = new File(file);
		System.out.println(f.canRead());
		
		ResponseBuilder response = Response.ok((Object) f);
		response.header("Content-Disposition",
			"attachment; filename="+f.getName());

		Response r = response.build();
		return r;
	}
	
	
	@GET
    @Timed
	@Path("/survey")
	public blankView sendSurvey(@QueryParam("session") int session)
	{
		Session s = dao.sessionFindById(session);

		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		
		dao.insertMessage(s.getCustomer(), 
				s.getAgent(), 
				timestamp.toString(), 
				"text", 
				survey, 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"",
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				"", 
				session);
		
		dao.setSurvey(session, -1);
		
		wechatToken.set(appid, appsecret);
		
		
		String post = URL + wechatToken.getToken();
						
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource webResource = client.resource(UriBuilder.fromUri(post).build());
				
		String text = "{\"touser\":\""+s.getCustomer() +"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+ survey +"\"}}";
		
		System.out.println(text);
		
		ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, text);
		System.out.println("Response " + response.getEntity(String.class));
		
		
		return new blankView();
	}
	
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
	 
			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];
	 
				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
	 
				e.printStackTrace();
			}
	 
		}
	
	private String uploadFile(String token, String fileName, String type)
	{
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type="+type;
		String res = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			FileBody fileContent= new FileBody(new File(fileName));
			StringBody comment = new StringBody("Filename: " + fileName);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("media", fileContent);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			res = EntityUtils.toString(resEntity);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return res;
		
		
	
	}
	
	
	
	
	
}
