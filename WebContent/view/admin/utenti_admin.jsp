<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    List<Utente> utenti = (List<Utente>) request.getAttribute("utenti");
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <title>GuitarGrove - Utenti Admin</title>
</head>

<body>

<jsp:include page="../header.jsp"/>

<h1>Gestione Utenti</h1>

<!-- TORNA INDIETRO -->
<div class="back-container">
    <a href="<%=request.getContextPath()%>/view/admin/admin.jsp">
        <button type="button">← Torna indietro</button>
    </a>
</div>

<% if (utenti == null || utenti.isEmpty()) { %>
<p>Nessun utente</p>
<% } else { %>

<table border="1">

    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Nome</th>
        <th>Cognome</th>
        <th>Telefono</th>
        <th>Data creazione</th>
        <th>Ruolo</th>
        <th>Azioni</th>
    </tr>

    <% for (Utente us : utenti) { %>

    <tr>
        <td><%= us.getUsername() %></td>
        <td><%= us.getEmail() %></td>
        <td><%= us.getNome() %></td>
        <td><%= us.getCognome() %></td>
        <td><%= us.getTelefono() %></td>
        <td><%= us.getCreatedAt() %></td>
        <td><%= us.isAdmin() ? "ADMIN" : "USER" %></td>

        <td>

            <form action="" method="post">
                <input type="hidden" name="id" value="<%= us.getUserId() %>">
                <button type="submit" style="color:red;">
                    Elimina
                </button>
            </form>

        </td>
    </tr>

    <% } %>

</table>

<% } %>

<jsp:include page="../footer.jsp"/>

</body>
</html>