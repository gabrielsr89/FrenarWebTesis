package Servlet;

import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Model.Persona;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "headerServlet", urlPatterns = {"/headerServlet"})
public class headerServlet extends HttpServlet {

    GestorLogin gl = new GestorLogin();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //-----------USUARIOS-------PARA LOGEAR-----------------
            
            boolean banderaIniciar = true;            
            boolean administrador = false;
            boolean operador = false;
            boolean cliente = false;        
            Persona p = new Persona("","");
            
            if(request.getSession().getAttribute("administrador") != null &&  
               request.getSession().getAttribute("operador") != null &&  
               request.getSession().getAttribute("cliente") != null &&  
               request.getSession().getAttribute("idUsuario") != null){ 
                int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();
   
            request.setAttribute("AlertaStock", AlertaStock);                  
                administrador = (boolean)request.getSession().getAttribute("administrador");
                operador = (boolean)request.getSession().getAttribute("operador");
                cliente = (boolean)request.getSession().getAttribute("cliente"); 
                p = new GestorPersonas().obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));
                banderaIniciar = false;
            }   
            int AlertaStock = new GestorRepuesto().ListarRepuestosBajoStock().size();   
            request.setAttribute("AlertaStock", AlertaStock);  
            
            request.setAttribute("banderaIniciar", banderaIniciar);            
            request.setAttribute("administrador", administrador);            
            request.setAttribute("operador", operador);             
            request.setAttribute("cliente", cliente);            
            request.setAttribute("p",p);
            //------------------------CERRAR SESION------------------------------------
            request.setAttribute("url", "index.jsp");//Se indica desde donde se cierra
            //--------------------------FIN USUARIOS-----------------------------------
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);

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
