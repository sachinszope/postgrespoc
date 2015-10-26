package com.gooru.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.google.gson.Gson;
import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;

public class GooruUserMapper implements ResultSetMapper<GooruUser> {
	Gson gson = new Gson();
	
	public GooruUser map(int index, ResultSet rs, StatementContext ctx) throws SQLException {

		GooruUser gUser = new GooruUser();
		gUser.setId(rs.getInt("id"));
		gUser.setUserName(rs.getString("user_name"));
		gUser.setAttributes_text(rs.getString("attributes_text"));
		gUser.setAttributes(gson.fromJson(rs.getString("attributes"), Attributes.class));
		return gUser;
	}

}
