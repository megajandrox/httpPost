package commons.db.utils;

import commons.db.utils.exceptions.DBOperationManager;

import java.sql.Connection;
import java.sql.Statement;

public class TableManager {
	
	public void createPacienteTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "CREATE TABLE pacientes (id INTEGER IDENTITY, user VARCHAR(256), email VARCHAR(256), dni INTEGER)";
			Statement s = c.createStatement();
			s.execute(sql);
		}, c::rollback);
	}
	
	
	public void dropPacienteTable() {
		Connection c = DBManager.connect();
		DBOperationManager.getInstance().tryDDLAction(c, () -> {
			String sql = "DROP TABLE pacientes";
			Statement s = c.createStatement();
			s.execute(sql);
			c.commit();
		}, c::rollback);
	}
}
