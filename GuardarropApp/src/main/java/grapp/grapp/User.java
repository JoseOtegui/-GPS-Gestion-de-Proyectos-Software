package grapp.grapp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User
{
    private String email, contrasenia, contraseniaRepetida;
    public User(){
    }
    public User(String email, String contrasenia, String contraseniaRepetida) {
        this.email = email;
        this.contrasenia = contrasenia;
        this.contraseniaRepetida=contraseniaRepetida;
    }
    public String getEmail() {
        return email;
    }
    public String getContraseniaRepetida() {
        return contraseniaRepetida;
    }
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public void setContraseniaRepetida(String contraseniaRepetida) {
        this.contraseniaRepetida = contraseniaRepetida;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean validarMail() {
        if (!this.email.equals("")) {
             Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
             + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
             Matcher mather = pattern.matcher(this.email);
             return mather.find();
         } else {
             return false;
         }
     }
    public String comprobarDatos(){
        if(this.email==null || this.contrasenia==null || this.contraseniaRepetida==null ) return "Completa todos los campos";
        if(!validarMail()) return "Error de formato del correo.";
        if(this.contrasenia.length()<8) return "La contraseña tiene que ser mayor de 8 caracteres";
        if(!this.contrasenia.equals(contraseniaRepetida)) return "La contraseña no coincide";
         return null;
    }
    public String comprobarDatosLogIn(){
        if(this.email=="" || this.contrasenia== "") return "Completa todos los campos";
        if(!validarMail()) return "Error de formato del correo.";
        return null;
    }


    public Boolean login(String email, String contrasenia){
        return this.email.equals(email) && this.contrasenia.equals(contrasenia);
    }
    public String hashContrasenia(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} 
		catch (NoSuchAlgorithmException e) {		
			e.printStackTrace();
			return null;
		}
		    
		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		    
		for(byte b : hash) {        
			sb.append(String.format("%02x", b));
		}
		    
		return sb.toString();
	}
    public String insertUser(DataSource dataSource){
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO USUARIOS VALUES ('"+ this.email + "', '" + hashContrasenia(this.contrasenia) +"')");
            c.close();
            return "Usuario insertado correctamente";
        } catch(Exception e){
            return "Fallo al insertar usuario, recuerde que debe ser un correo válido y la contraseña como mínimo debe tener 8 caracteres";
        }

    }
    public boolean searchUser(DataSource dataSource){
        boolean logueado = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USUARIOS WHERE email='"+this.email+"' AND contraseña='"+hashContrasenia(this.contrasenia)+"' ");
            if(rs.next()){
                if(rs.getInt(1) != 0)logueado = true;
            } 
            c.close();
        } catch(Exception e){
            System.out.println("Fallo al loguearse, recuerde que debe ser un correo válido y la contraseña como mínimo debe tener 8 caracteres");
        }
        return logueado;
    }

    
    public boolean searchUserForSignUp(DataSource dataSource){
        boolean existe = false;
        try (Connection c = dataSource.getConnection()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM USUARIOS WHERE email='"+email+"' ");
            if(rs.next()){
                if(rs.getInt(1) != 0)existe = true;
            }
            c.close(); 
        } catch(Exception e){
            System.out.println("Usuario ya existente");
        }
        return existe;
    }
}