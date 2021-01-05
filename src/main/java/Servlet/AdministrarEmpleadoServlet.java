package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Persona;
import Model.TipoUsuario;
import Model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarEmpleadoServlet", urlPatterns = {"/AdministrarEmpleadoServlet"})
public class AdministrarEmpleadoServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();

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
                Persona p = new GestorPersonas().obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
                request.setAttribute("AlertaStock", AlertaStock);

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);

                if (administrador) {
                    //---------------------------CUERPO SI ES ADMINISTRADOR------------------
                    String nombreApellido = "";
                    int id = 0;
                    if (request.getParameter("id") != null) {
                        //MOD
                        id = Integer.parseInt(request.getParameter("id"));
                        Persona perkin = gp.obtenerPersona(id);
                        nombreApellido = perkin.getNombre() + " " + perkin.getApellido();
                    }
                    request.setAttribute("resultado", "");
                    request.setAttribute("id", id);
                    ArrayList<TipoUsuario> lstTu = gp.ListarTipoUsuarios();
                    ArrayList<Usuario> lstOperadores = gp.ListarOperadores();
                    ArrayList<Persona> lstPersonas = gp.ListarPersonas();
                    request.setAttribute("nombreApellido", nombreApellido);
                    request.setAttribute("lstTu", lstTu);
                    request.setAttribute("lstOperadores", lstOperadores);
                    request.setAttribute("lstPersonas", lstPersonas);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarEmpleados.jsp");
                    rd.forward(request, response);
                } else {//---------------------------FIN QUE SEA ADM------------------
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
        try {

            int idPersona = Integer.parseInt(request.getParameter("identificalo"));
            if (idPersona != 0) {
                Persona pepe = new Persona(idPersona);

                int idTipo = Integer.parseInt(request.getParameter("idTipoCliente"));
                String nombre = (String) request.getParameter("nombre");

                System.out.println(nombre);

                Usuario u = new Usuario(idTipo, pepe);
                boolean exito = gp.AM_empleados(u);

                String resultado = "";
                if (exito) {
                    resultado = "Se ha actualizado de manera exitosa el cargo de " + nombre;
                } else {
                    resultado = "Error! No se han efectuado los cambios sobre " + nombre;
                }
                System.out.println(resultado);
                request.getSession().setAttribute("resultado", resultado);
                request.getSession().setAttribute("imgResultado", "./img/CambioCorrecto.png");
                request.getSession().setAttribute("url", "./AdministrarEmpleadoServlet");

                response.sendRedirect("./CambiosServlet");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
