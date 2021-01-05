package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Cliente;
import Model.Persona;
import Model.TipoCliente;
import Model.TipoUsuario;
import Model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarClientesServlet", urlPatterns = {"/AdministrarClientesServlet"})
public class AdministrarClientesServlet extends HttpServlet {

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
                Persona p = gp.obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));
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
                    ArrayList<TipoCliente> lstTipoCliente = gp.ListarTipoClientes();
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    ArrayList<Persona> lstPersonas = gp.ListarPersonas();
                    request.setAttribute("nombreApellido", nombreApellido);
                    request.setAttribute("lstTipoCliente", lstTipoCliente);
                    request.setAttribute("lstCliente", lstCliente);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarClientes.jsp");
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
            if (request.getParameter("eliminar") != null) {
                int id = Integer.parseInt(request.getParameter("id_cliente"));
                boolean exito = gp.EliminarCliente(id);

                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(exito);

            } else {

                int idPersona = Integer.parseInt(request.getParameter("identificalo"));
                if (idPersona != 0) {
                    int idTipoCliente = Integer.parseInt(request.getParameter("idTipoCliente"));
                    String nombre = (String) request.getParameter("nombre");

                    Persona pepe = gp.obtenerPersona(idPersona);
                    TipoCliente idTipo = new TipoCliente(idTipoCliente);

                    Cliente c = new Cliente(pepe, idTipo);

                    boolean exito = gp.AM_clientes(c);

                    String resultado = "";
                    if (exito) {
                        resultado = "Se ha actualizado de manera exitosa el cargo de " + nombre;
                    } else {
                        resultado = "Error! No se han efectuado los cambios sobre " + nombre;
                    }
                    request.getSession().setAttribute("resultado", resultado);
                    request.getSession().setAttribute("imgResultado", "./img/CambioCorrecto.png");
                    request.getSession().setAttribute("url", "./AdministrarClientesServlet");

                    response.sendRedirect("./CambiosServlet");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
