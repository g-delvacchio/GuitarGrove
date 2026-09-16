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

            if (user != null) {

                ProdottoCarrelloDAO cdao = new ProdottoCarrelloDAO();

                List<ProdottoCarrello> items = cdao.doRetrieveByCond("user_id=" + user.getUserId());

                for (ProdottoCarrello pc : items) {

                    Prodotto p = pdao.doRetrieveByKey(pc.getProductId());

                    if (p == null || !p.isAttivo() || p.getStock() <= 0) {
                        cdao.doDelete(user.getUserId(), pc.getProductId());
                        continue;
                    }

                    int qty = pc.getQuantita();

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

            else {

                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_CART);

                if (cart != null) {

                    Iterator<Map.Entry<Integer, Integer>> it = cart.entrySet().iterator();

                    while (it.hasNext()) {
                    	
                        Map.Entry<Integer, Integer> entry = it.next();

                        Prodotto p = pdao.doRetrieveByKey(entry.getKey());

                        if (p == null || !p.isAttivo() || p.getStock() <= 0) {
                            it.remove();
                            continue;
                        }

                        int qty = entry.getValue();

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

        request.getRequestDispatcher("/view/cart.jsp")
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

            else {

                Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(SESSION_CART);

                if (cart == null) {
                    cart = new HashMap<>();
                }

                if ("add".equals(action)) {
                	
                    int currentQty = cart.getOrDefault(productId, 0);

                    if (prodotto != null) {
                        if (currentQty < prodotto.getStock()) {
                            cart.put(productId, currentQty + 1);
                        }
                    }
                }

                if ("delete".equals(action)) {
                	
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