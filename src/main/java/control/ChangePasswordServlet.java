package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.dao.UtenteDAO;

import java.io.IOException;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // controllo login
        if(session == null || session.getAttribute("user") == null){

            response.sendRedirect(request.getContextPath()+"/view/login.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        // campi vuoti
        if(oldPass == null || newPass == null || confirm == null ||
                oldPass.trim().isEmpty() ||
                newPass.trim().isEmpty() ||
                confirm.trim().isEmpty()){

            response.sendRedirect(request.getContextPath()+"/Account?error=missing");
            return;
        }

        // controllo vecchia password
        String oldHash = HelperClass.toHash(oldPass);

        if(!oldHash.equals(user.getPasswordHash())){
            response.sendRedirect(request.getContextPath()+"/Account?error=wrongOld");
            return;
        }

        // controllo nuova password uguale conferma
        if(!newPass.equals(confirm)){
            response.sendRedirect(request.getContextPath()+"/Account?error=match");
            return;
        }

        // controllo formato nuova password
        if(!newPass.matches(PASSWORD_REGEX)){
            response.sendRedirect(request.getContextPath()+"/Account?error=password"
            );
            return;
        }

        try {
            String newHash = HelperClass.toHash(newPass);
            user.setPasswordHash(newHash);
            UtenteDAO dao = new UtenteDAO();
            dao.doSaveOrUpdate(user);

            // aggiorno sessione
            session.setAttribute("user", user);

            response.sendRedirect(
                    request.getContextPath()+"/Account?success=1"
            );

        }catch(Exception e){
            e.printStackTrace();
            response.sendRedirect(
                    request.getContextPath()+"/Account?error=db"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()+"/Account"
        );
    }
}