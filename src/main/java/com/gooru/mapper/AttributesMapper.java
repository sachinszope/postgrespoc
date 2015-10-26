package com.gooru.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.gson.Gson;
import com.gooru.beans.Attributes;

public class AttributesMapper implements ResultSetMapper<Attributes>{
	Gson gson = new Gson();
	
	public Attributes map(int index, ResultSet rs, StatementContext ctx) throws SQLException {
		Attributes attributes = new Attributes();
		attributes = gson.fromJson(rs.getString("attributes"), Attributes.class);
		return attributes;
	}

}
