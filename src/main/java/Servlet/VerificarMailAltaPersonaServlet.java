package Servlet;

import Controller.GestorLogin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "VerificarMailAltaPersonaServlet", urlPatterns = {"/VerificarMailAltaPersonaServlet"})
public class VerificarMailAltaPersonaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //VERIFICACIÓN PARA CAMBIAR EL CORREO
        if (request.getParameter("VerificarCorreo") != null) {
            String mail = request.getParameter("VerificarCorreo");
            GestorLogin gl = new GestorLogin();
            boolean disponible = gl.existeMail(mail);
            if (!disponible) {
                //mail disponible (la lógica es inversa ya que se usa el mismo metodo para otra cosa)
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print("<img src=\"./img/ok.png\" style=\"width: 30px; height: 30px;\"><input type=\"hidden\" value=\"positivo\" id=\"respuestaMail\">");
            } else {
                //NO DISPONIBLE
                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print("<img src=\"./img/Error.png\" style=\"width: 30px; height: 30px;\"><input type=\"hidden\" value=\"negativo\" id=\"respuestaMail\">");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
