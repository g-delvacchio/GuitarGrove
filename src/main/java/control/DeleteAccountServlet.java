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
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {

            int userId = user.getUserId();

            IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
            indirizzoDAO.doDelete(userId);

            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.doDelete(userId);

            session.invalidate();

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}