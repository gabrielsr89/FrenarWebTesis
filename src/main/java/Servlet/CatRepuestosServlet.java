package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Cliente;
import Model.Factura;
import Model.MarcaRepuesto;
import Model.Persona;
import Model.Repuesto;
import Model.Rubro;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Repuestos", urlPatterns = {"/CatRepuestosServlet"})
public class CatRepuestosServlet extends HttpServlet {

    GestorRepuesto gr = new GestorRepuesto();
    GestorPersonas gp = new GestorPersonas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //---------------------------------PARA LOGIN-------------------------------
            if (request.getSession().getAttribute("administrador") != null
                    && request.getSession().getAttribute("operador") != null
                    && request.getSession().getAttribute("cliente") != null
                    && request.getSession().getAttribute("idUsuario") != null) {

                boolean administrador = (boolean) request.getSession().getAttribute("administrador");
                boolean operador = (boolean) request.getSession().getAttribute("operador");
                boolean cliente = (boolean) request.getSession().getAttribute("cliente");
                Persona p = new GestorPersonas().obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

                request.setAttribute("banderaIniciar", false);
                request.setAttribute("administrador", administrador);
                request.setAttribute("operador", operador);
                request.setAttribute("cliente", cliente);
                request.setAttribute("p", p);

                //----------------------------PARA CARGAR CARRITO------------------------------------
                Factura carrito = null;
                
                if(request.getParameter("id_f")!= null){
                    carrito = new GestorFacturas().ObtenerFacturaPorId(Integer.parseInt(request.getParameter("id_f")));
                    request.getSession().setAttribute("carrito", carrito);
                    request.getSession().setAttribute("modificarReserva", true);                    
                }

                if (request.getSession().getAttribute("carrito") != null) {                   
                    
                    carrito = (Factura) request.getSession().getAttribute("carrito");
                    if (carrito.lstRepuesto == null) {
                        carrito.lstRepuesto = new ArrayList<>();
                        request.setAttribute("carrito", carrito);

                    } else {
                        //para sacar un elemento del carrito
                        if (request.getParameter("sacar") != null) {
                            int idRepuesto = Integer.parseInt(request.getParameter("sacar"));
                            carrito.removerRepuestoDelCarrito(idRepuesto);
                        }
                        request.setAttribute("carrito", carrito);
                    }
                } 
                
                request.setAttribute("carrito", carrito);
                //----------------------------PARA LISTADO DE REPUESTOS----------------------------------
                
                ArrayList<Rubro> lstRubro = gr.ListarRubros();
                ArrayList<MarcaRepuesto> lstMarcaRepuesto = gr.ListarMarcasRepuesto();
                ArrayList<Repuesto> lstr;

                if (request.getParameter("rubro") != null
                        && request.getParameter("marca") != null
                        && request.getParameter("precio") != null) {

                    int id_rubro = Integer.parseInt(request.getParameter("rubro"));
                    int id_marca = Integer.parseInt(request.getParameter("marca"));
                    boolean precio = request.getParameter("precio").equals("menor");

                    lstr = gr.ListarRepuestosParametrizados(id_marca,id_rubro, precio);
                    
                } else {
                    lstr = gr.ListarRepuestos(0);
                }

                request.setAttribute("lstRubro", lstRubro);
                request.setAttribute("lstMarcaRepuesto", lstMarcaRepuesto);
                request.setAttribute("lstr", lstr);
                //----------------------------PARA CLIENTE----------------------------------
                int idCt = 0;
                if (operador || administrador) {
                    if (request.getParameter("idCt") != null) {
                        idCt = Integer.parseInt(request.getParameter("idCt"));
                        Persona perkin = gp.obtenerNombreApellidoPorIdCliente(idCt);
                        request.setAttribute("perkin", perkin);
                    }
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    request.setAttribute("lstCliente", lstCliente);
                }
                if (cliente) {
                    idCt = gp.obtenerIdClientePorIdPersona(p.getId());
                }
                request.setAttribute("idCt", idCt);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/repuestosCatalogo.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect("./SinPermiso.jsp");
            }
        } catch (Exception ex) {
            System.out.println("Error en get de CatRepuestosServlet: " + ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("carrito") != null) {
            response.sendRedirect("./ConfirmarCompraServlet");
        } else {
            System.out.println("Nones");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
