package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.bean.Utente;

import java.io.IOException;

@WebServlet("/Admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        if (!user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/view/admin/admin.jsp")
                .forward(request, response);
    }
}