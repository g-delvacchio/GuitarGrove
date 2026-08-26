<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    List<Map<String, Object>> ordini = (List<Map<String, Object>>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>GuitarGrove - Ordini Admin</title>
</head>

<body>

<jsp:include page="../header.jsp"/>

<h1>Ordini effettuati</h1>

<!-- BOTTONE TORNA INDIETRO -->
<div class="back-container">
    <a href="<%=request.getContextPath()%>/view/admin/admin.jsp">
        <button type="button">← Torna indietro</button>
    </a>
</div>

<% if (ordini == null || ordini.isEmpty()) { %>

<p class="empty">Nessun ordine presente</p>

<% } else { %>

<div class="ordini-container">

    <% for (Map<String, Object> o : ordini) {

        Acquisto a = (Acquisto) o.get("ordine");
        Utente utente = (Utente) o.get("utente");
        List<Map<String, Object>> prodotti = (List<Map<String, Object>>) o.get("prodotti");
    %>

    <div class="ordine-card">

        <h3>Ordine #<%= a.getOrderId() %></h3>

        <div class="ordine-info">
            <p><strong>Utente:</strong> <%= utente.getUsername() %></p>
            <p><strong>Totale:</strong> € <%= a.getTotale() %></p>
            <p><strong>Data di acquisto:</strong> € <%= a.getDataAcquisto() %></p>
        </div>

        <div class="ordine-prodotti">
            <h4>Prodotti:</h4>

            <ul>
                <% for (Map<String, Object> p : prodotti) { %>

                <li>
                    <%= ((Prodotto)p.get("prodotto")).getNome() %>
                    - Prezzo: <%= ((Prodotto)p.get("prodotto")).getPrezzo() %>
                    - Qta: <%= p.get("quantita") %>
                </li>

                <% } %>
            </ul>
        </div>

    </div>

    <% } %>

</div>

<% } %>

<jsp:include page="../footer.jsp"/>

</body>
</html>