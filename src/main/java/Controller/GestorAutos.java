package Controller;

import Model.Auto;
import Model.Barrio;
import Model.MarcaAuto;
import Model.ModeloAuto;
import Model.Persona;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorAutos {

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

    public ArrayList<MarcaAuto> ListarMarcas() {
        ArrayList<MarcaAuto> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from marcas_autos");
            while (rs.next()) {
                int idMarca = rs.getInt(1);
                String marca = rs.getString(2);

                MarcaAuto ma = new MarcaAuto(idMarca, marca);

                lst.add(ma);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<ModeloAuto> ListarModelos() {
        ArrayList<ModeloAuto> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from modelos");
            while (rs.next()) {
                int idModelo = rs.getInt(1);
                String modelo = rs.getString(2);
                int idMarca = rs.getInt(3);

                ModeloAuto m = new ModeloAuto(idModelo, modelo, idMarca);

                lst.add(m);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public int NuevoModeloAutoCliente(ModeloAuto m) {
        int idBarrio = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(
                    "declare @idModelo int\n"
                    + "exec dbo.AModeloNuevo ?,?, @idModelo output\n"
                    + "select @idModelo");
            pst.setInt(1, m.getId_marcaAuto());
            pst.setString(2, m.getModeloAuto());
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

    public int NuevaMarcaAutoCliente(String marcaNueva) {
        int idMarca = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(
                    "declare @idMarca int\n"
                    + "exec dbo.AMarcaNueva ?, @idMarca output\n"
                    + "select @idMarca");
            pst.setString(1, marcaNueva);

            ResultSet rs = pst.executeQuery();
            rs.next();
            idMarca = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idMarca;
    }

    public boolean RegistrarAutoCliente(Auto nuevo, String accion) {
        String resultado = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(
                    "declare @resultado varchar(10)\n"
                    + "exec dbo.RegistrarAuto ?,?,?,?,?, @resultado output\n"
                    + "select @resultado");
            pst.setString(1, nuevo.getPatente());
            pst.setInt(2, nuevo.getId_cliente());
            pst.setInt(3, nuevo.getId_modelo());
            pst.setString(4, accion);
            pst.setInt(5, nuevo.getId_auto());

            ResultSet rs = pst.executeQuery();
            rs.next();
            resultado = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (resultado.equals("exito")) {
            return true;
        } else {
            return false;
        }
    }

    public int idAutoClienteFinal(Auto nuevo) {
        int idAuto = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement(
                    "declare @resultado varchar(10)\n"
                    + "exec dbo.idAutoClienteFinal ?,?, @resultado output\n"
                    + "select @resultado");
            pst.setString(1, nuevo.getPatente());
            pst.setInt(2, nuevo.getId_modelo());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idAuto = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idAuto;
    }

    public ArrayList<Auto> ListarAutosPorCliente(int id_cliente) {
        ArrayList<Auto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_auto, marca, modelo, patente \n"
                    + "from marcas_autos marca \n"
                    + "join modelos modelo on marca.id_marcaAuto = modelo.id_marcaAuto\n"
                    + "join autos a on a.id_modelo = modelo.id_modelo\n"
                    + "where id_cliente = ?");
            pst.setInt(1, id_cliente);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String marca = rs.getString(2);
                String modelo = rs.getString(3);
                String patente = rs.getString(4);
                

                Auto a = new Auto(id, patente, marca, modelo, id_cliente);

                lst.add(a);
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public int ObtenerIdAutoPorIdFactura(int id_factura) {
        int resultado = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_auto from facturas where id_factura = ?");
            pst.setInt(1, id_factura);
            ResultSet rs = pst.executeQuery();
            rs.next();
            resultado = rs.getInt(1);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return resultado;
    }

    public String obtenerMarcaModeloPorIdAuto(int idAuto) {
        String resultado = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select marca, modelo, patente from \n"
                    + "marcas_autos m \n"
                    + "join modelos mo  on m.id_marcaAuto = mo.id_marcaAuto \n"
                    + "join autos a on mo.id_modelo = a.id_modelo\n"
                    + "where id_auto = ?");
            pst.setInt(1, idAuto);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String marca = rs.getString(1);
            String modelo = rs.getString(2);
            String patente = rs.getString(3);

            resultado = marca + " " + modelo + "; Patente: " + patente;

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return resultado;
    }
    
    public String ObtenerStrngDeAuto(int idAuto) {
        String resultado = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select marca, modelo, patente from \n"
                    + "marcas_autos m \n"
                    + "join modelos mo  on m.id_marcaAuto = mo.id_marcaAuto \n"
                    + "join autos a on mo.id_modelo = a.id_modelo\n"
                    + "where id_auto = ?");
            pst.setInt(1, idAuto);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String marca = rs.getString(1);
            String modelo = rs.getString(2);
            String patente = rs.getString(3);

            resultado = marca + " " + modelo + " PATENTE " + patente;

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return resultado;
    }

    public ArrayList<Auto> ListarAutos() {
        ArrayList<Auto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_auto, marca, modelo, patente , id_cliente\n"
                    + "from marcas_autos marca \n"
                    + "join modelos modelo on marca.id_marcaAuto = modelo.id_marcaAuto\n"
                    + "join autos a on a.id_modelo = modelo.id_modelo");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String marca = rs.getString(2);
                String modelo = rs.getString(3);
                String patente = rs.getString(4);
                int id_c = rs.getInt(5);

                Auto a = new Auto(id, patente, marca, modelo, id_c);

                lst.add(a);
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public String obtenerNombreDePersonaPorIdCliente(int id_cliente) {

        String nombreApellido = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select nombre, apellido \n"
                    + "from personas p join clientes c on p.id_persona  = c.id_persona\n"
                    + "where id_cliente = ?");
            pst.setInt(1, id_cliente);

            ResultSet rs = pst.executeQuery();
            rs.next();

            String nombre = rs.getString(1);
            String apellido = rs.getString(2);

            nombreApellido = nombre + " " + apellido;

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return nombreApellido;
    }

    public boolean EliminarMarca(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete marcas_autos where id_marcaAuto = ?");
            pst.setInt(1, id);
            filas = pst.executeUpdate();

            exito = filas > 0;

            pst.close();
            Desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean EliminarModelo(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete modelos where id_modelo = ?");
            pst.setInt(1, id);
            filas = pst.executeUpdate();

            exito = filas > 0;

            pst.close();
            Desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean AltaModificacionModelosMarcas(int id, String nuevoRegistro, String accion, int id_marca) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst;
            if (accion.equals("modificarMarca")) {
                pst = con.prepareStatement("update marcas_autos set marca = ? where id_marcaAuto = ?");
                pst.setString(1, nuevoRegistro);
                pst.setInt(2, id);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("modificarModelo")) {
                pst = con.prepareStatement("update modelos set modelo = ? where id_modelo = ?");
                pst.setString(1, nuevoRegistro);
                pst.setInt(2, id);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("altaMarca")) {
                pst = con.prepareStatement("insert into marcas_autos values (?,0)");
                pst.setString(1, nuevoRegistro);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("altaModelo")) {
                pst = con.prepareStatement("insert into modelos values (?,?,0)");
                pst.setString(1, nuevoRegistro);
                pst.setInt(2, id_marca);
                filas = pst.executeUpdate();
                pst.close();
            }
            exito = filas > 0;
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Excepcion en AltaModificacionModelosMarcas en GestorAutos: " + ex);
        }
        return exito;
    }

    public ArrayList<ModeloAuto> ListarMarcasConModelos(String buscar) {
        ArrayList<ModeloAuto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            ResultSet rs;
            if (buscar.equals("")) {
                pst = con.prepareStatement("select ma.id_marcaAuto, marca, id_modelo, modelo \n"
                        + "from marcas_autos ma join modelos mo on mo.id_marcaAuto = ma.id_marcaAuto");
                rs = pst.executeQuery();
            } else {
                pst = con.prepareStatement("select ma.id_marcaAuto, marca, id_modelo, modelo \n"
                        + "from marcas_autos ma join modelos mo on mo.id_marcaAuto = ma.id_marcaAuto\n"
                        + "where modelo like '%'+?+'%'");
                pst.setString(1, buscar);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                int idMarca = rs.getInt(1);
                String marca = rs.getString(2);
                int idModelo = rs.getInt(3);
                String modelo = rs.getString(4);

                ModeloAuto modeloAuto = new ModeloAuto(idMarca, marca, idModelo, modelo);

                lst.add(modeloAuto);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public Auto ObtenerAuto(int id_auto) {
        Auto a = null;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("select a.*, id_marcaAuto from autos a join modelos m on m.id_modelo = a.id_modelo where id_auto = ?");
            pst.setInt(1, id_auto);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String patente = rs.getString(2);
            int id_cliente = rs.getInt(3);
            int id_modelo = rs.getInt(4);
            int id_marca = rs.getInt(5);
            a = new Auto(id_auto, patente, id_cliente, id_modelo, id_marca);
            rs.close();
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println("Problemas en ObtenerAuto GestorAutos: " + ex.toString());
        }
        return a;
    }

    public ArrayList<MarcaAuto> ListarMarcasParametrizadas(String buscar) {
        ArrayList<MarcaAuto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            ResultSet rs;
            if (buscar.equals("")) {
                pst = con.prepareStatement("select * from marcas_autos");
                rs = pst.executeQuery();
            } else {
                pst = con.prepareStatement("select * from marcas_autos \n"
                        + "where marca like '%'+?+'%'");
                pst.setString(1, buscar);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                int idMarca = rs.getInt(1);
                String marca = rs.getString(2);

                MarcaAuto m = new MarcaAuto(idMarca, marca);

                lst.add(m);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public boolean EliminarAuto(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete autos where id_auto = ?");
            pst.setInt(1, id);
            filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

}
