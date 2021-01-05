package Servlet;

import Controller.GestorFacturas;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Cliente;
import Model.Factura;
import Model.Persona;
import Model.TipoPago;
import com.mercadopago.MercadoPago;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NuevoPagoServlet", urlPatterns = {"/NuevoPagoServlet"})
public class NuevoPagoServlet extends HttpServlet {

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
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();

                request.setAttribute("AlertaStock", AlertaStock);
                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);
                if (administrador || operador) {
                    int idFormaPago = 0;
                    int idCt = 0;
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    ArrayList<Factura> lstFacturas = gf.listarFacturasListasParaPagar();
                    ArrayList<TipoPago> lstTipoPago = gf.ListarTipoPagos();

                    if (request.getParameter("idCt") != null) {
                        idCt = Integer.parseInt(request.getParameter("idCt"));
                    }
                    if (request.getParameter("idFormaPago") != null) {
                        idFormaPago = Integer.parseInt(request.getParameter("idFormaPago"));
                    }

                    request.setAttribute("lstTipoPago", lstTipoPago);
                    request.setAttribute("idFormaPago", idFormaPago);
                    request.setAttribute("idCt", idCt);
                    request.setAttribute("lstCliente", lstCliente);
                    request.setAttribute("lstFacturas", lstFacturas);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/nuevoPago.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./ErrorServlet?msjError=SinAutorizacion");
                }

            } else {
                response.sendRedirect("./SesionCaducada.jsp");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " error en get NuevoPagoServlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getSession().getAttribute("administrador") != null
                    && request.getSession().getAttribute("operador") != null
                    && request.getSession().getAttribute("cliente") != null
                    && request.getSession().getAttribute("idUsuario") != null) {

                int id_usuario = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));
                int id_factura = Integer.parseInt(request.getParameter("idFactura"));

                if (request.getParameter("idFormaPago") != null) {
                    int idFormaPago = Integer.parseInt(request.getParameter("idFormaPago"));
                    if (idFormaPago == 1) {
                        boolean exito = false;
                        //siguiente método pone ft en 7
                        exito = gf.PagarFacturaEnLocal(id_factura, id_usuario);
                        if (exito) {
                            //FACTURADO Y PAGADO LISTO PARA ENTREGAR
                            response.sendRedirect("./FacturadoYPagadoListados");
                        }else{
                            response.sendRedirect("./ErrorServlet");                            
                        }
                    }
                }

            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " problemas en el POST de NuevoPagoServlet");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
