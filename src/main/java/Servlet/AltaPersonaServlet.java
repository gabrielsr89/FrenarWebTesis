package Servlet;

import Controller.GestorLogin;
import Controller.GestorPersonas;
import Controller.GestorUbicacion;
import Model.Barrio;
import Model.Cliente;
import Model.Localidad;
import Model.Persona;
import Model.Provincia;
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

@WebServlet(urlPatterns = {"/AltaPersonaServlet"})
public class AltaPersonaServlet extends HttpServlet {

    GestorPersonas gp = new GestorPersonas();
    GestorUbicacion gu = new GestorUbicacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //PARA PREPARAR ALTA O MODIFICACIÓN
            String tipoUsuario = "";
            int idPersona = 0;
            String accion = "";

            if (request.getParameter("idPersona") == null) {
                accion = "Alta";
                if (request.getParameter("altaEmpleado") != null) {
                    tipoUsuario = "empleado";

                } else {
                    tipoUsuario = "cliente";
                }
            } else {
                accion = "Modificación";
                int id = Integer.parseInt(request.getParameter("idPersona"));
                Persona p = gp.obtenerPersona(id);
                request.setAttribute("p", p);
                if (request.getParameter("modificarEmpleado") != null) {
                    tipoUsuario = "empleado";
                } else {
                    tipoUsuario = "cliente";
                }
            }

            ArrayList<Provincia> ptc = gu.ListarProvincias();
            ArrayList<Localidad> lstLocalidades = gu.ListaLocalidades();
            ArrayList<Barrio> lstBarrios = gu.ListaBarrios();
            ArrayList<Localidad> lpjs = gu.LocalidadesParaJS();
            ArrayList<Barrio> bpjs = gu.BarriosParaJS();

            request.setAttribute("accion", accion);
            request.setAttribute("ptc", ptc);
            request.setAttribute("lstLocalidades", lstLocalidades);
            request.setAttribute("lstBarrios", lstBarrios);
            request.setAttribute("lpjs", lpjs);
            request.setAttribute("bpjs", bpjs);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/altaPersona.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //-----------------------------CARGAR PERSONA----------------------- //          
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String mail = request.getParameter("mail");
            String password = request.getParameter("pw");
            String telefono = request.getParameter("telefono");
            String tel_alternativo = request.getParameter("tel_alternativo");
            String direccion = request.getParameter("direccion");
            String cp = request.getParameter("cp");
            String accion = request.getParameter("action");
            Barrio b = null;

            int idBarrio = Integer.parseInt(request.getParameter("idBarrio"));
            String nombreBarrio = gu.ObtenerNombreBarrio(idBarrio);
            if (nombreBarrio.equals("OTRO BARRIO")) {
                String nuevoNombreBarrio = request.getParameter("nuevoBarrio");
                int idLocalidad = Integer.parseInt(request.getParameter("idLocalidad"));
                b = new Barrio(nuevoNombreBarrio, idLocalidad);
                idBarrio = gu.NuevoBarrioCliente(b);
                b.setId_barrio(idBarrio);
            } else {
                b = new Barrio(idBarrio);
            }
            Localidad l = new Localidad(Integer.parseInt(request.getParameter("idLocalidad")));
            Provincia p = new Provincia(Integer.parseInt(request.getParameter("idProvincia")));

            Persona persona = new Persona(nombre, apellido, mail, password, telefono, tel_alternativo, b.getId_barrio(), direccion, cp);

            //-------------------------------FIN CARGAR PERSONA-------------//
            //PARAMETRO ENVIADO DESDE OTRO JSP QUE INDICA SI EMPLEADO O CLIENTE
            String tipoUsuario = (String) request.getParameter("tipoUsuario");

            //VARIABLE PARA INDICAR EXITO O FRACASO EN LOS PROCEDIMIENTOS
            boolean exito = false;

            //------------------------TRANSFERIR A BD--------------------//
            //ALTA 
            System.out.println("" + tipoUsuario);

            if (accion.equals("Alta")) {
                // cliente
                if (tipoUsuario.equals("cliente")) {

                    System.out.println("entra acá " + persona.toString());

                    exito = gp.AMPersona(persona, "cliente", "alta");
                }// operador
                if (tipoUsuario.equals("operador")) {
                    exito = gp.AMPersona(persona, "empleado", "alta");
                }

            } else {//MODIFICACION  
                if (tipoUsuario.equals("cliente")) {
                    exito = gp.AMPersona(persona, "cliente", "modificar");
                } else {
                    persona.setId(Integer.parseInt(request.getParameter("idPersona")));//pasarle el id para saber cual modificar               
                    exito = gp.AMPersona(persona, "empleado", "modificar");
                }
            }//-----------------------------------FIN------------------------//

            //-----------------------------CIERRE Y REDIRECCIÓN-------------//
            if (exito) {

                if (tipoUsuario.equals("operador")) {
                    if (accion.equals("Alta")) {
                        response.sendRedirect("./AdministrarEmpleadoServlet");
                    } else {
                        response.sendRedirect("./inicioPersonaServlet");
                    }
                } else if (tipoUsuario.equals("cliente")) {
                    if (accion.equals("Alta")) {
                        response.sendRedirect("./LoginServlet");
                    } else {
                        response.sendRedirect("./inicioPersonaServlet");
                    }
                } else {
                    response.sendRedirect("./inicioPersonaServlet");

                }
            } else {
                response.sendRedirect("./Error.jsp");
            }
            //------------------------------------FIN--------------------------//

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
