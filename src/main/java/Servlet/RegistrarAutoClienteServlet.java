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

@WebServlet(name = "RegistrarAutoClienteServlet", urlPatterns = {"/RegistrarAutoClienteServlet"})
public class RegistrarAutoClienteServlet extends HttpServlet {

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

                if (operador || administrador || cliente) {
                    ArrayList<MarcaAuto> lstMarcas = ga.ListarMarcas();
                    ArrayList<ModeloAuto> lstModelos = ga.ListarModelos();
                    ArrayList<Auto> lstAutos = new ArrayList<Auto>();
                    Auto a = null;

                    if (request.getParameter("idAuto") != null) {
                        int id_auto = Integer.parseInt(request.getParameter("idAuto"));
                        request.setAttribute("id_auto", id_auto);
                        a = ga.ObtenerAuto(id_auto);
                    }

                    if (cliente) {
                        lstAutos = ga.ListarAutosPorCliente((Integer)request.getSession().getAttribute("idClienteX"));
                    }

                    if (administrador || operador) {
                        if (request.getParameter("idCliente") != null) {

                            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                            String nombreApellido = ga.obtenerNombreDePersonaPorIdCliente(idCliente);

                            lstAutos = ga.ListarAutosPorCliente(idCliente);

                            request.setAttribute("idCliente", idCliente);
                            request.setAttribute("nombreApellido", nombreApellido);
                        } else {

                            ArrayList<Auto> lstAutosJs = ga.ListarAutos();
                            ArrayList<Cliente> lstCliente = gp.ListarClientes();

                            request.setAttribute("lstCliente", lstCliente);
                            request.setAttribute("lstAutosJs", lstAutosJs);
                        }
                    }
                    for (Auto aut : lstAutos) {
                        System.out.println("id_cliente: " + aut.getId_cliente());
                    }

                    request.setAttribute("auto", a);
                    request.setAttribute("lstMarcas", lstMarcas);
                    request.setAttribute("lstModelos", lstModelos);
                    request.setAttribute("lstAutos", lstAutos);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/RegistrarAutoCliente.jsp");
                    rd.forward(request, response);
                }

                if (!administrador && !cliente && !operador) {
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
            if (request.getParameter("eliminarAuto") != null) {
                int id = Integer.parseInt(request.getParameter("eliminarAuto"));

                boolean x = ga.EliminarAuto(id);

                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(x);

            } else {
                int id_marca = Integer.parseInt(request.getParameter("id_marca"));
                int id_modelo = Integer.parseInt(request.getParameter("id_modelo"));
                String patente = request.getParameter("patente");
                int id_cliente = 0;
                String accion = request.getParameter("accion");

                if (request.getParameter("idCliente") != null) {
                    id_cliente = Integer.parseInt(request.getParameter("idCliente"));
                } else {
                    id_cliente = new GestorLogin().obtenerIdCliente((Integer) request.getSession().getAttribute("idUsuario"));
                }

                int id_modeloFinal = 0;
                int id_marcaFinal = 0;

                if (id_modelo == 1010) {
                    String modelo = request.getParameter("modeloNuevo");
                    if (id_marca == 159) {
                        id_marcaFinal = ga.NuevaMarcaAutoCliente(request.getParameter("marcaNueva"));

                        ModeloAuto modelovski = new ModeloAuto(modelo, id_marcaFinal);
                        id_modeloFinal = ga.NuevoModeloAutoCliente(modelovski);
                    } else {
                        ModeloAuto modelovski = new ModeloAuto(modelo, id_marca);
                        id_modeloFinal = ga.NuevoModeloAutoCliente(modelovski);
                        id_marcaFinal = id_marca;
                    }
                } else {
                    id_modeloFinal = id_modelo;
                }
                Auto tutu = new Auto(patente, id_cliente, id_modeloFinal);

                boolean exito = false;
                if (accion.equals("modificar")) {
                    System.out.println(" entra acá? 1");
                    int id = Integer.parseInt(request.getParameter("id_auto"));
                    System.out.println(" entra acá? 2");
                    tutu.setId_auto(id);
                }

                exito = ga.RegistrarAutoCliente(tutu, accion);

                if (exito) {
                    response.sendRedirect("./RegistrarAutoClienteServlet");
                } else {
                    response.sendRedirect("./Error.jsp");
                }
            }
        } catch (Exception ex) {
            System.out.println("Problemas en doPost de RegistrarAutoClienteServlet " + ex.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
