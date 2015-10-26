package com.gooru;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;

import com.gooru.util.DBConnection;

public class JooqHelloWorld {

	public static void main(String[] args) {
		DSLContext create = DBConnection.getDSLContext();
		List<Table<?>> tables = create.meta().getTables();
		
/*		for(Table<?> table : tables) {
			System.out.println(table.getName());
		}
		String table = tables.get(0).getName();
*/		

		String sql = create.select()
				.from(tables.get(0))
				.getSQL();
		
		System.out.println(sql);
		
		Result<Record> result = create.fetch(sql);
		
		for (Record r : result) {
		    //Integer id = r.getValue(1);
		    String firstName = r.getValue(2).toString();
		    String lastName = r.getValue(3).toString();

		    System.out.println("ID: " + " first name: " + firstName + " last name: " + lastName);
		}
	}

}
