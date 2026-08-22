package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.*;
import model.dao.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/VisualizzaOrdiniServlet")
public class VisualizzaOrdiniServlet extends HttpServlet {

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
            AcquistoDAO acquistoDAO = new AcquistoDAO();
            ProdottoAcquistatoDAO paDAO = new ProdottoAcquistatoDAO();
            ProdottoDAO prodottoDAO = new ProdottoDAO();

            // ORDINI UTENTE
            List<Acquisto> ordini =
                    acquistoDAO.doRetrieveByCond("user_id=" + user.getUserId());

            // MAP: orderId -> lista prodotti acquistati (con dettagli prodotto)
            Map<Integer, List<Map<String, Object>>> prodottiPerOrdine = new HashMap<>();

            for (Acquisto ordine : ordini) {

                List<ProdottoAcquistato> prodottiAcquistati =
                        paDAO.doRetrieveByCond("order_id=" + ordine.getOrderId());

                List<Map<String, Object>> prodottiView = new ArrayList<>();

                for (ProdottoAcquistato pa : prodottiAcquistati) {

                    Prodotto p = prodottoDAO.doRetrieveByKey(pa.getProductId());

                    Map<String, Object> row = new HashMap<>();
                    row.put("prodotto", p);
                    row.put("quantita", pa.getQuantita());
                    row.put("prezzo", pa.getPrezzo());

                    prodottiView.add(row);
                }

                prodottiPerOrdine.put(ordine.getOrderId(), prodottiView);
            }

            request.setAttribute("ordini", ordini);
            request.setAttribute("prodottiPerOrdine", prodottiPerOrdine);

            request.getRequestDispatcher("/view/visualizza_ordini.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}