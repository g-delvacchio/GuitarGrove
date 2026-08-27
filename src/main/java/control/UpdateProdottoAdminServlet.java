package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UpdateProdottoAdminServlet")
public class UpdateProdottoAdminServlet extends HttpServlet {

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

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String action = request.getParameter("action");

            ProdottoDAO dao = new ProdottoDAO();

            Prodotto p = dao.doRetrieveByKey(id);

            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
                return;
            }

            switch (action) {

                case "stock":

                    int stock = Integer.parseInt(request.getParameter("stock"));

                    // mai negativo
                    if (stock < 0) stock = 0;

                    p.setStock(stock);
                    dao.doSaveOrUpdate(p);
                    break;

                case "attivo":

                    int attivo = Integer.parseInt(request.getParameter("attivo"));

                    // solo 0/1
                    p.setAttivo(attivo == 1);
                    dao.doSaveOrUpdate(p);
                    break;
            }

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/AdminProdottiServlet");
    }
}