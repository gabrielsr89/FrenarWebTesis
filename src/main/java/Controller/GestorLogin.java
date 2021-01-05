package Controller;

import Model.Barrio;
import Model.Cliente;
import Model.Localidad;
import Model.Persona;
import Model.Provincia;
import Model.TipoCliente;
import Model.TipoUsuario;
import Model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorLogin {

    Connection con;

    //ABRIR CONEXION
    private void Conectar() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://DESKTOP-OQ35RLD\\SQLEXPRESS:1433;databaseName=frenarweb1_3", "sa", "123");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //CERRAR CONEXION
    private void Desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión");
        }
    }
    
    //RECUPERAR PASSWORD
    public boolean existeMail(String mail) {
        Conectar();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select email from personas where email = ?");
            pst.setString(1, mail);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return false;
    }
    
    
    //OBTENER PERSONA POR MAIL
    public Persona obtenerPersona(String mail) {
        Persona p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_persona, \n"
                    + "nombre, \n"
                    + "apellido, \n"
                    + "email, \n"
                    + "pass \n"
                    + "from personas \n"
                    + "where email = ?");
           
            pst.setString(1, mail);
            
            ResultSet rs = pst.executeQuery();
            rs.next();
           
            int idP = rs.getInt(1);
            String nombre = rs.getString(2);
            String apellido = rs.getString(3);
            String email = rs.getString(4);
            String pass = rs.getString(5);
            
            p = new Persona(idP, nombre, apellido, email, pass);            

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }
    public int obtenerIdCliente(int id_persona){
    
        int id_cliente = 0;
         try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_cliente from clientes where id_persona = ?");            
            pst.setInt(1, id_persona);
            
            ResultSet rs = pst.executeQuery();
            rs.next();
            id_cliente = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return id_cliente;
    }
    public int ObtenerIdUsuario(int idPersona){
        int id_usuario = 0;
         try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_usuario from usuarios where id_persona = ?");            
            pst.setInt(1, idPersona);
            
            ResultSet rs = pst.executeQuery();
            rs.next();
            id_usuario = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }        
        return id_usuario;
    }

   
}
