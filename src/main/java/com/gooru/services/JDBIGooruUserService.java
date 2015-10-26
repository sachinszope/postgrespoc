package com.gooru.services;

import org.postgresql.util.PGobject;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import com.gooru.beans.Attributes;
import com.gooru.beans.GooruUser;
import com.gooru.mapper.AttributesMapper;
import com.gooru.mapper.GooruUserMapper;

public interface JDBIGooruUserService extends GooruUserService {

	@SqlQuery("select id, user_name, attributes_text, attributes from gooru_user where id = :id")
	@Mapper(GooruUserMapper.class)
	GooruUser getGooruUserById(@Bind("id") int id);

	@SqlQuery("select attributes from gooru_user where id = :id")
	@Mapper(AttributesMapper.class)
	Attributes getUserAttributesInJson(@Bind("id") int id);
	
	@SqlQuery("select attributes_text from gooru_user where id = :id")
	String getUserAttributesInString(@Bind("id") int id);

	@SqlUpdate("insert into gooru_user(user_name, attributes_text, attributes) values(:userName, :jsonData, :jsonObject)")
	void save(@Bind("userName") String userName, @Bind("jsonData") String jsonData, @Bind("jsonObject") PGobject jsonObject);
	
	void close();

}
