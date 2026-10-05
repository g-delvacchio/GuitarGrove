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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );
            return;
        }

        Utente user =
                (Utente) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );
            return;
        }

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            String action =
                    request.getParameter("action");

            if (action == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/AdminProdottiServlet?error=action"
                );

                return;
            }

            ProdottoDAO dao = new ProdottoDAO();

            Prodotto p =
                    dao.doRetrieveByKey(id);

            if (p == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/AdminProdottiServlet?error=notfound"
                );

                return;
            }


            switch (action) {


                /*
                 * MODIFICA VELOCE STOCK
                 */
                case "stock":

                    int stock =
                            Integer.parseInt(
                                    request.getParameter("stock")
                            );

                    if (stock < 0) {
                        stock = 0;
                    }

                    p.setStock(stock);

                    dao.doSaveOrUpdate(p);

                    break;


                /*
                 * MODIFICA VELOCE STATO
                 */
                case "attivo":

                    int attivo =
                            Integer.parseInt(
                                    request.getParameter("attivo")
                            );

                    p.setAttivo(attivo == 1);

                    dao.doSaveOrUpdate(p);

                    break;


                /*
                 * MODIFICA COMPLETA PRODOTTO
                 */
                case "completo":

                    String nome =
                            request.getParameter("nome");

                    String marca =
                            request.getParameter("marca");

                    String modello =
                            request.getParameter("modello");

                    String descrizione =
                            request.getParameter("descrizione");

                    String categoria =
                            request.getParameter("categoria");

                    String immagine =
                            request.getParameter("immagine");

                    String prezzoParam =
                            request.getParameter("prezzo");

                    String stockParam =
                            request.getParameter("stock");

                    String attivoParam =
                            request.getParameter("attivo");


                    /*
                     * VALIDAZIONE CAMPI
                     */
                    if (nome == null || nome.trim().isEmpty()
                            || marca == null || marca.trim().isEmpty()
                            || modello == null || modello.trim().isEmpty()
                            || categoria == null || categoria.trim().isEmpty()
                            || immagine == null || immagine.trim().isEmpty()
                            || prezzoParam == null
                            || stockParam == null
                            || attivoParam == null) {

                        request.setAttribute(
                                "error",
                                "Compila tutti i campi obbligatori"
                        );

                        request.setAttribute(
                                "prodotto",
                                p
                        );

                        request.getRequestDispatcher(
                                "/WEB-INF/view/admin/form_prodotto_admin.jsp"
                        ).forward(request, response);

                        return;
                    }


                    double prezzo =
                            Double.parseDouble(prezzoParam);

                    int nuovoStock =
                            Integer.parseInt(stockParam);


                    if (prezzo < 0 || nuovoStock < 0) {

                        request.setAttribute(
                                "error",
                                "Prezzo e stock non possono essere negativi"
                        );

                        request.setAttribute(
                                "prodotto",
                                p
                        );

                        request.getRequestDispatcher(
                                "/WEB-INF/view/admin/form_prodotto_admin.jsp"
                        ).forward(request, response);

                        return;
                    }


                    /*
                     * AGGIORNAMENTO BEAN
                     */

                    p.setNome(nome.trim());

                    p.setMarca(marca.trim());

                    p.setModello(modello.trim());

                    if (descrizione != null) {
                        p.setDescrizione(
                                descrizione.trim()
                        );
                    }

                    p.setPrezzo(prezzo);

                    p.setStock(nuovoStock);

                    p.setCategoria(
                            categoria.trim()
                    );

                    p.setAttivo(
                            "1".equals(attivoParam)
                    );

                    p.setImmagine(
                            immagine.trim()
                    );


                    /*
                     * UPDATE DATABASE
                     */
                    dao.doSaveOrUpdate(p);

                    break;


                default:

                    response.sendRedirect(
                            request.getContextPath()
                                    + "/AdminProdottiServlet?error=action"
                    );

                    return;
            }


        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/AdminProdottiServlet?error=format"
            );

            return;

        } catch (SQLException e) {

            throw new ServletException(e);
        }


        response.sendRedirect(
                request.getContextPath()
                        + "/AdminProdottiServlet?success=update"
        );
    }
}