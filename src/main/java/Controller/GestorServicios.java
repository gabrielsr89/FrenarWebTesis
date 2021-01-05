package Controller;

import Model.Categorias;
import Model.DetallePedidoServicio;
import Model.Factura;
import Model.Reporte;
import Model.Servicio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class GestorServicios {

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

    //TRAER SERVICIOS POR ID_CATEGORÍA
    public ArrayList<Servicio> TraerServiciosPorCategoria(int id_categoria) {
        ArrayList<Servicio> lista = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select "
                    + "id_servicio,\n"
                    + "servicio,\n"
                    + "descripcion,\n"
                    + "tiempo,\n"
                    + "precio,\n"
                    + "categoria\n"
                    + "from servicios s join categorias c on s.id_categoria = c.id_categoria\n"
                    + "where c.id_categoria = ?");
            pst.setInt(1, id_categoria);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_s = rs.getInt(1);
                String servicio = rs.getString(2);
                String descripcion = rs.getString(3);
                int tiempo = rs.getInt(4);
                double precio = rs.getDouble(5);
                String categoria = rs.getString(6);

                Categorias c = new Categorias(id_categoria, categoria);
                Servicio s = new Servicio(id_s, c, servicio, descripcion, tiempo, precio);

                lista.add(s);
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lista;
    }

    //TRAER LAS CATEGORIAS
    public ArrayList<Categorias> TraerCategorias() {
        ArrayList<Categorias> lista = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from categorias");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_c = rs.getInt(1);
                String categoria = rs.getString(2);
                Categorias c = new Categorias(id_c, categoria);
                lista.add(c);
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lista;
    }

    public ArrayList<Servicio> TraerServicios() {
        ArrayList<Servicio> lista = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_servicio,\n"
                    + "servicio,\n"
                    + "descripcion,\n"
                    + "tiempo,\n"
                    + "precio,  \n"
                    + "c.id_categoria,                 \n"
                    + "categoria\n"
                    + "from servicios s \n"
                    + "join categorias c on s.id_categoria = c.id_categoria "
                    + "where c.id_categoria != 5");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_s = rs.getInt(1);
                String servicio = rs.getString(2);
                String descripcion = rs.getString(3);
                int tiempo = rs.getInt(4);
                double precio = rs.getDouble(5);
                int id_categoria = rs.getInt(6);
                String categoria = rs.getString(7);

                Categorias c = new Categorias(id_categoria, categoria);
                Servicio s = new Servicio(id_s, c, servicio, descripcion, tiempo, precio);

                lista.add(s);
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lista;
    }

    public boolean EliminarServicio(int id_servicio) {
        String consulta = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(10)\n"
                    + "exec dbo.EliminarServicio ?, @salida output\n"
                    + "select @salida");

            pst.setInt(1, id_servicio);

            ResultSet rs = pst.executeQuery();
            rs.next();
            consulta = rs.getString(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (consulta.equals("exito")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean NuevoServicio(Servicio s) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(10)\n"
                    + "exec dbo.AgregarServicio ?,?,?,?, @salida output\n"
                    + "select @salida");
            pst.setString(1, s.getServicio());
            pst.setInt(2, s.getTiempo());
            pst.setInt(3, s.getId_categoria());
            pst.setString(4, s.getDescripcion());

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

    public int IdNuevoServicio(Servicio s) {
        int idNuevoServicio = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int\n"
                    + "exec dbo.IdNuevoServicio ?,?,?, @salida output\n"
                    + "select @salida");
            pst.setString(1, s.getServicio());
            pst.setInt(2, s.getTiempo());
            pst.setString(3, s.getDescripcion());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idNuevoServicio = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idNuevoServicio;
    }

    public boolean ModificarServicio(Servicio s) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(10)\n"
                    + "exec dbo.ModificarServicio ?,?,?,?,?, @salida output\n"
                    + "select @salida");
            pst.setInt(1, s.getId_servicio());
            pst.setString(2, s.getServicio());
            pst.setInt(3, s.getTiempo());
            pst.setInt(4, s.getId_categoria());
            pst.setString(5, s.getDescripcion());

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

    public Servicio obtenerServicio(int id) {
        Servicio s = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from servicios where id_servicio = ?");
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            rs.next();
            int id_servicio = rs.getInt(1);
            String servicio = rs.getString(2);
            int tiempo = rs.getInt(3);
            double precio = rs.getDouble(4);
            int categoria = rs.getInt(5);
            String descripcion = rs.getString(6);

            s = new Servicio(id_servicio, servicio, tiempo, precio, categoria, descripcion);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return s;
    }

    public boolean VerificarServiciosPorFacturas(int id_factura) {
        //SI RETORNA FALSO ES PORQUE TODAVÍA NO SE TERMINARON DE REPARAR
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select o.id_estado from detalle_pedidoServicio d \n"
                    + "join facturas f on d.id_factura = f.id_factura\n"
                    + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                    + "where f.id_estado = 2 and f.id_factura = ?");
            pst.setInt(1, id_factura);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int x = rs.getInt(1);
                if (x != 3) {
                    return false;
                }
            }
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return true;
    }

    public boolean FinalizarServicio(int id_detalle, int id_factura) {
        String condicion = "";
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;

            pst = con.prepareStatement("update ordenTrabajo set id_estado = 3, fin = getdate() where id_detalleServicio = ?");
            pst.setInt(1, id_detalle);
            int filas = pst.executeUpdate();
            exito = filas >= 1;
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        //VERIFICAR SI CON ESTA FINALIZACIÓN SE COMPLETA LOS SERVICIOS DE LA FT      
        boolean terminado = false;
        try {
            ArrayList<Integer> lstVerificar = new ArrayList<>();

            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select o.id_estado from ordenTrabajo o \n"
                    + "join detalle_pedidoServicio d on o.id_detalleServicio = d.id_detalleServicio \n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "where f.id_factura = ?");
            pst.setInt(1, id_factura);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_estado = rs.getInt(1);
                lstVerificar.add(id_estado);
            }
            for (Integer id_estado : lstVerificar) {
                terminado = id_estado >= 3;
            }
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Exepción " + ex);
        }
        try {
            if (terminado) {
                //UPGRADE EL ESTADO DE LA FT  
                Conectar();
                PreparedStatement pst;
                pst = con.prepareStatement("update facturas set id_estado = 7 where id_factura = ?");
                pst.setInt(1, id_factura);
                int filas = pst.executeUpdate();

                pst.close();
                Desconectar();
                if (filas > 0) {
                    exito = true;
                }
            }
        } catch (Exception ex) {
            System.out.println("Exepción: " + ex);
        }

        return exito;
    }

    public String TomarServicio(int id_detalle, int id_usuario) {
        String condicion = "";
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(50)\n"
                    + "exec dbo.TomarOrden ?,?, @salida output\n"
                    + "select @salida");
            pst.setInt(1, id_detalle);
            pst.setInt(2, id_usuario);

            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);

            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return condicion;
    }

    public ArrayList<DetallePedidoServicio> obtenerServiciosSegúnId(ArrayList<DetallePedidoServicio> lst) {
        ArrayList<DetallePedidoServicio> lista = new ArrayList<DetallePedidoServicio>();
        try {
            Conectar();
            for (int i = 0; i < lst.size(); i++) {
                if (lst.get(i).getId_servicio() != 0) {
                    PreparedStatement pst;
                    pst = con.prepareStatement("select * from servicios where id_servicio = ?");
                    pst.setInt(1, lst.get(i).getId_servicio());
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String servicio = rs.getString(2);
                        int tiempo = rs.getInt(3);
                        double precio = rs.getDouble(4);
                        int id_categoria = rs.getInt(5);
                        String descripcion = rs.getString(6);

                        Servicio s = new Servicio(id, servicio, tiempo, precio, id_categoria, descripcion);

                        DetallePedidoServicio dps = new DetallePedidoServicio(lst.get(i).getId_factura(), id, precio, s);

                        lista.add(dps);
                    }
                    rs.close();
                    pst.close();
                }
            }
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lista;
    }

    public boolean EliminarCategoria(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete categorias where id_categoria = ?");
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

    public boolean NuevaCategoria(String categoria) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("insert into categorias values (?)");
            pst.setString(1, categoria);
            filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean ModificarCategoria(int id, String categoria) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("update categorias set categoria = ? where id_categoria = ?");
            pst.setString(1, categoria);
            pst.setInt(2, id);
            filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public ArrayList<Reporte> ClientesPorServicio(int id_estado) {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            if (id_estado == 3) {
                pst = con.prepareStatement("select nombre+' '+apellido Cliente, marca+' '+modelo Vehiculo, servicio, estado, o.inicio, o.fin from personas p \n"
                        + "join clientes c on p.id_persona = c.id_persona\n"
                        + "join facturas f on f.id_cliente =  c.id_cliente\n"
                        + "join autos a on a.id_auto = f.id_auto\n"
                        + "join modelos m on m.id_modelo = a.id_modelo\n"
                        + "join marcas_autos ma on ma.id_marcaAuto = m.id_marcaAuto   \n"
                        + "join detalle_pedidoServicio d on d.id_factura = f.id_factura\n"
                        + "join servicios s on s.id_servicio = d.id_servicio\n"
                        + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                        + "join estadosOrdenTrabajo e on e.id_estado = o.id_estado \n"
                        + "where e.id_estado = ? and f.id_estado = 7");
                pst.setInt(1, id_estado);
                
            } else {
                pst = con.prepareStatement("select nombre+' '+apellido Cliente, marca+' '+modelo Vehiculo, servicio, estado, o.inicio, o.fin from personas p \n"
                        + "join clientes c on p.id_persona = c.id_persona\n"
                        + "join facturas f on f.id_cliente =  c.id_cliente\n"
                        + "join autos a on a.id_auto = f.id_auto\n"
                        + "join modelos m on m.id_modelo = a.id_modelo\n"
                        + "join marcas_autos ma on ma.id_marcaAuto = m.id_marcaAuto   \n"
                        + "join detalle_pedidoServicio d on d.id_factura = f.id_factura\n"
                        + "join servicios s on s.id_servicio = d.id_servicio\n"
                        + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                        + "join estadosOrdenTrabajo e on e.id_estado = o.id_estado \n"
                        + "where e.id_estado = ? and f.id_estado = 2");
                pst.setInt(1, id_estado);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String nombreCliente = rs.getString(1);
                String nombreVehiculo = rs.getString(2);
                String servicio = rs.getString(3);
                String estado = rs.getString(4);
                Timestamp fechaInicio = rs.getTimestamp(5);
                Timestamp fechaFin = rs.getTimestamp(6);

                Reporte r = new Reporte(nombreCliente, nombreVehiculo, servicio, estado, fechaInicio, fechaFin);

                lst.add(r);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en ClientesP  orServicio / GestorServicios: " + ex.toString());
        }
        return lst;
    }

}
