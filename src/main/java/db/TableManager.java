package db;

import commons.db.utils.DBManager;
import commons.db.utils.exceptions.DBOperationManager;

import java.sql.Connection;
import java.sql.Statement;

public class TableManager {
	
	public static void createCustomerTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "CREATE TABLE customer (id INTEGER IDENTITY, username VARCHAR(256), email VARCHAR(256))";
			Statement s = c.createStatement();
			s.execute(sql);
		}, c::rollback);
	}
	
	
	public static void dropCustomerTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "DROP TABLE customer";
			Statement s = c.createStatement();
			s.execute(sql);
			c.commit();
		}, c::rollback);
	}
}
