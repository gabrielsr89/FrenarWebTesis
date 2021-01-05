package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorUbicacion;
import Model.Barrio;
import Model.Localidad;
import Model.Persona;
import Model.Provincia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarUbicacionesServlet", urlPatterns = {"/AdministrarUbicacionesServlet"})
public class AdministrarUbicacionesServlet extends HttpServlet {

    GestorUbicacion gu = new GestorUbicacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //-------------VALIDAR QUE SEA ADM Y CARGAR EL HEADER------------------
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

                if (administrador) {
                    //---------------------------CUERPO SI ES ADMIN----------------------//

                    ArrayList<Provincia> ptc = gu.ListarProvincias();
                    ArrayList<Localidad> lstLocalidades = gu.ListaLocalidades();
                    ArrayList<Barrio> lstBarrios = gu.ListaBarrios();
                    ArrayList<Persona> lstBarriosNuevos = gu.ListarBarriosNuevos();

                    request.setAttribute("cantidad", lstBarriosNuevos.size());
                    request.setAttribute("ptc", ptc);
                    request.setAttribute("lstLocalidades", lstLocalidades);
                    request.setAttribute("lstBarrios", lstBarrios);
                    //JS
                    ArrayList<Localidad> lpjs = gu.LocalidadesParaJS();
                    ArrayList<Barrio> bpjs = gu.BarriosParaJS();
                    request.setAttribute("lpjs", lpjs);
                    request.setAttribute("bpjs", bpjs);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarUbicaciones.jsp");
                    rd.forward(request, response);

                } else {//--------------------------FIN SI ES ADM---------------------//
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                    rd.forward(request, response);
                }
            } else {
                response.sendRedirect("./headerServlet");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int id_padre = Integer.parseInt(request.getParameter("id_padre"));
            String texto = request.getParameter("texto");
            String accion = request.getParameter("accion");

            boolean exito = false;

            exito = gu.ABMProvinciaLocalidadBarrio(id, id_padre, texto, accion);

            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(exito);

        } catch (Exception ex) {
            System.out.println("Error en post de AdministrarUbicacionesServlet: " + ex.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";

    }

}
