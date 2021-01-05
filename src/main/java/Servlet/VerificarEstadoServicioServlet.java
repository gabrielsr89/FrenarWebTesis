package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Cliente;
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

@WebServlet(name = "VerificarEstadoServicioServlet", urlPatterns = {"/VerificarEstadoServicioServlet"})
public class VerificarEstadoServicioServlet extends HttpServlet {

    GestorFacturas gf = new GestorFacturas();
    GestorPersonas gp = new GestorPersonas();

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
                if (cliente || administrador || operador) {                    
                    int id_cliente = 0;                    
                    if (request.getParameter("id_c") != null) {
                        id_cliente = Integer.parseInt(request.getParameter("id_c"));
                    } else {
                        id_cliente = (Integer) request.getSession().getAttribute("idClienteX");
                    }
                    ArrayList<Factura> lstEspera = gf.ListadoEspera(id_cliente);
                    ArrayList<Factura> lstReparacion = gf.ListadoReparacion(id_cliente);
                    ArrayList<Factura> lstFinalizado = gf.ListadoTerminados(id_cliente);
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    
                    request.setAttribute("lstCliente", lstCliente);
                    request.setAttribute("lstEspera", lstEspera);
                    request.setAttribute("lstReparacion", lstReparacion);
                    request.setAttribute("lstFinalizado", lstFinalizado);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/VerificarEstadoServicio.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./SinPermiso.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " Problemas en get de VerificarEstadoServicioServlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
