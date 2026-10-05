package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteProdottoAdminServlet")
public class DeleteProdottoAdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet?error=missing");
            return;
        }

        try {
        	
            int productId = Integer.parseInt(idParam);

            ProdottoDAO dao = new ProdottoDAO();

            Prodotto prodotto = dao.doRetrieveByKey(productId);

            if (prodotto == null) {
                response.sendRedirect(
                        request.getContextPath()
                        + "/AdminProdottiServlet?error=notfound"
                );
                return;
            }

            prodotto.setAttivo(false);

            dao.doSaveOrUpdate(prodotto);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet?error=format");
            return;

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet?error=db");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
    }
}