package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorServicios;
import Model.Categorias;
import Model.Persona;
import Model.Servicio;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarServicios", urlPatterns = {"/AdministrarServicios"})
public class AdministrarServicios extends HttpServlet {

    GestorServicios gs = new GestorServicios();

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
                Persona p = new GestorPersonas().obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);

                if (administrador) {

                    ArrayList<Servicio> lstServicios = gs.TraerServicios();
                    ArrayList<Categorias> lstCategorias = gs.TraerCategorias();

                    int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
                    request.setAttribute("AlertaStock", AlertaStock);

                    request.setAttribute("lstServicios", lstServicios);
                    request.setAttribute("lstCategorias", lstCategorias);

                    if (request.getParameter("id_servicio") != null) {
                        int id_servicio = Integer.parseInt(request.getParameter("id_servicio"));
                        Servicio s = gs.obtenerServicio(id_servicio);
                        request.setAttribute("s", s);
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarServicios.jsp");
                    rd.forward(request, response);
                } else {//---------------------------FIN QUE SEA ADM------------------
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                    rd.forward(request, response);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String servicio = request.getParameter("servicio");
        int tiempo = Integer.parseInt(request.getParameter("tiempo"));
        int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));
        String descripcion = request.getParameter("descripcion");

        Servicio s = new Servicio(servicio, tiempo, id_categoria, descripcion);

        boolean exito = false;

        if (request.getParameter("id_servicio") == null) {
            exito = gs.NuevoServicio(s);
        } else {
            s.setId_servicio(Integer.parseInt(request.getParameter("id_servicio")));

            exito = gs.ModificarServicio(s);
        }

        if (exito) {
            response.sendRedirect("./ExitoServlet?url=AdministrarServicios");
        } else {
            response.sendRedirect("./Error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
