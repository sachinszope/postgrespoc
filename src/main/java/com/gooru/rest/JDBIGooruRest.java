package com.gooru.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.postgresql.util.PGobject;

import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.services.JDBIGooruUserService;
import com.gooru.util.DBConnection;
import com.gooru.util.RandomGenerator;

@Path("/gooru/jdbi-postgres")
public class JDBIGooruRest {
	
	JDBIGooruUserService jdbiService = DBConnection.getJDBIGooruService(); 
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GooruUser getGooruUserById(@PathParam("id") int id) {
		System.out.println("request to get user:" + id);
		return jdbiService.getGooruUserById(id);
	}
	
	@GET
	@Path("/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Attributes getJsonAttributes(@PathParam("id") int id) {
		return jdbiService.getUserAttributesInJson(id);
	}
	
	@GET
	@Path("/text/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStringAttributes(@PathParam("id") int id) {
		return jdbiService.getUserAttributesInString(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveGooruUser(String jsonData) {
		String response = "{\"response\":\"failure\"}";
		System.out.println(jsonData);
		try {
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(jsonData);
			
			jdbiService.save(RandomGenerator.getRandomString(), jsonData, jsonObject);
			response = "{\"response\":\"success\"}"; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
