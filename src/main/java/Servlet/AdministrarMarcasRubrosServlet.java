package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.MarcaRepuesto;
import Model.Persona;
import Model.Rubro;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarMarcasRubrosServlet", urlPatterns = {"/AdministrarMarcasRubrosServlet"})
public class AdministrarMarcasRubrosServlet extends HttpServlet {

    GestorRepuesto gr = new GestorRepuesto();

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

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                //---------------------------VALIDAR QUE SEA ADM------------------
                if (administrador) {
                    ArrayList<MarcaRepuesto> lstMarcas = gr.ListarMarcasRepuesto();
                    ArrayList<Rubro> lstRubros = gr.ListarRubros();

                    request.setAttribute("lstMarcas", lstMarcas);
                    request.setAttribute("lstRubros", lstRubros);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarMarcasRubros.jsp");
                    rd.forward(request, response);
                }
            } else {//---------------------------FIN QUE SEA ADM------------------
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("error en adm marcas y rubros " + ex.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("eliminarMarca") != null) {
            int id = Integer.parseInt(request.getParameter("eliminarMarca")); 
            String x = "false";
            if(gr.EliminarMarca(id)){
                x= "true";
            }
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(x);

        } else if (request.getParameter("eliminarRubro") != null) {
            int id = Integer.parseInt(request.getParameter("eliminarRubro"));
            String x = "false";
            if(gr.EliminarRubro(id)){
                x= "true";
            }            
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(x);

        } else {

            int id = Integer.parseInt(request.getParameter("hiddenId"));
            String accion = request.getParameter("hiddenAccion");
            String nuevoRegistro = request.getParameter("txtNuevoRegistro");

            boolean exito = gr.AltaModificacionRubrosMarcas(id, nuevoRegistro, accion);

            if (exito) {
                response.sendRedirect("./AdministrarMarcasRubrosServlet");
            } else {
                response.sendRedirect("/ErrorServlet");
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
