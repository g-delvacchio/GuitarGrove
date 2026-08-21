<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, model.bean.*"
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
</head>

<body>

<jsp:include page="header.jsp"/>

<%
    // TUTTO ARRIVA DALLA SERVLET (NO LOGICA SESSIONE QUI)
    Indirizzo indirizzo = (Indirizzo) request.getAttribute("indirizzo");

    List<Map<String, Object>> items = (List<Map<String, Object>>) request.getAttribute("items");

    double totale = (Double) request.getAttribute("totale");
%>

<section class="checkout">

    <!-- BOTTONE TORNA INDIETRO -->
    <div class="back-container">
        <a href="<%=request.getContextPath()%>/Carrello">
            <button type="button">← Torna indietro</button>
        </a>
    </div>

    <h1>Checkout</h1>

    <!-- INDIRIZZO -->
    <div class="box">

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

    <!-- CARRELLO -->
    <div class="box">

        <h2>Riepilogo ordine</h2>

        <% if (items == null || items.isEmpty()) { %>

        <p>Carrello vuoto</p>

        <% } else { %>

        <table border="1">

            <tr>
                <th>Immagine</th>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo</th>
                <th>Subtotale</th>
            </tr>

            <%
                for (Map<String, Object> row : items) {

                    Prodotto p = (Prodotto) row.get("prodotto");
                    int qty = (Integer) row.get("quantita");
                    double subtotal = (Double) row.get("subtotal");
            %>

            <tr>
                <td><div class="product-image"><img src="" alt="Immagine prodotto"></div></td>
                <td><%= p.getNome() %></td>
                <td><%= qty %></td>
                <td><%= p.getPrezzo() %> €</td>
                <td><%= subtotal %> €</td>
            </tr>

            <% } %>

        </table>

        <% } %>

        <h3>Totale: € <%= totale %> + €10 di spedizione</h3>

    </div>

    <!-- PAGAMENTO -->
    <div class="box">

        <h2>Dati pagamento</h2>
        

    </div>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>