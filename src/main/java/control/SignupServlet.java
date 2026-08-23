package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.bean.Indirizzo;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/Signup")
public class SignupServlet extends HttpServlet {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";
    private static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,}$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final String CAP_REGEX = "^[0-9]{5}$";
    private static final String CIVICO_REGEX = "^[0-9]{1,5}[A-Za-z]?$";
    private static final String PLACE_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]{2,50}$";

    private void error(HttpServletRequest request, HttpServletResponse response, String messaggio)
            throws ServletException, IOException {
        request.setAttribute("error", messaggio);
        request.getRequestDispatcher("/view/signup.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String conferma = request.getParameter("conferma_password");

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String telefono = request.getParameter("telefono");

        String paese = request.getParameter("paese");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String via = request.getParameter("via");
        String civico = request.getParameter("civico");

        try {
            /*CONTROLLO CAMPI VUOTI*/
            if(username == null ||
                    email == null ||
                    password == null ||
                    conferma == null ||
                    nome == null ||
                    cognome == null ||
                    telefono == null ||
                    paese == null ||
                    citta == null ||
                    cap == null ||
                    via == null ||
                    civico == null) {

                error(request,response, "Compila tutti i campi");
                return;
            }

            /*RIMOZIONE SPAZI*/
            username = username.trim();
            email = email.trim();
            nome = nome.trim();
            cognome = cognome.trim();
            telefono = telefono.trim();

            paese = paese.trim();
            citta = citta.trim();
            cap = cap.trim();
            via = via.trim();
            civico = civico.trim();

            /*VALIDAZIONE DATI*/
            if(!username.matches(USERNAME_REGEX)) {
                error(request,response, "Username non valido");
                return;
            }
            if(!nome.matches(NAME_REGEX)) {
                error(request,response, "Nome non valido");
                return;
            }
            if(!cognome.matches(NAME_REGEX)) {
                error(request,response, "Cognome non valido");
                return;
            }
            if(!email.matches(EMAIL_REGEX)) {
                error(request,response, "Email non valida");
                return;
            }
            if(!password.matches(PASSWORD_REGEX)) {
                error(request,response, "Password non valida: minimo 8 caratteri, maiuscola, minuscola, numero e simbolo");
                return;
            }
            if(!password.equals(conferma)) {
                error(request,response, "Le password non coincidono");
                return;
            }
            if(!telefono.matches(PHONE_REGEX)) {
                error(request,response, "Telefono non valido");
                return;
            }
            if(!paese.matches(PLACE_REGEX)) {
                error(request,response, "Paese non valido");
                return;
            }
            if(!citta.matches(PLACE_REGEX)) {
                error(request,response, "Città non valida");
                return;
            }
            if(!cap.matches(CAP_REGEX)) {
                error(request,response, "CAP non valido");
                return;
            }
            if(via.length() < 2) {
                error(request,response, "Via non valida");
                return;
            }
            if(!civico.matches(CIVICO_REGEX)) {
                error(request,response, "Civico non valido");
                return;
            }

            /*CONTROLLO DATABASE*/
            UtenteDAO utenteDAO = new UtenteDAO();
            if(utenteDAO.existsUsername(username)) {
                error(request,response, "Username già esistente");
                return;
            }
            if(utenteDAO.existsEmail(email)) {
                error(request,response, "Email già registrata");
                return;
            }

            /*CREAZIONE UTENTE*/
            Utente utente = new Utente();
            utente.setUsername(username);
            utente.setEmail(email);
            utente.setPasswordHash(
                    HelperClass.toHash(password)
            );
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setTelefono(telefono);
            utente.setAdmin(false);
            utenteDAO.doSave(utente);

            /*CREAZIONE INDIRIZZO*/
            Indirizzo indirizzo = new Indirizzo();
            indirizzo.setUserId(
                    utente.getUserId()
            );
            indirizzo.setPaese(paese);
            indirizzo.setCitta(citta);
            indirizzo.setCap(cap);
            indirizzo.setVia(via);
            indirizzo.setCivico(civico);
            IndirizzoDAO indirizzoDAO =
                    new IndirizzoDAO();
            indirizzoDAO.doSave(indirizzo);

            /*REDIRECT LOGIN*/
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");

        } catch(SQLException e) {
            e.printStackTrace();
            error(request,response, "Errore durante la registrazione");
        }

    }

}