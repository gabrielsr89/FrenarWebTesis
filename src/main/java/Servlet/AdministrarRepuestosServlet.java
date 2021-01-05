package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.MarcaRepuesto;
import Model.Persona;
import Model.Repuesto;
import Model.Rubro;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = {"/AdministrarRepuestosServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5,
        location = "C:\\Users\\Pandorium\\Documents\\NetBeansProjects\\FrenarWeb\\src\\main\\webapp\\img")

public class AdministrarRepuestosServlet extends HttpServlet {

    GestorRepuesto gr = new GestorRepuesto();

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
                if (administrador) {
                    //---------------------------CUERPO SI ES ADMINISTRADOR------------------

                    ArrayList<Repuesto> lstRepuesto;
                    ArrayList<Rubro> lstRubro = gr.ListarRubros();
                    ArrayList<MarcaRepuesto> lstMarcaRepuesto = gr.ListarMarcasRepuesto();
                    String tipoListado = "Parametrizar listado";

                    if (request.getParameter("numListado") != null) {
                        int eleccion = Integer.parseInt(request.getParameter("numListado"));
                        lstRepuesto = gr.ListarRepuestos(eleccion);
                        if (eleccion == 0) {
                            tipoListado = "Listado ordenado por precios";
                        } else if (eleccion == 1) {
                            tipoListado = "Listado ordenado por rubros";
                        } else if (eleccion == 2) {
                            tipoListado = "Listado ordenado por marcas";
                        }
                    } else {
                        lstRepuesto = gr.ListarRepuestos(0);

                    }

                    request.setAttribute("tipoListado", tipoListado);
                    request.setAttribute("lstRepuesto", lstRepuesto);
                    request.setAttribute("lstRubro", lstRubro);
                    request.setAttribute("lstMarcaRepuesto", lstMarcaRepuesto);

                    if (request.getParameter("idRepuesto") == null) {
                        //alta
                        request.setAttribute("accion", "Nuevo repuesto");
                        request.setAttribute("foto", "./img/fotonueva.png");
                        request.setAttribute("idRubro", 0);
                        request.setAttribute("idMarca", 0);

                    } else {
                        //modificar
                        request.setAttribute("accion", "Modificar repuesto");
                        int id = Integer.parseInt(request.getParameter("idRepuesto"));
                        Repuesto r = gr.obtenerRepuesto(id);

                        request.setAttribute("r", r);

                        request.setAttribute("repuesto", r.getRepuesto());
                        request.setAttribute("idRubro", r.getR().getId());
                        request.setAttribute("idMarca", r.getMr().getId_marcaRepuesto());
                        request.setAttribute("descripcion", r.getDescripcion());
                        request.setAttribute("foto", r.getFoto());
                        request.setAttribute("precio", r.getPrecio());
                    }

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarProductos.jsp");
                    rd.forward(request, response);
                }
            } else {//---------------------------FIN QUE SEA ADM------------------
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                rd.forward(request, response);
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            if (request.getParameter("eliminar") != null) {
                boolean exito = false;
                int id = Integer.parseInt(request.getParameter("id_repuesto"));
                exito = gr.EliminarRepuesto(id);
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print(exito);

            } else {
                int id_r = Integer.parseInt(request.getParameter("idR"));
                int id_m = Integer.parseInt(request.getParameter("idM"));
                System.out.println("marca: " + id_m + " rubro: " + id_r);
                int stock_min = Integer.parseInt(request.getParameter("stock_min"));
                String repuesto = request.getParameter("repuesto");
                String descripcion = request.getParameter("descripcion");
                double precio = Double.parseDouble(request.getParameter("precio"));

                Rubro rubrovich = new Rubro(id_r);
                MarcaRepuesto m = new MarcaRepuesto(id_m);
                String foto = "";

                for (Part part : request.getParts()) {
                    String fileName = getFileName(part);
                    if (!fileName.isEmpty()) {
                        part.write(fileName);
                        foto = fileName;
                    }
                }                

                if (request.getParameter("idRepuesto") == null) {
                    //alta
//                    Repuesto r = new Repuesto(repuesto, m, rubrovich, descripcion, precio, "./img/" + foto);
                    Repuesto r = new Repuesto(repuesto, m, rubrovich, descripcion, precio, "./img/" + foto, stock_min);
                    gr.NuevoRepuesto(r);
                    response.sendRedirect("./AdministrarRepuestosServlet");
                } else {
                    //mod
                    int id = Integer.parseInt(request.getParameter("idRepuesto"));
                    Repuesto r = new Repuesto(id, repuesto, m, rubrovich, descripcion, precio, "./img/" + foto, stock_min);
                    gr.ModificarRepuesto(r);
                    response.sendRedirect("./AdministrarRepuestosServlet");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exepción en post del administrarRepuestosServlet: " + ex.toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "";
    }

}
