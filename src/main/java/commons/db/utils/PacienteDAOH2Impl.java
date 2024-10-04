package commons.db.utils;

import commons.db.utils.bussiness.exceptions.*;
import commons.db.utils.bussiness.exceptions.BusinessException;
import commons.db.utils.exceptions.DBOperationManager;
import commons.db.utils.exceptions.SQLActionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/* PARA ENTREGAR EL TP DE JDBC TENGO QUE HACER MIS PROPIAS EXCEPCIONES!!!! */

public class PacienteDAOH2Impl implements PacienteDAO {

	public void crearPaciente(Paciente unPaciente) throws CreateException {
		String user = unPaciente.getUser();
		String email = unPaciente.getEmail();
		Integer dni = unPaciente.getDni();
		Connection c = DBManager.connect();
		try {
			DBOperationManager.getInstance().trySqlAction(c, () -> {
				Statement s = c.createStatement();
				String sql = "INSERT INTO pacientes (user, email, dni) VALUES ('" + user + "', '" + email + "', '" + dni + "' )";
				s.executeUpdate(sql);
				c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
		} catch (SQLActionException e) {
			String msgError = "There was an error inserting the patient";
			System.err.println(msgError);
			throw new CreateException(msgError);
        }
	}

	 
	public void borrarPaciente(String user) throws DeletionException {
		Connection c = DBManager.connect();
		try {
			DBOperationManager.getInstance().trySqlAction(c, () -> {
				String sql = "DELETE FROM pacientes WHERE user = '" + user + "'";
				Statement s = c.createStatement();
				s.executeUpdate(sql);
				c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
		} catch (SQLActionException e) {
			String msgError = "There was an error deleting the patient";
			System.err.println(msgError);
			throw new DeletionException(msgError);
		}
	}

	public void actualizarPaciente(Paciente unPaciente) throws UpdateException {
		String user = unPaciente.getUser();
		String email = unPaciente.getEmail();
		int dni = unPaciente.getDni();
		Connection c = DBManager.connect();
		try {
			DBOperationManager.getInstance().trySqlAction(c, () -> {
				String sql = "UPDATE pacientes set email = '" + email + "', dni = '" + dni + "' WHERE user = '" + user + "'";
				Statement s = c.createStatement();
				s.executeUpdate(sql);
				c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
		} catch (SQLActionException e) {
			String msgError = "There was an error updating the patient";
			System.err.println(msgError);
			throw new UpdateException(msgError);
		}
	}

	/*para crear, editar o borrar se usa el .executeUpdate()
	 * y para mostrar/consultar algo a la base de datos, uso el .executeQuery()*/
	public Paciente muestraPaciente(String user) throws GetException {
		Connection c = DBManager.connect();
		try {
			Optional<Paciente> optResult = DBOperationManager.getInstance().trySqlAction(c, () -> {
				String sql = "SELECT * FROM pacientes WHERE user = '" + user + "'";
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql); //me devuele un ResultSet, que contiene las tuplas del resultado
				/*obtengo el resultado y las tuplas almacenadas en la var rs. luego tegno que recorrer el rs, porque es un conjunto de datos sueltos q vienen de la BD. arranca con un puntero en una posicion antes del resultado, y el next() va moviendo ese puntero y si no hya mas resultados, devuelve false. y esto lo puedo usar para un bucle  */
				if (rs.next()) { // si hay resultados, entra en el if
					int id = rs.getInt("id");
					String nombrePaciente = rs.getString("user");
					String email = rs.getString("email");
					int dni = rs.getInt("dni");
					return Optional.of(new Paciente(nombrePaciente, email, dni));
				}
				return Optional.empty();
			}, c::rollback);
            return optResult.orElse(null);
		} catch (SQLActionException e) {
			String msgError = "There was an error updating the patient";
			System.err.println(msgError);
			throw new GetException(msgError);
		}
	}

	
	public List<Paciente> listaTodosLosPacientes() throws SearchException {
		List<Paciente> resultado = new ArrayList<>();
		Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "SELECT * FROM pacientes";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()) { //mientras haya mas resultados, repite este codigo
                    int id = rs.getInt("id");
                    String user = rs.getString("user");
                    String email = rs.getString("email");
                    int dni = rs.getInt("dni");
                    Paciente p = new Paciente(user, email, dni);
                    resultado.add(p); // por cada usuario nuevo que encuentro, lo guardo en esta lista
                }
                return Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
			String msgError = "There was an error getting the patients";
			System.err.println(msgError);
			throw new SearchException(msgError);
        }
		return resultado;
	}
}
