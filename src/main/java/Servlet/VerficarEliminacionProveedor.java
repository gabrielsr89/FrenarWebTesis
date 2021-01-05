package Servlet;

import Controller.GestorProveedores;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerficarEliminacionProveedor", urlPatterns = {"/VerficarEliminacionProveedor"})
public class VerficarEliminacionProveedor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String x = "false";

        /*GestorProveedores gp = new GestorProveedores();
        int id_prov = Integer.parseInt(request.getParameter("id_prov"));
        System.out.println(" a " + id_prov);*/

        response.setContentType("text/plain ");
        PrintWriter out = response.getWriter();
        out.print(x);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
