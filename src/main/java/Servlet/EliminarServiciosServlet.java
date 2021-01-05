package Servlet;

import Controller.GestorServicios;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EliminarServiciosServlet", urlPatterns = {"/EliminarServiciosServlet"})
public class EliminarServiciosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id_servicio = Integer.parseInt(request.getParameter("id_servicio"));
        GestorServicios gs = new GestorServicios();
        boolean resultado = gs.EliminarServicio(id_servicio);
        
        System.out.println(resultado+" acá en eliminarservlet");
        
        if(resultado){
            response.sendRedirect("./ExitoServlet?url=AdministrarServicios");
        } else{
            response.sendRedirect("./Error.jsp");        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
