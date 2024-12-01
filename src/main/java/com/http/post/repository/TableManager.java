package com.http.post.repository;

import com.http.post.utils.DBManager;
import com.http.post.utils.exceptions.DBOperationManager;

import java.sql.Connection;
import java.sql.Statement;

public class TableManager {
	
	public static void createRequestTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "CREATE TABLE IF NOT EXISTS request (\n" +
					"    id INTEGER IDENTITY,\n" +
					"    url VARCHAR(1024),\n" +
					"    method VARCHAR(20),\n" +
					"    json_data CLOB,\n" +
					"    is_favorite BOOLEAN,\n" +
					"    PRIMARY KEY (id)\n" +
					");";
			Statement s = c.createStatement();
			s.execute(sql);
		}, c::rollback);
	}
	
	
	public static void dropRequestTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "DROP TABLE request";
			Statement s = c.createStatement();
			s.execute(sql);
			c.commit();
		}, c::rollback);
	}
}
