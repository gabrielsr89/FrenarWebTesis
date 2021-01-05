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

@WebServlet(name = "PresupuestosCancelados", urlPatterns = {"/PresupuestosCancelados"})
public class PresupuestosCancelados extends HttpServlet {

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
                    ArrayList<Factura> lista = new ArrayList();
                    String usuario = "";
                    if (cliente) {
                        int id_c = (Integer) request.getSession().getAttribute("idClienteX");
                        lista = gf.PresupuestosCanceladosPorIdCt(id_c);
                        usuario = "cliente";
                        request.setAttribute("id_c", id_c);
                    }
                    if (administrador || operador) {
                        lista = gf.PresupuestosCancelados();
                        usuario = "empleado";
                    }
                    request.setAttribute("lista", lista);
                    request.setAttribute("usuario", usuario);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/PresupuestosCancelados.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./ErrorServlet?msjError=SinAutorizacion");
                }

            } else {
                response.sendRedirect("./SesionCaducada.jsp");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " error en get ConfirmarPresupuestoServlet");
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
