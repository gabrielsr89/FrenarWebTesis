package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.DetallePedidoRepuesto;
import Model.Factura;
import Model.Persona;
import Model.Repuesto;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalleArticuloServlet", urlPatterns = {"/DetalleArticuloServlet"})
public class DetalleArticuloServlet extends HttpServlet {

    GestorRepuesto gr = new GestorRepuesto();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //---------------------------SESION------------------
            if (request.getSession().getAttribute("administrador") != null
                    && request.getSession().getAttribute("operador") != null
                    && request.getSession().getAttribute("cliente") != null
                    && request.getSession().getAttribute("idUsuario") != null) {

                boolean administrador = (boolean) request.getSession().getAttribute("administrador");
                boolean operador = (boolean) request.getSession().getAttribute("operador");
                boolean cliente = (boolean) request.getSession().getAttribute("cliente");
                Persona p = new GestorPersonas().obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);

                int idRepuesto = 0;
                int cantidad = 1;
                int posicion = 0;
                int bandera = 0;

                if (request.getParameter("idRepuesto") != null) {
                    idRepuesto = Integer.parseInt(request.getParameter("idRepuesto"));
                    bandera = 1;
                }
                if (request.getParameter("modificar") != null) {
                    idRepuesto = Integer.parseInt(request.getParameter("modificar"));
                    
                    Factura carro = (Factura) request.getSession().getAttribute("carrito");
                    System.out.println("tamaño lista: " + carro.lstRepuesto.size());

                    for (DetallePedidoRepuesto dpr : carro.lstRepuesto) {
                        if (idRepuesto == dpr.getR().getId_repuesto()) {
                            cantidad = dpr.getCantidad();
                            posicion = carro.lstRepuesto.indexOf(dpr);
                            break;
                        }
                    }
                    bandera = 2;
                }
                int idCliente = 0;
                if (request.getParameter("idCliente") != null) {
                    idCliente = Integer.parseInt(request.getParameter("idCliente"));
                } else {
                    idCliente = (Integer) request.getSession().getAttribute("idClienteX");
                }
                request.setAttribute("idCliente", idCliente);

                Repuesto r = gr.obtenerRepuesto(idRepuesto);
                request.setAttribute("posicion", posicion);
                request.setAttribute("cantidad", cantidad);
                request.setAttribute("bandera", bandera);
                request.setAttribute("r", r);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/DetalleArticulo.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Factura carrito = null;
            ArrayList<DetallePedidoRepuesto> lstDetallePedido;
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int id_repuesto = Integer.parseInt(request.getParameter("id_repuesto"));
            Repuesto r = gr.obtenerRepuesto(id_repuesto);
            DetallePedidoRepuesto detalle = new DetallePedidoRepuesto(cantidad, r);
            detalle.setId_repuesto(id_repuesto);
            detalle.setPrecio(r.getPrecio());

            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            //SI CARRO ES NULO CREAMOS UN CARRO
            if (request.getSession().getAttribute("carrito") == null) {
                lstDetallePedido = new ArrayList<>();
                lstDetallePedido.add(detalle);
                carrito = new Factura(lstDetallePedido, idCliente);

            } else { //SI NO ES NULO O AGREGAMOS UNO, O MODIFICAMOS UN ITEM.
                carrito = (Factura) request.getSession().getAttribute("carrito");
                //SE AGREGA UN ITEM AL CARRO
                if (Integer.parseInt(request.getParameter("bandera")) == 1) {
                    carrito.CargarDetalleRepuesto(detalle);
                }//SE MODIFICA LA CANTIDAD ACORDE A LA POSICIÓN DEL ARREGLO CARRITO TOMADA EN EL DOGET
                if (Integer.parseInt(request.getParameter("bandera")) == 2) {
                    int posicion = Integer.parseInt(request.getParameter("posicion"));
                    carrito.CambiarCantidad(cantidad, posicion);
                }
            }

            request.getSession().setAttribute("carrito", carrito);
            response.sendRedirect("./CatRepuestosServlet?idCt=" + idCliente);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
