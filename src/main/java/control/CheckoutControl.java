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
    private static final String CAP_REGEX = "^[0-9]{5}$";
    private static final String CIVICO_REGEX = "^[0-9]{1,5}[A-Za-z]?$";
    private static final String PLACE_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        Utente user = (Utente) session.getAttribute("user");
        String card = request.getParameter("cardNumber");
        String expiry = request.getParameter("expiry");
        String cvv = request.getParameter("cvv");
        String paese = request.getParameter("paese");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String via = request.getParameter("via");
        String civico = request.getParameter("civico");

        if (card == null || expiry == null || cvv == null || !card.matches(CARD_REGEX) || !cvv.matches(CVV_REGEX)) {
            response.sendRedirect(request.getContextPath() + "/Checkout?error=payment");
            return;
        }
        
        if (paese == null || citta == null || cap == null || via == null || civico == null) {
            response.sendRedirect(request.getContextPath() + "/Checkout?error=address");
            return;
        }
        
        paese = paese.trim();
        citta = citta.trim();
        cap = cap.trim();
        via = via.trim();
        civico = civico.trim();
        
        if (!paese.matches(PLACE_REGEX) || !citta.matches(PLACE_REGEX) || !cap.matches(CAP_REGEX) || via.length() < 2 || !civico.matches(CIVICO_REGEX)) {
            response.sendRedirect(request.getContextPath() + "/Checkout?error=address");
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

            for (ProdottoCarrello pc : carrello) {
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(pc.getProductId());

                if (prodotto == null || prodotto.getStock() < pc.getQuantita()) {
                    response.sendRedirect(request.getContextPath() + "/Carrello?error=stock");
                    return;
                }

                totale += prodotto.getPrezzo() * pc.getQuantita();
            }

            Acquisto acquisto = new Acquisto();
            acquisto.setUserId(user.getUserId());
            acquisto.setTotale(totale);
            acquisto.setSpedizione(10);
            acquisto.setStato("COMPLETATO");
            acquisto.setPagamento("CARTA");
            acquisto.setPaeseSpedizione(paese);
            acquisto.setCittaSpedizione(citta);
            acquisto.setCapSpedizione(cap);
            acquisto.setViaSpedizione(via);
            acquisto.setCivicoSpedizione(civico);
            int orderId = acquistoDAO.doSave(acquisto);

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

            for (ProdottoCarrello pc : carrello) {
                cartDao.doDelete(user.getUserId(), pc.getProductId());
            }

            response.sendRedirect(request.getContextPath() + "/Success");

        } catch(SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/Checkout?error=db"
            );

        }

    }
}