package commons.db.utils;

import commons.db.utils.bussiness.exceptions.*;

import java.util.List;


public class Main {
	
	public static void main(String [] args) throws CreateException, DeletionException, UpdateException, GetException, SearchException {
		
		TableManager tm = new TableManager();
		tm.createPacienteTable();  //Creo la tabla de usuarios..
		
		
		PacienteDAO dao = new PacienteDAOH2Impl();  
		/* usamos DAO p definir las cosas q podemos hacer contra la BD (en este caso, hacemos alta baja modificacion y consulta de una entidad.
		en la interfaz definiamos lo que ibamos a hacer y en las implementaciones definiamos cómo lo ibamos a hacer.
		por eso, del lado IZQ del igual tengo la INTERFAZ y del lado derecho tengo la implementación ==> pq el dia de mañana quiero hacer uso del polimorfismo y quiero soportar otro motor de BD, solo tengo que cambiar esa implementacion por la de otra BD (ej.UsuarioDAOPostgreImpl()). puedo reutilizar todo el resto del codigo, pq se adhiere al contrato que definió la interfaz UsuarioDAO   */
		
		String user = "user1";
		String email = "email1";
		int dni = 12345;
		Paciente aInsertar = new Paciente();
		aInsertar.setUser(user);
		aInsertar.setEmail(email);
		aInsertar.setDni(dni);
		dao.crearPaciente(aInsertar);
		
		String userx = "userx";
		String emailx = "emailx";
		int dnix = 54321;
		Paciente paraEditar = new Paciente(userx, emailx, dnix);
		dao.crearPaciente(paraEditar);
		
		System.out.println("Ahora muestro al paciente recien cargado: ");
		String unPaciente = "user1"; //la operacion para mostrar un paciente es segun su user, pero puede ser cualquier otro criterio
		Paciente pacienteBase = dao.muestraPaciente(unPaciente);
		System.out.println(pacienteBase);
		System.out.println("------");
		
		System.out.println("Ahora modifico al paciente: ");
		String user2 = "userx"; 
		String email2 = "email2@gmail.com";
		int dni2 = 1234567;
		Paciente aEditar = new Paciente(user2, email2, dni2);
		dao.actualizarPaciente(aEditar); 
		/*el metodo actualizarPaciente() recibe un objeto Paciente y edita todo el objeto. porque no se ponen los 3 valores q quiero editar sueltos? pq estamos trabajando  con objetos, la BD es una BD relacional, no entiende de objetos, pero nosotros hicimos un sistema orientado a objetos, porque le pasamos el objeto paciente completo, con todos los valores a editar juntos   */
		
		System.out.println("Tengo estos usuarios: ");
		List<Paciente> listaTodosLosPacientes = dao.listaTodosLosPacientes();
		for (Paciente paciente : listaTodosLosPacientes) {
			System.out.println(paciente);
		}
		System.out.println("------");
		
		
		System.out.println("Borrar un paciente");
		dao.borrarPaciente("user1"); //le paso el criterio para encontrar al usuario q quiero borrar
		
		System.out.println("Tengo estos pacientes");
		List<Paciente> otraListaPacientes = dao.listaTodosLosPacientes();
		for (Paciente p : otraListaPacientes) {
			System.out.println(p);
		}
		System.out.println("-----");
		
		tm.dropPacienteTable(); //aca se borró toda la tabla.
	}

}
