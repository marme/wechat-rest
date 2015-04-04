package com.daogen.socialmedia.database;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	
	 private int id;
	 private String ToUserName;
	 private String FromUserName;
	 private String CreateTime;
	 private String MsgType;    		
	 private String Content;
	 private String MsgId;
	 private String PicUrl;
	 private String MediaId;
	 private String Format;
	 private String ThumbMediaId;
	 private String Location_X;
	 private String Location_Y;
	 private String Scale;
	 private String Label;
	 private String Title;
	 private String Description;
	 private String Url;
	 private String Event;
	 private String EventKey;
	 private String Ticket;
	 private String Latitude;
	 private String Longitude;
	 private String Precision;
	 private String Recognition;
	 private int sessionid;
	 
	 public Message(){
		 
	 }
	  
	 
	 public Message(int id, String ToUserName, String FromUserName,
			String CreateTime, String MsgType, String Content, String MsgId,
			String picUrl, String mediaId, String format, String thumbMediaId,
			String location_X, String location_Y, String scale, String label,
			String title, String description, String url, String event,
			String eventKey, String ticket, String latitude, String longitude,
			String precision, int sessionid, String recognition) {
		this.id = id;
		this.ToUserName = ToUserName;
		this.FromUserName = FromUserName;
		this.CreateTime = CreateTime;
		this.MsgType = MsgType;
		this.Content = Content;
		this.MsgId = MsgId;
		PicUrl = picUrl;
		MediaId = mediaId;
		Format = format;
		ThumbMediaId = thumbMediaId;
		Location_X = location_X;
		Location_Y = location_Y;
		Scale = scale;
		Label = label;
		Title = title;
		Description = description;
		Url = url;
		Event = event;
		EventKey = eventKey;
		Ticket = ticket;
		Latitude = latitude;
		Longitude = longitude;
		Precision = precision;
		this.sessionid = sessionid;
		Recognition = recognition;
	}
	 @JsonProperty("id")
	public int getId() {
		return id;
	}
	 @JsonProperty("id")
	public void setId(int id) {
		this.id = id;
	}
	@JsonProperty("sessionid")
	public int getSessionid() {
		return sessionid;
	}
	@JsonProperty("sessionid")
	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}
	@JsonProperty("ToUserName")
	public String getToUserName() {
		return ToUserName;
	}
	@JsonProperty("ToUserName")
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	@JsonProperty("FromUserName")
	public String getFromUserName() {
		return FromUserName;
	}
	@JsonProperty("FromUserName")
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	@JsonProperty("CreateTime")
	public String getCreateTime() {
		return CreateTime;
	}
	@JsonProperty("CreateTime")
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	@JsonProperty("MsgType")
	public String getMsgType() {
		return MsgType;
	}
	@JsonProperty("MsgType")
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	@JsonProperty("Content")
	public String getContent() {
		return Content;
	}
	@JsonProperty("Content")
	public void setContent(String content) {
		Content = content;
	}
	@JsonProperty("MsgId")
	public String getMsgId() {
		return MsgId;
	}
	@JsonProperty("MsgId")
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	@JsonProperty("PicUrl")
	public String getPicUrl() {
		return PicUrl;
	}
	@JsonProperty("PicUrl")
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	@JsonProperty("MediaId")
	public String getMediaId() {
		return MediaId;
	}
	@JsonProperty("MediaId")
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	@JsonProperty("Format")
	public String getFormat() {
		return Format;
	}
	@JsonProperty("Format")
	public void setFormat(String format) {
		Format = format;
	}
	@JsonProperty("ThumbMediaId")
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	@JsonProperty("ThumbMediaId")
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	@JsonProperty("Location_X")
	public String getLocation_X() {
		return Location_X;
	}
	@JsonProperty("Location_X")
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	@JsonProperty("Location_Y")
	public String getLocation_Y() {
		return Location_Y;
	}
	@JsonProperty("Location_Y")
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	@JsonProperty("Scale")
	public String getScale() {
		return Scale;
	}
	@JsonProperty("Scale")
	public void setScale(String scale) {
		Scale = scale;
	}
	@JsonProperty("Label")
	public String getLabel() {
		return Label;
	}
	@JsonProperty("Label")
	public void setLabel(String label) {
		Label = label;
	}
	@JsonProperty("Title")
	public String getTitle() {
		return Title;
	}
	@JsonProperty("Title")
	public void setTitle(String title) {
		Title = title;
	}
	@JsonProperty("Description")
	public String getDescription() {
		return Description;
	}
	@JsonProperty("Description")
	public void setDescription(String description) {
		Description = description;
	}
	@JsonProperty("Url")
	public String getUrl() {
		return Url;
	}
	@JsonProperty("Url")
	public void setUrl(String url) {
		Url = url;
	}
	@JsonProperty("Event")
	public String getEvent() {
		return Event;
	}
	@JsonProperty("Event")
	public void setEvent(String event) {
		Event = event;
	}
	@JsonProperty("EventKey")
	public String getEventKey() {
		return EventKey;
	}
	@JsonProperty("EventKey")
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	@JsonProperty("Ticket")
	public String getTicket() {
		return Ticket;
	}
	@JsonProperty("Ticket")
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	@JsonProperty("Latitude")
	public String getLatitude() {
		return Latitude;
	}
	@JsonProperty("Latitude")
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	@JsonProperty("Longitude")
	public String getLongitude() {
		return Longitude;
	}
	@JsonProperty("Longitude")
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	@JsonProperty("Precision")
	public String getPrecision() {
		return Precision;
	}
	@JsonProperty("Precision")
	public void setPrecision(String precision) {
		Precision = precision;
	}
	@JsonProperty("Recognition")
	public String getRecognition() {
		return Recognition;
	}
	@JsonProperty("Recognition")
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	

}
