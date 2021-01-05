package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Persona;
import Model.Repuesto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReportesStockServlet", urlPatterns = {"/ReportesStockServlet"})
public class ReportesStockServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorRepuesto gr = new GestorRepuesto();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            if (administrador) {
                ArrayList<Repuesto> lstNoAlerta = gr.ListarRepuestosBuenStock();
                ArrayList<Repuesto> lstAlerta = gr.ListarRepuestosBajoStock();

                request.setAttribute("lstNoAlerta", lstNoAlerta);
                request.setAttribute("lstAlerta", lstAlerta);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ReportesStock.jsp");
                rd.forward(request, response);
            }
        } else {//---------------------------FIN QUE SEA ADM------------------
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
