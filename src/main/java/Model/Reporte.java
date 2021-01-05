package Model;

import Controller.GestorProveedores;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Reporte {

    //Para reportes de compras por mes
    private int mesCompra;
    private int año;
    private int mes;
    private int id_cliente;
    private int id_marca;
    private int id_rubro;
    private int id_repuesto;
    private int id_servicio;
    private int cantidad;
    
    private double importe;    
    
    private String categoria;
    private String repuesto;
    private String marca;
    private String rubro;
    private String nombreCliente;
    private String nombreVehiculo;
    private String servicio;
    private String descripcion;
    private String estado;
    
    private Date fecha;
    private Date fechaInicio;
    private Date fechaFin;
    
    //Reporte 3c
    public Reporte(int año, int cantidad, String categoria){
        this.año = año;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }
    
    //Reporte 3b
    public Reporte(int mes, int id_servicio, String servicio, String descripcion, int cantidad){
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.id_servicio = id_servicio;
        this.mes = mes;    
    }
    
    //Reporte 3-a
    public Reporte(String auto, int cantidad){
        this.nombreVehiculo = auto;
        this.cantidad = cantidad;
    }
    
    //Reporte 2-c
    public Reporte(int id_repuesto, String repuesto, int cantidad, int mes){
        this.id_repuesto = id_repuesto;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.mes = mes;
    }
    
    //Reporte 2-b
    public Reporte(int id_rubro, int cantidad, String rubro, int mes){
        this.id_rubro = id_rubro;
        this.rubro = rubro;
        this.cantidad = cantidad;
        this.mes = mes;
    }
    
    //Reporte 2-a
    public Reporte(int id_marca, String marca, int cantidad){
        this.id_marca = id_marca;
        this.marca = marca;
        this.cantidad = cantidad;
    }

    //Reporte 1-a
    public Reporte(int año, int mes, int id_cliente, String nombreCliente, int cantidad ){
        this.año = año;
        this.mes= mes;
        this.id_cliente = id_cliente;
        this.nombreCliente = nombreCliente;
        this.cantidad = cantidad;    
    }

    public int getId_repuesto() {
        return id_repuesto;
    }

    public void setId_repuesto(int id_repuesto) {
        this.id_repuesto = id_repuesto;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    public int getId_rubro() {
        return id_rubro;
    }

    public void setId_rubro(int id_rubro) {
        this.id_rubro = id_rubro;
    }

    public String getRubro() {
        return rubro;
    }

    //Para listar clientes por servicio
    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreVehiculo() {
        return nombreVehiculo;
    }

    public void setNombreVehiculo(String nombreVehiculo) {
        this.nombreVehiculo = nombreVehiculo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    //Para reporte de clientes por servicio realizado
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getMesCompra() {
        return mesCompra;
    }

    public void setMesCompra(int mesCompra) {
        this.mesCompra = mesCompra;
    }

    public String getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(String repuesto) {
        this.repuesto = repuesto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
    public Reporte(){}

    //Gastos por mes
    public Reporte(String repuesto, int mesCompra, int cantidad, double importe) {
        this.mesCompra = mesCompra;
        this.cantidad = cantidad;
        this.repuesto = repuesto;
        this.importe = importe;
    }
    //Clientes por servicio
     public Reporte(String nombreCliente, String nombreVehiculo, String servicio, String estado, Date fechaInicio, Date fechaFin) {
        this.nombreCliente = nombreCliente;
        this.nombreVehiculo = nombreVehiculo;
        this.servicio = servicio;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String mes() {
        String mesTexto = "";
        int mesAfiltrar= 0;
        
        if(mesCompra>0){
            mesAfiltrar = this.mesCompra;
        }
        else{
            mesAfiltrar = this.mes;
        }
        switch (mesAfiltrar) {
            case 1:
                mesTexto = "Enero";
                break;
            case 2:
                mesTexto = "Febrero";
                break;
            case 3:
                mesTexto = "Marzo";
                break;
            case 4:
                mesTexto = "Abril";
                break;
            case 5:
                mesTexto = "Mayo";
                break;
            case 6:
                mesTexto = "Junio";
                break;
            case 7:
                mesTexto = "Julio";
                break;
            case 8:
                mesTexto = "Agosto";
                break;
            case 9:
                mesTexto = "Septiembre";
                break;
            case 10:
                mesTexto = "Octubre";
                break;
            case 11:
                mesTexto = "Noviembre";
                break;
            case 12:
                mesTexto = "Diciembre";
                break;
            default:
                mesTexto = "Todos los meses";
                break;
        }
        return mesTexto;
    }
    
     public String FechaInicio() {
        Format formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        Format formatoHora = new SimpleDateFormat("HH:mm:ss");        
        return "Día: "+formatoFecha.format(fechaInicio)+", hora: "+formatoHora.format(fechaInicio);
    }
      public String FechaFin() {
        Format formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        Format formatoHora = new SimpleDateFormat("HH:mm");        
        return "Día: "+formatoFecha.format(fechaFin)+", hora: "+formatoHora.format(fechaFin);
    }
}
