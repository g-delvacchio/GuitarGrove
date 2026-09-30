package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Pagine")
public class PagineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pagina = request.getParameter("pagina");

        if ("chi-siamo".equals(pagina)) {

            request.getRequestDispatcher("/WEB-INF/view/chi_siamo.jsp")
                    .forward(request, response);

        } else if ("assistenza".equals(pagina)) {

            request.getRequestDispatcher("/WEB-INF/view/assistenza.jsp")
                    .forward(request, response);

        } else {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}