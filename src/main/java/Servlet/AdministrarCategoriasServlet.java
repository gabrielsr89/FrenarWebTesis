package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorServicios;
import Model.Categorias;
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

@WebServlet(name = "AdministrarCategoriasServlet", urlPatterns = {"/AdministrarCategoriasServlet"})
public class AdministrarCategoriasServlet extends HttpServlet {

    GestorServicios gs = new GestorServicios();

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
                    //---------------------------CUERPO SI ES ADMINISTRADOR------------------
                    ArrayList<Categorias> lstCategorias = gs.TraerCategorias();

                    request.setAttribute("lstCategorias", lstCategorias);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarCategorias.jsp");
                    rd.forward(request, response);
                }
            } else {//---------------------------FIN QUE SEA ADM------------------
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            System.out.println("Error en el doGet de AdministrarCategoriasServlet: " + ex.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getParameter("eliminar") != null) {
                boolean exito = false;
                int id = Integer.parseInt(request.getParameter("eliminar"));
                exito = gs.EliminarCategoria(id);
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(exito);

            } else {
                boolean exito = true;
                String categoria = request.getParameter("categoria");
                if (Integer.parseInt(request.getParameter("hiddenId")) != 0) {
                    int id = Integer.parseInt(request.getParameter("hiddenId"));                    
                    exito = gs.ModificarCategoria(id, categoria);
                } else {    
                    exito = gs.NuevaCategoria(categoria);
                }
                if (exito) {
                    response.sendRedirect("./AdministrarCategoriasServlet");
                }else {
                    response.sendRedirect("./ErrorServlet");
                }
            }
        } catch (Exception ex) {
            System.out.println("Excepción en doPost de AdministrarCategoriasServlet: " + ex.toString());
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
