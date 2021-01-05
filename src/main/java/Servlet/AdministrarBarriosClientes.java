/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.GestorPersonas;
import Controller.GestorRepuesto;
import Controller.GestorUbicacion;
import Model.Barrio;
import Model.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pandorium
 */
@WebServlet(name = "AdministrarBarriosClientes", urlPatterns = {"/AdministrarBarriosClientes"})
public class AdministrarBarriosClientes extends HttpServlet {

    GestorUbicacion gu = new GestorUbicacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //-------------VALIDAR QUE SEA ADM Y CARGAR EL HEADER------------------
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

                if (administrador) {
                    //---------------------------CUERPO SI ES ADMIN----------------------//

                    Persona perkins = null;

                    if (request.getParameter("id") != null) {
                        int idPersona = Integer.parseInt(request.getParameter("id"));
                        perkins = gu.obtenerBarrio(idPersona);
                    }

                    ArrayList<Persona> lstBarriosNuevos = gu.ListarBarriosNuevos();
                    System.out.println(""+lstBarriosNuevos.size());
                    
                    
                    ArrayList<Barrio> lstBarrios = gu.ListarBarriosViejos();
                    request.setAttribute("perkins", perkins);
                    request.setAttribute("lstBarriosNuevos", lstBarriosNuevos);
                    request.setAttribute("lstBarrios", lstBarrios);

                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdministrarBarriosClientes.jsp");
                    rd.forward(request, response);
                } else {//--------------------------FIN SI ES ADM---------------------//
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/SinPermiso.jsp");
                    rd.forward(request, response);
                }
            } else {
                response.sendRedirect("./headerServlet");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            boolean exito = false;
            String rbt = request.getParameter("rbtConfirmar");

            int idPerkins = Integer.parseInt(request.getParameter("idPerkins"));
            Persona perkins = gu.obtenerBarrio(idPerkins);

            int idBarrioDescartado = perkins.getBarrio().getId_barrio();
            int idLocalidad = perkins.getLocalidad().getId_localidad();
            int idCboBarrio = Integer.parseInt(request.getParameter("cbo"));

            if (rbt.equals("option1")) {
                exito = gu.ActualizarBarrioCorrecto(idBarrioDescartado);
            }
            if (rbt.equals("option2")) {
                exito = gu.DescartarBarrio(idLocalidad, idBarrioDescartado);
            }
            if (rbt.equals("option3")) {
                exito = gu.CorregirBarrio(idCboBarrio, idBarrioDescartado, idPerkins);
            }
            if (exito) {
                response.sendRedirect("./ExitoServlet?url=AdministrarUbicacionesServlet");
            } else{
                response.sendRedirect("./Error.jsp");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            response.sendRedirect("./Error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
