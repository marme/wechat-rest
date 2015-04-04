package com.daogen.socialmedia.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class MessageMapper implements ResultSetMapper<Message>{

	@Override
	public Message map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
	
		return new Message(
				r.getInt("id"),
				r.getString("toUserName"),
				r.getString("FromUserName"),
				r.getString("CreateTime"),
				r.getString("MsgType"),
				r.getString("Content"),
				r.getString("MsgId"),
				r.getString("PicUrl"),
				r.getString("MediaId"),
				r.getString("Format"),
				r.getString("ThumbMediaId"),
				r.getString("Location_X"),
				r.getString("Location_Y"),
				r.getString("Scale"),
				r.getString("Label"),
				r.getString("Title"),
				r.getString("Description"),
				r.getString("Url"),
				r.getString("Event"),
				r.getString("EventKey"),
				r.getString("Ticket"),
				r.getString("Latitude"),
				r.getString("Longitude"),
				r.getString("Precision"),
				r.getInt("sessionid"),
				r.getString("Recognition")
				);
	}

}
