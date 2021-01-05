package Controller;

import Model.DetallePedidoRepuesto;
import Model.DetallePedidoServicio;
import Model.Factura;
import Model.OrdenTrabajo;
import Model.Repuesto;
import Model.Servicio;
import Model.TipoPago;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import jdk.jfr.Timespan;

public class GestorFacturas {

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

    public int altaPresupuestoFactura(Factura f) {
        int idFactura = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int \n"
                    + "	exec dbo.altaPresupuestoFactura ?,?, @salida output \n"
                    + "	select @salida");
            pst.setInt(1, f.getId_usuario());
            pst.setInt(2, f.getId_auto());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idFactura = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idFactura;
    }

    public int altaReserva(Factura f) {
        int idFactura = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int\n"
                    + "exec altaReservaFactura ?, 4, @salida output\n"
                    + "select @salida");
            pst.setInt(1, f.getId_cliente());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idFactura = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idFactura;
    }

    public int Presupuestar(Factura f) {
        int idFactura = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int\n"
                    + "exec [enReparacionFactura] ?,?,?, @salida output\n"
                    + "select @salida");
            pst.setInt(1, f.getId_usuario());
            pst.setInt(2, f.getId_cliente());
            pst.setInt(3, f.getId_auto());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idFactura = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idFactura;
    }

    public int ventaRepuestosFacturados(Factura f) {
        int idFactura = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int\n"
                    + "exec ventaRepuestosFacturados ?, ?, 3, @salida output\n"
                    + "select @salida");
            pst.setInt(1, f.getId_usuario());
            pst.setInt(2, f.getId_cliente());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idFactura = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idFactura;
    }

    public boolean ModificarPresupuestoDetalleRepuesto(int id_detalle, int cantidad, int id_repuesto) {
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @precio float = (select precio from repuestos where id_repuesto = ?)\n"
                    + "update detalle_pedidoRepuesto set cantidad = ?, precioRepuesto = @precio, id_repuesto  = ? where id_detalleRepuesto = ?");
            pst.setInt(1, id_repuesto);
            pst.setInt(2, cantidad);
            pst.setInt(3, id_repuesto);
            pst.setInt(4, id_detalle);

            int filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public ArrayList<TipoPago> ListarTipoPagos() {
        ArrayList<TipoPago> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from tipo_pagos");
            while (rs.next()) {
                int id = rs.getInt(1);
                String tipo = rs.getString(2);
                TipoPago tp = new TipoPago(id, tipo);
                lst.add(tp);
            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return lst;
    }

    public Factura ObtenerFacturaPorId(int id_f) {
        Factura f = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_factura = ? ");
            pst.setInt(1, id_f);
            ResultSet rs = pst.executeQuery();

            rs.next();

            int id_usuario = rs.getInt(2);
            Date fecha = rs.getDate(3);
            int id_auto = 0;
            if (rs.getInt(4) != 0) {
                id_auto = rs.getInt(4);
            }
            int id_cliente = rs.getInt(6);

            if (id_auto != 0) {
                f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
            } else {
                f = new Factura(id_f, id_usuario, fecha, id_cliente);
            }

            f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

            f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return f;
    }

    public ArrayList<Factura> ObtenerPresupuestosPorIdCt(int id_c) {
        ArrayList<Factura> lstFactura = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (6) and id_cliente = ? ");
            pst.setInt(1, id_c);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();

                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                if (id_auto != 0) {
                    f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id_f, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

                lstFactura.add(f);

            }

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstFactura;
    }

    public ArrayList<Factura> PresupuestosCanceladosPorIdCt(int id_c) {
        ArrayList<Factura> lstFactura = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (6) and id_cliente = ? ");
            pst.setInt(1, id_c);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();

                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                if (id_auto != 0) {
                    f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id_f, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

                lstFactura.add(f);

            }

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstFactura;
    }

    public ArrayList<Factura> ObtenerPresupuestos() {
        ArrayList<Factura> lstFactura = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (6)");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();

                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                if (id_auto != 0) {
                    f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id_f, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

                lstFactura.add(f);

            }

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstFactura;
    }

    public ArrayList<Factura> PresupuestosCancelados() {
        ArrayList<Factura> lstFactura = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (1)");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();

                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                if (id_auto != 0) {
                    f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id_f, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

                lstFactura.add(f);

            }

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstFactura;
    }

    public ArrayList<Factura> ObtenerPresupuestosConfirmados() {
        ArrayList<Factura> lstFactura = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (2) order by 3");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();

                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                if (id_auto != 0) {
                    f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id_f, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

                lstFactura.add(f);

            }

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lstFactura;
    }

    public Factura ObtenerDetallesDeEntrega(int id_f) {
        Factura f = null;
        try {
            System.out.println(id_f);
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_factura = ? ");
            pst.setInt(1, id_f);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int id_usuario = rs.getInt(2);
            Date fecha = rs.getDate(3);
            int id_auto = 0;
            if (rs.getInt(4) != 0) {
                id_auto = rs.getInt(4);
            }
            int id_cliente = rs.getInt(6);

            if (id_auto != 0) {
                f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
            } else {
                f = new Factura(id_f, id_usuario, fecha, id_cliente);
            }

            f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

            f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return f;
    }

    public Factura ObtenerDetallesDePresupuesto(int id_f) {
        Factura f = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_factura = ? ");
            pst.setInt(1, id_f);
            ResultSet rs = pst.executeQuery();

            rs.next();

            int id_usuario = rs.getInt(2);
            Date fecha = rs.getDate(3);
            int id_auto = 0;
            if (rs.getInt(4) != 0) {
                id_auto = rs.getInt(4);
            }
            int id_cliente = rs.getInt(6);

            if (id_auto != 0) {
                f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente);
            } else {
                f = new Factura(id_f, id_usuario, fecha, id_cliente);
            }

            f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id_f);

            f.lstServicio = obtenerListadoServiciosPorIdFt(id_f);

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return f;
    }

    public ArrayList<Factura> listarFacturasListasParaPagar() {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from facturas where id_estado in (4,7)");
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                Factura f = null;
                if (id_auto != 0) {
                    f = new Factura(id, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id);

                lst.add(f);

            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public DetallePedidoServicio ObtenerDetalleServicioPorId(int id_detalle) {
        DetallePedidoServicio dps = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from detalle_pedidoServicio where id_detalleServicio = ?");
            pst.setInt(1, id_detalle);
            ResultSet rs = pst.executeQuery();

            rs.next();
            int id_factura = rs.getInt(2);
            int id_servicio = rs.getInt(3);
            double precio = rs.getDouble(4);

            dps = new DetallePedidoServicio(id_detalle, id_factura, id_servicio, precio);

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return dps;

    }

    public ArrayList<Factura> FacturadoYPagadoListados() {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from facturas where id_estado in (5,8)");
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                Factura f = null;
                if (id_auto != 0) {
                    f = new Factura(id, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id);

                f.lstServicio = obtenerListadoServiciosPorIdFt(id);

                lst.add(f);

            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<Factura> ListadoReservas() {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from facturas where id_estado in (4)");
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                Factura f = null;
                if (id_auto != 0) {
                    f = new Factura(id, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id);
                lst.add(f);

            }
            rs.close();
            st.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<Factura> ListadoReservasCliente(int id_c) {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();

            PreparedStatement pst;
            pst = con.prepareStatement("select * from facturas where id_estado in (4) and id_cliente =  ?");
            pst.setInt(1, id_c);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                int id_auto = 0;
                if (rs.getInt(4) != 0) {
                    id_auto = rs.getInt(4);
                }
                int id_cliente = rs.getInt(6);

                Factura f = null;
                if (id_auto != 0) {
                    f = new Factura(id, id_usuario, fecha, id_auto, id_cliente);
                } else {
                    f = new Factura(id, id_usuario, fecha, id_cliente);
                }

                f.lstRepuesto = obtenerListadoRepuestosPorIdFt(id);
                lst.add(f);

            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public void CancelarReserva(Factura f) {
        try {
            Conectar();
            for (DetallePedidoRepuesto d : f.lstRepuesto) {
                try {
                    PreparedStatement pst;
                    pst = con.prepareStatement("delete detalle_pedidoRepuesto where id_factura = ?");
                    pst.setInt(1, d.getId_factura());
                    pst.executeQuery();
                    pst.close();
                    Desconectar();
                } catch (Exception ex) {
                    //ERROR: com.microsoft.sqlserver.jdbc.SQLServerException: La instrucción no devolvió un conjunto de resultados.
                }
            }
            PreparedStatement pst;
            pst = con.prepareStatement("delete facturas where id_factura = ?");
            pst.setInt(1, f.getId_factura());
            ResultSet rs = pst.executeQuery();
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    public ArrayList<DetallePedidoRepuesto> obtenerListadoRepuestosPorIdFt(int id_factura) {
        ArrayList<DetallePedidoRepuesto> lst = new ArrayList<>();
        try {
            PreparedStatement pst;
            pst = con.prepareStatement("select * from detalle_pedidoRepuesto where id_factura = ? ");
            pst.setInt(1, id_factura);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_detalle = rs.getInt(1);
                int id_repuesto = rs.getInt(3);
                int cantidad = rs.getInt(4);
                double precio = rs.getDouble(5);

                Repuesto r = new GestorRepuesto().obtenerRepuesto(id_repuesto);

                DetallePedidoRepuesto dpr = new DetallePedidoRepuesto(id_detalle, id_factura, id_repuesto, cantidad, precio, r);
                lst.add(dpr);
            }

            rs.close();
            pst.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<DetallePedidoServicio> obtenerListadoServiciosPorIdFt(int id_factura) {
        ArrayList<DetallePedidoServicio> lst = new ArrayList<>();
        try {
            PreparedStatement pst;
            pst = con.prepareStatement("SELECT * from detalle_pedidoServicio where id_factura = ? ");
            pst.setInt(1, id_factura);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_detalle = rs.getInt(1);
                int id_servicio = rs.getInt(3);
                double precio = rs.getDouble(4);

                Servicio s = new GestorServicios().obtenerServicio(id_servicio);

                DetallePedidoServicio dps = new DetallePedidoServicio(id_detalle, id_factura, id_servicio, precio, s);
                lst.add(dps);
            }
            rs.close();
            pst.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public ArrayList<DetallePedidoServicio> ObtenerTodosLosDetallesDeServicioAReparar() {
        ArrayList<DetallePedidoServicio> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select * from detalle_pedidoServicio d \n"
                    + "join  facturas f  on d.id_factura = f.id_factura\n"
                    + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                    + "join estadosOrdenTrabajo eo on eo.id_estado = o.id_estado\n"
                    + "where f.id_estado in (2,6) and o.id_estado in (1,2) order by o.id_estado desc, d.id_detalleServicio asc");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_detalle = rs.getInt(1);
                int id_factura = rs.getInt(2);
                int id_servicio = rs.getInt(3);
                double precio = rs.getDouble(4);
                int id_orden = rs.getInt(11);
                int id_estado = rs.getInt(13);
                String estado = rs.getString(18);

                OrdenTrabajo o = new OrdenTrabajo(id_orden, id_estado, estado);

                if (id_estado == 2) {
                    Timestamp fechaComienzo = rs.getTimestamp(15);
                    Timestamp fechaFinEstimado = rs.getTimestamp(16);
                    o.setInicio(fechaComienzo);
                    o.setFin(fechaFinEstimado);
                }
                Servicio s = new GestorServicios().obtenerServicio(id_servicio);

                DetallePedidoServicio dps = new DetallePedidoServicio(id_detalle, id_factura, id_servicio, precio, s, o);
                lst.add(dps);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public boolean altaDetalleServicios(ArrayList<DetallePedidoServicio> lista) {
        String condicion = "";
        try {
            Conectar();
            for (DetallePedidoServicio d : lista) {
                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(50) \n"
                        + "	exec dbo.altaDetalleServicios ?,?, @resultado output \n"
                        + "	select @resultado");
                pst.setInt(1, d.getId_factura());
                pst.setInt(2, d.getId_servicio());

                ResultSet rs = pst.executeQuery();
                rs.next();
                condicion = rs.getString(1);
                pst.close();

            }
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

    public boolean altaDetalleRepuestos(ArrayList<DetallePedidoRepuesto> lista, int id_factura) {
        String condicion = "";
        try {
            Conectar();

            for (int i = 0; i < lista.size(); i++) {
                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec [altaDetalleRepuesto] ?, ?, ?,  @resultado output\n"
                        + "select @resultado");
                pst.setInt(1, id_factura);
                pst.setInt(2, lista.get(i).getR().getId_repuesto());
                pst.setInt(3, lista.get(i).getCantidad());

                ResultSet rs = pst.executeQuery();
                rs.next();
                condicion = rs.getString(1);
                pst.close();
                if (condicion.equals("falla")) {
                    return false;
                }
            }

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

    public boolean CompradoOnlineFactura(int id_factura) {
        String condicion = "";
        try {
            Conectar();

            QuitarStock(id_factura);

            PreparedStatement pst;
            pst = con.prepareStatement("declare @resultado varchar(10)\n"
                    + "exec CompradoOnlineFactura ?, @resultado output\n"
                    + "select @resultado");
            pst.setInt(1, id_factura);

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

    public boolean PasarAlTaller(int id_factura) {
        String condicion = "";
        try {
            Conectar();
            boolean e = QuitarStock(id_factura);
            if (e) {
                ArrayList<DetallePedidoServicio> lst = obtenerListadoServiciosPorIdFt(id_factura);

                for (DetallePedidoServicio d : lst) {
                    PreparedStatement pst;
                    pst = con.prepareStatement("insert into ordenTrabajo values (?,1,null,null, null)");
                    pst.setInt(1, d.getId_detalleServicio());
                    pst.execute();
                }

                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec PasarAlTaller ?, @resultado output\n"
                        + "select @resultado");
                pst.setInt(1, id_factura);

                ResultSet rs = pst.executeQuery();
                rs.next();
                condicion = rs.getString(1);

                pst.close();

                Desconectar();

            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (condicion.equals("falla")) {
            return false;
        } else {
            return true;
        }
    }

    public void DevolverStock(int id_factura) {
        try {
            ArrayList<DetallePedidoRepuesto> lista = obtenerListadoRepuestosPorIdFt(id_factura);

            if (lista.size() > 0) {
                for (DetallePedidoRepuesto d : lista) {
                    PreparedStatement pst;

                    pst = con.prepareStatement("select stock from repuestos where id_repuesto = ?");
                    pst.setInt(1, d.getId_repuesto());
                    ResultSet rs = pst.executeQuery();
                    rs.next();
                    int stock = rs.getInt(1);

                    stock = stock + d.getCantidad();

                    pst = con.prepareStatement("update repuestos set stock = ? where id_repuesto = ? ");
                    pst.setInt(1, stock);
                    pst.setInt(2, d.getId_repuesto());
                    pst.execute();

                    pst.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public boolean QuitarStock(int id_factura) {
        boolean exito = true;
        try {
            ArrayList<DetallePedidoRepuesto> lista = obtenerListadoRepuestosPorIdFt(id_factura);
            if (lista.size() > 0) {

                con.setAutoCommit(false);

                for (DetallePedidoRepuesto d : lista) {

                    PreparedStatement pst;

                    pst = con.prepareStatement("select stock from repuestos where id_repuesto = ?");
                    pst.setInt(1, d.getId_repuesto());
                    ResultSet rs = pst.executeQuery();
                    rs.next();
                    int stock = rs.getInt(1);

                    if (stock < d.getCantidad()) {
                        exito = false;
                        con.rollback();
                        break;
                    }
                    stock = stock - d.getCantidad();

                    pst = con.prepareStatement("update repuestos set stock = ? where id_repuesto = ? ");
                    pst.setInt(1, stock);
                    pst.setInt(2, d.getId_repuesto());
                    pst.execute();
                }

                con.commit();
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean CancelarPresupuesto(int id_factura) {
        String condicion = "";
        try {
            Conectar();
            con.setAutoCommit(false);

            PreparedStatement pst;
            pst = con.prepareStatement("declare @resultado varchar(10)\n"
                    + "exec CancelarPresupuesto ?, @resultado output\n"
                    + "select @resultado");
            pst.setInt(1, id_factura);

            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);

            con.commit();
            con.setAutoCommit(true);
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

    public boolean CancelarPresupuestoCliente(int id_factura) {
        String condicion = "";
        try {
            Conectar();
            DevolverStock(id_factura);
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida varchar(10)\n"
                    + "update facturas set id_estado = 1 where id_factura = ?\n"
                    + "if(@@rowcount>0)\n"
                    + "begin\n"
                    + "	set @salida = 'exito'\n"
                    + "end\n"
                    + "else\n"
                    + "begin\n"
                    + "	set @salida = 'fallo'\n"
                    + "end		 \n"
                    + "select @salida");
            pst.setInt(1, id_factura);

            ResultSet rs = pst.executeQuery();
            rs.next();
            condicion = rs.getString(1);

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

    public boolean TerminadoFactura(int id_factura, int id_usuario) {
        String condicion = "";
        try {
            Conectar();

            PreparedStatement pst;
            pst = con.prepareStatement("declare @resultado varchar(10)\n"
                    + "exec [TerminadoFactura] ?, ?, @resultado output\n"
                    + "select @resultado");
            pst.setInt(1, id_factura);
            pst.setInt(2, id_usuario);

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

    public boolean EntregaDeComprasOnline(int id_factura, int id_usuario) {
        String condicion = "";
        try {
            Conectar();

            PreparedStatement pst;
            pst = con.prepareStatement("declare @resultado varchar(10)\n"
                    + "exec [EntregaDeComprasOnline] ?, ?, @resultado output\n"
                    + "select @resultado");
            pst.setInt(1, id_factura);
            pst.setInt(2, id_usuario);

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

    //si id_usuario es 0 es porque ya conoce al usuario, si no lo inserta
    public boolean PagarFacturaEnLocal(int id_factura, int id_usuario) {
        String condicion = "";
        try {
            Conectar();

            PreparedStatement pst;
            pst = con.prepareStatement("declare @salidas varchar(10)\n"
                    + "exec PagarFacturaEnLocal ?, ?, @salidas output\n"
                    + "select @salidas");
            pst.setInt(1, id_factura);
            pst.setInt(2, id_usuario);

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

    public int CompraOnline(Factura f) {
        int idFactura = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @salida int\n"
                    + "exec altaReservaFactura ?, 5, @salida output\n"
                    + "select @salida");
            pst.setInt(1, f.getId_cliente());

            ResultSet rs = pst.executeQuery();
            rs.next();
            idFactura = rs.getInt(1);
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return idFactura;
    }

    public boolean AgregarPresupuestoDetalleRepuesto(int cantidad, int id_repuesto, int id_factura) {
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @precio float = (select precio from repuestos where id_repuesto = ?)\n"
                    + "insert into  detalle_pedidoRepuesto values (?,?,?, @precio)");
            pst.setInt(1, id_repuesto);
            pst.setInt(2, id_factura);
            pst.setInt(3, id_repuesto);
            pst.setInt(4, cantidad);

            int filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;

    }

    public boolean AgregarPresupuestoDetalleServicio(int id_servicio, int id_factura) {
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @precio float = (select precio from servicios where id_servicio = ?) \n"
                    + "insert into  detalle_pedidoServicio values (?,?, @precio)");
            pst.setInt(1, id_servicio);
            pst.setInt(2, id_factura);
            pst.setInt(3, id_servicio);

            int filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;

    }

    public boolean EliminarPresupuestoDetalleRepuesto(int id_detalle, int id_factura) {
        boolean exito = false;
        try {
            Conectar();

            int cantRepuesto = obtenerListadoRepuestosPorIdFt(id_factura).size();
            int cantServicios = obtenerListadoServiciosPorIdFt(id_factura).size();

            boolean test = cantRepuesto + cantServicios > 1;

            if (test) {
                PreparedStatement pst;

                pst = con.prepareStatement("delete detalle_pedidoRepuesto where id_detalleRepuesto = ?");
                pst.setInt(1, id_detalle);

                int filas = pst.executeUpdate();
                exito = filas > 0;
                pst.close();
            }
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean EliminarPresupuestoDetalleServicio(int id_detalle, int id_factura) {
        boolean exito = false;
        try {
            Conectar();

            int cantRepuesto = obtenerListadoRepuestosPorIdFt(id_factura).size();
            int cantServicios = obtenerListadoServiciosPorIdFt(id_factura).size();

            boolean test = cantRepuesto + cantServicios > 1;

            if (test) {
                PreparedStatement pst;

                pst = con.prepareStatement("delete detalle_pedidoServicio where id_detalleServicio = ?");
                pst.setInt(1, id_detalle);

                int filas = pst.executeUpdate();
                exito = filas > 0;
                pst.close();
            }
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean ModificarPresupuestoDetalleServicio(int id_detalle, int id_servicio) {
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("declare @precio float = (select precio from servicios where id_servicio = ?)\n"
                    + "update detalle_pedidoServicio set precioServicio = @precio, id_servicio  = ?  where id_detalleServicio = ?");
            pst.setInt(1, id_servicio);
            pst.setInt(2, id_servicio);
            pst.setInt(3, id_detalle);

            int filas = pst.executeUpdate();
            exito = filas > 0;
            pst.close();
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean modificacionDetallesRepuestos(Factura carrito) {
        String condicion = "";
        try {
            Conectar();

            for (int i = 0; i < carrito.lstRepuesto.size(); i++) {
                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec [modificarDetalleRepuesto] ?, ?, ?, ?, @resultado output\n"
                        + "select @resultado");
                pst.setInt(1, carrito.getId_factura());
                pst.setInt(2, carrito.lstRepuesto.get(i).getId_repuesto());
                pst.setInt(3, carrito.lstRepuesto.get(i).getCantidad());
                pst.setInt(4, carrito.lstRepuesto.get(i).getId_detalleRepuesto());

                ResultSet rs = pst.executeQuery();
                rs.next();
                condicion = rs.getString(1);
                pst.close();
                if (condicion.equals("falla")) {
                    return false;
                }
            }

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

    public ArrayList<Factura> TraerFacturasParametrizadas(int id_cliente, int id_auto, int eleccion) {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("exec dbo.[HistorialFacturas] ?,?,?");
            pst.setInt(1, id_cliente);
            pst.setInt(2, id_auto);
            pst.setInt(3, eleccion);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_f = rs.getInt(1);
                int id_usuario = rs.getInt(2);
                Date fecha = rs.getDate(3);
                id_auto = rs.getInt(4);
                id_cliente = rs.getInt(6);
                double importe = rs.getDouble(7);

                Factura f = new Factura(id_f, id_usuario, fecha, id_auto, id_cliente, importe);
                lst.add(f);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en TraerFacturasParametrizadas GestorFacturas: " + ex.toString());
        }
        return lst;
    }

    public ArrayList<Factura> ListadoEspera(int id_cliente) {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select  f.id_factura, fecha, id_auto \n"
                    + "from facturas f\n"
                    + "join detalle_pedidoServicio d on d.id_factura = f.id_factura\n"
                    + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                    + "where f.id_estado = 2 and o.id_estado = 1  and f.id_cliente = ?");
            pst.setInt(1, id_cliente);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_f = rs.getInt(1);
                Date fecha = rs.getDate(2);
                int id_auto = rs.getInt(3);

                Factura f = new Factura(id_f, fecha, id_auto, id_cliente);
                lst.add(f);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en listadoEspera GestorFacturas: " + ex.toString());
        }
        return lst;
    }

    public ArrayList<Factura> ListadoReparacion(int id_cliente) {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select  f.id_factura, fecha, id_auto, inicio\n"
                    + "from facturas f\n"
                    + "join detalle_pedidoServicio d on d.id_factura = f.id_factura\n"
                    + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                    + "where f.id_estado = 2 and o.id_estado = 2 and f.id_cliente = ?");
            pst.setInt(1, id_cliente);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_f = rs.getInt(1);
                Date fecha = rs.getDate(2);
                int id_auto = rs.getInt(3);
                Timestamp inicio = rs.getTimestamp(4);

                System.out.println("Inicio en gestor_reparacion " + inicio);

                Factura f = new Factura(id_f, fecha, id_auto, inicio, id_cliente);
                lst.add(f);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en ListadoReparacion GestorFacturas: " + ex.toString());
        }
        return lst;
    }

    public ArrayList<Factura> ListadoTerminados(int id_cliente) {
        ArrayList<Factura> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select  f.id_factura, fecha, id_auto, inicio, fin\n"
                    + "from facturas f\n"
                    + "join detalle_pedidoServicio d on d.id_factura = f.id_factura\n"
                    + "join ordenTrabajo o on o.id_detalleServicio = d.id_detalleServicio\n"
                    + "where f.id_estado = 7 and f.id_cliente = ?");
            pst.setInt(1, id_cliente);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id_f = rs.getInt(1);
                Date fecha = rs.getDate(2);
                int id_auto = rs.getInt(3);
                Timestamp inicio = rs.getTimestamp(4);
                Timestamp fin = rs.getTimestamp(5);

                Factura f = new Factura(id_f, fecha, id_auto, inicio, fin, id_cliente);
                lst.add(f);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en ListadoTerminados GestorFacturas: " + ex.toString());
        }
        return lst;
    }

    public boolean HabilitarPagoPorReserva(int id_f, int id_u) {
        boolean exito = false;
        try {
            Conectar();
            PreparedStatement pst;
            boolean e = QuitarStock(id_f);
            
            if (e) {
                if (id_u == 0) {
                    pst = con.prepareStatement("update facturas set id_estado = 7, id_usuario = ?  where id_factura = ?");
                    pst.setInt(1, id_f);
                    int filas = pst.executeUpdate();
                    pst.close();
                    exito = filas > 0;
                } else {
                    pst = con.prepareStatement("update facturas set id_estado = 7, id_usuario = ?  where id_factura = ?");
                    pst.setInt(1, id_u);
                    pst.setInt(2, id_f);
                    int filas = pst.executeUpdate();
                    pst.close();
                    exito = filas > 0;
                }
                Desconectar();
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

}
