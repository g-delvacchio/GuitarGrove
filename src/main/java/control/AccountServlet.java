package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Indirizzo;
import model.bean.Utente;
import model.dao.IndirizzoDAO;

import java.io.IOException;

@WebServlet("/Account")
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        try {
            IndirizzoDAO dao = new IndirizzoDAO();
            Indirizzo indirizzo = dao.doRetrieveByKey(user.getUserId());

            session.setAttribute("indirizzo", indirizzo);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        request.getRequestDispatcher("/view/account.jsp")
                .forward(request, response);
    }
}