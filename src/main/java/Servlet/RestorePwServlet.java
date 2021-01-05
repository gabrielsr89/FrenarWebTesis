package Servlet;

import Controller.GestorLogin;
import Model.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RestorePwServlet", urlPatterns = {"/RestorePwServlet"})
public class RestorePwServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorLogin gl = new GestorLogin();
        String mail = request.getParameter("correoAlServlet");
        boolean existe = gl.existeMail(mail);

        if (existe) {
            try {
                Persona p = gl.obtenerPersona(mail);
                //SALUDO
                String saludo = "";
                Calendar calendario = Calendar.getInstance();
                int hora;
                hora = calendario.get(Calendar.HOUR_OF_DAY);

                if (hora > 6 && hora < 13) {
                    saludo = "Buen día " + p.getNombre() + " " + p.getApellido();
                }
                if (hora > 12 && hora < 20) {
                    saludo = "Buenas tardes " + p.getNombre() + " " + p.getApellido();
                }
                if (hora > 19 && hora < 25 || hora >= 0 && hora < 7) {
                    saludo = "Buenas noches " + p.getNombre() + " " + p.getApellido();
                }
                
                //EL RESTO
                Properties props = new Properties();
                props.setProperty("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.auth", "true");
                Session s = Session.getDefaultInstance(props);

                String correoRemitente = "frenarweb@gmail.com";
                String passwordRemitente = "Frenar123";

                String asunto = "Recuperar contraseña FrenarWeb";
                String mensaje = "<div>"
                        + "<p>" + saludo + ", nos ha contactado para recuperar su contraseña.<br/> Su contraseña es:</p>"
                        + "<h4 style=\"color: red;\">" + p.getPass() + "</h4>"
                        + "<p>Saludos cordiales.</p></div>";

                MimeMessage message = new MimeMessage(s);
                message.setFrom(new InternetAddress(correoRemitente));

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                message.setSubject(asunto);
                message.setText(mensaje, "ISO-8859-1", "html");

                Transport t = s.getTransport("smtp");
                t.connect(correoRemitente, passwordRemitente);
                t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                t.close();

                response.setContentType("text/plain ");
                PrintWriter out = response.getWriter();
                out.print("<img src=\"./img/ok.png\"><h3 style='color:ivory; font-size: 30px;'>¡Le hemos enviado un mensaje a su correo con los datos pertinentes!</h3>");
            
            } catch (AddressException ex) {
                Logger.getLogger(RestorePwServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(RestorePwServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            response.setContentType("text/plain ");
            PrintWriter out = response.getWriter();
            out.print("<img src=\"./img/Advertencia.png\"><h3 style='color:ivory; font-size: 30px;'>El mail ingresado no se encuentra en nuestra base de datos. Cree una cuenta nueva o contáctese con un administrador.</h3>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
