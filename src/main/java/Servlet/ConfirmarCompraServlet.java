package Servlet;

import Controller.GestorAutos;
import Controller.GestorFacturas;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Model.DetallePedidoServicio;
import Model.Factura;
import Model.Persona;
import Model.TipoPago;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//MERCADOR PAGO
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Identification;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.Phone;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
//FIN

@WebServlet(name = "ConfirmarCompraServlet", urlPatterns = {"/ConfirmarCompraServlet"})
public class ConfirmarCompraServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorFacturas gf = new GestorFacturas();

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

            request.setAttribute("banderaIniciar", false);
            request.setAttribute("administrador", administrador);
            request.setAttribute("operador", operador);
            request.setAttribute("cliente", cliente);
            request.setAttribute("p", p);
            if (cliente || administrador || operador) {

                try {
                    if (request.getSession().getAttribute("carrito") != null
                            || request.getSession().getAttribute("carritoServicios") != null
                            || request.getParameter("idPresupuesto") != null) {

                        try {
                            Factura carrito = null;

                            if (request.getSession().getAttribute("carritoServicios") != null && request.getSession().getAttribute("carrito") != null) {

                                carrito = (Factura) request.getSession().getAttribute("carritoServicio");
                                Factura carritoRepuestos = (Factura) request.getSession().getAttribute("carrito");
                                carrito.lstRepuesto = carritoRepuestos.lstRepuesto;
                            }
                            if (request.getSession().getAttribute("carrito") != null && request.getSession().getAttribute("carritoServicios") == null) {

                                carrito = (Factura) request.getSession().getAttribute("carrito");

                            }
                            if (request.getSession().getAttribute("carritoServicios") != null && request.getSession().getAttribute("carrito") == null) {
                                carrito = (Factura) request.getSession().getAttribute("carritoServicios");
                            }

                            request.setAttribute("carrito", carrito);
                            String auto = "";
                            String nombre = "";

                            if (carrito.getId_cliente() != 0) {
                                if (carrito.getId_auto() != 0) {
                                    auto = new GestorAutos().obtenerMarcaModeloPorIdAuto(carrito.getId_auto());
                                }

                                Persona perkin = gp.obtenerNombreApellidoPorIdCliente(carrito.getId_cliente());
                                nombre = perkin.getNombre() + " " + perkin.getApellido();

                            }
                            request.setAttribute("nombre", nombre);
                            request.setAttribute("auto", auto);

                            ArrayList<TipoPago> lstTipoPago = gf.ListarTipoPagos();

                            request.setAttribute("lstTipoPago", lstTipoPago);

                            MercadoPago.SDK.setAccessToken("TEST-1730732033826243-062801-b8cb04afe84992e43aef4bd361d75f6b-158535050");

                            // Crea un objeto de preferencia
                            Preference preference = new Preference();

                            //crea un array de tipo items                    
                            Item item = new Item();
                            item.setTitle("Repuestos seleccionados")
                                    .setQuantity(1)
                                    .setUnitPrice((float) carrito.calcularTotal());
                            preference.appendItem(item);
                            //preference.save();

                            //Crea un usuario en la preferencia
                            Payer payer = new Payer();
                            preference.setPayer(payer);

                            BackUrls test = new BackUrls();
                            test.setSuccess("http://localhost:8080/FrenarWeb/ConfirmationMP");
                            test.setPending("http://localhost:8080/FrenarWeb/ConfirmationMP");
                            test.setFailure("./ErrorServlet?msjError=Mpago");

                            preference.setBackUrls(test);
                            preference.setAutoReturn(Preference.AutoReturn.all);
                            preference.setExpires(Boolean.FALSE);
                            preference.save();

                            request.setAttribute("preference", preference);

                        } catch (MPConfException ex) {
                            Logger.getLogger(ConfirmarCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MPException ex) {
                            Logger.getLogger(ConfirmarCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConfirmarCompra.jsp");
                        rd.forward(request, response);

                    } else {
                        response.sendRedirect("./ErrorServlet?msjError='No_hay_items_registrados'");
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                response.sendRedirect("./ErrorServlet?msjError='No se encuentra logeado,vaya al menu principal iniciar sesión'");

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            if (cliente || administrador || operador) {
                int idFormaPago = Integer.parseInt(request.getParameter("idFormaPago"));
                Factura carrito = (Factura) request.getSession().getAttribute("carrito");

                if (cliente) {
                    boolean exito = false;
                    if (idFormaPago == 1) {
                        //si la siguiente variable de sesión está en true, es porque estamos modificando una reserva
                        
                        boolean accion = false;      
                        
                        if( request.getSession().getAttribute("modificarReserva") != null){
                            accion =  (boolean)request.getSession().getAttribute("modificarReserva");                        
                        }                          
                        
                        if (!accion) {
                            //ALTA DE RESERVA
                            int id_f = gf.altaReserva(carrito);
                            carrito.setId_factura(id_f);
                            exito = gf.altaDetalleRepuestos(carrito.lstRepuesto, id_f);
                        } else{
                            exito = gf.modificacionDetallesRepuestos(carrito);
                        }
                    }
                    if (exito) {
                        response.sendRedirect("./ExitoServlet?url=headerServlet");
                    } else {
                        response.sendRedirect("./ErrorServlet");
                    }
                }

                if (administrador || operador) {
                    boolean exito = false;
                    int ft = 0;
                    if (carrito.getId_usuario() < 1) {
                        int id_usuario = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));
                        carrito.setId_usuario(id_usuario);
                    }

                    if (idFormaPago == 3) {
                        //SI ENTRA ACÁ ES ALTA DE PRESUPUESTO 
                        if (carrito.lstServicio != null) {
                            if (carrito.lstServicio.size() > 0) {
                                ft = gf.Presupuestar(carrito);
                                carrito.setId_factura(ft);
                                for (DetallePedidoServicio d : carrito.lstServicio) {
                                    d.setId_factura(ft);
                                }

                                exito = gf.altaDetalleServicios(carrito.lstServicio);

                                System.out.println("Situación del éxito: " + exito);

                                if (carrito.lstRepuesto != null && carrito.lstRepuesto.size() > 0) {
                                    System.out.println("aca estamos 3");

                                    exito = gf.altaDetalleRepuestos(carrito.lstRepuesto, ft);
                                }
                            }
                        }

                        if (exito) {
                            //listar LOS PRESUPUESTOS DISPONIBLES PARA ENVIAR AL TALLER
                            response.sendRedirect("./ConfirmarPresupuestoServlet");

                        } else {
                            response.sendRedirect("./ErrorServlet?msjError=PostConfirmarCompraServlet216");

                        }
                    } else {
                        //VENTA DE REPUESTOS
                        if (idFormaPago == 1) {
                            //PAGO EFECTIVO
                            ft = gf.ventaRepuestosFacturados(carrito);
                            carrito.setId_factura(ft);
                            exito = gf.altaDetalleRepuestos(carrito.lstRepuesto, ft);
                            if (exito) {
                                //ENVIAR A PÁGINA DE PAGOS ENVIANDO CLIENTE Y TIPO PAGO (EFECTIVO)
                                response.sendRedirect("./NuevoPagoServlet?idCt=" + carrito.getId_cliente() + "&idFormaPago=1");
                            } else {
                                response.sendRedirect("./ErrorServlet?msjError=PostConfirmarCompraServlet217");
                            }
                        }

                    }
                }
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
