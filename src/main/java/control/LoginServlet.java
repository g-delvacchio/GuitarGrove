package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Utente;
import model.bean.ProdottoCarrello;
import model.dao.UtenteDAO;
import model.dao.ProdottoCarrelloDAO;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()){
            request.setAttribute("error","Compila tutti i campi");
            request.getRequestDispatcher("/view/login.jsp").forward(request,response);
            return;
        }

        try {
            UtenteDAO dao = new UtenteDAO();
            Utente user = dao.doRetrieveByEmail(email);

            if(user == null){
                request.setAttribute("error", "Email errata");
                request.getRequestDispatcher("/view/login.jsp").forward(request,response);
                return;
            }

            Map<Integer,Integer> sessionCart = null;
            HttpSession oldSession = request.getSession(false);

            if(oldSession != null){

                sessionCart = (Map<Integer,Integer>) oldSession.getAttribute("sessionCart");

                oldSession.invalidate();
            }

            HttpSession session = request.getSession(true);

            session.setAttribute("user", user);
            session.setAttribute("isLogged", true);
            session.setAttribute("isAdmin", user.isAdmin());

            if(sessionCart != null && !sessionCart.isEmpty()){

                ProdottoCarrelloDAO cartDao = new ProdottoCarrelloDAO();
                for(Map.Entry<Integer,Integer> entry : sessionCart.entrySet()){

                    int productId = entry.getKey();
                    int qty = entry.getValue();
                    ProdottoCarrello pc = cartDao.doRetrieveByKey(user.getUserId(), productId);

                    if(pc == null){
                        pc = new ProdottoCarrello();
                        pc.setUserId(user.getUserId());
                        pc.setProductId(productId);
                        pc.setQuantita(qty);
                    }else{
                        pc.setQuantita(pc.getQuantita()+qty);
                    }
                    cartDao.doSaveOrUpdate(pc);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Account");

        }catch(SQLException e){
            e.printStackTrace();
            request.setAttribute("error","Errore database");
            request.getRequestDispatcher("/view/login.jsp").forward(request,response);
        }
    }
}