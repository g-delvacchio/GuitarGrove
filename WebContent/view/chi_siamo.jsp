<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>Chi siamo - GuitarGrove</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<main>

    <h1>Chi siamo</h1>

    <p>
        <strong>GuitarGrove</strong> è un'e-commerce specializzato nella vendita di strumenti musicali,
        accessori e attrezzature per musicisti di ogni livello.
    </p>

    <p>
        Il nostro obiettivo è quello di offrire prodotti di qualità, prezzi competitivi e un’esperienza
        d’acquisto semplice e veloce.
    </p>

    <p>
        Siamo un team appassionato di musica e tecnologia, sempre pronto a supportare i nostri clienti
        nella scelta dello strumento perfetto.
    </p>

    <h2>La nostra missione</h2>

    <p>
        Rendere la musica accessibile a tutti, dai principianti ai professionisti.
    </p>

</main>

<jsp:include page="footer.jsp"/>

</body>
</html>