package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteProdottoAdminServlet")
public class DeleteProdottoAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // controllo sessione
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        // controllo login + admin
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/view/admin/prodotti_admin.jsp?error=missing");
            return;
        }

        try {
            int productId = Integer.parseInt(idParam);

            ProdottoDAO dao = new ProdottoDAO();
            dao.doDelete(productId);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/view/admin/prodotti_admin.jsp?error=format");
            return;

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/view/admin/prodotti_admin.jsp?error=db");
            return;
        }

        // ritorna sempre alla pagina admin prodotti
        response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
    }
}