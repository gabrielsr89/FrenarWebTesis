package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Model.Factura;
import Model.Persona;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PagarMercadoPagoServlet", urlPatterns = {"/PagarMercadoPagoServlet"})
public class PagarMercadoPagoServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();

    GestorFacturas gf = new GestorFacturas();

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
                Persona p = gp.obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                if (administrador || operador) {
                    if (request.getSession().getAttribute("carritoMP") != null) {
                        Factura carritoMP = (Factura) request.getSession().getAttribute("carritoMP");

                        int id_c = carritoMP.getId_cliente();
                        Persona perkins = gp.obtenerNombreApellidoPorIdCliente(id_c);
                        String nombreApellido = perkins.getNombre() + " " + perkins.getApellido();
                        double monto = carritoMP.calcularTotal();
                        int id_f = carritoMP.getId_factura();

                        request.setAttribute("nombreApellido", nombreApellido);
                        request.setAttribute("id_f", id_f);
                        request.setAttribute("monto ", monto);

                        //MERCADO PAGO
                        MercadoPago.SDK.setAccessToken("TEST-1730732033826243-062801-b8cb04afe84992e43aef4bd361d75f6b-158535050");
                        Preference preference = new Preference();
                        Item item = new Item();
                        item.setTitle("Repuestos seleccionados")
                                .setQuantity(1)
                                .setUnitPrice((float) carritoMP.calcularTotal());
                        preference.appendItem(item);
                        Payer payer = new Payer();
                        preference.setPayer(payer);
                        BackUrls test = new BackUrls();
                        test.setSuccess("./Exito.jsp");
                        test.setPending("");
                        test.setFailure("./ErrorServlet?msjError=Mpago");
                        preference.setBackUrls(test);
                        preference.setAutoReturn(Preference.AutoReturn.all);
                        preference.setExpires(Boolean.FALSE);
                        preference.save();
                        request.setAttribute("preference", preference);

                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/PagarMercadoPagoServlet.jsp");
                        rd.forward(request, response);

                    }
                }
            }
        } catch (MPConfException ex) {
            Logger.getLogger(ConfirmarCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MPException ex) {
            Logger.getLogger(ConfirmarCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //GENERAR EL PAGO (CAMBIAR DE ESTADO LA FT)
        int id_f = Integer.parseInt(request.getParameter("id_factura"));
        boolean exito = gf.CompradoOnlineFactura(id_f);

        if (exito) {
            request.getSession().setAttribute("carritoMP", null);
            //REDIRECCIONAR AL LISTADO DE ENTREGAS
            response.sendRedirect("");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
