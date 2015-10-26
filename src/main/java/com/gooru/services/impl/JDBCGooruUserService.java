package com.gooru.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PGobject;

import com.google.gson.Gson;
import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.services.GooruUserService;
import com.gooru.util.DBConnection;
import com.gooru.util.RandomGenerator;

public class JDBCGooruUserService implements GooruUserService {
	Connection connection = DBConnection.getJDBCConnection();
	Gson gson = new Gson();
	
	public GooruUser getGooruUserById(int id) {
		GooruUser gUser = null;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select id, user_name, attributes_text, attributes from gooru_user where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				gUser = new GooruUser();
				gUser.setId(rs.getInt("id"));
				gUser.setUserName(rs.getString("user_name"));
				gUser.setAttributes_text(rs.getString("attributes_text"));
				gUser.setAttributes(gson.fromJson(rs.getString("attributes"), Attributes.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gUser;
	}

	public boolean save(String jsonData) {
		boolean retFlag = false;
		try {
			PreparedStatement pstmt = connection.prepareStatement("insert into gooru_user(user_name, attributes_text, attributes) values(?,?,?)");
			pstmt.setString(1, RandomGenerator.getRandomString());
			pstmt.setString(2, jsonData);
			
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(jsonData);
			
			pstmt.setObject(3, jsonObject);
			pstmt.executeUpdate();
			retFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retFlag;
	}

	public Attributes getUserAttributesInJson(int id) {
		Attributes attributes = new Attributes();
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select attributes from gooru_user where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				attributes = gson.fromJson(rs.getString("attributes"), Attributes.class);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attributes;
	}

	public String getUserAttributesInString(int id) {
		String attributes = null;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select attributes_text from gooru_user where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				attributes = rs.getString("attributes_text");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attributes;
	}
	
	
}
