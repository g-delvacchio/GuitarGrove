package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminUtentiServlet")
public class AdminUtentiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        // controllo admin
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            UtenteDAO dao = new UtenteDAO();

            List<Utente> utenti = dao.doRetrieveAll();

            request.setAttribute("utenti", utenti);

            request.getRequestDispatcher("/view/admin/utenti_admin.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}