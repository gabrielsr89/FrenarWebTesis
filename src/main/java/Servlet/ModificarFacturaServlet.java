package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorServicios;
import Model.Categorias;
import Model.Factura;
import Model.Persona;
import Model.Repuesto;
import Model.Servicio;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ModificarFacturaServlet", urlPatterns = {"/ModificarFacturaServlet"})
public class ModificarFacturaServlet extends HttpServlet {

    GestorFacturas gf = new GestorFacturas();
    GestorRepuesto gr = new GestorRepuesto();
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
                if (administrador || operador) {
                    //---------------------------CUERPO SI ES ADMINISTRADOR------------------
                    Factura carrito = null;
                    String tipoFactura = "";
                    if (request.getParameter("idPresupuesto") != null) {
                        int id_presupuesto = Integer.parseInt(request.getParameter("idPresupuesto"));
                        carrito = gf.ObtenerFacturaPorId(id_presupuesto);
                        tipoFactura = "presupuesto";
                    }
                    ArrayList<Repuesto> lstRepuestos = gr.ListarRepuestos(0);
                    ArrayList<Servicio> lstServicios = gs.TraerServicios();

                    request.setAttribute("lstServicios", lstServicios);
                    request.setAttribute("lstRepuestos", lstRepuestos);
                    request.setAttribute("tipoFactura", tipoFactura);
                    request.setAttribute("carrito", carrito);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ModificarFactura.jsp");
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
        boolean exito = false;

        if (request.getParameter("id_detalle") != null
                || request.getParameter("accion") != null
                || request.getParameter("id_repuesto") != null
                || request.getParameter("cantidad") != null
                || request.getParameter("id_factura") != null
                || request.getParameter("tipoFactura") != null) {

            int id_detalle = Integer.parseInt(request.getParameter("id_detalle"));
            int id_repuesto = Integer.parseInt(request.getParameter("id_repuesto"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int id_factura = Integer.parseInt(request.getParameter("id_factura"));
            String accion = request.getParameter("accion");
            String tipoFactura = request.getParameter("tipoFactura");

            if (tipoFactura.equals("presupuesto")) {
                if (accion.equals("modificar")) {
                    exito = gf.ModificarPresupuestoDetalleRepuesto(id_detalle, cantidad, id_repuesto);
                }
                if (accion.equals("agregar")) {
                    exito = gf.AgregarPresupuestoDetalleRepuesto(cantidad, id_repuesto, id_factura);
                }
                if (accion.equals("eliminar")) {
                    exito = gf.EliminarPresupuestoDetalleRepuesto(id_detalle, id_factura);
                }
            }
        } else if (request.getParameter("id_detalle_s") != null
                || request.getParameter("accion_s") != null
                || request.getParameter("id_servicio") != null
                || request.getParameter("id_factura_s") != null
                || request.getParameter("tipoFactura_s") != null) {

            int id_detalle_s = Integer.parseInt(request.getParameter("id_detalle_s"));
            int id_servicio = Integer.parseInt(request.getParameter("id_servicio"));
            int id_factura_s = Integer.parseInt(request.getParameter("id_factura_s"));
            String accion_s = request.getParameter("accion_s");
            String tipoFactura_s = request.getParameter("tipoFactura_s");
            
            if (tipoFactura_s.equals("presupuesto")) {
                if (accion_s.equals("modificar")) {
                    exito = gf.ModificarPresupuestoDetalleServicio(id_detalle_s, id_servicio);
                }
                if (accion_s.equals("agregar")) {
                    exito = gf.AgregarPresupuestoDetalleServicio(id_servicio, id_factura_s);
                }
                if (accion_s.equals("eliminar")) {
                    exito = gf.EliminarPresupuestoDetalleServicio(id_detalle_s, id_factura_s);
                }
            }

        }
        response.setContentType("text/plain ");
        PrintWriter out = response.getWriter();
        out.print(exito);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
