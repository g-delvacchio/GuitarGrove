package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Prodotto;
import model.bean.Utente;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditProdottoAdminServlet")
public class EditProdottoAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
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
            response.sendRedirect(
                    request.getContextPath() + "/AdminProdottiServlet"
            );
            return;
        }

        try {

            int id = Integer.parseInt(idParam);

            ProdottoDAO dao = new ProdottoDAO();

            Prodotto prodotto = dao.doRetrieveByKey(id);

            if (prodotto == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/AdminProdottiServlet?error=notfound"
                );

                return;
            }

            request.setAttribute("prodotto", prodotto);

            request.getRequestDispatcher(
                    "/WEB-INF/view/admin/form_prodotto_admin.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/AdminProdottiServlet?error=format"
            );

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}