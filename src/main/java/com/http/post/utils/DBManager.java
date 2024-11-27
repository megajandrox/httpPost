package com.http.post.utils;

import com.http.post.utils.exceptions.DBConnectionRuntimeException;
import com.http.post.utils.exceptions.DBDriverNotFoundRuntimeException;
import com.http.post.utils.exceptions.DBPropertiesRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBManager {

	private static final String DRIVER = "driver";
	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final Properties properties= new Properties();
	private static final String SCHEMA = "schema";

	static {
		try (InputStream input = DBManager.class.getClassLoader().getResourceAsStream("database.properties")) {
			if (input == null) {
				System.err.println("Please create a database.properties file in the resources folder");
				System.exit(0);
			}
			properties.load(input);
		} catch (IOException e) {
			throw new IllegalStateException("Could not load database.properties file", e);
		}
        try {
            checkProperty(DRIVER);
			checkProperty(URL);
			checkProperty(USER);
			checkProperty(PASSWORD);
		} catch (DBPropertiesRuntimeException e) {
			throw new IllegalStateException("Please check the database.properties file", e);
		}
	}

	private static String getDBProperty(String name) {
		return properties.getProperty("db."+name);
	}

	private static void checkProperty(String name) {
		String value = getDBProperty(name);
		if(value == null) {
			String msg = String.format("Please add a db.%s property to the database.properties file", name);
			throw new DBPropertiesRuntimeException(msg);
		}
	}


	public static Connection connect() {
		Connection c = null;
		try {
			Class.forName(getDBProperty(DRIVER));
		} catch (ClassNotFoundException e) {
			System.err.println("Please add the driver to the classpath");
			throw new DBDriverNotFoundRuntimeException(e);
		}
		try {
			String url = getDBProperty(URL);
			String dbUsername = getDBProperty(USER);
			String dbPassword = getDBProperty(PASSWORD);
			c = DriverManager.getConnection(url, dbUsername, dbPassword);
			c.setAutoCommit(false);
			String schema = getDBProperty(SCHEMA); // Add this to your properties
			try (Statement stmt = c.createStatement()) {
				stmt.execute("SET search_path TO " + schema);
			}
		} catch (SQLException e) {
			throw new DBConnectionRuntimeException(e);
		}
		return c;
	}
}
