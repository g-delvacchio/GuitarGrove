package control;

import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.bean.*;
import model.dao.*;

@WebServlet("/Checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        // login check
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        Indirizzo indirizzo = (Indirizzo) session.getAttribute("indirizzo");

        List<Map<String, Object>> items = new ArrayList<>();
        double totale = 0;

        try {

            ProdottoDAO pdao = new ProdottoDAO();
            ProdottoCarrelloDAO cdao = new ProdottoCarrelloDAO();

            List<ProdottoCarrello> dbItems =
                    cdao.doRetrieveByCond("user_id=" + user.getUserId());

            for (ProdottoCarrello pc : dbItems) {

                Prodotto p = pdao.doRetrieveByKey(pc.getProductId());

                // se prodotto non valido → rimuovi dal carrello
                if (p == null || !p.isAttivo() || p.getStock() <= 0) {
                    cdao.doDelete(user.getUserId(), pc.getProductId());
                    continue;
                }

                double subtotal = p.getPrezzo() * pc.getQuantita();

                Map<String, Object> row = new HashMap<>();
                row.put("prodotto", p);
                row.put("quantita", pc.getQuantita());
                row.put("subtotal", subtotal);

                items.add(row);
                totale += subtotal;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        // CHECK FINALE: carrello vuoto o totale = 0
        if (items.isEmpty() || totale <= 0) {
            response.sendRedirect(request.getContextPath() + "/Carrello");
            return;
        }

        request.setAttribute("items", items);
        request.setAttribute("totale", totale);
        request.setAttribute("indirizzo", indirizzo);

        request.getRequestDispatcher("/view/checkout.jsp")
                .forward(request, response);
    }
}