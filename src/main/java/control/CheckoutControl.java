package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.*;
import model.dao.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CheckoutControl")
public class CheckoutControl extends HttpServlet {

    private static final String CARD_REGEX = "^[0-9]{16}$";
    private static final String CVV_REGEX = "^[0-9]{3}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");
        String card = request.getParameter("cardNumber");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");

        if (card == null || expiry == null || cvv == null || !card.matches(CARD_REGEX) || !cvv.matches(CVV_REGEX)) {
            response.sendRedirect(request.getContextPath() + "/view/checkout.jsp?error=payment");
            return;
        }
        try {
            ProdottoCarrelloDAO cartDao = new ProdottoCarrelloDAO();
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            AcquistoDAO acquistoDAO = new AcquistoDAO();
            ProdottoAcquistatoDAO prodottoAcquistatoDAO = new ProdottoAcquistatoDAO();

            List<ProdottoCarrello> carrello = cartDao.doRetrieveByCond("user_id=" + user.getUserId());
            if (carrello == null || carrello.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Carrello?error=empty");
                return;
            }

            double totale = 0;

            // controllo prodotti e stock
            for (ProdottoCarrello pc : carrello) {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(pc.getProductId());

                if (prodotto == null || prodotto.getStock() < pc.getQuantita()) {
                    response.sendRedirect(request.getContextPath() + "/Carrello?error=stock");
                    return;
                }

                totale += prodotto.getPrezzo() * pc.getQuantita();
            }

            // CREAZIONE ORDINE
            Acquisto acquisto = new Acquisto();
            acquisto.setUserId(user.getUserId());
            acquisto.setTotale(totale);
            acquisto.setSpedizione(10);
            acquisto.setStato("COMPLETATO");
            acquisto.setPagamento("CARTA");
            int orderId = acquistoDAO.doSave(acquisto);

            // SALVATAGGIO PRODOTTI ACQUISTATI + UPDATE STOCK
            for (ProdottoCarrello pc : carrello) {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(pc.getProductId());

                ProdottoAcquistato pa = new ProdottoAcquistato();

                pa.setOrderId(orderId);
                pa.setProductId(pc.getProductId());
                pa.setQuantita(pc.getQuantita());
                pa.setPrezzo(prodotto.getPrezzo());

                prodottoAcquistatoDAO.doSave(pa);

                prodotto.setStock(prodotto.getStock() - pc.getQuantita());
                prodottoDAO.doSaveOrUpdate(prodotto);
            }

            // SVUOTA CARRELLO
            for (ProdottoCarrello pc : carrello) {
                cartDao.doDelete(user.getUserId(), pc.getProductId());
            }

            response.sendRedirect(request.getContextPath() + "/view/success.jsp");

        } catch(SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/view/checkout.jsp?error=db"
            );

        }

    }
}