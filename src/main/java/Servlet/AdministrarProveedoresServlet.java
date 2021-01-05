package Servlet;

import Controller.GestorPersonas;
import Controller.GestorProveedores;
import Controller.GestorRepuesto;
import Controller.GestorUbicacion;
import Model.Barrio;
import Model.Localidad;
import Model.Persona;
import Model.Proveedor;
import Model.Provincia;
import Model.TipoCliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdministrarProveedoresServlet", urlPatterns = {"/AdministrarProveedoresServlet"})
public class AdministrarProveedoresServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorUbicacion gu = new GestorUbicacion();
    GestorProveedores gprov = new GestorProveedores();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

                int id_prov = 0;
                Proveedor prov = null;
                ArrayList<Provincia> ptc = gu.ListarProvincias();
                ArrayList<Localidad> lstLocalidades = gu.ListaLocalidades();
                ArrayList<Barrio> lstBarrios = gu.ListaBarrios();
                ArrayList<Proveedor> lstProveedores;
                String opcion = "";

                if (request.getParameter("numListado") != null) {
                    int eleccion = Integer.parseInt(request.getParameter("numListado"));
                    lstProveedores = gprov.ListarProveedores(eleccion);
                    if (eleccion == 0) {
                        opcion = "todos";
                    } else if (eleccion == 1) {
                        opcion = "rubros";
                    } else {
                        opcion = "marcas";
                    }
                } else {
                    lstProveedores = gprov.ListarProveedores(0);
                    opcion = "todos";
                }

                if (request.getParameter("idProveedor") != null) {
                    //para modificación
                    id_prov = Integer.parseInt(request.getParameter("idProveedor"));
                    prov = gprov.ObtenerProveedorPorId(id_prov);
                } else {
                    Persona pers = new Persona("", "", "", "", new Barrio(0), new Localidad(0), new Provincia(0));
                    prov = new Proveedor("", pers, "");
                }

                request.setAttribute("opcion", opcion);
                request.setAttribute("prov", prov);
                request.setAttribute("id_prov", id_prov);
                request.setAttribute("lstProv", lstProveedores);
                request.setAttribute("ptc", ptc);
                request.setAttribute("lstLocalidades", lstLocalidades);
                request.setAttribute("lstBarrios", lstBarrios);
                //JS
                ArrayList<Localidad> lpjs = gu.LocalidadesParaJS();
                ArrayList<Barrio> bpjs = gu.BarriosParaJS();
                request.setAttribute("lpjs", lpjs);
                request.setAttribute("bpjs", bpjs);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarProveedores.jsp");
                rd.forward(request, response);
            } else {//---------------------------FIN QUE SEA ADM------------------
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("eliminarProveedor") != null) {
            int id_p = Integer.parseInt(request.getParameter("eliminarProveedor"));            
            String x = "false";
            if(gprov.EliminarProveedor(id_p)){
                x = "true";
            }
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(x);

        } else {

            //variables tomadas del jsp
            String nombre = request.getParameter("proveedor");
            int id_prov = Integer.parseInt(request.getParameter("id_prov"));
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String pagina = request.getParameter("pagina");
            String direccion = request.getParameter("direccion");
            String cp = request.getParameter("cp");
            int idBarrio = Integer.parseInt(request.getParameter("idBarrio"));
            int idLocalidad = Integer.parseInt(request.getParameter("idBarrio"));
            int idProvincia = Integer.parseInt(request.getParameter("idBarrio"));

            //crear los objetos
            Barrio barrio = new Barrio(idBarrio);
            Localidad localidad = new Localidad(idLocalidad);
            Provincia provincia = new Provincia(idProvincia);
            Persona p = new Persona(telefono, email, direccion, cp, barrio, localidad, provincia);
            Proveedor proveedor = new Proveedor(nombre, p, pagina);

            //ingresar a la bd
            boolean exito = false;
            if (id_prov == 0) {
                exito = gprov.AltaProveedor(proveedor);
            } else {
                proveedor.setId(id_prov);
                exito = gprov.ModificarProveedor(proveedor);
            }

            //redireccionar
            if (exito) {
                response.sendRedirect("./AdministrarProveedoresServlet");
            } else {
                response.sendRedirect("./ErrorServlet");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
