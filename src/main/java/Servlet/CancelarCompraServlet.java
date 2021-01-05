package Servlet;

import Controller.GestorServicios;
import Model.DetallePedidoServicio;
import Model.Factura;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CancelarCompraServlet", urlPatterns = {"/CancelarCompraServlet"})
public class CancelarCompraServlet extends HttpServlet {

    GestorServicios gs = new GestorServicios();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Factura carrito = (Factura) request.getSession().getAttribute("carrito");
        if (request.getParameter("repuestos") != null) {
            int repuestos = Integer.parseInt(request.getParameter("repuestos"));
            System.out.println(repuestos + "a as");
            if (repuestos == 1) {
                request.getSession().setAttribute("carrito", null);
                response.sendRedirect("./CatRepuestosServlet");
            }
            if (repuestos == 2) {
                if (carrito.lstServicio == null || carrito.lstServicio.size() < 1) {
                    request.getSession().setAttribute("carrito", null);
                    response.sendRedirect("./CatRepuestosServlet");
                } else {
                    carrito.lstRepuesto.clear();
                    carrito.lstRepuesto = null;
                    request.getSession().setAttribute("carrito", carrito);
                    response.sendRedirect("./ConfirmarCompraServlet");
                }
            }
        }
        if (request.getParameter("servicio") != null) {
            int servicio = Integer.parseInt(request.getParameter("servicio"));
            if (request.getSession().getAttribute("ServicioNuevo") != null) {
                int id_servicio = (Integer) request.getSession().getAttribute("ServicioNuevo");
                for (DetallePedidoServicio d : carrito.lstServicio) {
                    if(d.getS().getId_servicio() == id_servicio){
                        gs.EliminarServicio(id_servicio);                        
                    }
                }
            }
            if (servicio == 1) {
                carrito.lstServicio.clear();
                carrito.lstServicio = null;
                request.getSession().setAttribute("carrito", carrito);
                response.sendRedirect("./ConfirmarCompraServlet");
            }
            if (servicio == 2) {
                carrito = null;
                request.getSession().setAttribute("carrito", carrito);
                response.sendRedirect("./headerServlet");
            }
            
        }
        if (request.getParameter("todo") != null) {
            int todo = Integer.parseInt(request.getParameter("todo"));
            if (todo == 1) {
                carrito = null;
                request.getSession().setAttribute("carrito", carrito);
                response.sendRedirect("./headerServlet");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
