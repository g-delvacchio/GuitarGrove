package control;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.*;
import model.dao.*;

@WebServlet("/Carrello")
public class CartServlet extends HttpServlet {

    private static final String SESSION_CART = "sessionCart";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        List<Map<String, Object>> itemsView = new ArrayList<>();
        double totale = 0;

        try {

            ProdottoDAO pdao = new ProdottoDAO();

            // =========================
            // LOGGATO (DB)
            // =========================
            if (user != null) {

                ProdottoCarrelloDAO cdao = new ProdottoCarrelloDAO();

                List<ProdottoCarrello> items = cdao.doRetrieveByCond("user_id=" + user.getUserId());

                for (ProdottoCarrello pc : items) {

                    Prodotto p = pdao.doRetrieveByKey(pc.getProductId());

                    // elimina prodotti non validi
                    if (p == null || !p.isAttivo() || p.getStock() <= 0) {
                        cdao.doDelete(user.getUserId(), pc.getProductId());
                        continue;
                    }

                    int qty = pc.getQuantita();

                    // quantità allo stock
                    if (qty > p.getStock()) {
                        qty = p.getStock();

                        pc.setQuantita(qty);
                        cdao.doSaveOrUpdate(pc);
                    }

                    double subtotal = p.getPrezzo() * qty;

                    Map<String, Object> row = new HashMap<>();
                    row.put("prodotto", p);
                    row.put("quantita", qty);
                    row.put("subtotal", subtotal);

                    itemsView.add(row);

                    totale += subtotal;
                }
            }

            // =========================
            // GUEST (SESSIONE)
            // =========================
            else {

                //sessione utente: id prodotto, quantità
                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_CART);

                if (cart != null) {

                    //scorrere tutto il carrello elemento per elemento
                    Iterator<Map.Entry<Integer, Integer>> it = cart.entrySet().iterator();

                    while (it.hasNext()) {
                        //prendere un elemento del carrello alla volta
                        Map.Entry<Integer, Integer> entry = it.next();

                        Prodotto p = pdao.doRetrieveByKey(entry.getKey());

                        // elimina prodotti non validi
                        if (p == null || !p.isAttivo() || p.getStock() <= 0) {
                            it.remove();
                            continue;
                        }

                        int qty = entry.getValue();

                        // quantità allo stock
                        if (qty > p.getStock()) {
                            qty = p.getStock();
                            entry.setValue(qty);
                        }

                        double subtotal = p.getPrezzo() * qty;

                        Map<String, Object> row = new HashMap<>();
                        row.put("prodotto", p);
                        row.put("quantita", qty);
                        row.put("subtotal", subtotal);

                        itemsView.add(row);

                        totale += subtotal;
                    }

                    session.setAttribute(SESSION_CART, cart);
                }
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        request.setAttribute("items", itemsView);
        request.setAttribute("totale", totale);

        request.getRequestDispatcher("/jsp/cart.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action == null || idParam == null) {
            response.sendRedirect(request.getContextPath() + "/Carrello");
            return;
        }

        int productId = Integer.parseInt(idParam);

        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        try {

            ProdottoDAO pdao = new ProdottoDAO();
            Prodotto prodotto = pdao.doRetrieveByKey(productId);

            // =========================
            // LOGGATO
            // =========================
            if (user != null) {

                ProdottoCarrelloDAO cdao = new ProdottoCarrelloDAO();

                ProdottoCarrello pc =
                        cdao.doRetrieveByKey(user.getUserId(), productId);

                if ("add".equals(action)) {

                    if (pc == null) {
                        pc = new ProdottoCarrello();
                        pc.setUserId(user.getUserId());
                        pc.setProductId(productId);
                        pc.setQuantita(1);
                    } else {

                        int newQty = pc.getQuantita() + 1;

                        if (prodotto != null) {
                            newQty = Math.min(newQty, prodotto.getStock());
                        }

                        pc.setQuantita(newQty);
                    }

                    cdao.doSaveOrUpdate(pc);
                }

                if ("delete".equals(action)) {

                    if (pc != null) {

                        if (pc.getQuantita() > 1) {
                            pc.setQuantita(pc.getQuantita() - 1);
                            cdao.doSaveOrUpdate(pc);
                        } else {
                            cdao.doDelete(user.getUserId(), productId);
                        }
                    }
                }
            }

            // =========================
            // GUEST
            // =========================
            else {

                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_CART);

                if (cart == null) {
                    cart = new HashMap<>();
                }

                if ("add".equals(action)) {
                    // si prende dal carrello la quantità attuale del prodotto con quell'id
                    // e se il prodotto non è presente nel carrello si mette 0
                    int currentQty = cart.getOrDefault(productId, 0);

                    if (prodotto != null) {
                        if (currentQty < prodotto.getStock()) {
                            cart.put(productId, currentQty + 1);
                        }
                    }
                }

                if ("delete".equals(action)) {
                    //decrementa la quantità di 1 se si clicca sul "-"
                    cart.put(productId, cart.getOrDefault(productId, 0) - 1);

                    if (cart.get(productId) <= 0) {
                        cart.remove(productId);
                    }
                }

                session.setAttribute(SESSION_CART, cart);
            }

        } catch (Exception e) {
            throw new IOException(e);
        }

        response.sendRedirect(request.getContextPath() + "/Carrello");
    }
}