package Controller;

import Model.MarcaRepuesto;
import Model.Reporte;
import Model.Repuesto;
import Model.Rubro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorRepuesto {

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

    public ArrayList<Repuesto> ListarRepuestosBuenStock() {
        ArrayList<Repuesto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select repuesto, stock from repuestos where stock  > stock_min order by 2");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String repuesto = rs.getString(1);
                int stock = rs.getInt(2);                

                Repuesto r = new Repuesto(repuesto, stock);

                lst.add(r);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }
    
    public ArrayList<Repuesto> ListarRepuestosBajoStock() {
        ArrayList<Repuesto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select repuesto, stock from repuestos where stock  <= stock_min order by 2");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String repuesto = rs.getString(1);
                int stock = rs.getInt(2);                

                Repuesto r = new Repuesto(repuesto, stock);

                lst.add(r);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }
    

    public ArrayList<Repuesto> ListarRepuestos(int eleccion) {
        ArrayList<Repuesto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            if (eleccion == 0) {
                //listado por precio
                pst = con.prepareStatement("select \n"
                        + "id_repuesto,\n"
                        + "marcaRepuesto,\n"
                        + "rubro,\n"
                        + "repuesto,\n"
                        + "descripcion,\n"
                        + "stock,\n"
                        + "precio,\n"
                        + "foto, \n"
                        + "rubro.id_rubro \n"
                        + "from repuestos r\n"
                        + "join marcas_repuestos mr on r.id_marcaRepuesto = mr.id_marcaRepuesto\n"
                        + "join rubros rubro on rubro.id_rubro = r.id_rubro\n"
                        + "order by precio");

            } else if (eleccion == 1) {
                //listado por rubros
                pst = con.prepareStatement("select \n"
                        + "id_repuesto,\n"
                        + "marcaRepuesto,\n"
                        + "rubro,\n"
                        + "repuesto,\n"
                        + "descripcion,\n"
                        + "stock,\n"
                        + "precio,\n"
                        + "foto, \n"
                        + "rubro.id_rubro \n"
                        + "from repuestos r\n"
                        + "join marcas_repuestos mr on r.id_marcaRepuesto = mr.id_marcaRepuesto\n"
                        + "join rubros rubro on rubro.id_rubro = r.id_rubro\n"
                        + "order by rubro");

            } else {
                //listado por marcas
                pst = con.prepareStatement("select \n"
                        + "id_repuesto,\n"
                        + "marcaRepuesto,\n"
                        + "rubro,\n"
                        + "repuesto,\n"
                        + "descripcion,\n"
                        + "stock,\n"
                        + "precio,\n"
                        + "foto, \n"
                        + "rubro.id_rubro \n"
                        + "from repuestos r\n"
                        + "join marcas_repuestos mr on r.id_marcaRepuesto = mr.id_marcaRepuesto\n"
                        + "join rubros rubro on rubro.id_rubro = r.id_rubro\n"
                        + "order by marcaRepuesto");
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String marcaRepuesto = rs.getString(2);
                String rubro = rs.getString(3);
                String repuesto = rs.getString(4);
                String descripcion = rs.getString(5);
                int stock = rs.getInt(6);
                double precio = rs.getDouble(7);
                String foto = rs.getString(8);

                MarcaRepuesto mr = new MarcaRepuesto(marcaRepuesto);
                Rubro rubrosky = new Rubro(rubro);
                rubrosky.setId(rs.getInt(9));

                Repuesto r = new Repuesto(id, mr, rubrosky, repuesto, descripcion, stock, precio, foto);

                lst.add(r);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<Repuesto> ListarRepuestosParametrizados(int idMarca, int idRubro, boolean ordenPrecio) {
        ArrayList<Repuesto> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("exec RepuestosParametrizados ?,?,?");
            pst.setInt(1, idMarca);
            pst.setInt(2, idRubro);
            pst.setBoolean(3, ordenPrecio);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String marcaRepuesto = rs.getString(2);
                String rubro = rs.getString(3);
                String repuesto = rs.getString(4);
                String descripcion = rs.getString(5);
                int stock = rs.getInt(6);
                double precio = rs.getDouble(7);
                String foto = rs.getString(8);

                MarcaRepuesto mr = new MarcaRepuesto(marcaRepuesto);
                Rubro rubrosky = new Rubro(rubro);
                rubrosky.setId(rs.getInt(9));

                Repuesto r = new Repuesto(id, mr, rubrosky, repuesto, descripcion, stock, precio, foto);

                lst.add(r);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public Repuesto obtenerRepuesto(int id) {
        Repuesto r = null;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("select * from repuestos where id_repuesto = ?");
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_r = rs.getInt(1);
                int id_marca = rs.getInt(2);
                int id_rubro = rs.getInt(3);
                String repuesto = rs.getString(4);
                String descripcion = rs.getString(5);
                int stock = rs.getInt(6);
                double precio = rs.getDouble(7);
                String foto = rs.getString(8);

                MarcaRepuesto mr = new MarcaRepuesto(id_marca);
                Rubro rubrosky = new Rubro(id_rubro);
                r = new Repuesto(id_r, mr, rubrosky, repuesto, descripcion, stock, precio, foto);

            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return r;
    }

    public ArrayList<MarcaRepuesto> ListarMarcasRepuesto() {
        ArrayList<MarcaRepuesto> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from marcas_repuestos");
            while (rs.next()) {
                int id = rs.getInt(1);
                String marcaRepuesto = rs.getString(2);

                MarcaRepuesto mr = new MarcaRepuesto(id, marcaRepuesto);
                lst.add(mr);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<Rubro> ListarRubros() {
        ArrayList<Rubro> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from rubros");
            while (rs.next()) {
                int id = rs.getInt(1);
                String rubro = rs.getString(2);

                Rubro r = new Rubro(id, rubro);
                lst.add(r);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public boolean AltaModificacionRubrosMarcas(int id, String nuevoRegistro, String accion) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst;
            if (accion.equals("modificarMarca")) {
                pst = con.prepareStatement("update marcas_repuestos set marcaRepuesto = ? where id_marcaRepuesto = ?");
                pst.setString(1, nuevoRegistro);
                pst.setInt(2, id);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("modificarRubro")) {
                pst = con.prepareStatement("update rubros set rubro = ? where id_rubro = ?");
                pst.setString(1, nuevoRegistro);
                pst.setInt(2, id);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("altaMarca")) {
                pst = con.prepareStatement("insert into marcas_repuestos values (?)");
                pst.setString(1, nuevoRegistro);
                filas = pst.executeUpdate();
                pst.close();
            }
            if (accion.equals("altaRubro")) {
                pst = con.prepareStatement("insert into rubros values (?)");
                pst.setString(1, nuevoRegistro);
                filas = pst.executeUpdate();
                pst.close();
            }
            exito = filas > 0;
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Excepcion en AltaModificacionRubrosMarcas " + ex);
        }
        return exito;
    }

    public void NuevoRepuesto(Repuesto r) {
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("insert into repuestos values (?,?,?,?,0,?,?,?)");
            pst.setInt(1, r.getMr().getId_marcaRepuesto());
            pst.setInt(2, r.getR().getId());
            pst.setString(3, r.getRepuesto());
            pst.setString(4, r.getDescripcion());
            pst.setDouble(5, r.getPrecio());
            pst.setString(6, r.getFoto());
            pst.setInt(7, r.getStock_min());
            pst.executeUpdate();

            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void ModificarRepuesto(Repuesto r) {
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("update repuestos set id_marcaRepuesto =  ?, id_rubro = ?, repuesto = ?, descripcion = ?, precio = ?, foto = ? where id_repuesto = ?");
            pst.setInt(1, r.getMr().getId_marcaRepuesto());
            pst.setInt(2, r.getR().getId());
            pst.setString(3, r.getRepuesto());
            pst.setString(4, r.getDescripcion());
            pst.setDouble(5, r.getPrecio());
            pst.setString(6, r.getFoto());
            pst.setInt(7, r.getId_repuesto());
            pst.executeUpdate();

            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public boolean EliminarRepuesto(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete repuestos where id_repuesto = ?");
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

    public boolean EliminarMarca(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete marcas_repuestos where id_marcarepuesto = ?");
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

    public boolean EliminarRubro(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete rubros where id_rubro = ?");
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
