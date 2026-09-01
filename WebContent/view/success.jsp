<!DOCTYPE html>
<html lang="it">

<head>
    <title>Acquisto completato</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<section class="success">
    <div class="success-box">

        <h1>Acquisto completato con successo!</h1>

        <p>Grazie per il tuo ordine.</p>

        <a href="<%=request.getContextPath()%>/view/catalogo.jsp">
            Continua lo shopping
        </a>

    </div>
</section>

<jsp:include page="footer.jsp"/>

</body>
</html>