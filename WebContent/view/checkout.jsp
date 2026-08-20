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

<section class="checkout">

    <h1>Checkout</h1>

    <!-- CARRELLO -->
    <div class="box">

        <h2>Riepilogo ordine</h2>

        <% if (items == null || items.isEmpty()) { %>

        <p>Carrello vuoto</p>

    </div>

    <!-- PAGAMENTO -->
    <div class="box">

        <h2>Dati pagamento</h2>

    </div>

</section>

<jsp:include page="footer.jsp"/>

</body>
</html>