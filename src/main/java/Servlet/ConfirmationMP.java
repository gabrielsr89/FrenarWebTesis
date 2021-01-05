/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controller.GestorFacturas;
import Controller.GestorLogin;
import Controller.GestorPersonas;
import Model.Factura;
import Model.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ConfirmationMP", urlPatterns = {"/ConfirmationMP"})
public class ConfirmationMP extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorFacturas gf = new GestorFacturas();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("administrador") != null
                && request.getSession().getAttribute("operador") != null
                && request.getSession().getAttribute("cliente") != null
                && request.getSession().getAttribute("idUsuario") != null) {

            boolean administrador = (boolean) request.getSession().getAttribute("administrador");
            boolean operador = (boolean) request.getSession().getAttribute("operador");
            boolean cliente = (boolean) request.getSession().getAttribute("cliente");
            Persona p = gp.obtenerPersona((Integer) request.getSession().getAttribute("idUsuario"));

            request.setAttribute("banderaIniciar", false);
            request.setAttribute("administrador", administrador);
            request.setAttribute("operador", operador);
            request.setAttribute("cliente", cliente);
            request.setAttribute("p", p);
            if (cliente || administrador || operador) {

                Factura carrito = (Factura) request.getSession().getAttribute("carrito");
                if (carrito.getId_usuario() == 0) {
                    int id_usuario = new GestorLogin().ObtenerIdUsuario((Integer) request.getSession().getAttribute("idUsuario"));
                    carrito.setId_usuario(id_usuario);
                }

                boolean exito = false;
                int ft = 0;

                if (cliente) {
                    ft = gf.CompraOnline(carrito);
                } else {
                    ft = gf.ventaRepuestosFacturados(carrito);
                }

                //ENVIAR A PÁGINA DE REPUESTOS A RETIRAR  
                carrito.setId_factura(ft);
                exito = gf.altaDetalleRepuestos(carrito.lstRepuesto, ft);

                if (exito) {
                    if (cliente) {                        
                        response.sendRedirect("./headerServlet");

                    } else {
                        response.sendRedirect("./FacturadoYPagadoListados");
                    }
                } else {
                    response.sendRedirect("./ErrorServlet?msjError=PostConfirmarCompraServlet217");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(" do something ON POST ");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
