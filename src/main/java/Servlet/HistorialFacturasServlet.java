package Servlet;


import Controller.GestorAutos;
import Controller.GestorFacturas;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Auto;
import Model.Categorias;
import Model.Cliente;
import Model.Factura;
import Model.MarcaAuto;
import Model.ModeloAuto;
import Model.Persona;
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



@WebServlet(name = "HistorialFacturasServlet", urlPatterns = {"/HistorialFacturasServlet"})
public class HistorialFacturasServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorAutos ga = new GestorAutos();
    GestorFacturas gf = new GestorFacturas();

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
                    int id_auto = 0;
                    int id_cliente = 0;
                    ArrayList<Factura> listado = null;

                    if (request.getParameter("idAuto") != null && request.getParameter("idCliente") != null) {
                        //viene de verdetalles registrarautocliente
                        id_auto = Integer.parseInt(request.getParameter("idAuto"));
                        id_cliente = Integer.parseInt(request.getParameter("idCliente"));
                        if(cliente){
                            listado = gf.TraerFacturasParametrizadas(id_cliente, id_auto, 0);
                        }else{
                            listado = gf.TraerFacturasParametrizadas(0, id_auto, 0);
                        }
                    } else if (cliente) {
                        id_cliente = (Integer) request.getSession().getAttribute("idClienteX");
                        listado = gf.TraerFacturasParametrizadas(id_cliente,0 , 0);
                    } else {
                        listado = gf.TraerFacturasParametrizadas(0, 0, 3);
                    }
                    
                    ArrayList<Cliente> lstCliente = gp.ListarClientes();
                    ArrayList<Auto> lstAutos = ga.ListarAutos();
                    
                    

                    request.setAttribute("lstFacturas", listado);
                    request.setAttribute("id_cliente", id_cliente);
                    request.setAttribute("id_auto", id_auto);
                    request.setAttribute("lstCliente", lstCliente);
                    request.setAttribute("lstAutos", lstAutos);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/HistorialFacturas.jsp");
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

            System.out.println("entra? post");
            int id_c = Integer.parseInt(request.getParameter("id_c"));
            int id_a = Integer.parseInt(request.getParameter("id_a"));
            int eleccion = Integer.parseInt(request.getParameter("eleccion"));           
            
            
            ArrayList<Factura> lstFacturas = gf.TraerFacturasParametrizadas(id_c,id_a,eleccion);
            
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            String x = "";
            for (Factura f : lstFacturas) {
                x += f.toStringRevoluciones() + ",\n";
            }
            out.print(x);       
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
