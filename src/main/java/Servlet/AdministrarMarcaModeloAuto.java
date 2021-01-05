package Servlet;

import Controller.GestorAutos;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Auto;
import Model.Cliente;
import Model.MarcaAuto;
import Model.ModeloAuto;
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

@WebServlet(name = "AdministrarMarcaModeloAuto", urlPatterns = {"/AdministrarMarcaModeloAuto"})
public class AdministrarMarcaModeloAuto extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorAutos ga = new GestorAutos();

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
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
                request.setAttribute("AlertaStock", AlertaStock);

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);

                if (administrador) {
                    ArrayList<MarcaAuto> lstMarcas = ga.ListarMarcas();
                    ArrayList<ModeloAuto> lstModelos = ga.ListarMarcasConModelos("");

                    request.setAttribute("lstMarcas", lstMarcas);
                    request.setAttribute("lstModelos", lstModelos);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarMarcaModeloAuto.jsp");
                    rd.forward(request, response);
                } else {
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                    rd.forward(request, response);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getParameter("txtParametroMarca") != null) {
            String buscar = request.getParameter("txtParametroMarca");
            ArrayList<MarcaAuto> lstMarcas = ga.ListarMarcasParametrizadas(buscar);
            String x = "";
            for (MarcaAuto m : lstMarcas) {
                x += m.toStringRevoluciones() + ",\n";
            }

            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(x);

        } else if (request.getParameter("txtParametroModelo") != null) {
            String buscar = request.getParameter("txtParametroModelo");
            
            ArrayList<ModeloAuto> lstModelos = ga.ListarMarcasConModelos(buscar);

            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            String x = "";
            for (ModeloAuto m : lstModelos) {
                x += m.toStringRevoluciones() + ",\n";
            }
            out.print(x);

        } else {

            if (request.getParameter("eliminarMarca") != null) {
                int id = Integer.parseInt(request.getParameter("eliminarMarca"));
                String x = "false";
                if (ga.EliminarMarca(id)) {
                    x = "true";
                }
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(x);

            } else if (request.getParameter("eliminarModelo") != null) {                
                
                int id = Integer.parseInt(request.getParameter("eliminarModelo"));
                String x = "false";
                if (ga.EliminarModelo(id)) {
                    x = "true";
                }
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(x);

            } else {

                int id = Integer.parseInt(request.getParameter("hiddenId"));
                String accion = request.getParameter("hiddenAccion");
                String nuevoRegistro = request.getParameter("txtNuevoRegistro");
                int id_marca = 0;
                if (request.getParameter("id_marca") != null) {
                    id_marca = Integer.parseInt(request.getParameter("id_marca"));
                }

                boolean exito = ga.AltaModificacionModelosMarcas(id, nuevoRegistro, accion, id_marca);

                if (exito) {
                    response.sendRedirect("./AdministrarMarcaModeloAuto");
                } else {
                    response.sendRedirect("/ErrorServlet");
                }
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
