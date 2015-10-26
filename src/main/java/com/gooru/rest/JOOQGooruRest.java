package com.gooru.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.services.GooruUserService;
import com.gooru.services.impl.JooqGooruUserService;

@Path("/gooru/jooq-postgres")
public class JOOQGooruRest {
	GooruUserService gooruUserService = new JooqGooruUserService();
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public GooruUser getGooruUserById(@PathParam("id") int id) {
		System.out.println("request to get user:" + id);
		return gooruUserService.getGooruUserById(id);
	}
	
	@GET
	@Path("/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Attributes getJsonAttributes(@PathParam("id") int id) {
		return gooruUserService.getUserAttributesInJson(id);
	}
	
	@GET
	@Path("/text/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStringAttributes(@PathParam("id") int id) {
		return gooruUserService.getUserAttributesInString(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String saveGooruUser(String jsonData) {
		String response = null;
		System.out.println(jsonData);
		if(gooruUserService.save(jsonData)) {
			response = "{\"response\":\"success\"}"; 
		} else {
			response = "{\"response\":\"failure\"}"; 
		}
		return response;
	}
}
