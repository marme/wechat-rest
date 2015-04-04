package com.daogen.socialmedia.database;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface wechatDAO {

	//Messages---------------------------------------------------------------------------------------------
	@SqlUpdate("insert into marme.messages (ToUserName,FromUserName,CreateTime,MsgType,Content,MsgId,PicUrl,MediaId,Format,ThumbMediaId ,Location_X,Location_Y,Scale,Label,Title,Description,Url,Event,EventKey,Ticket,Latitude,Longitude,Precision,Recognition,sessionid) values (:ToUserName,:FromUserName,:CreateTime,:MsgType,:Content,:MsgId,:PicUrl,:MediaId,:Format,:ThumbMediaId,:Location_X,:Location_Y,:Scale,:Label,:Title,:Description,:Url,:Event,:EventKey,:Ticket,:Latitude,:Longitude,:Precision,:Recognition,:sessionid)")
	  void insertMessage(@Bind("ToUserName") String ToUserName,
			  @Bind("FromUserName") String FromUserName,
			  @Bind("CreateTime") String CreateTime,
			  @Bind("MsgType") String MsgType,    		
			  @Bind("Content") String Content,
			  @Bind("MsgId") String MsgId,
			  @Bind("PicUrl") String PicUrl,
			  @Bind("MediaId") String MediaId,
			  @Bind("Format") String Format,
			  @Bind("ThumbMediaId") String ThumbMediaId,
			  @Bind("Location_X") String Location_X,
			  @Bind("Location_Y") String Location_Y,
			  @Bind("Scale") String Scale,
			  @Bind("Label") String Label,
			  @Bind("Title") String Title,
			  @Bind("Description") String Description,
			  @Bind("Url") String Url,
			  @Bind("Event") String Event,
			  @Bind("EventKey") String EventKey,
			  @Bind("Ticket") String Ticket,
			  @Bind("Latitude") String Latitude,
			  @Bind("Longitude") String Longitude,
			  @Bind("Precision") String Precision,
			  @Bind("Recognition") String Recognition,
			  @Bind("sessionid") int sessionid);
	
	@Mapper(MessageMapper.class)
	@SqlQuery("select id,ToUserName,FromUserName,CreateTime,MsgType,Content,MsgId,PicUrl,MediaId,Format,ThumbMediaId ,Location_X,Location_Y,Scale,Label,Title,Description,Url,Event,EventKey,Ticket,Latitude,Longitude,Precision,Recognition,sessionid from marme.messages where sessionid = :sessionid order by id asc")
	List<Message> getMessagesBySessionId(@Bind("sessionid") int sessionid);
	
	@Mapper(MessageMapper.class)
	@SqlQuery("select id,ToUserName,FromUserName,CreateTime,MsgType,Content,MsgId,PicUrl,MediaId,Format,ThumbMediaId ,Location_X,Location_Y,Scale,Label,Title,Description,Url,Event,EventKey,Ticket,Latitude,Longitude,Precision,Recognition,sessionid from marme.messages where ToUserName = :customer or FromUserName = :customer order by id asc")
	List<Message> getMessagesBycustomer(@Bind("customer") String customer);
	
	
	
	//Sessions---------------------------------------------------------------------------------------------
	
	@SqlUpdate("insert into marme.CHATSESSION (customer) values (:customer)")
	void createSession(@Bind("customer") String customer);
	
	@SqlUpdate("update marme.CHATSESSION set agentid = :agentid where id = :id")
	void assignAgent(@Bind("id")int id, @Bind("agentid") int agentid);
	
	@SqlUpdate("update marme.CHATSESSION set end = CURRENT_TIMESTAMP where id = :id")
	void closeSession(@Bind("id")int id);
	
	@SqlUpdate("update marme.CHATSESSION set agent = :agent where id = :id")
	void acceptSession(@Bind("id")int id, @Bind("agent") String agent);
	
	@SqlUpdate("update marme.CHATSESSION set survey = :survey where id = :id")
	void setSurvey(@Bind("id")int id, @Bind("survey") int survey);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where id = :id")
	Session sessionFindById(@Bind("id") int id);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where agentid = :agentid")
	Session sessionFindByAgentId(@Bind("agentid") int agentid);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where customer = :customer")
	Session sessionFindByCustomer(@Bind("customer") String customer);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where customer = :customer and end is null order by id desc")
	Session getOpenSession(@Bind("customer") String customer);
	
	@SqlQuery("select count(id) from marme.chatsession where agentid = :agentid and end is null order by id desc")
	int getOpenCount(@Bind("agentid") int agentid);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where customer = :customer and end is not null order by id desc")
	Session getLastSession(@Bind("customer") String customer);
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where agentid is null and end is null order by id asc")
	List<Session> getUnassignedSession();
	
	@Mapper(SessionMapper.class)
	@SqlQuery("select id, begin, end, customer, agent, agentid, survey from marme.chatsession where agentid = :agentid order by id asc")
	List<Session> getAssaignedSession(@Bind("agentid") int agentid);
	
	
	
	
	
	//Agent---------------------------------------------------------------------------------------------
	
	@SqlQuery("select id, login_name, logged_in, available, rating from marme.agent where id = :id")
	@Mapper(AgentMapper.class)
	  Agent findById(@Bind("id") int id);
	
	@SqlQuery("select id, login_name, logged_in, available, rating from marme.agent where login_name = :login_name")
	@Mapper(AgentMapper.class)
	  Agent findByLogin(@Bind("login_name") String login_name);
	
	@SqlQuery("select id, login_name, logged_in, available, rating from marme.agent where available = 1 and logged_in = 1")
	@Mapper(AgentMapper.class)
	  List<Agent> getAvailable();
	
	@SqlUpdate("update marme.agent set logged_in = :logged_in where id = :id")
	void changeLoggedIn(@Bind("id") int id, @Bind("logged_in") int logged_in);
	
	@SqlUpdate("update marme.agent set available = :available where id = :id")
	void changeAvailable(@Bind("id") int id, @Bind("available") int available);
	
}
