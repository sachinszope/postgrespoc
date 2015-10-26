package com.gooru.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.skife.jdbi.v2.DBI;

import com.gooru.services.JDBIGooruUserService;

public class DBConnection {
	public static Connection getJDBCConnection() {
		System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");
		Connection connection  =null;
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("PostgreSQL JDBC Driver Registered!");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"postgres");
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
		}		
		return connection;
	}
	
	public static JDBIGooruUserService getJDBIGooruService() {
		DBI dbi = null;
		try {
			Class.forName("org.postgresql.Driver");
			dbi = new DBI("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return dbi.open(JDBIGooruUserService.class);
	}
	public static DSLContext getDSLContext() {
		Connection connection = null;
		DSLContext create = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"postgres");
			create = DSL.using(connection, SQLDialect.POSTGRES_9_4);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return create;
	}
}
