<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <title>Prodotti Admin</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>

<body>

<jsp:include page="../header.jsp"/>

<h1>Gestione Prodotti</h1>

<!--  TORNA INDIETRO -->
<div class="back-container">
    <a href="<%=request.getContextPath()%>/view/admin/admin.jsp">
        <button type="button">← Torna indietro</button>
    </a>
</div>

<% if (prodotti == null || prodotti.isEmpty()) { %>
<p>Nessun prodotto</p>
<% } else { %>

<table border="1">

    <tr>
        <th>Immagine</th>
        <th>Nome</th>
        <th>Marca</th>
        <th>Modello</th>
        <th>Prezzo</th>
        <th>Stock</th>
        <th>Attivo</th>
        <th>Azioni</th>
    </tr>

    <% for (Prodotto p : prodotti) { %>

    <tr>
        <td><div class="product-image"><img src="<%=request.getContextPath()%>/images/products/<%= p.getImmagine()%>" alt="Immagine prodotto"></div></td>
        <td><%= p.getNome() %></td>
        <td><%= p.getMarca() %></td>
        <td><%= p.getModello() %></td>
        <td><%= p.getPrezzo() %></td>

        <!-- STOCK -->
        <td>
            <form action="<%=request.getContextPath()%>/UpdateProdottoAdminServlet" method="post">
                <input type="hidden" name="id" value="<%= p.getProductId() %>">
                <input type="hidden" name="action" value="stock">

                <input type="number" name="stock" value="<%= p.getStock() %>" min="0">

                <button type="submit">Aggiorna</button>
            </form>
        </td>

        <!-- ATTIVO -->
        <td>
            <form action="<%=request.getContextPath()%>/UpdateProdottoAdminServlet" method="post">
                <input type="hidden" name="id" value="<%= p.getProductId() %>">
                <input type="hidden" name="action" value="attivo">

                <select name="attivo">
                    <option value="1" <%= p.isAttivo() ? "selected" : "" %>>Attivo</option>
                    <option value="0" <%= !p.isAttivo() ? "selected" : "" %>>Non attivo</option>
                </select>

                <button type="submit">Salva</button>
            </form>
        </td>

        <!-- AZIONI -->
        <td>

            <!-- ELIMINA PRODOTTO -->
            <form action="<%=request.getContextPath()%>/DeleteProdottoAdminServlet"
                  method="post"
                  onsubmit="return confirm('Sei sicuro di voler eliminare questo prodotto?');">

                <input type="hidden" name="id" value="<%= p.getProductId() %>">

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