package com.gooru;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.postgresql.util.PGobject;

import com.google.gson.Gson;
import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.util.RandomGenerator;

public class JDBCHelloWorldPostgres {

	Connection connection = null;
	Gson gson = new Gson();

	public static void main(String[] args) {
		JDBCHelloWorldPostgres hwp = new JDBCHelloWorldPostgres();
		//Get Connection
		hwp.getConnection();
		
		//Query by user id
		GooruUser gUser = hwp.getGooruUser(2);
		System.out.println("==> Select by ID:");
		System.out.println(gUser != null ? gUser.toString() : "Record not exist");
		
		//Query by user name
		System.out.println("\n==> Select by USERNAME:");
		GooruUser gUser1 = hwp.getGooruUser("japfifaybc");
		System.out.println(gUser1 != null ? gUser1.toString() : "Record not exist");
		
		System.out.println("\n==> Inserting new User:");
		hwp.insert(hwp.getNewUser());
		
		//Getting last inserted ID
		int lastInserted = hwp.getLastInsertedId();
		System.out.println("\n==> Last inserted ID: " + lastInserted);
		
		//Updating UserName
		System.out.println("\n==> updating username of last inserted record");
		hwp.updateUserName(lastInserted, RandomGenerator.getRandomString());
		System.out.println("\n==> Updated record:");
		System.out.println(hwp.getGooruUser(lastInserted).toString()); 
		
		System.out.println("\n Get users having more than 10 collections");
		List<GooruUser> gooruUsers = hwp.getGooruUsersHaving10orMoreCollections();
		for (Iterator<GooruUser> iterator = gooruUsers.iterator(); iterator.hasNext();) {
			GooruUser gooruUser =  iterator.next();
			System.out.println(gooruUser);
		}
		
		System.out.println("\n==> Updating user attributes");
		hwp.updateCollectionCount(lastInserted);
		System.out.println(hwp.getGooruUser(lastInserted).toString());
	}
	
	private int getLastInsertedId() {
		int id = 0;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select max(id) from gooru_user");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private GooruUser getNewUser() {
		GooruUser newGooruUser = new GooruUser();
		String uname = RandomGenerator.getRandomString();
		newGooruUser.setUserName(uname);
		
		Attributes attributes = new Attributes();
		attributes.setDisplayName(uname.toUpperCase());
		attributes.setFullName(uname.toUpperCase() + " " + RandomGenerator.getRandomString().toUpperCase());
		attributes.setProfilePic("/user/" + RandomGenerator.getRandomNumber());
		attributes.setAssessmentAdded(RandomGenerator.getRandomNumber());
		attributes.setCollectionsCreated(RandomGenerator.getRandomNumber());
		attributes.setResourceAdded(RandomGenerator.getRandomNumber());
		newGooruUser.setAttributes(attributes);
		
		return newGooruUser;
	}
	
	private void updateCollectionCount(int id) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("update gooru_user set attributes = ? where id = ?");
			
			GooruUser gUser = getGooruUser(id);
			Attributes attr = gUser.getAttributes();
			attr.setCollectionsCreated(RandomGenerator.getRandomNumber());
			
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(gson.toJson(attr));
			
			pstmt.setObject(1, jsonObject);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void updateUserName(int id, String newUserName) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("update gooru_user set user_name=? where id = ?");
			pstmt.setString(1, newUserName);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insert(GooruUser gUser) {
		try {
			PreparedStatement pstmt = connection.prepareStatement("insert into gooru_user(user_name, attributes) values(?,?)");
			pstmt.setString(1, gUser.getUserName());
			
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(gson.toJson(gUser.getAttributes()));
			
			pstmt.setObject(2, jsonObject);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<GooruUser> getGooruUsersHaving10orMoreCollections() {
		List<GooruUser> gooruUsers = new ArrayList<GooruUser>();
		try {
			PreparedStatement pstmt = connection.prepareStatement("select id, user_name, attributes from gooru_user where CAST(attributes->>'collectionsCreated' as integer) > 10");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				GooruUser gUser = new GooruUser();
				gUser.setId(rs.getInt("id"));
				gUser.setUserName(rs.getString("user_name"));
				gUser.setAttributes(gson.fromJson(rs.getString("attributes"), Attributes.class));
				
				gooruUsers.add(gUser);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return gooruUsers;
	}

	private GooruUser getGooruUser(int id) {
		GooruUser gUser = null;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select id, user_name, attributes from gooru_user where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				gUser = new GooruUser();
				gUser.setId(rs.getInt("id"));
				gUser.setUserName(rs.getString("user_name"));
				gUser.setAttributes(gson.fromJson(rs.getString("attributes"), Attributes.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gUser;
	}

	private GooruUser getGooruUser(String userName) {
		GooruUser gUser = null;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select id, user_name, attributes from gooru_user where user_name = ?");
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				gUser = new GooruUser();
				gUser.setId(rs.getInt("id"));
				gUser.setUserName(rs.getString("user_name"));
				gUser.setAttributes(gson.fromJson(rs.getString("attributes"), Attributes.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gUser;
	}

	private void getConnection() {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("PostgreSQL JDBC Driver Registered!");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
					"postgres");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
