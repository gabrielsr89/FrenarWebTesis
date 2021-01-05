package Servlet;

import Controller.GestorPersonas;
import Controller.GestorProveedores;
import Controller.GestorRepuesto;
import Model.Persona;
import Model.Proveedor;
import Model.Reporte;
import Model.Reposicion;
import Model.Repuesto;
import Model.Rubro;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReporteGastosServlet", urlPatterns = {"/ReporteGastosServlet"})
public class ReporteGastosServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorRepuesto gr = new GestorRepuesto();
    GestorProveedores gprov = new GestorProveedores();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //---------------------------VALIDAR QUE SEA ADM------------------
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

                if (administrador) {
                    ArrayList<Integer> lstYear = gprov.TraerLosAñosRegistrados();
                    ArrayList<Reporte> lstGastosPorMes;
                    if (request.getParameter("mes") != null && request.getParameter("year") != null) {
                        int mes = Integer.parseInt(request.getParameter("mes"));
                        int year = Integer.parseInt(request.getParameter("year"));
                        ArrayList<Reporte> lstParametrizada = gprov.GastosPorMes(mes, year);
                        Reporte r = new Reporte();
                        r.setMesCompra(mes);
                        String año = "";
                        if (year == 0) {
                            año = "Todos los años";
                        } else {
                            año = String.valueOf(year);
                        }
                        request.setAttribute("lstParametrizada", lstParametrizada);
                        request.setAttribute("year", año);
                        request.setAttribute("mes", r.mes());

                    } else {
                        lstGastosPorMes = gprov.GastosPorMes(0, 0);
                        request.setAttribute("lstGastosPorMes", lstGastosPorMes);
                    }
                    request.setAttribute("lstYear", lstYear);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ReporteGastos.jsp");
                    rd.forward(request, response);

                }
            } else {
                System.out.println("no hay nadie logeado en get de reponerRepuestos");
            }
        } catch (Exception ex) {
            System.out.println("Exepción en get de reponerRepuestos: " + ex.toString());
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
