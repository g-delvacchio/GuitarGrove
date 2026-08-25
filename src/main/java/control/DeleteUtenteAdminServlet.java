package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteUtenteAdminServlet")
public class DeleteUtenteAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente admin = (Utente) session.getAttribute("user");

        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            IndirizzoDAO indirizzo = new IndirizzoDAO();
            UtenteDAO dao = new UtenteDAO();

            // evita che un admin si cancelli da solo
            if (admin.getUserId() == id) {
                response.sendRedirect(request.getContextPath() + "/AdminUtentiServlet?error=selfdelete");
                return;
            }

            indirizzo.doDelete(id);
            dao.doDelete(id);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/AdminUtentiServlet");
    }
}