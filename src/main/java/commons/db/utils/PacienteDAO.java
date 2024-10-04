package commons.db.utils;

import commons.db.utils.bussiness.exceptions.*;

import java.util.List;

public interface PacienteDAO {
	
	void crearPaciente(Paciente unPaciente) throws CreateException;
	
	void borrarPaciente(String user) throws DeletionException;
	
	void actualizarPaciente(Paciente unPaciente) throws UpdateException;
	
	Paciente muestraPaciente(String user) throws GetException;
	
	List<Paciente> listaTodosLosPacientes() throws SearchException;

}
