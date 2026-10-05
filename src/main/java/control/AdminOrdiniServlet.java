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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/AdminOrdiniServlet")
public class AdminOrdiniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * CONTROLLO ADMIN
         */
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


        /*
         * PARAMETRI DEI FILTRI
         */
        String dataDaParam = request.getParameter("dataDa");
        String dataAParam = request.getParameter("dataA");
        String clienteParam = request.getParameter("cliente");


        /*
         * CONVERSIONE DATE
         */
        LocalDate dataDa = null;
        LocalDate dataA = null;

        try {

            if (dataDaParam != null && !dataDaParam.isBlank()) {
                dataDa = LocalDate.parse(dataDaParam);
            }

            if (dataAParam != null && !dataAParam.isBlank()) {
                dataA = LocalDate.parse(dataAParam);
            }

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/AdminOrdiniServlet?error=date"
            );

            return;
        }


        /*
         * CONTROLLO INTERVALLO DATE
         */
        if (dataDa != null && dataA != null && dataDa.isAfter(dataA)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/AdminOrdiniServlet?error=interval"
            );

            return;
        }


        /*
         * CLIENTE SELEZIONATO
         */
        Integer clienteId = null;

        if (clienteParam != null && !clienteParam.isBlank()) {

            try {

                clienteId = Integer.parseInt(clienteParam);

            } catch (NumberFormatException e) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/AdminOrdiniServlet?error=cliente"
                );

                return;
            }
        }


        try {

            AcquistoDAO acquistoDAO = new AcquistoDAO();
            ProdottoAcquistatoDAO paDAO = new ProdottoAcquistatoDAO();
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            UtenteDAO utenteDAO = new UtenteDAO();


            /*
             * RECUPERA TUTTI GLI ORDINI
             */
            List<Acquisto> ordini = acquistoDAO.doRetrieveAll();


            /*
             * LISTA CHE CONTERRA' SOLO GLI ORDINI
             * CHE RISPETTANO I FILTRI
             */
            List<Map<String, Object>> result = new ArrayList<>();


            for (Acquisto ordine : ordini) {


                /*
                 * ===========================
                 * FILTRO CLIENTE
                 * ===========================
                 */
                if (clienteId != null
                        && ordine.getUserId() != clienteId) {

                    continue;
                }


                /*
                 * ===========================
                 * FILTRO DATA DA
                 * ===========================
                 */
                if (dataDa != null) {

                    LocalDateTime inizioGiorno =
                            dataDa.atStartOfDay();

                    if (ordine.getDataAcquisto()
                            .isBefore(inizioGiorno)) {

                        continue;
                    }
                }


                /*
                 * ===========================
                 * FILTRO DATA A
                 * ===========================
                 */
                if (dataA != null) {

                    LocalDateTime fineGiorno =
                            dataA.plusDays(1).atStartOfDay();

                    /*
                     * Se l'ordine è uguale o successivo
                     * all'inizio del giorno successivo,
                     * non rientra nell'intervallo.
                     */
                    if (!ordine.getDataAcquisto()
                            .isBefore(fineGiorno)) {

                        continue;
                    }
                }


                /*
                 * ===========================
                 * ORDINE VALIDO
                 * ===========================
                 */

                Map<String, Object> row = new HashMap<>();


                /*
                 * Recupero cliente
                 */
                Utente u =
                        utenteDAO.doRetrieveByKey(
                                ordine.getUserId()
                        );


                /*
                 * Recupero prodotti dell'ordine
                 */
                List<ProdottoAcquistato> prodottiAcquistati =
                        paDAO.doRetrieveByCond(
                                "order_id="
                                        + ordine.getOrderId()
                        );


                List<Map<String, Object>> dettagliProdotti =
                        new ArrayList<>();


                for (ProdottoAcquistato pa : prodottiAcquistati) {

                    Prodotto p =
                            prodottoDAO.doRetrieveByKey(
                                    pa.getProductId()
                            );

                    Map<String, Object> item =
                            new HashMap<>();

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


            /*
             * ===========================
             * LISTA CLIENTI PER SELECT
             * ===========================
             */
            List<Utente> utenti =
                    utenteDAO.doRetrieveAll();


            /*
             * DATI PASSATI ALLA JSP
             */
            request.setAttribute("ordini", result);
            request.setAttribute("utenti", utenti);


            /*
             * FORWARD
             */
            request.getRequestDispatcher(
                    "/WEB-INF/view/admin/ordini_admin.jsp"
            ).forward(request, response);


        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}