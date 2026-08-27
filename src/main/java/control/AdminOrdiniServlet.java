package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.bean.Acquisto;
import model.bean.ProdottoAcquistato;
import model.bean.Prodotto;

import model.dao.AcquistoDAO;
import model.dao.ProdottoAcquistatoDAO;
import model.dao.UtenteDAO;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/AdminOrdiniServlet")
public class AdminOrdiniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
            AcquistoDAO acquistoDAO = new AcquistoDAO();
            ProdottoAcquistatoDAO paDAO = new ProdottoAcquistatoDAO();
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            UtenteDAO utenteDAO = new UtenteDAO();

            List<Acquisto> ordini = acquistoDAO.doRetrieveAll();

            List<Map<String, Object>> result = new ArrayList<>();

            for (Acquisto ordine : ordini) {

                Map<String, Object> row = new HashMap<>();

                Utente u = utenteDAO.doRetrieveByKey(ordine.getUserId());

                List<ProdottoAcquistato> prodottiAcquistati = paDAO.doRetrieveByCond("order_id=" + ordine.getOrderId());

                List<Map<String, Object>> dettagliProdotti = new ArrayList<>();

                for (ProdottoAcquistato pa : prodottiAcquistati) {

                    Prodotto p = prodottoDAO.doRetrieveByKey(pa.getProductId());

                    Map<String, Object> item = new HashMap<>();
                    item.put("prodotto", p);
                    item.put("quantita", pa.getQuantita());
                    item.put("prezzo", pa.getPrezzo());

                    dettagliProdotti.add(item);
                }

                row.put("ordine", ordine);
                row.put("utente", u);
                row.put("prodotti", dettagliProdotti);

                result.add(row);
            }

            request.setAttribute("ordini", result);

            request.getRequestDispatcher("/view/admin/ordini_admin.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}