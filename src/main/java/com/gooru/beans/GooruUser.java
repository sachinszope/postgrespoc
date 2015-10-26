package com.gooru.beans;

public class GooruUser {
	private int id;
	private String userName;

	private String attributes_text;
	private Attributes attributes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAttributes_text() {
		return attributes_text;
	}

	public void setAttributes_text(String attributes_text) {
		this.attributes_text = attributes_text;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "GooruUser [id=" + id + ", userName=" + userName + ", attributes_text=" + attributes_text + ", attributes=" + attributes + "]";
	}

}
