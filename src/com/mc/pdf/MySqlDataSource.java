package com.mc.pdf;

/**
* MySqlDataSource.java
* Copyright (c) 2007 by Dr. Herong Yang. All rights reserved.
*/
import java.sql.*;
import javax.sql.*;

public class MySqlDataSource {
	public static void main(String[] args) {
		Connection con = null;
		try {

			// Setting up the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("GymView");
			ds.setUser("jboss");
			ds.setPassword("password");

			// Getting a connection object
			con = ds.getConnection();

			// Getting database info
			DatabaseMetaData meta = con.getMetaData();
			System.out.println("Server name: " + meta.getDatabaseProductName());
			System.out.println("Server version: " + meta.getDatabaseProductVersion());

			// Closing the connection
			con.close();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}
}