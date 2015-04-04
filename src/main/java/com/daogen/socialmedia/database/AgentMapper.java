package com.daogen.socialmedia.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AgentMapper implements ResultSetMapper<Agent>{

	  public Agent map(int index, ResultSet r, StatementContext ctx) throws SQLException
	  {		  	
			boolean available =  r.getInt("available") == 1 ? true : false;;
			boolean logged_in = r.getInt("logged_in") == 1 ? true : false;
			
			return new Agent(r.getInt("id"), r.getString("login_name"), logged_in, available,r.getInt("rating"));
	  }
	
}
