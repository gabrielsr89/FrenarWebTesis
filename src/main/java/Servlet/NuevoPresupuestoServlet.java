package Servlet;

import Controller.GestorAutos;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorServicios;
import Model.Auto;
import Model.Categorias;
import Model.Cliente;
import Model.DetallePedidoServicio;
import Model.Factura;
import Model.MarcaAuto;
import Model.ModeloAuto;
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

@WebServlet(name = "NuevoPresupuestoServlet", urlPatterns = {"/NuevoPresupuestoServlet"})
public class NuevoPresupuestoServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorServicios gs = new GestorServicios();
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

                if (operador || administrador) {

                    if (request.getParameter("idCliente") != null) {
                        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                        request.setAttribute("idCliente", idCliente);
                    }

                    ArrayList<Categorias> lstCategorias = gs.TraerCategorias();
                    ArrayList<Servicio> lstServicios = gs.TraerServicios();
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    ArrayList<Auto> lstAutos = ga.ListarAutos();
                    ArrayList<MarcaAuto> lstMarcas = ga.ListarMarcas();
                    ArrayList<ModeloAuto> lstModelos = ga.ListarModelos();

                    request.setAttribute("lstMarcas", lstMarcas);
                    request.setAttribute("lstModelos", lstModelos);
                    request.setAttribute("lstCategorias", lstCategorias);
                    request.setAttribute("lstCliente", lstCliente);
                    request.setAttribute("lstServicios", lstServicios);
                    request.setAttribute("lstAutos", lstAutos);

                    //-------------------------CARGAR LOS SERVICIOS-----------
                    if (request.getParameter("id_servicio") != null) {
                        int id_servicio = Integer.parseInt(request.getParameter("id_servicio"));
                        Factura f = null;

                        if (request.getSession().getAttribute("servicios") != null) {
                            f = (Factura) request.getSession().getAttribute("servicios");
                        }
                        f = new Factura();
                        //f.lstServicio.add(e);
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/nuevoPresupuesto.jsp");
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
        try {
            int id_usuario = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));
            //######### -- PARA REGISTRAR AL CLIENTE Y SU AUTO -- ######
            String casoCliente = request.getParameter("casoCliente");
            //variables para insertar cuando es ct final                 
            int id_modeloFinal = 0;
            int id_auto = 0;
            int id_cliente = 0;
            if (casoCliente.equals("caso1")) {
                //caso1: ct tiene marca modelo registrado                
                id_modeloFinal = Integer.parseInt(request.getParameter("idModelo"));
            }
            if (casoCliente.equals("caso2")) {
                //caso2: ct tiene marca registrada pero no el modelo
                int idMarca = Integer.parseInt(request.getParameter("idMarca"));
                String nombreModelo = request.getParameter("nombreModelo");
                id_modeloFinal = ga.NuevoModeloAutoCliente(new ModeloAuto(nombreModelo, idMarca));
            }
            if (casoCliente.equals("caso3")) {
                //caso3: no tiene ni marca ni modelo registrado                
                String nombreMarca = request.getParameter("nombreMarca");
                String nombreModelo = request.getParameter("nombreModelo");
                int id_marcaFinal = ga.NuevaMarcaAutoCliente(request.getParameter("marcaNueva"));
                id_modeloFinal = ga.NuevoModeloAutoCliente(new ModeloAuto(nombreModelo, id_marcaFinal));

            }
            if (casoCliente.equals("caso1") || casoCliente.equals("caso2") || casoCliente.equals("caso3")) {
                String patente = patente = request.getParameter("patente");
                id_cliente = 48;
                id_auto = ga.idAutoClienteFinal(new Auto(patente, id_modeloFinal));
            }
            if (casoCliente.equals("caso4")) {
                //caso4: es un ct registrado con repectiva marca y modelo*/                
                id_auto = Integer.parseInt(request.getParameter("aut"));
                id_cliente = Integer.parseInt(request.getParameter("ct"));
            }
            //################-- CARRITO SERVICIOS-- ####################
            ArrayList<DetallePedidoServicio> listaDetallesServicios = new ArrayList<DetallePedidoServicio>();

            String[] idServicios = request.getParameterValues("idServicios");

            boolean servicioNuevo = false;

            for (int i = 0; i < idServicios.length; i++) {
                if (idServicios[i].equals("0")) {
                    servicioNuevo = true;
                }
            }

            if (servicioNuevo) {
                String nombreServicioNuevo = request.getParameter("nombreServicioNuevo");
                String descripcionServicioNuevo = request.getParameter("descripcionServicioNuevo");
                int tiempoServicioNuevo = Integer.parseInt(request.getParameter("tiempoServicioNuevo"));
                Servicio s = new Servicio(nombreServicioNuevo, tiempoServicioNuevo, 5, descripcionServicioNuevo);
                int ServicioNuevo = gs.IdNuevoServicio(s);
                request.getSession().setAttribute("ServicioNuevo", ServicioNuevo);
                DetallePedidoServicio dps = new DetallePedidoServicio(ServicioNuevo);
                listaDetallesServicios.add(dps);
            }

            for (String x : idServicios) {
                DetallePedidoServicio dps = new DetallePedidoServicio(Integer.parseInt(x));
                listaDetallesServicios.add(dps);
            }

            ArrayList<DetallePedidoServicio> lstCarritoServicio = gs.obtenerServiciosSegúnId(listaDetallesServicios);

            Factura carrito = new Factura();
            if (request.getSession().getAttribute("carrito") != null) {
                carrito = (Factura) request.getSession().getAttribute("carrito");
            }
            carrito.lstServicio = lstCarritoServicio;
            carrito.setId_auto(id_auto);
            carrito.setId_usuario(id_usuario);
            carrito.setId_cliente(id_cliente);
            request.getSession().setAttribute("carrito", carrito);
            response.sendRedirect("./ConfirmarCompraServlet");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
