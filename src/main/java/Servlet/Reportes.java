package Servlet;

import Controller.GestorPersonas;
import Controller.GestorReportes;
import Controller.GestorRepuesto;
import Model.Persona;
import Model.Reporte;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Reportes", urlPatterns = {"/Reportes"})
public class Reportes extends HttpServlet {

    GestorReportes gr = new GestorReportes();

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
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();

                request.setAttribute("AlertaStock", AlertaStock);
                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                //---------------------------VALIDAR QUE SEA ADM------------------
                if (administrador) {
                    ArrayList<Reporte> lst1a = gr.lst1a();
                    ArrayList<Reporte> lst2a = gr.lst2a();
                    ArrayList<Reporte> lst2b = gr.lst2b();                    
                    ArrayList<Reporte> lst2c = gr.lst2c();
                    ArrayList<Reporte> lst3a = gr.lst3a();
                    ArrayList<Reporte> lst3b = gr.lst3b();
                    ArrayList<Reporte> lst3c = gr.lst3c();

                    request.setAttribute("lst1a", lst1a);
                    request.setAttribute("lst2a", lst2a);
                    request.setAttribute("lst2b", lst2b);
                    request.setAttribute("lst2c", lst2c);
                    request.setAttribute("lst3a", lst3a);
                    request.setAttribute("lst3b", lst3b);
                    request.setAttribute("lst3c", lst3c);
                    
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Reportes.jsp");
                    rd.forward(request, response);

                }
            } else {//---------------------------FIN QUE SEA ADM------------------
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("Error en el doGet de Reportes: " + ex.toString());
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
