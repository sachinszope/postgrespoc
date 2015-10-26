package com.gooru.services.impl;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Table;
import org.postgresql.util.PGobject;

import com.google.gson.Gson;
import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.services.GooruUserService;
import com.gooru.util.DBConnection;
import com.gooru.util.RandomGenerator;

public class JooqGooruUserService implements GooruUserService {
	
	DSLContext create = DBConnection.getDSLContext();
	Gson gson = new Gson();
	
	public GooruUser getGooruUserById(int id) {
		GooruUser gooruUser = null;
		List<Table<?>> tables = create.meta().getTables();
		
		try {
			Result<Record> result = create.select()
					.from(tables.get(0))
					.where("id = " + id)
					.fetch();
			
			gooruUser = new GooruUser();
			gooruUser.setId(Integer.parseInt(result.getValues(0).get(0).toString()));
			gooruUser.setUserName(result.getValues(1).get(0).toString());
			gooruUser.setAttributes_text(result.getValues(2).get(0).toString());
			gooruUser.setAttributes(gson.fromJson(result.getValues(3).get(0).toString(), Attributes.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gooruUser;
	}

	public Attributes getUserAttributesInJson(int id) {
		Attributes attributes = null;
		com.gooru.beans.jooq.public_.tables.GooruUser gooruUser = com.gooru.beans.jooq.public_.tables.GooruUser.GOORU_USER;
		try {
			Result<Record1<Object>> result = create.select(gooruUser.ATTRIBUTES)
					.from(gooruUser)
					.where(gooruUser.ID.equal(id))
					.fetch();
			attributes = gson.fromJson(result.get(0).getValue(0).toString(), Attributes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributes;
	}

	public String getUserAttributesInString(int id) {
		String strAttributes = null;
		com.gooru.beans.jooq.public_.tables.GooruUser gooruUser = com.gooru.beans.jooq.public_.tables.GooruUser.GOORU_USER;
		try {
			Result<Record1<String>> result = create.select(gooruUser.ATTRIBUTES_TEXT)
					.from(gooruUser)
					.where(gooruUser.ID.equal(id))
					.fetch();
			strAttributes = result.get(0).getValue(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strAttributes;
	}

	public boolean save(String jsonData) {
		com.gooru.beans.jooq.public_.tables.GooruUser gooruUser = com.gooru.beans.jooq.public_.tables.GooruUser.GOORU_USER;
		try {
			PGobject jsonObject = new PGobject();
			jsonObject.setType("json");
			jsonObject.setValue(jsonData);
			
			int cnt = create.insertInto(gooruUser, gooruUser.USER_NAME, gooruUser.ATTRIBUTES_TEXT, gooruUser.ATTRIBUTES)
			.values(RandomGenerator.getRandomString(), jsonData, jsonObject)
			.execute();
			
			if(cnt == 1) return true;
			else return false;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
