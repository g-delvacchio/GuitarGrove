package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String q = request.getParameter("q");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (q == null || q.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        try {
            ProdottoDAO dao = new ProdottoDAO();
            List<Prodotto> list = dao.searchByName(q);

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (int i = 0; i < list.size(); i++) {

                Prodotto p = list.get(i);

                json.append("{")
                        .append("\"id\":").append(p.getProductId()).append(",")
                        .append("\"nome\":\"").append(escape(p.getNome())).append("\",")
                        .append("\"marca\":\"").append(escape(p.getMarca())).append("\"")
                        .append("}");

                if (i < list.size() - 1) json.append(",");
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            response.getWriter().write("[]");
        }
    }

    // evita problemi con " e caratteri speciali
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}