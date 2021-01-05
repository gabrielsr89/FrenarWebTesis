package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Persona;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "inicioPersonaServlet", urlPatterns = {"/inicioPersonaServlet"})
public class inicioPersonaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //------------------------------LOGIN---------------------------------//
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
            //---------------------------------FIN LOGIN-----------------------------//
            String tipo = "";
            if (administrador) {
                tipo += "ADMINISTRADOR ";
            }
            if (operador) {
                tipo = "OPERADOR ";
            }
            if (cliente) {
                tipo = "CLIENTE ";
            }

            int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
            request.setAttribute("AlertaStock", AlertaStock);

            request.setAttribute("tipo", tipo);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/inicioPersona.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
            rd.forward(request, response);
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
