package Servlet;

import Controller.GestorPersonas;
import Model.Cliente;
import Model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        boolean administrador = false;
        boolean cliente = false;
        boolean operador = false;
        int idUsuario = 0;

        ArrayList<Cliente> lstCliente = gp.ListarClientes();
        ArrayList<Usuario> lstAdmin = gp.ListarAdministradores();
        ArrayList<Usuario> lstOperadores = gp.ListarOperadores();

        for (Usuario u : lstAdmin) {
            if (u.p.getEmail() != null && u.p.getPass() != null) {
                if (u.p.getEmail().equals(email) && u.p.getPass().equals(pass)) {
                    administrador = true;
                    request.getSession().setAttribute("idAdministradorX", u.getId_usuario());
                    idUsuario = u.p.getId();
                    break;
                }
            }
        }
        for (Usuario u : lstOperadores) {
            if (u.p.getEmail() != null && u.p.getPass() != null) {
                if (u.p.getEmail().equals(email) && u.p.getPass().equals(pass)) {
                    idUsuario = u.p.getId();
                    operador = true;
                    break;
                }
            }
        }
        System.out.println("" + lstCliente);
        for (Cliente c : lstCliente) {
            if (c.p.getEmail() != null && c.p.getPass() != null) {
                if (c.p.getEmail().equals(email) && c.p.getPass().equals(pass)) {
                    idUsuario = c.p.getId();
                    request.getSession().setAttribute("idClienteX", c.getId());
                    if (administrador || operador) {
                        cliente = false;
                    } else {
                        operador = false;
                        administrador = false;
                        cliente = true;
                    }
                    break;
                }
            }
        }

        if (administrador || operador || cliente) {
            request.getSession().setAttribute("administrador", administrador);
            request.getSession().setAttribute("operador", operador);
            request.getSession().setAttribute("cliente", cliente);
            request.getSession().setAttribute("idUsuario", idUsuario);
            response.sendRedirect("./inicioPersonaServlet");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("<img src=\"./img/Advertencia.png\">El mail ingresado no existe en nuestro sistema");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
