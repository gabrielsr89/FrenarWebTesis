package Servlet;

import Controller.GestorFacturas;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorServicios;
import Model.DetallePedidoServicio;
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

@WebServlet(name = "ListarTrabajos", urlPatterns = {"/ListarTrabajos"})
public class ListarTrabajos extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorServicios gs = new GestorServicios();
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
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();

                request.setAttribute("AlertaStock", AlertaStock);
                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                if (administrador || operador) {
                    ArrayList<DetallePedidoServicio> lista = gf.ObtenerTodosLosDetallesDeServicioAReparar();

                    request.setAttribute("lista", lista);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListarTrabajos.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./SinPermiso.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " Problemas en Listar Trabajos Servlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
                if (administrador || operador) {

                    String accion = request.getParameter("accion");
                    int id_detalle = Integer.parseInt(request.getParameter("id_detalle"));
                    int id_factura = 0;
                    if (request.getParameter("id_fts") != null) {
                        id_factura = Integer.parseInt(request.getParameter("id_fts"));
                    }
                    int id_usuario = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));

                    boolean exito = false;

                    if (accion.equals("tomar")) {
                        String condicion = gs.TomarServicio(id_detalle, id_usuario);
                        if (!condicion.equals("Orden de trabajo insertada con éxito")) {
                            request.getSession().setAttribute("msjError", condicion);
                            exito = false;
                        } else {
                            exito = true;
                        }
                    }
                    if (accion.equals("finalizar")) {
                        exito = gs.FinalizarServicio(id_detalle, id_factura);
                    }
                    if (exito) {
                        response.sendRedirect("./ListarTrabajos");
                    } else {
                        response.sendRedirect("./ErrorServlet");
                    }

                } else {
                    response.sendRedirect("./SinPermiso.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " Problemas en do post ListarTrabajos");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
