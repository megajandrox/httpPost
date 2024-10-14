package com.http.post.dao;

import commons.db.utils.DBManager;
import commons.db.utils.exceptions.DBOperationManager;

import java.sql.Connection;
import java.sql.Statement;

public class TableManager {
	
	public static void createRequestTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "CREATE TABLE  IF NOT EXISTS request (id INTEGER IDENTITY, url VARCHAR(1024), method VARCHAR(20), json_data CLOB)";
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
