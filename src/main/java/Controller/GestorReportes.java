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
import java.util.Date;

public class GestorReportes {

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

    //1-a
    public ArrayList<Reporte> lst1a() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select year(fecha), month(fecha), c.id_cliente, nombre+' '+apellido Cliente, count(id_factura) 'cantidad de veces que fue atendido' \n"
                    + "from clientes c \n"
                    + "join personas p on p.id_persona = c.id_persona\n"
                    + "join facturas f on c.id_cliente = f.id_cliente\n"
                    + "group by year(fecha), month(fecha), c.id_cliente, nombre, apellido\n"
                    + "order by 5 desc");
            while (rs.next()) {
                int año = rs.getInt(1);
                int mes = rs.getInt(2);
                int id_cliente = rs.getInt(3);
                String nombreCliente = rs.getString(4);
                int cantidad = rs.getInt(5);
                Reporte r = new Reporte(año, mes, id_cliente, nombreCliente, cantidad);
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

    //--2-a) Emitir listado de las 5 marcas de repuestos más vendidas en lo que va del año.
    public ArrayList<Reporte> lst2a() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select top 5 m.id_marcaRepuesto, marcaRepuesto, sum(cantidad) cantidad\n"
                    + "from marcas_repuestos m\n"
                    + "join repuestos r on m.id_marcaRepuesto = r.id_marcaRepuesto\n"
                    + "join detalle_pedidoRepuesto d on d.id_repuesto = r.id_repuesto\n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "where year(fecha) = year(getdate())\n"
                    + "group by m.id_marcaRepuesto, marcaRepuesto\n"
                    + "order by 3 desc");
            while (rs.next()) {
                int id_marca = rs.getInt(1);
                String marca = rs.getString(2);
                int cantidad = rs.getInt(3);

                Reporte r = new Reporte(id_marca, marca, cantidad);
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

    //2-b)Generar reporte del rubro de repuesto más vendido mensualmente en lo que va del año.
    public ArrayList<Reporte> lst2b() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("--2-b)Generar reporte del rubro de repuesto más vendido mensualmente en lo que va del año.\n"
                    + "select month(fecha), rub.id_rubro, rubro, sum(cantidad) cantidad\n"
                    + "from rubros rub\n"
                    + "join repuestos r on r.id_rubro = rub.id_rubro\n"
                    + "join detalle_pedidoRepuesto d on d.id_repuesto = r.id_repuesto\n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "where year(fecha) = year(getdate())\n"
                    + "group by rub.id_rubro, rubro, month(fecha)\n"
                    + "having rub.id_rubro = (select top 1 ru.id_rubro \n"
                    + "						from rubros ru \n"
                    + "						join repuestos rep on ru.id_rubro = rep.id_repuesto \n"
                    + "						join detalle_pedidoRepuesto det on det.id_repuesto= rep.id_repuesto\n"
                    + "						group by ru.id_rubro\n"
                    + "						having sum(cantidad)> sum(d.cantidad))\n"
                    + "order by 4 desc");

            while (rs.next()) {
                int mes = rs.getInt(1);
                int id_rubro = rs.getInt(2);
                String rubro = rs.getString(3);
                int cantidad = rs.getInt(4);

                Reporte r = new Reporte(id_rubro, cantidad, rubro, mes);
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

    //--2-c)-Emitir listado de los 5 repuestos más vendidos el mes pasado.
    public ArrayList<Reporte> lst2c() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select top 5 r.id_repuesto, repuesto, sum(cantidad) cantidad, month(fecha)\n"
                    + "from repuestos r\n"
                    + "join detalle_pedidoRepuesto d on d.id_repuesto = r.id_repuesto\n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "where month(fecha) = month(dateadd(month,-1,getdate())) and year(fecha) = year(getdate())\n"
                    + "group by r.id_repuesto, repuesto, month(fecha)\n"
                    + "order by 3 desc");

            while (rs.next()) {
                int id_repuesto = rs.getInt(1);
                String repuesto = rs.getString(2);
                int cantidad = rs.getInt(3);
                int mes = rs.getInt(4);

                Reporte r = new Reporte(id_repuesto, repuesto, cantidad, mes);
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

    //3)a)-Emitir listado de marca y modelo de autos en lo que va del año ordenados de los más atendidos a los menos atendidos.\n"
    public ArrayList<Reporte> lst3a() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select distinct marca+' '+modelo 'Vehículo', count(*) 'Cantidad de veces atendida'\n"
                    + "from marcas_autos ma\n"
                    + "join modelos mo on ma.id_marcaAuto = mo.id_modelo\n"
                    + "join autos a on a.id_modelo = mo.id_modelo\n"
                    + "join facturas f on f.id_auto = a.id_auto\n"
                    + "where year(fecha) = year(getdate())\n"
                    + "group by marca,modelo\n"
                    + "order by 2 desc");

            while (rs.next()) {

                String auto = rs.getString(1);
                int cantidad = rs.getInt(2);

                Reporte r = new Reporte(auto, cantidad);
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

    //--3)b)-Generar reporte mensual de los 5 servicios más recurrentes por mes en lo que va del año.
    public ArrayList<Reporte> lst3b() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select top 5 month(fecha) Mes, s.id_servicio, servicio, descripcion, count(s.id_servicio)\n"
                    + "from servicios s\n"
                    + "join detalle_pedidoServicio d on s.id_servicio = d.id_servicio\n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "where year(fecha) = year(getdate())\n"
                    + "group by month(fecha),s.id_servicio, servicio, descripcion\n"
                    + "order by 5 desc");

            while (rs.next()) {

                int mes = rs.getInt(1);
                int id_servicio = rs.getInt(2);
                String servicio = rs.getString(3);
                String descripcion = rs.getString(4);
                int cantidad = rs.getInt(5);

                Reporte r = new Reporte(mes, id_servicio, servicio, descripcion, cantidad);
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

    //--3)c)-Generar reporte de la categoría de servicio más frecuente por año.
    public ArrayList<Reporte> lst3c() {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select year(fecha),categoria, count(*)\n"
                    + "from categorias c\n"
                    + "join servicios s on c.id_categoria = s.id_categoria\n"
                    + "join detalle_pedidoServicio d on d.id_servicio = s.id_servicio\n"
                    + "join facturas f on f.id_factura = d.id_factura\n"
                    + "group by year(fecha), categoria, c.id_categoria\n "
                    + "order by 1 desc");
            while (rs.next()) {

                int año = rs.getInt(1);
                String categoria = rs.getString(2);
                int cantidad = rs.getInt(3);

                Reporte r = new Reporte(año, cantidad, categoria);
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

}
