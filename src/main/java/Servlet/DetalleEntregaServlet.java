package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Model.Factura;
import Model.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalleEntregaServlet", urlPatterns = {"/DetalleEntregaServlet"})
public class DetalleEntregaServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorFacturas gf = new GestorFacturas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getSession().getAttribute("administrador") != null
                    && request.getSession().getAttribute("operador") != null
                    && request.getSession().getAttribute("cliente") != null
                    && request.getSession().getAttribute("idUsuario") != null) {

                boolean administrador = (boolean) request.getSession().getAttribute("administrador");
                boolean operador = (boolean) request.getSession().getAttribute("operador");
                boolean cliente = (boolean) request.getSession().getAttribute("cliente");
                Persona p = gp.obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                if (administrador || operador || cliente) {

                    Factura carrito = null;
                    int id_f = 0;

                    if (request.getParameter("id_factura") != null) {
                        id_f = Integer.parseInt(request.getParameter("id_factura"));
                        carrito = gf.ObtenerDetallesDeEntrega(id_f);
                    }

                    if (request.getParameter("id_presupuesto") != null) {
                        id_f = Integer.parseInt(request.getParameter("id_presupuesto"));
                        carrito = gf.ObtenerDetallesDePresupuesto(id_f);

                    }

                    request.setAttribute("carrito", carrito);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/DetallesDeEntrega.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./SinPermiso.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " Problemas en get DetallesEntrega Servlet");
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
