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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorPersonas {

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

    //---------------------------------------LISTADOS---------------------------
    //TRAER ADMINISTRADORES
    public ArrayList<Usuario> ListarAdministradores() {
        ArrayList<Usuario> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select \n"
                    + "id_usuario,\n"
                    + "tc.id_tipousuario,\n"
                    + "tc.usuario,\n"
                    + "fechaIngreso,\n"
                    + "p.id_persona,	\n"
                    + "nombre,\n"
                    + "apellido,\n"
                    + "email,\n"
                    + "pass,\n"
                    + "telefono,\n"
                    + "telefonoAlter\n"
                    + "from personas p \n"
                    + "join usuarios c on p.id_persona = c.id_persona\n"
                    + "join tipos_usuarios tc on tc.id_tipousuario = c.id_tipousuario\n"
                    + "where tc.id_tipousuario = 1");
            while (rs.next()) {
                int idUsuario = rs.getInt(1);
                int idtu = rs.getInt(2);
                String tipoUsuario = rs.getString(3);
                Date fechaIngreso = rs.getDate(4);
                int idPersona = rs.getInt(5);
                String nombre = rs.getString(6);
                String apellido = rs.getString(7);
                String email = rs.getString(8);
                String pass = rs.getString(9);
                String telefono = rs.getString(10);
                String telefonoAlter = rs.getString(11);

                TipoUsuario tu = new TipoUsuario(idtu, tipoUsuario);
                Persona p = new Persona(idPersona, nombre, apellido, email, pass, telefono, telefonoAlter);
                Usuario u = new Usuario(idUsuario, tu, p, fechaIngreso);

                lst.add(u);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return lst;
    }

    //TRAER OPERADORES
    public ArrayList<Usuario> ListarOperadores() {
        ArrayList<Usuario> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select \n"
                    + "id_usuario,\n"
                    + "tc.id_tipousuario,\n"
                    + "tc.usuario,\n"
                    + "fechaIngreso,\n"
                    + "p.id_persona,	\n"
                    + "nombre,\n"
                    + "apellido,\n"
                    + "email,\n"
                    + "pass,\n"
                    + "telefono,\n"
                    + "telefonoAlter\n"
                    + "from personas p \n"
                    + "join usuarios c on p.id_persona = c.id_persona\n"
                    + "join tipos_usuarios tc on tc.id_tipousuario = c.id_tipousuario\n"
                    + "where tc.id_tipousuario = 2");
            while (rs.next()) {
                int idUsuario = rs.getInt(1);
                int idtu = rs.getInt(2);
                String tipoUsuario = rs.getString(3);
                Date fechaIngreso = rs.getDate(4);
                int idPersona = rs.getInt(5);
                String nombre = rs.getString(6);
                String apellido = rs.getString(7);
                String email = rs.getString(8);
                String pass = rs.getString(9);
                String telefono = rs.getString(10);
                String telefonoAlter = rs.getString(11);

                TipoUsuario tu = new TipoUsuario(idtu, tipoUsuario);
                Persona p = new Persona(idPersona, nombre, apellido, email, pass, telefono, telefonoAlter);
                Usuario u = new Usuario(idUsuario, tu, p, fechaIngreso);

                lst.add(u);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    //TRAER CLIENTES
    public ArrayList<Cliente> ListarClientes() {
        ArrayList<Cliente> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select \n"
                    + "id_cliente,\n"
                    + "tc.id_tipoCliente,\n"
                    + "tipo,\n"
                    + "fechaIngreso,\n"
                    + "p.id_persona,	\n"
                    + "nombre,\n"
                    + "apellido,\n"
                    + "email,\n"
                    + "pass,\n"
                    + "telefono,\n"
                    + "telefonoAlter\n"
                    + "from personas p \n"
                    + "join clientes c on p.id_persona = c.id_persona\n"
                    + "join tipo_clientes tc on tc.id_tipocliente = c.id_tipocliente");
            while (rs.next()) {
                int idCliente = rs.getInt(1);
                int idtc = rs.getInt(2);
                String tipoCliente = rs.getString(3);
                Date fechaIngreso = rs.getDate(4);
                int idPersona = rs.getInt(5);
                String nombre = rs.getString(6);
                String apellido = rs.getString(7);
                String email = rs.getString(8);
                String pass = rs.getString(9);
                String telefono = rs.getString(10);
                String telefonoAlter = rs.getString(11);

                TipoCliente tc = new TipoCliente(idtc, tipoCliente);
                Persona p = new Persona(idPersona, nombre, apellido, email, pass, telefono, telefonoAlter);
                Cliente c = new Cliente(idCliente, p, tc, fechaIngreso);

                lst.add(c);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return lst;
    }

    public boolean EliminarCliente(int id) {
        boolean exito = false;
        //la primer condicion es para no borrar al cliente final con id 48
        if (id != 48) {
            try {
                Conectar();
                try {
                    PreparedStatement pst;
                    pst = con.prepareStatement("select count(*) \n"
                            + "from clientes c \n"
                            + "join facturas f on f.id_cliente = c.id_cliente where c.id_cliente = ?");
                    pst.setInt(1, id);
                    ResultSet rs = pst.executeQuery();
                    rs.next();
                    int resultado = rs.getInt(1);
                    exito = resultado == 0;
                    pst.close();
                } catch (Exception ex) {
                    System.out.println("Error en primer try del EliminarCliente: " + ex.toString());
                }
                if (exito) {
                    try {
                        //borramos a cliente de tabla autos                
                        PreparedStatement pst;
                        pst = con.prepareStatement("select count(*) from clientes c join autos a on a.id_cliente = c.id_cliente\n"
                                + "where c.id_cliente = ?");
                        pst.setInt(1, id);
                        ResultSet rs = pst.executeQuery();
                        rs.next();
                        int resultado = rs.getInt(1);
                        if (resultado > 0) {
                            PreparedStatement pstCA;
                            pstCA = con.prepareStatement("delete autos where id_cliente = ?");
                            pstCA.setInt(1, id);
                            int filas = pstCA.executeUpdate();
                            pstCA.close();
                            exito = filas > 0;
                        }
                        rs.close();

                        if (exito) {
                            pst = con.prepareStatement("delete clientes where id_cliente = ?");
                            pst.setInt(1, id);
                            int filas = pst.executeUpdate();
                            exito = filas > 0;
                        }
                        pst.close();

                    } catch (Exception ex) {
                        System.out.println("Error en el segundo try de EliminarCliente: " + ex.toString());
                    }
                }
                Desconectar();

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } else {
            exito = false;
        }
        return exito;
    }

    //TRAER PERSONAS PARA EL ADMINISTRADO DE EMPLEADOS
    public ArrayList<Persona> ListarPersonas() {
        ArrayList<Persona> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select \n"
                    + "                    b.id_barrio,\n"
                    + "                    barrio,\n"
                    + "                    l.id_localidad,\n"
                    + "                    localidad,\n"
                    + "                    pr.id_provincia,\n"
                    + "                    provincia,\n"
                    + "                    p.id_persona, \n"
                    + "                    nombre, \n"
                    + "                    apellido,\n"
                    + "                    domicilio,\n"
                    + "                    telefono,\n"
                    + "                    telefonoalter,\n"
                    + "                    email, \n"
                    + "                    pass, \n"
                    + "                    cp\n"
                    + "                    from personas p \n"
                    + "                    join barrios b on b.id_barrio = p.id_barrio \n"
                    + "                    join localidades l on l.id_localidad = b.id_localidad \n"
                    + "                    join provincias pr on pr.id_provincia = l.id_provincia\n"
                    + "		     where not exists ( select u.id_persona \n"
                    + "					from usuarios u\n"
                    + "					where u.id_persona = p.id_persona )");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idBarrio = rs.getInt(1);
                String barrio = rs.getString(2);
                int idLocalidad = rs.getInt(3);
                String localidad = rs.getString(4);
                int idProvincia = rs.getInt(5);
                String provincia = rs.getString(6);
                int idP = rs.getInt(7);
                String nombre = rs.getString(8);
                String apellido = rs.getString(9);
                String domicilio = rs.getString(10);
                String telefono = rs.getString(11);
                String telAlter = rs.getString(12);
                String email = rs.getString(13);
                String pass = rs.getString(14);
                String cp = rs.getString(15);

                Barrio b = new Barrio(idBarrio, barrio);
                Localidad l = new Localidad(idLocalidad, localidad);
                Provincia pr = new Provincia(idProvincia, provincia);

                lst.add(new Persona(l, pr, b, idP, nombre, apellido, domicilio, telefono, telAlter, email, pass, cp));
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;

    }

    //TRAER TIPOS DE USUARIOS
    public ArrayList<TipoUsuario> ListarTipoUsuarios() {
        ArrayList<TipoUsuario> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from tipos_usuarios");
            while (rs.next()) {
                int id_tipousuario = rs.getInt(1);
                String usuario = rs.getString(2);
                TipoUsuario tu = new TipoUsuario(id_tipousuario, usuario);
                lst.add(tu);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return lst;
    }

    //TRAER TIPOS DE CLIENTES
    public ArrayList<TipoCliente> ListarTipoClientes() {
        ArrayList<TipoCliente> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from tipo_clientes");
            while (rs.next()) {
                int id_tipoct = rs.getInt(1);
                String tipoCliente = rs.getString(2);
                TipoCliente tc = new TipoCliente(id_tipoct, tipoCliente);
                lst.add(tc);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public Persona obtenerPersona(int id) {
        Persona p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select \n"
                    + "b.id_barrio,\n"
                    + "barrio,\n"
                    + "l.id_localidad,\n"
                    + "localidad,\n"
                    + "pr.id_provincia,\n"
                    + "provincia,\n"
                    + "p.id_persona, \n"
                    + "nombre, \n"
                    + "apellido,\n"
                    + "domicilio,\n"
                    + "telefono,\n"
                    + "telefonoalter,\n"
                    + "email, \n"
                    + "pass, \n"
                    + "cp \n"
                    + "from personas p \n"
                    + "join barrios b on b.id_barrio = p.id_barrio \n"
                    + "join localidades l on l.id_localidad = b.id_localidad \n"
                    + "join provincias pr on pr.id_provincia = l.id_provincia "
                    + "where id_persona = ?");
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            rs.next();

            int idBarrio = rs.getInt(1);
            String barrio = rs.getString(2);
            int idLocalidad = rs.getInt(3);
            String localidad = rs.getString(4);
            int idProvincia = rs.getInt(5);
            String provincia = rs.getString(6);
            int idP = rs.getInt(7);
            String nombre = rs.getString(8);
            String apellido = rs.getString(9);
            String domicilio = rs.getString(10);
            String telefono = rs.getString(11);
            String telAlter = rs.getString(12);
            String email = rs.getString(13);
            String pass = rs.getString(14);
            String cp = rs.getString(15);

            Barrio b = new Barrio(idBarrio, barrio);
            Localidad l = new Localidad(idLocalidad, localidad);
            Provincia pr = new Provincia(idProvincia, provincia);

            p = new Persona(l, pr, b, idP, nombre, apellido, domicilio, telefono, telAlter, email, pass, cp);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }

    public Persona obtenerNombreApellidoPorIdCliente(int idCliente) {
        Persona p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select nombre, apellido from personas p join clientes c on p.id_persona = c.id_persona where id_cliente = ?");
            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String nombre = rs.getString(1);
            String apellido = rs.getString(2);

            p = new Persona(nombre, apellido);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }
    public Persona obtenerNombreApellidoPorIdEmpleado(int id) {
        Persona p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select nombre, apellido from personas p join usuarios u on p.id_persona = u.id_persona where id_usuario = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String nombre = rs.getString(1);
            String apellido = rs.getString(2);

            p = new Persona(nombre, apellido);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }

    public int obtenerIdClientePorIdPersona(int idPersona) {
        int idCt = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_cliente from clientes c where c.id_persona = ?");
            pst.setInt(1, idPersona);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int id = rs.getInt(1);

            idCt = id;

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idCt;
    }

    public int obtenerDescuentoCtPorIdCt(int idCliente) {
        int idCt = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select descuento from clientes c join tipo_clientes tc on c.id_tipocliente = tc.id_tipocliente where id_cliente = ?");
            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int id = rs.getInt(1);

            idCt = id;

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idCt;
    }

    public boolean AMPersona(Persona p, String usuario, String accion) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @test varchar(10)\n"
                    + "                                       EXEC	[dbo].[AMB_USUARIOS]\n"
                    + "                                       @tipoUsuario = '" + usuario
                    + "',                                      @tipoGestion = '" + accion
                    + "',                                      @id_persona = ?,\n"
                    + "                                       @nombre = ?,\n"
                    + "                                       @apellido = ?,\n"
                    + "                                       @id_barrio = ?,\n"
                    + "                                       @domicilio = ?,\n"
                    + "                                       @telefono = ?,\n"
                    + "                                       @telefonoAlter = ?,\n"
                    + "                                       @email = ?,\n"
                    + "                                       @pass = ?,\n"
                    + "                                       @cp = ?,\n"
                    + "                                       @exito = @test OUTPUT\n"
                    + "                                       if @test = 1\n"
                    + "                                       set @test = 'funciono'\n"
                    + "                                       else \n"
                    + "                                       set @test = 'no funciono'\n"
                    + "                                       select @test as 'test'");

            if (accion.equals("alta")) {
                pst.setString(1, null);
            } else {
                pst.setInt(1, p.getId());
            }
            pst.setString(2, p.getNombre());
            pst.setString(3, p.getApellido());
            pst.setInt(4, p.getBarrio().getId_barrio());
            pst.setString(5, p.getDomicilio());
            pst.setString(6, p.getTelefono());
            pst.setString(7, p.getTel_alternativo());
            pst.setString(8, p.getEmail());
            pst.setString(9, p.getPass());
            pst.setString(10, p.getCp());

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

    //ADMINISTRAR EMPLEADOS  (MODIFICAR UNO, O HACER EMPLEADO A UNA PERSONA REGISTRADA)
    public boolean AM_empleados(Usuario u) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salidas varchar(5)\n"
                    + "exec dbo.[AM_empleados] ?,?, @salida = @salidas output\n"
                    + "select @salidas");
            pst.setInt(1, u.p.getId());
            pst.setInt(2, u.getTu().getId_tipousuario());

            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (condicion.equals("exito")) {
            return true;
        } else {
            return false;
        }
    }

    //PARA ADMINISTRAR CLIENTES (MODIFICAR UNO, O HACER CLIENTE A UNA PERSONA REGISTRADA)
    public boolean AM_clientes(Cliente c) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salidas varchar(5)\n"
                    + "exec dbo.[AM_clientes] ?,?, @salida = @salidas output\n"
                    + "select @salidas");
            pst.setInt(1, c.p.getId());
            pst.setInt(2, c.tc.getIdtipo());
            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (condicion.equals("exito")) {
            return true;
        } else {
            return false;
        }
    }
}
