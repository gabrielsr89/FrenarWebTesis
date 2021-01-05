package Servlet;

import Controller.GestorFacturas;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
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

@WebServlet(name = "FacturadoYPagadoListados", urlPatterns = {"/FacturadoYPagadoListados"})
public class FacturadoYPagadoListados extends HttpServlet {

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
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();

                request.setAttribute("AlertaStock", AlertaStock);
                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                if (administrador || operador) {
                    ArrayList<Factura> listado = gf.FacturadoYPagadoListados();

                    request.setAttribute("listado", listado);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/FacturadoYPagadoListados.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./SinPermiso.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " Problemas en FacturadoYPagadoListados Servlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("id_factura") != null) {
            int id_f = Integer.parseInt(request.getParameter("id_factura"));
            int id_u = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));
            
            boolean exito = gf.EntregaDeComprasOnline(id_f, id_u);
            if (exito) {
                response.sendRedirect("./ExitoServlet?url=headerServlet");
            } else {
                response.sendRedirect("./ErrorServlet?msjError=ProblemasEnDoPostDeFacturadoYPagado");
            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
