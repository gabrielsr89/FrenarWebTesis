package Servlet;

import Controller.GestorServicios;
import Model.Categorias;
import Model.Servicio;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CatServiciosServlet", urlPatterns = {"/CatServiciosServlet"})
public class CatServiciosServlet extends HttpServlet {

    GestorServicios gs = new GestorServicios();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.getProperty("java.classpath");
        try {
            if (request.getParameter("id_categoria") != null) {
                int id_categoria = Integer.parseInt(request.getParameter("id_categoria"));
                ArrayList<Categorias> lista = gs.TraerCategorias();
                String categoria = "";
                for (Categorias c : lista) {
                    if(c.getId_categoria() == id_categoria){
                        categoria = c.getCategoria();
                    }
                }                
                ArrayList<Servicio> lstServicio = gs.TraerServiciosPorCategoria(id_categoria);
                request.setAttribute("lstServicio", lstServicio);
                request.setAttribute("categoria", categoria);
                
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/serviciosCatalogo.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/headerServlet.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
