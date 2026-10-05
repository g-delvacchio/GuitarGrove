<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>
<%@ page import="model.bean.Indirizzo" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <title>GuitarGrove - Account</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/account.css">
    <script src="<%=request.getContextPath()%>/scripts/validate.js" defer></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<%
    Utente user = (Utente) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/Login");
        return;
    }

    Indirizzo indirizzo = (Indirizzo) session.getAttribute("indirizzo");

    String error = request.getParameter("error");
    String success = request.getParameter("success");

    boolean isAdmin = user.isAdmin();
%>

<section class="account">

    <h1>Benvenuto, <%= user.getNome() %> !</h1>

    <div style="margin: 15px 0;">
        <form action="<%=request.getContextPath()%>/VisualizzaOrdiniServlet" method="get">
            <button type="submit">
                Visualizza ordini
            </button>
        </form>
    </div>

    <div class="account-box">

        <h2>Dati utente</h2>

        <p><strong>Username:</strong> <%= user.getUsername() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>
        <p><strong>Nome:</strong> <%= user.getNome() %></p>
        <p><strong>Cognome:</strong> <%= user.getCognome() %></p>
        <p><strong>Telefono:</strong> <%= user.getTelefono() %></p>

    </div>

    <div class="account-box">

        <h2>Indirizzo</h2>

        <% if (indirizzo != null) { %>

        <p><strong>Paese:</strong> <%= indirizzo.getPaese() %></p>
        <p><strong>Città:</strong> <%= indirizzo.getCitta() %></p>
        <p><strong>CAP:</strong> <%= indirizzo.getCap() %></p>
        <p><strong>Via:</strong> <%= indirizzo.getVia() %></p>
        <p><strong>Civico:</strong> <%= indirizzo.getCivico() %></p>

        <% } else { %>
        <p>Nessun indirizzo registrato</p>
        <% } %>

    </div>

    <div class="account-box">

        <h2>Cambia password</h2>

        <% if (error != null) { %>
        <p style="color:red;">
            <% if (error.equals("wrongOld")) { %>Password attuale errata<% }
        else if (error.equals("match")) { %>Le password non coincidono<% }
        else if (error.equals("missing")) { %>Compila tutti i campi<% }
        else if (error.equals("password")) {%>Password non valida: minimo 8 caratteri, maiuscola, minuscola, numero e simbolo<%}
        else { %>Errore durante l'operazione<% } %>
        </p>
        <% } %>

        <% if (success != null) { %>
        <p style="color:green;">Password aggiornata con successo</p>
        <% } %>

        <form id="changePasswordForm"
              action="<%=request.getContextPath()%>/ChangePasswordServlet"
              method="post"
              novalidate
              onsubmit="return checkChangePassword(this);">

            <label for="oldPassword">
			    Vecchia password
			</label>
			
			<input type="password"
			       id="oldPassword"
			       name="oldPassword"
			       required
			       onchange="validateOldPassword()">
			
			<span id="errorOldPassword"
			      style="display:block;color:red;margin-bottom:10px;">
			</span>

            <label>Nuova password</label>
            <input type="password" name="newPassword" onchange="validateNewPassword()" required>

            <span id="errorNewPassword" style="display:block;color:red;margin-bottom:10px;"></span>

            <label>Conferma nuova password</label>
            <input type="password" name="confirmPassword" onchange="matchNewPassword()" required>

            <span id="errorConfirmPassword" style="display:block;color:red;margin-bottom:15px;"></span>

            <button type="submit">Aggiorna password</button>

        </form>

    </div>

    <div class="account-actions">

        <form action="<%=request.getContextPath()%>/LogoutServlet" method="post">
            <button type="submit">Logout</button>
        </form>

        <form action="<%=request.getContextPath()%>/DeleteAccountServlet" method="post"
              onsubmit="return confirm('Sei sicuro di voler eliminare questo account?');">
            <button type="submit" style="color:red;">Elimina Account</button>
        </form>

        <% if (isAdmin) { %>
        <form action="<%=request.getContextPath()%>/Admin" method="get">
            <button type="submit" style="background:#81A6C6;">
                Gestione ADMIN
            </button>
        </form>
        <% } %>

    </div>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>