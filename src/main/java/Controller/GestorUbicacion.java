package Controller;

import Model.Barrio;
import Model.Localidad;
import Model.Persona;
import Model.Provincia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorUbicacion {

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

    //---------------------------ComboBox---------------------------------------
    //TRAER TODAS LAS PROVINCIAS
    public ArrayList<Provincia> ListarProvincias() {
        ArrayList<Provincia> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from provincias order by 2 asc");
            while (rs.next()) {
                int id_p = rs.getInt(1);
                String provincia = rs.getString(2);
                Provincia p = new Provincia(id_p, provincia);
                lst.add(p);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //TODAS LAS LOCALIDADES
    public ArrayList<Localidad> ListaLocalidades() {
        ArrayList<Localidad> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from localidades");
            while (rs.next()) {
                int id_l = rs.getInt(1);
                String localidad = rs.getString(3);

                Localidad l = new Localidad(id_l, localidad);
                lst.add(l);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    // TODOS LOS BARRIOS
    public ArrayList<Barrio> ListaBarrios() {
        ArrayList<Barrio> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from barrios where nuevoBarrio = 0");
            while (rs.next()) {
                int id_b = rs.getInt(1);
                String barrio = rs.getString(3);

                Barrio b = new Barrio(id_b, barrio);
                lst.add(b);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //---------------------------JavaScript-------------------------------------
    //TRAER LOCALIDADES PARA JS
    public ArrayList<Localidad> LocalidadesParaJS() {
        ArrayList<Localidad> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from localidades");
            while (rs.next()) {
                int id_l = rs.getInt(1);
                int id_p = rs.getInt(2);
                String localidad = rs.getString(3);

                Localidad l = new Localidad(id_l, localidad, id_p);
                lst.add(l);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<Barrio> BarriosParaJS() {
        ArrayList<Barrio> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from barrios where nuevoBarrio = 0");
            while (rs.next()) {
                int id_b = rs.getInt(1);
                int id_l = rs.getInt(2);
                String barrio = rs.getString(3);
                Barrio b = new Barrio(id_b, barrio, id_l);
                lst.add(b);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //LISTAR BARRIOS PARA CONFIRMAR BARRIOS NUEVOS
    public ArrayList<Persona> ListarBarriosNuevos() {
        ArrayList<Persona> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(" select id_persona,\n"
                    + "nombre,\n"
                    + "apellido,\n"
                    + "email,\n"
                    + "telefono,\n"
                    + "telefonoAlter,\n"
                    + "b.id_barrio,\n"
                    + "barrio,\n"
                    + "l.id_localidad,\n"
                    + "localidad,\n"
                    + "p.id_provincia,\n"
                    + "provincia \n"
                    + "from personas join barrios b on b.id_barrio = personas.id_barrio\n"
                    + "join localidades l on l.id_localidad = b.id_localidad\n"
                    + "join provincias p on p.id_provincia = l.id_provincia\n"
                    + "where nuevoBarrio = 1");
            while (rs.next()) {
                int idPersona = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String email = rs.getString(4);
                String telefono = rs.getString(5);
                String telefonoAlter = rs.getString(6);
                int id_b = rs.getInt(7);
                String barrio = rs.getString(8);
                int id_l = rs.getInt(9);
                String localidad = rs.getString(10);
                int id_p = rs.getInt(11);
                String provincia = rs.getString(12);

                Provincia prov = new Provincia(id_p, provincia);
                Localidad l = new Localidad(id_l, localidad);
                Barrio b = new Barrio(id_b, barrio, l, prov);
                Persona p = new Persona(idPersona, nombre, apellido, email, telefono, telefonoAlter, prov, l, b);

                lst.add(p);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //para ver que no existan coincidencias
    public ArrayList<Barrio> ListarBarriosViejos() {
        ArrayList<Barrio> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("  select id_barrio,\n"
                    + "  barrio,\n"
                    + "  l.id_localidad,\n"
                    + "  localidad,\n"
                    + "  p.id_provincia,\n"
                    + "  provincia,\n"
                    + "  nuevoBarrio\n"
                    + "  from barrios b join localidades l on l.id_localidad = b.id_localidad\n"
                    + "  join provincias p on p.id_provincia = l.id_provincia\n"
                    + "  where nuevoBarrio = 0");
            String n = "";
            while (rs.next()) {
                int id_b = rs.getInt(1);
                String barrio = rs.getString(2);
                int id_l = rs.getInt(3);
                String localidad = rs.getString(4);
                int id_p = rs.getInt(5);
                String provincia = rs.getString(6);
                boolean nuevo = rs.getBoolean(7);

                Localidad l = new Localidad(id_l, localidad);
                Provincia p = new Provincia(id_p, provincia);
                Barrio b = new Barrio(id_b, barrio, l, p);
                lst.add(b);
                n += b.getLocalidad().getId_localidad() + ", ";
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //PARA CREAR UN BARRIO
    public int NuevoBarrioCliente(Barrio b) {
        int idBarrio = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(
                    "declare @idBarrio int\n"
                    + "exec dbo.ABarrioNuevo ?,?, @idBarrio output\n"
                    + "select @idBarrio");
            pst.setInt(1, b.getId_localidad());
            pst.setString(2, b.getBarrio());
            ResultSet rs = pst.executeQuery();
            rs.next();
            idBarrio = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idBarrio;
    }

    // para confirmar un barrio nuevo
    public Persona obtenerBarrio(int id) {
        Persona p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(" select id_persona,\n"
                    + "nombre,\n"
                    + "apellido,\n"
                    + "email,\n"
                    + "telefono,\n"
                    + "telefonoAlter,\n"
                    + "b.id_barrio,\n"
                    + "barrio,\n"
                    + "l.id_localidad,\n"
                    + "localidad,\n"
                    + "p.id_provincia,\n"
                    + "provincia \n"
                    + "from personas right join barrios b on b.id_barrio = personas.id_barrio\n"
                    + "right join localidades l on l.id_localidad = b.id_localidad\n"
                    + "right join provincias p on p.id_provincia = l.id_provincia\n"
                    + "where id_persona = ?");

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            rs.next();

            int idPersona = rs.getInt(1);
            String nombre = rs.getString(2);
            String apellido = rs.getString(3);
            String email = rs.getString(4);
            String telefono = rs.getString(5);
            String telefonoAlter = rs.getString(6);
            int id_b = rs.getInt(7);
            String barrio = rs.getString(8);
            int id_l = rs.getInt(9);
            String localidad = rs.getString(10);
            int id_p = rs.getInt(11);
            String provincia = rs.getString(12);

            Provincia prov = new Provincia(id_p, provincia);
            Localidad l = new Localidad(id_l, localidad);
            Barrio b = new Barrio(id_b, barrio, l, prov);
            p = new Persona(idPersona, nombre, apellido, email, telefono, telefonoAlter, prov, l, b);

            rs.close();
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }

    public boolean ActualizarBarrioCorrecto(int idBarrio) {
        String condicion = "";
        
        System.out.println("id_barrio " + idBarrio);
        
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(10)\n"
                    + "exec dbo.ActualizarBarrioCorrecto ?, @salida output\n"
                    + "select @salida");
            pst.setInt(1, idBarrio);
            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);
            pst.close();
            Desconectar();
            System.out.println("condicion "+ condicion);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return condicion.equals("funciono");
    }

    //PARA OBTENER EL NOMBRE DE UN BARRIO, SIRVE PARA SABER SI ES OTRO BARRIO
    public String ObtenerNombreBarrio(int idBarrio) {
        String nombreBarrio = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select barrio from barrios where id_barrio = ?");

            pst.setInt(1, idBarrio);

            ResultSet rs = pst.executeQuery();
            rs.next();
            nombreBarrio = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return nombreBarrio;
    }

    public boolean DescartarBarrio(int idLocalidad, int idBarrioDescartado) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(" declare @salida varchar(50)\n"
                    + "exec dbo.DescartarBarrio ?, ?, @salida output\n"
                    + "select @salida");

            pst.setInt(1, idLocalidad);
            pst.setInt(2, idBarrioDescartado);
            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return condicion.equals("funciono");
    }

    public boolean CorregirBarrio(int idCboBarrio, int idBarrioDescartado, int idPerkins) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(" declare @salida varchar(50)\n"
                    + "exec dbo.CorregirBarrio ?, ?, ?, @salida output\n"
                    + "select @salida");

            pst.setInt(1, idCboBarrio);
            pst.setInt(2, idBarrioDescartado);
            pst.setInt(3, idPerkins);

            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (condicion.equals("funciono")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ABMProvinciaLocalidadBarrio(int id, int id_padre, String texto, String accion) {
        System.out.println("id: "+id +" id_padre: "+id_padre +" texto: "+texto +" accion: "+ accion);
        boolean exito = false;
        try {//agregar
            Conectar();
            PreparedStatement pst;
            if (accion.equals("agregarProvincia")) {
                pst = con.prepareStatement("insert into provincias values (?)");
                pst.setString(1, texto);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("agregarLocalidad")) {
                pst = con.prepareStatement("insert into localidades values (?,?)");
                pst.setInt(1, id_padre);
                pst.setString(2, texto);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("agregarBarrio")) {
                pst = con.prepareStatement("insert into barrios values (?,?,0)");
                pst.setInt(1, id_padre);
                pst.setString(2, texto);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            //modificar
            if (accion.equals("modificarProvincia")) {
                pst = con.prepareStatement("update provincias set provincia = ? where id_provincia = ?");
                pst.setString(1, texto);
                pst.setInt(2, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("modificarLocalidad")) {
                pst = con.prepareStatement("update localidades set localidad = ? where id_localidad = ?");
                pst.setString(1, texto);
                pst.setInt(2, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("modificarBarrio")) {
                pst = con.prepareStatement("update barrios set barrio = ? where id_barrio = ?");
                pst.setString(1, texto);
                pst.setInt(2, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            //eliminar
            if (accion.equals("eliminarProvincia")) {
                pst = con.prepareStatement("delete provincias where id_provincia = ?");
                pst.setInt(1, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("eliminarLocalidad")) {
                pst = con.prepareStatement("delete localidades where id_localidad = ?");
                pst.setInt(1, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            if (accion.equals("eliminarBarrio")) {
                pst = con.prepareStatement("delete barrios where id_barrio = ?");
                pst.setInt(1, id);
                int filas = pst.executeUpdate();
                pst.close();
                exito = filas > 0;
            }
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en ABMProvinciaLocalidadBarrio de GestorUbicacion: "+ex.toString());
        }
        return exito;
    }

}
