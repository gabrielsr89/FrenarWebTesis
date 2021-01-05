package Servlet;

import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Cliente;
import Model.Factura;
import Model.Persona;
import Model.TipoPago;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ConfirmarPresupuestoServlet", urlPatterns = {"/ConfirmarPresupuestoServlet"})
public class ConfirmarPresupuestoServlet extends HttpServlet {

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
                if (administrador || operador || cliente) {
                    ArrayList<Factura> lista = new ArrayList();
                    String usuario = "";
                    if (cliente) {
                        int id_c = (Integer) request.getSession().getAttribute("idClienteX");
                        lista = gf.ObtenerPresupuestosPorIdCt(id_c);
                        usuario = "cliente";
                        request.setAttribute("id_c", id_c);
                    }
                    if (administrador || operador) {
                        lista = gf.ObtenerPresupuestos();
                        usuario = "empleado";
                    }
                    request.setAttribute("lista", lista);
                    request.setAttribute("usuario", usuario);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ConfirmarPresupuesto.jsp");
                    rd.forward(request, response);

                } else {
                    response.sendRedirect("./ErrorServlet?msjError=SinAutorizacion");
                }

            } else {
                response.sendRedirect("./SesionCaducada.jsp");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString() + " error en get ConfirmarPresupuestoServlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         if (request.getParameter("id_f") != null) {
             
            int id_factura = Integer.parseInt(request.getParameter("id_f"));
            boolean x = gf.PasarAlTaller(id_factura);
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print(x);

        } else {

            boolean cliente = (boolean) request.getSession().getAttribute("cliente");//Es para redireccionar acorde si es cliente o no

            String accion = request.getParameter("accion");
            int id_factura = Integer.parseInt(request.getParameter("id_factura"));
            boolean exito = false;

            if (accion.equals("cancelar")) {
                if (cliente) {
                    exito = gf.CancelarPresupuestoCliente(id_factura);
                }
                exito = gf.CancelarPresupuesto(id_factura);
            }

            if (exito) {
                if (cliente) {
                    response.sendRedirect("./headerServlet");
                } else {
                    response.sendRedirect("./ConfirmarPresupuestoServlet");
                }
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
