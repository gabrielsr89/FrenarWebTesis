package Model;

import Controller.GestorAutos;
import Controller.GestorPersonas;
import java.sql.Date;
import java.text.Format;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Factura {

    private int id_factura;
    private int id_cliente;
    private int id_usuario;
    private int id_auto;
    private Date fecha;
    private int id_estado;
    public ArrayList<DetallePedidoRepuesto> lstRepuesto = null;
    public ArrayList<DetallePedidoServicio> lstServicio = null;
    private double importe;

    private Timestamp f_inicio;
    private Timestamp f_fin;

    public Timestamp getF_inicio() {
        return f_inicio;
    }

    public void setF_inicio(Timestamp f_inicio) {
        this.f_inicio = f_inicio;
    }

    public Timestamp getF_fin() {
        return f_fin;
    }

    //GET & SET
    public void setF_fin(Timestamp f_fin) {
        this.f_fin = f_fin;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public ArrayList<DetallePedidoRepuesto> getLstRepuesto() {
        return lstRepuesto;
    }

    public void setLstRepuesto(ArrayList<DetallePedidoRepuesto> lstRepuesto) {
        this.lstRepuesto = lstRepuesto;
    }

    public ArrayList<DetallePedidoServicio> getLstServicio() {
        return lstServicio;
    }

    public void setLstServicio(ArrayList<DetallePedidoServicio> lstServicio) {
        this.lstServicio = lstServicio;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    //CONSTRUCTORES
    //listados para ct
    public Factura(int id_f, Date fecha, int id_auto, int id_cliente) {
        this.id_factura = id_f;
        this.fecha = fecha;
        this.id_auto = id_auto;
        this.id_cliente = id_cliente;
    }

    public Factura(int id_f, Date fecha, int id_auto, Timestamp f_inicio, int id_cliente) {
        this.id_factura = id_f;
        this.fecha = fecha;
        this.id_auto = id_auto;
        this.f_inicio = f_inicio;
        this.id_cliente = id_cliente;
    }

    public Factura(int id_f, Date fecha, int id_auto, Timestamp inicio, Timestamp fin, int id_cliente) {
        this.id_factura = id_f;
        this.fecha = fecha;
        this.id_auto = id_auto;
        this.f_inicio = inicio;
        this.f_fin = fin;
        this.id_cliente = id_cliente;
    }

    public Factura(int id, int id_usuario, Date fecha, int id_auto, int id_cliente) {
        this.id_factura = id;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.id_auto = id_auto;
        this.id_cliente = id_cliente;

    }

    public Factura(int id, int id_usuario, Date fecha, int id_cliente) {
        this.id_factura = id;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.id_cliente = id_cliente;
    }

    public Factura() {
    }

    //Factura para historial de facturas
    public Factura(int id_f, int id_usuario, Date fecha, int id_auto, int id_cliente, double importe) {
        this.id_factura = id_f;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.id_auto = id_auto;
        this.id_cliente = id_cliente;
        this.importe = importe;
    }

    public String toStringRevoluciones() {
        return "{" + id_factura + ", " + Fecha() + ", " + importe + ", " + ObtenerNombreCliente() + ", " + ObtenerStrngDeAuto() + ", " + ObtenerNombreResponsable() + '}';
    }

    public Factura(ArrayList<DetallePedidoRepuesto> lstRepuesto, int idCliente) {
        this.lstRepuesto = lstRepuesto;
        this.id_cliente = idCliente;
    }

    public Factura(int id_usuario, int id_auto) {
        this.id_usuario = id_usuario;
        this.id_auto = id_auto;
    }

    //MÉTODOS
    public void CargarDetalleRepuesto(DetallePedidoRepuesto detalle) {
        lstRepuesto.add(detalle);
    }

    public double calcularTotalServicios() {
        double importe = 0;
        if (lstServicio != null && lstServicio.size() > 0) {
            for (DetallePedidoServicio d : lstServicio) {
                importe += d.getPrecioServicio();
            }
        }
        return importe;
    }

    public double calcularBonificacionServiciosPorCategoriaEmpleado() {
        int descuentoPorCategoria = new GestorPersonas().obtenerDescuentoCtPorIdCt(id_cliente);
        return (calcularTotalServicios() * descuentoPorCategoria) / 100;
    }

    public double calcularMontoFinalServicios() {
        return calcularTotalServicios() - calcularBonificacionServiciosPorCategoriaEmpleado();
    }

    public double calcularTotalRepuestos() {
        double importe = 0;
        if (lstRepuesto != null && lstRepuesto.size() > 0) {
            for (DetallePedidoRepuesto d : lstRepuesto) {
                importe += d.getCantidad() * d.getPrecio();
            }
        }

        return importe;
    }

    public double calcularTotal() {
        return calcularTotalRepuestos() + calcularMontoFinalServicios();
    }

    public void removerRepuestoDelCarrito(int idRepuesto) {
        try {
            for (DetallePedidoRepuesto d : lstRepuesto) {
                if (d.getR().getId_repuesto() == idRepuesto) {
                    lstRepuesto.remove(d);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public int carritoCantidadElementos() {
        return lstRepuesto.size();
    }

    public void CambiarCantidad(int cantidad, int posicion) {
        lstRepuesto.get(posicion).setCantidad(cantidad);
    }

    public String Fecha() {
        String fecha = "";
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        fecha = formatter.format(this.fecha);
        return fecha;
    }

    public String FechaInicio() {
        
        DateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat Hora = new SimpleDateFormat("HH:mm:ss");
        String fechaInicio = "Día: "+dia.format(f_inicio)+", hora: "+Hora.format(f_inicio);
        return fechaInicio;
        
    }

    public String FechaFin() {
        DateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat Hora = new SimpleDateFormat("HH:mm:ss");
        return "Día: "+dia.format(f_fin)+", hora: "+Hora.format(f_fin);
    }

    public String ObtenerNombreCliente() {
        Persona perk = new GestorPersonas().obtenerNombreApellidoPorIdCliente(id_cliente);
        return perk.getNombre() + " " + perk.getApellido();
    }

    public String ObtenerNombreResponsable() {
        Persona perk = new GestorPersonas().obtenerNombreApellidoPorIdEmpleado(id_cliente);
        return perk.getNombre() + " " + perk.getApellido();
    }

    public String marcaModeloDelAuto() {
        if (id_auto > 0) {
            return new GestorAutos().obtenerMarcaModeloPorIdAuto(id_auto);
        } else {
            return "No aplica";
        }
    }

    public String ObtenerStrngDeAuto() {
        if (id_auto > 0) {
            return new GestorAutos().ObtenerStrngDeAuto(id_auto);
        } else {
            return "No aplica";
        }
    }

    @Override
    public String toString() {
        return "Nro factura: " + id_factura + ", fecha de emisión: " + fecha + ", importe: " + calcularTotal();
    }

}
