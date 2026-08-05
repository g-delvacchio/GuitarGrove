package control;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;


@WebServlet("/Catalogo")
public class CatalogoFilter extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String categoria = request.getParameter("categoria");
        String marca = request.getParameter("marca");
        String prezzoMin = request.getParameter("prezzoMin");
        String prezzoMax = request.getParameter("prezzoMax");

        //boolean categAll = false;

        if (prezzoMin == null || prezzoMin.isBlank())
            prezzoMin = "0";

        if (prezzoMax == null || prezzoMax.isBlank())
            prezzoMax = "999999";

        if (marca == null)
            marca = "";

        if (categoria == null)
            categoria = "";


        ProdottoDAO dao = new ProdottoDAO();
        try {

            Collection<Prodotto> prodotti = dao.doRetrieveByFilter(
                    Double.parseDouble(prezzoMin),
                    Double.parseDouble(prezzoMax),
                    marca,
                    categoria
            );

            request.setAttribute("prodotti", prodotti);

            request.getRequestDispatcher("/jsp/catalogo.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
