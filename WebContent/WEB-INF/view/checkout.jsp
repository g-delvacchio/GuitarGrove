<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, model.bean.*"
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/checkout.css">
    <script src="<%=request.getContextPath()%>/scripts/validate.js" defer></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<%
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

	<form id="checkoutForm"
      action="<%=request.getContextPath()%>/CheckoutControl"
      method="post"
      onsubmit="return checkCheckout(this)">
      
      <div class="box">

		    <h2>Indirizzo di spedizione</h2>
		
		    <label for="paese">Paese:</label>
		    <input type="text"
		           id="paese"
		           name="paese"
		           required
		           oninput="validatePaese()">
		    <span id="errorPaese"></span>
		    <br>
		
		    <label for="citta">Città:</label>
		    <input type="text"
		           id="citta"
		           name="citta"
		           required
		           oninput="validateCitta()">
		    <span id="errorCitta"></span>
		    <br>
		
		    <label for="cap">CAP:</label>
		    <input type="text"
		           id="cap"
		           name="cap"
		           required
		           maxlength="10"
		           oninput="validateCAP()">
		    <span id="errorCAP"></span>
		    <br>
		
		    <label for="via">Via:</label>
		    <input type="text"
		           id="via"
		           name="via"
		           required
		           oninput="validateVia()">
		    <span id="errorVia"></span>
		    <br>
		
		    <label for="civico">Civico:</label>
		    <input type="text"
		           id="civico"
		           name="civico"
		           required
		           maxlength="10"
		           oninput="validateCivico()">
		    <span id="errorCivico"></span>
		    <br>
		
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
                <td><div class="product-image"><img src="<%=request.getContextPath()%>/images/products/<%= p.getImmagine()%>" alt="Immagine prodotto"></div></td>
                <td><%= p.getNome() %></td>
                <td><%= qty %></td>
                <td><%= String.format("%.2f", p.getPrezzo()) %> €</td>
                <td><%= String.format("%.2f", subtotal) %> €</td>
            </tr>

            <% } %>

        </table>

        <% } %>

        <h3>Totale: € <%= String.format("%.2f", totale) %> + €10 di spedizione</h3>

    </div>

    <!-- PAGAMENTO -->
    <div class="box">

        <h2>Dati pagamento</h2>

            <label>Numero carta</label>
            <input type="text"
                   id="cardNumber"
                   name="cardNumber"
                   maxlength="16"
                   required
                   oninput="validateCardNumber()">
            <span id="errorCardNumber"></span><br>

            <label>Scadenza</label>
            <input type="date"
                   id="expiry"
                   name="expiry"
                   required
                   oninput="validateExpiry()">
            <span id="errorExpiry"></span><br>

            <label>CVV</label>
            <input type="text"
                   id="cvv"
                   name="cvv"
                   maxlength="3"
                   required
                   oninput="validateCVV()">
            <span id="errorCVV"></span><br>

         <button type="submit">Acquista</button>

	</div>
        
    </form>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>