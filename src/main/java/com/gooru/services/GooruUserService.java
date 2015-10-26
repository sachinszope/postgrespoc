package com.gooru.services;

import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;

public interface GooruUserService {
	
	public GooruUser getGooruUserById(int id);
	
	public Attributes getUserAttributesInJson(int id);
	
	public String getUserAttributesInString(int id);
	
	public boolean save(String jsonData);

}
