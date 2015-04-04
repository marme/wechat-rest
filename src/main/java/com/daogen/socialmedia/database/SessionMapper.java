package com.daogen.socialmedia.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class SessionMapper implements ResultSetMapper<Session>{

	@Override
	public Session map(int index, ResultSet r, StatementContext ctx)
			throws SQLException {
		return new Session(r.getInt("id"), r.getTimestamp("begin"),r.getTimestamp("end"),r.getString("customer"),r.getString("agent"),r.getInt("agentid"), r.getInt("survey"));
	}

}
