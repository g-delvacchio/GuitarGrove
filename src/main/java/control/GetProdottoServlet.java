package control;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

@WebServlet("/ProdottoGuitarGrove")
public class GetProdottoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {

            int id = Integer.parseInt(idParam);

            ProdottoDAO dao = new ProdottoDAO();

            Prodotto prodotto = dao.doRetrieveByKey(id);

            if (prodotto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/view/prodotto.jsp").forward(request, response);

        } catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        	e.printStackTrace();
            throw new ServletException(e);
        }
    }
}