package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;

import java.io.IOException;

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("view/login.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("view/login.jsp");
            return;
        }

        try {

            int userId = user.getUserId();

            // 1. elimina indirizzo
            IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
            indirizzoDAO.doDelete(userId);

            // 2. elimina utente
            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.doDelete(userId);

            // 3. chiudi sessione
            session.invalidate();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}