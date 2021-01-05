package Controller;

import Model.Barrio;
import Model.Detalle_reposicion;
import Model.Localidad;
import Model.Persona;
import Model.Proveedor;
import Model.Provincia;
import Model.Reporte;
import Model.Reposicion;
import Model.Repuesto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestorProveedores {

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

    public ArrayList<Proveedor> ListarProveedores(int eleccion) {
        ArrayList<Proveedor> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            ResultSet rs;

            if (eleccion == 0) {
                //todos los proveedores ordenados de los más usados a los menos usados
                pst = con.prepareStatement("select distinct  a.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina, count(r.id_proveedor)\n"
                        + "from proveedores a join barrios b on b.id_barrio =  a.id_barrio\n"
                        + "join localidades l on l.id_localidad = b.id_localidad\n"
                        + "join provincias p on p.id_provincia = l.id_provincia\n"
                        + "left join Reposiciones r on r.id_proveedor = a.id_proveedor\n"
                        + "group by a.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina\n"
                        + "order by count(r.id_proveedor) desc");
                rs = pst.executeQuery();

            } else if (eleccion == 1) {
                //proveedores ordenados por rubros desde los más usados a los menos usados
                pst = con.prepareStatement("select distinct prov.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina, rubro, rub.id_rubro, count(repo.id_proveedor)\n"
                        + "from proveedores prov\n"
                        + "join barrios b on b.id_barrio =  prov.id_barrio\n"
                        + "join localidades l on l.id_localidad = b.id_localidad\n"
                        + "join provincias p on p.id_provincia = l.id_provincia\n"
                        + "join reposiciones repo on prov.id_proveedor = repo.id_proveedor\n"
                        + "join Detalles_reposicion d on d.id_reposicion = repo.id_reposicion\n"
                        + "join repuestos r on r.id_repuesto = d.id_repuesto\n"
                        + "join rubros rub on rub.id_rubro = r.id_rubro\n"
                        + "group by  rubro, rub.id_rubro, prov.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina\n"
                        + "order by count(repo.id_proveedor) desc");
                rs = pst.executeQuery();
            } else {
                //proveedores ordenados por marcas desde los más usados a los menos usados
                pst = con.prepareStatement("select distinct prov.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina,  marcaRepuesto, m.id_marcaRepuesto, count(repo.id_proveedor)\n"
                        + "from proveedores prov\n"
                        + "join barrios b on b.id_barrio =  prov.id_barrio\n"
                        + "join localidades l on l.id_localidad = b.id_localidad\n"
                        + "join provincias p on p.id_provincia = l.id_provincia\n"
                        + "join reposiciones repo on prov.id_proveedor = repo.id_proveedor\n"
                        + "join Detalles_reposicion d on d.id_reposicion = repo.id_reposicion\n"
                        + "join repuestos r on r.id_repuesto = d.id_repuesto\n"
                        + "join marcas_repuestos m on m.id_marcaRepuesto = r.id_marcaRepuesto\n"
                        + "group by   marcaRepuesto, m.id_marcaRepuesto, prov.id_proveedor, proveedor, b.id_barrio, barrio, l.id_localidad, localidad, p.id_provincia, provincia, domicilio, cp, telefono, email, pagina\n"
                        + "order by count(repo.id_proveedor) desc");
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                int id_proveedor = rs.getInt(1);
                String proveedor = rs.getString(2);

                String domicilio = rs.getString(9);
                String cp = rs.getString(10);
                String telefono = rs.getString(11);
                String email = rs.getString(12);
                String pagina = rs.getString(13);

                Barrio b = new Barrio(rs.getInt(3), rs.getString(4));
                Localidad l = new Localidad(rs.getInt(5), rs.getString(6));
                Provincia p = new Provincia(rs.getInt(7), rs.getString(8));

                Persona perkin = new Persona(telefono, email, domicilio, cp, b, l, p);
                Proveedor proveedxr = new Proveedor(id_proveedor, proveedor, perkin, pagina);

                if (eleccion > 0) {
                    proveedxr.setDescripcion_aux(rs.getString(14));
                    proveedxr.setId_aux(rs.getInt(15));
                }
                lst.add(proveedxr);
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return lst;
    }

    public Proveedor ObtenerProveedorPorId(int id_prov) {
        Proveedor p = null;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select id_proveedor,\n"
                    + "proveedor,\n"
                    + "b.id_barrio,\n"
                    + "barrio,\n"
                    + "l.id_localidad,\n"
                    + "localidad,\n"
                    + "p.id_provincia,\n"
                    + "provincia,\n"
                    + "domicilio,\n"
                    + "cp,\n"
                    + "telefono,\n"
                    + "email,\n"
                    + "pagina \n"
                    + "from proveedores a join barrios b on b.id_barrio =  a.id_barrio\n"
                    + "join localidades l on l.id_localidad = b.id_localidad\n"
                    + "join provincias p on p.id_provincia = l.id_provincia "
                    + " where id_proveedor = ?");

            pst.setInt(1, id_prov);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_proveedor = rs.getInt(1);
                String proveedor = rs.getString(2);

                String domicilio = rs.getString(9);
                String cp = rs.getString(10);
                String telefono = rs.getString(11);
                String email = rs.getString(12);
                String pagina = rs.getString(13);

                Barrio b = new Barrio(rs.getInt(3), rs.getString(4));
                Localidad l = new Localidad(rs.getInt(5), rs.getString(6));
                Provincia provincia = new Provincia(rs.getInt(7), rs.getString(8));

                Persona perkin = new Persona(telefono, email, domicilio, cp, b, l, provincia);

                p = new Proveedor(id_proveedor, proveedor, perkin, pagina);

            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return p;
    }

    public Reposicion ObtenerReposicion(int id_reposicion) {
        Reposicion r = null;
        try {
            Conectar();
            //TRAER LOS DETALLES

            PreparedStatement pst;
            pst = con.prepareStatement("select r.id_reposicion, \n"
                    + "p.id_proveedor,\n"
                    + "id_usuario,\n"
                    + "fechaReposicion,\n"
                    + "proveedor \n"
                    + "from reposiciones r \n"
                    + "join proveedores p on p.id_proveedor = r.id_proveedor "
                    + "where id_reposicion = ?");
            pst.setInt(1, id_reposicion);

            ResultSet rs = pst.executeQuery();
            rs.next();
            int id_proveedor = rs.getInt(2);
            int id_usuario = rs.getInt(3);
            Date fecha = rs.getDate(4);
            String nombreProveedor = rs.getString(5);

            ArrayList<Detalle_reposicion> lstDetalles = TraerDetallesPorReposicion(id_reposicion);

            double importe = 0;

            for (Detalle_reposicion d : lstDetalles) {
                importe += d.getPrecio_unitario();
            }

            r = new Reposicion(id_reposicion, fecha, id_proveedor, nombreProveedor, id_usuario, importe, lstDetalles);

            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return r;
    }

    public boolean AltaProveedor(Proveedor p) {
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("insert into proveedores values(?,?,?,?,?,?,?)");

            pst.setString(1, p.getProveedor());
            pst.setInt(2, p.getP().getBarrio().getId_barrio());
            pst.setString(3, p.getP().getDomicilio());
            pst.setString(4, p.getP().getTelefono());
            pst.setString(5, p.getP().getEmail());
            pst.setString(6, p.getPagina());
            pst.setString(7, p.getP().getCp());
            filas = pst.executeUpdate();

            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (filas == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean ModificarProveedor(Proveedor p) {
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("update proveedores set proveedor = ?, id_barrio = ?, domicilio = ?, telefono = ?, email = ?, pagina = ?, cp = ? where id_proveedor = ?");

            pst.setString(1, p.getProveedor());
            pst.setInt(2, p.getP().getBarrio().getId_barrio());
            pst.setString(3, p.getP().getDomicilio());
            pst.setString(4, p.getP().getTelefono());
            pst.setString(5, p.getP().getEmail());
            pst.setString(6, p.getPagina());
            pst.setString(7, p.getP().getCp());
            pst.setInt(8, p.getId());
            filas = pst.executeUpdate();

            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (filas == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean AltaDeReposicion(Reposicion rep) {
        boolean exito = false;
        int filas = 0;
        int id_reposicion = 0;

        try {
            Conectar();
            con.setAutoCommit(false);
            try {
                //alta de reposicion
                PreparedStatement pst;
                pst = con.prepareStatement("insert into reposiciones values (?,?,getdate())");
                pst.setInt(1, rep.getId_proveedor());
                pst.setInt(2, rep.getId_usuario());
                filas = pst.executeUpdate();
                pst.close();

            } catch (Exception ex) {
                System.out.println("Exepción en el alta de reposición: " + ex.toString());
            }
            try {
                //obtener el idreposicion
                if (filas > 0) {
                    PreparedStatement pst;
                    pst = con.prepareStatement("select ident_current('reposiciones')");
                    ResultSet rs = pst.executeQuery();
                    rs.next();
                    id_reposicion = rs.getInt(1);
                    pst.close();

                } else {
                    return false;
                }
            } catch (Exception ex) {
                System.out.println("Exepción en el la consulta del id: " + ex.toString());
            }

            try {
                //alta de los detalles
                for (Detalle_reposicion d : rep.getDetalles_reposicion()) {
                    PreparedStatement pst;
                    pst = con.prepareStatement("insert into detalles_reposicion values (?,?,?,?)");
                    pst.setInt(1, d.getId_repuesto());
                    pst.setDouble(2, d.getPrecio_unitario());
                    pst.setInt(3, d.getCantidad());
                    pst.setInt(4, id_reposicion);

                    int verficarInsert = pst.executeUpdate();
                    pst.close();

                    if (verficarInsert > 0) {
                        String result = ActualizarStock(d, "sumar");
                        if (result.equals("exito")) {

                            exito = true;
                        } else {
                            exito = false;
                            break;
                        }
                    } else {
                        exito = false;
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Exepción en el alta de detalles: " + ex.toString());
            }
            con.commit();
            con.setAutoCommit(true);
            Desconectar();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return exito;
    }

    public boolean EliminarProveedor(int id) {
        boolean exito = false;
        int filas = 0;
        try {
            Conectar();
            PreparedStatement pst = con.prepareStatement("delete proveedores where id_proveedor = ?");
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

    public boolean EliminarReposicion(int id_reposicion) {
        boolean exito = false;
        try {
            try {
                Conectar();
                con.setAutoCommit(false);
                //Identificar los detalles para luego actualizar stock
                ArrayList<Detalle_reposicion> lstDetalle = TraerDetallesPorReposicion(id_reposicion);
                for (Detalle_reposicion d : lstDetalle) {
                    String result = ActualizarStock(d, "quitar");

                    if (result.equals("exito")) {
                        exito = true;
                    } else {
                        exito = false;
                        con.rollback();
                        break;
                    }
                }
                //borro los detalles
                PreparedStatement pst;
                pst = con.prepareStatement("delete detalles_reposicion where id_reposicion = ?");
                pst.setInt(1, id_reposicion);
                int filas = pst.executeUpdate();
                pst.close();
                if (filas > 0) {
                    exito = true;
                } else {
                    exito = false;
                    con.rollback();
                }

            } catch (Exception ex) {
                System.out.println("Exepción en EliminarReposición: " + ex.toString());
            }
            //Después las reposiciones
            if (exito) {
                PreparedStatement pstReposicion;
                pstReposicion = con.prepareStatement("delete reposiciones where id_reposicion = ?");
                pstReposicion.setInt(1, id_reposicion);
                int filaReposicion = pstReposicion.executeUpdate();
                pstReposicion.close();

                exito = filaReposicion > 0;
                con.commit();
            } else {
                exito = false;
                con.rollback();
            }

            con.setAutoCommit(true);
            Desconectar();

        } catch (Exception ex) {
            System.out.println("Exepción en EliminarReposición: " + ex.toString());
        }
        return exito;
    }

    public boolean ActualizarDetalle(int id_d, int id_r, int cant) {
        boolean exito = false;
        try {
            Conectar();
            con.setAutoCommit(false);

            PreparedStatement pstCantidad;
            pstCantidad = con.prepareStatement("select cantidad from detalles_reposicion where id_detalle_reposicion = ?");
            pstCantidad.setInt(1, id_d);
            ResultSet rsCantidad = pstCantidad.executeQuery();
            rsCantidad.next();
            int cantidadVieja = rsCantidad.getInt(1);
            rsCantidad.close();
            pstCantidad.close();

            boolean actualizarStock = false;

            if (cantidadVieja > cant) {
                //restar stock
                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec actualizarStock ?,?,'quitar', @resultado output\n"
                        + "select @resultado");
                pst.setInt(1, id_r);
                pst.setInt(2, cant);
                ResultSet rs = pst.executeQuery();
                rs.next();
                actualizarStock = rs.getString(1).equals("exito");

            } else {
                //sumar stock
                PreparedStatement pst;
                pst = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec actualizarStock ?,?,'sumar', @resultado output\n"
                        + "select @resultado");
                pst.setInt(1, id_r);
                pst.setInt(2, cant);
                ResultSet rs = pst.executeQuery();
                rs.next();
                actualizarStock = rs.getString(1).equals("exito");
            }
            if (actualizarStock) {
                PreparedStatement pst;
                pst = con.prepareStatement("declare @precio float = (select precio from repuestos where id_repuesto = ? )\n"
                        + "update Detalles_reposicion set id_repuesto = ?, precio_unitario = @precio, cantidad = ? "
                        + "where id_detalle_reposicion = ? ");
                pst.setInt(1, id_r);
                pst.setInt(2, id_r);
                pst.setInt(3, cant);
                pst.setInt(4, id_d);
                exito = pst.executeUpdate() > 0;
            }
            if (exito) {
                con.commit();
            }
            con.setAutoCommit(true);
            Desconectar();

        } catch (Exception ex) {
            System.out.println("Exepción en ActualizarDetalle/GestorProveedores: " + ex.toString());
        }
        return exito;

    }

    public ArrayList<Detalle_reposicion> TraerDetallesPorReposicion(int id) {
        //Carga los detalles por cada reposición
        ArrayList<Detalle_reposicion> lstDetalle = new ArrayList<>();
        try {
            PreparedStatement pstDetalle;
            pstDetalle = con.prepareStatement("select id_detalle_reposicion,"
                    + "r.id_repuesto,"
                    + "precio_unitario,"
                    + "cantidad, "
                    + "r.repuesto "
                    + "from detalles_reposicion d \n"
                    + "join repuestos r on d.id_repuesto = r.id_repuesto\n"
                    + "where id_reposicion = ?");
            pstDetalle.setInt(1, id);
            ResultSet rsDetalle = pstDetalle.executeQuery();
            while (rsDetalle.next()) {
                int id_detalle = rsDetalle.getInt(1);
                int id_repuesto = rsDetalle.getInt(2);
                double precio_u = rsDetalle.getDouble(3);
                int cantidad = rsDetalle.getInt(4);
                String descripcion = rsDetalle.getString(5);

                Detalle_reposicion d = new Detalle_reposicion(id_detalle, id_repuesto, precio_u, cantidad, descripcion);
                lstDetalle.add(d);
            }
            rsDetalle.close();
            pstDetalle.close();

        } catch (Exception ex) {
            System.out.println("ex en TraerDetallesPorReposicion: " + ex);
        }

        return lstDetalle;

    }

    public ArrayList<Reposicion> TraerReposiciones() {
        ArrayList<Reposicion> lista = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select r.id_reposicion,\n"
                    + "p.id_proveedor,\n"
                    + "id_usuario,\n"
                    + "fechaReposicion,\n"
                    + "proveedor \n"
                    + "from reposiciones r \n"
                    + "join proveedores p on p.id_proveedor = r.id_proveedor");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //carga las reposiciones
                int id_r = rs.getInt(1);
                int id_prov = rs.getInt(2);
                int id_u = rs.getInt(3);
                Date fecha = rs.getDate(4);
                String proveedor = rs.getString(5);

                ArrayList<Detalle_reposicion> lstDetalle = TraerDetallesPorReposicion(id_r);
                double importe = 0;
                for (Detalle_reposicion d : lstDetalle) {
                    importe += d.getPrecio_unitario();
                }

                Reposicion r = new Reposicion(id_r, fecha, id_prov, proveedor, id_u, importe, lstDetalle);
                lista.add(r);
            }
            rs.close();
            pst.close();

            Desconectar();
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
        return lista;
    }

    public String ActualizarStock(Detalle_reposicion d, String accion) {
        String resultado = "";
        try {
            PreparedStatement pstActualizar;
            pstActualizar = con.prepareStatement("declare @salida varchar(50)\n"
                    + "exec dbo.actualizarStock ?,?,?,@salida output\n"
                    + "select @salida");
            pstActualizar.setInt(1, d.getId_repuesto());
            pstActualizar.setInt(2, d.getCantidad());
            pstActualizar.setString(3, accion);
            ResultSet rsActualizar = pstActualizar.executeQuery();
            rsActualizar.next();
            resultado = rsActualizar.getString(1);
            rsActualizar.close();
            pstActualizar.close();

        } catch (Exception ex) {
            System.out.println("Error en actualizarstock " + ex.toString());
        }
        return resultado;
    }

    //Reportes
    public ArrayList<Reporte> GastosPorMes(int mes, int año) {
        ArrayList<Reporte> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            if (mes == 0 && año == 0) {
                pst = con.prepareStatement("select rep.repuesto, month(fechaReposicion)'mes', sum(cantidad), sum(cantidad*precio_unitario)\n"
                        + "from reposiciones r \n"
                        + "join Detalles_reposicion d  on d.id_reposicion = r.id_reposicion\n"
                        + "join repuestos rep on rep.id_repuesto = d.id_repuesto\n"
                        + "group by month(fechaReposicion), rep.repuesto order by 2");
            } else if (mes != 0 && año == 0) {
                pst = con.prepareStatement("select rep.repuesto, month(fechaReposicion)'mes', sum(cantidad), sum(cantidad*precio_unitario)\n"
                        + "from reposiciones r \n"
                        + "join Detalles_reposicion d  on d.id_reposicion = r.id_reposicion\n"
                        + "join repuestos rep on rep.id_repuesto = d.id_repuesto\n"
                        + "where month(fechaReposicion) = ?\n"
                        + "group by month(fechaReposicion), rep.repuesto order by 2");
                pst.setInt(1, mes);

            } else if (mes == 0 && año != 0) {
                pst = con.prepareStatement("select rep.repuesto, month(fechaReposicion)'mes', sum(cantidad), sum(cantidad*precio_unitario)\n"
                        + "from reposiciones r \n"
                        + "join Detalles_reposicion d  on d.id_reposicion = r.id_reposicion\n"
                        + "join repuestos rep on rep.id_repuesto = d.id_repuesto\n"
                        + "where year(fechaReposicion) = ?\n"
                        + "group by month(fechaReposicion), rep.repuesto order by 2");
                pst.setInt(1, año);

            } else {
                pst = con.prepareStatement("select rep.repuesto, month(fechaReposicion)'mes', sum(cantidad), sum(cantidad*precio_unitario)\n"
                        + "from reposiciones r \n"
                        + "join Detalles_reposicion d  on d.id_reposicion = r.id_reposicion\n"
                        + "join repuestos rep on rep.id_repuesto = d.id_repuesto\n"
                        + "where month(fechaReposicion) = ? and year(fechaReposicion) = ?\n"
                        + "group by month(fechaReposicion), rep.repuesto");
                pst.setInt(1, mes);
                pst.setInt(2, año);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String repuesto = rs.getString(1);
                int mesObtenido = rs.getInt(2);
                int cantidad = rs.getInt(3);
                double importe = rs.getDouble(4);

                Reporte r = new Reporte(repuesto, mesObtenido, cantidad, importe);

                lst.add(r);
            }
        } catch (Exception ex) {
            System.out.println("Error en traer: GastosPorMes " + ex);
        }
        return lst;
    }

    public ArrayList<Integer> TraerLosAñosRegistrados() {
        ArrayList<Integer> lst = new ArrayList<>();
        try {
            Conectar();
            PreparedStatement pst;
            pst = con.prepareStatement("select distinct year(fechaReposicion) from reposiciones order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(rs.getInt(1));
            }
            rs.close();
            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en traer: GastosPorMes " + ex);
        }
        return lst;
    }

    public boolean AltaDetalleReposicion(int id_repuesto, int cant, int id_reposicion) {

        boolean exito = false;
        try {
            Conectar();
            con.setAutoCommit(false);
            PreparedStatement pst;
            pst = con.prepareStatement("declare @precio float = (select precio from repuestos where id_repuesto = ?)\n"
                    + "insert into detalles_reposicion values (?,@precio,?,?)");
            pst.setInt(1, id_repuesto);
            pst.setInt(2, id_repuesto);
            pst.setInt(3, cant);
            pst.setInt(4, id_reposicion);

            boolean exitoDetalle = pst.executeUpdate() > 0;

            if (exitoDetalle) {
                PreparedStatement pstActualizarStock;
                pstActualizarStock = con.prepareStatement("declare @resultado varchar(10)\n"
                        + "exec actualizarStock ?,?,'sumar', @resultado output\n"
                        + "select @resultado");
                pstActualizarStock.setInt(1, id_repuesto);
                pstActualizarStock.setInt(2, cant);

                ResultSet rsActualizarStock = pstActualizarStock.executeQuery();
                rsActualizarStock.next();
                exito = rsActualizarStock.getString(1).equals("exito");
                if (!exito) {
                    con.rollback();
                }
            } else {
                con.rollback();
            }
            con.commit();
            con.setAutoCommit(true);

            pst.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en AltaDetalleReposicion/GestorProveedores: " + ex.toString());
        }
        return exito;
    }

    public boolean EliminarDetalleReposicion(int id_d, int id_repuesto, int cant, int id_r) {
        boolean exito = false;
        try {
            Conectar();
            con.setAutoCommit(false);

            PreparedStatement pstVerificar;
            pstVerificar = con.prepareStatement("select count(*) from Detalles_reposicion where id_reposicion = ?");
            pstVerificar.setInt(1, id_r);
            ResultSet rsVerificar = pstVerificar.executeQuery();
            rsVerificar.next();
            if (rsVerificar.getInt(1) <= 1) {
                exito = false;
                con.rollback();
            } else {

                PreparedStatement pst;
                pst = con.prepareStatement("delete detalles_reposicion where id_detalle_reposicion = ?");
                pst.setInt(1, id_d);

                boolean exitoDetalle = pst.executeUpdate() > 0;

                if (exitoDetalle) {
                    PreparedStatement pstActualizarStock;
                    pstActualizarStock = con.prepareStatement("declare @resultado varchar(10)\n"
                            + "exec actualizarStock ?,?,'quitar', @resultado output\n"
                            + "select @resultado");
                    pstActualizarStock.setInt(1, id_repuesto);
                    pstActualizarStock.setInt(2, cant);

                    ResultSet rsActualizarStock = pstActualizarStock.executeQuery();
                    rsActualizarStock.next();
                    exito = rsActualizarStock.getString(1).equals("exito");
                    rsActualizarStock.close();
                    pstActualizarStock.close();
                    
                    if (!exito) {
                        con.rollback();
                    }
                    
                } else {
                    con.rollback();
                }
            }
            con.commit();
            con.setAutoCommit(true);

            rsVerificar.close();
            pstVerificar.close();
            Desconectar();
        } catch (Exception ex) {
            System.out.println("Error en EliminarDetalleReposicion / GestorProveedores: " + ex.toString());
        }
        return exito;

    }

}
