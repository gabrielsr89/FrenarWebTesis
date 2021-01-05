
package Servlet;

import Controller.GestorFacturas;
import Model.Factura;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CancelarReserva", urlPatterns = {"/CancelarReserva"})
public class CancelarReserva extends HttpServlet {
    
    GestorFacturas gf = new GestorFacturas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id_f =  Integer.parseInt(request.getParameter("id_f"));
        Factura f = gf.ObtenerFacturaPorId(id_f);
        gf.CancelarReserva(f);
        response.sendRedirect("./ConfirmarReservaServlet");

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
