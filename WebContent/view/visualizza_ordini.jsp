<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>I miei ordini - GuitarGrove</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<%
    List<Acquisto> ordini = (List<Acquisto>) request.getAttribute("ordini");

    Map<Integer, List<Map<String, Object>>> prodottiPerOrdine = (Map<Integer, List<Map<String, Object>>>) request.getAttribute("prodottiPerOrdine");
%>

<section style="padding:40px;">

    <!-- BOTTONE TORNA INDIETRO -->
    <div class="back-container">
        <a href="<%=request.getContextPath()%>/Account">
            <button type="button">← Torna indietro</button>
        </a>
    </div>

    <h1>I miei ordini</h1>

    <% if (ordini == null || ordini.isEmpty()) { %>

    <p>Non hai ancora effettuato ordini.</p>

    <% } else { %>

    <% for (Acquisto ordine : ordini) { %>

    <div style="border:1px solid #ccc; margin-bottom:20px; padding:15px;">

        <h3>Ordine #<%= ordine.getOrderId() %></h3>

        <p><strong>Totale:</strong> <%= ordine.getTotale() %> €</p>
        <p><strong>Spedizione:</strong> <%= ordine.getSpedizione() %> €</p>
        <p><strong>Stato:</strong> <%= ordine.getStato() %></p>
        <p><strong>Data di acquisto:</strong> <%= ordine.getDataAcquisto() %></p>

        <h4>Prodotti acquistati:</h4>

        <%
            List<Map<String, Object>> prodotti = prodottiPerOrdine.get(ordine.getOrderId());

            if (prodotti == null || prodotti.isEmpty()) {
        %>

        <p>Nessun prodotto trovato.</p>

        <%
        } else {
        %>

        <table border="1" style="width:100%; text-align:center;">

            <tr>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo</th>
                <th>Subtotale</th>
            </tr>

            <%
                for (Map<String, Object> row : prodotti) {

                    Prodotto p = (Prodotto) row.get("prodotto");
                    int qty = (Integer) row.get("quantita");
                    double prezzo = (Double) row.get("prezzo");
            %>

            <tr>
                <td><%= p.getNome() %></td>
                <td><%= qty %></td>
                <td><%= prezzo %> €</td>
                <td><%= (prezzo * qty) %> €</td>
            </tr>

            <% } %>

        </table>

        <% } %>

    </div>

    <% } %>

    <% } %>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>