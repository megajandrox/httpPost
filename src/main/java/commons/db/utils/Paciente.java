package commons.db.utils;

public class Paciente {
	
	private String user;
	private String email;
	private int dni;
	
	public Paciente() {
		
	}
	
	public Paciente(String user, String email, int dni) {
		this.user = user;
		this.email = email;
		this.dni = dni;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getDni() {
		return dni;
	}
	
	public void setDni(int dni) {
		this.dni = dni;
	}

	public String toString() {
		return "Paciente [user=" + user + ", email=" + email + ", dni=" + dni + "]";
	}
}
