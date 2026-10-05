package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Prodotto;
import model.bean.Utente;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/InsertProdottoAdminServlet")
public class InsertProdottoAdminServlet extends HttpServlet {

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

        request.getRequestDispatcher(
                "/WEB-INF/view/admin/form_prodotto_admin.jsp"
        ).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request,
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

        try {

            String nome = request.getParameter("nome");
            String marca = request.getParameter("marca");
            String modello = request.getParameter("modello");
            String descrizione = request.getParameter("descrizione");
            String categoria = request.getParameter("categoria");
            String immagine = request.getParameter("immagine");

            String prezzoParam = request.getParameter("prezzo");
            String stockParam = request.getParameter("stock");
            String attivoParam = request.getParameter("attivo");

            if (nome == null || nome.trim().isEmpty()
                    || marca == null || marca.trim().isEmpty()
                    || modello == null || modello.trim().isEmpty()
                    || categoria == null || categoria.trim().isEmpty()
                    || immagine == null || immagine.trim().isEmpty()
                    || prezzoParam == null
                    || stockParam == null
                    || attivoParam == null) {

                request.setAttribute("error", "Compila tutti i campi obbligatori");

                request.getRequestDispatcher(
                        "/WEB-INF/view/admin/form_prodotto_admin.jsp"
                ).forward(request, response);

                return;
            }

            double prezzo = Double.parseDouble(prezzoParam);
            int stock = Integer.parseInt(stockParam);

            if (prezzo < 0 || stock < 0) {

                request.setAttribute(
                        "error",
                        "Prezzo e stock non possono essere negativi"
                );

                request.getRequestDispatcher(
                        "/WEB-INF/view/admin/form_prodotto_admin.jsp"
                ).forward(request, response);

                return;
            }

            Prodotto p = new Prodotto();

            p.setNome(nome.trim());
            p.setMarca(marca.trim());
            p.setModello(modello.trim());

            if (descrizione != null) {
                p.setDescrizione(descrizione.trim());
            }

            p.setPrezzo(prezzo);
            p.setStock(stock);
            p.setCategoria(categoria.trim());
            p.setAttivo("1".equals(attivoParam));
            p.setImmagine(immagine.trim());

            ProdottoDAO dao = new ProdottoDAO();

            dao.doSave(p);

            response.sendRedirect(
                    request.getContextPath()
                            + "/AdminProdottiServlet?success=insert"
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Prezzo o stock non validi"
            );

            request.getRequestDispatcher(
                    "/WEB-INF/view/admin/form_prodotto_admin.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(e);
        }
    }
}