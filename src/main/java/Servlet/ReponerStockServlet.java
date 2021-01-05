package Servlet;

import Controller.GestorPersonas;
import Controller.GestorProveedores;
import Controller.GestorRepuesto;
import Model.Detalle_reposicion;
import Model.Persona;
import Model.Proveedor;
import Model.Reposicion;
import Model.Repuesto;
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

@WebServlet(name = "ReponerStockServlet", urlPatterns = {"/ReponerStockServlet"})
public class ReponerStockServlet extends HttpServlet {

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

                    ArrayList<Repuesto> lstRepuestos = gr.ListarRepuestos(0);
                    request.setAttribute("lstRepuestos", lstRepuestos);

                    if (request.getParameter("id_repo") != null) {
                        Reposicion r = null;
                        int id_reposicion = Integer.parseInt(request.getParameter("id_repo"));
                        r = gprov.ObtenerReposicion(id_reposicion);
                        request.setAttribute("r", r);
                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModificarReposicion.jsp");
                        rd.forward(request, response);
                    } else {

                        ArrayList<Proveedor> lstProveedor = gprov.ListarProveedores(0);
                        ArrayList<Rubro> lstRubros = gr.ListarRubros();
                        ArrayList<Reposicion> lstReposiciones = gprov.TraerReposiciones();

                        int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
                        request.setAttribute("AlertaStock", AlertaStock);
                        request.setAttribute("lstReposiciones", lstReposiciones);
                        request.setAttribute("lstRubros", lstRubros);
                        request.setAttribute("lstProveedor", lstProveedor);

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/Reposicion.jsp");
                        rd.forward(request, response);
                    }

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

        try {
            if (request.getParameter("eliminar") != null) {
                int id = Integer.parseInt(request.getParameter("eliminar"));
                boolean exito = gprov.EliminarReposicion(id);

                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(exito);

            } else if (request.getParameter("idDetalle") != null && request.getParameter("id_rep") != null && request.getParameter("cant") != null && request.getParameter("accion") != null) {

                int id_d = Integer.parseInt(request.getParameter("idDetalle"));
                int id_repuesto = Integer.parseInt(request.getParameter("id_rep"));
                String accion = request.getParameter("accion");
                int cant = Integer.parseInt(request.getParameter("cant"));
                int id_reposicion = Integer.parseInt(request.getParameter("id_reposicion"));

                boolean exito = false;

                if (accion.equals("modificar")) {
                    exito = gprov.ActualizarDetalle(id_d, id_repuesto, cant);
                } else if (accion.equals("eliminar")) {
                    exito = gprov.EliminarDetalleReposicion(id_d, id_repuesto, cant, id_reposicion);
                } else {
                    exito = gprov.AltaDetalleReposicion(id_repuesto, cant, id_reposicion);
                }

                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(exito);

            } else {
                //lo que nos trae el jsp
                String[] cantidades = request.getParameterValues("cantidad");
                String[] idRepuestos = request.getParameterValues("id_repuesto");
                String[] precios = request.getParameterValues("precio_unitario");
                int id_proveedor = Integer.parseInt(request.getParameter("idProveedor"));
                double importeParcial = Double.parseDouble(request.getParameter("importeParcial"));

                //cargar los objetos
                ArrayList<Detalle_reposicion> detalles = new ArrayList<>();
                int id_usuario = (Integer) request.getSession().getAttribute("idAdministradorX");
                for (int i = 0; i < idRepuestos.length; i++) {
                    Detalle_reposicion dr = new Detalle_reposicion(Integer.parseInt(idRepuestos[i]), Integer.parseInt(precios[i]), Integer.parseInt(cantidades[i]));
                    detalles.add(dr);
                }
                Reposicion reposicion = new Reposicion(id_proveedor, id_usuario, importeParcial, detalles);

                //Los manda a la base de datos.
                boolean exito = gprov.AltaDeReposicion(reposicion);
                if (exito) {
                    response.sendRedirect("./ReponerStockServlet");
                } else {
                    response.sendRedirect("./ErrorServlet");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error en el post de ReponerStockServlet: " + ex.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
