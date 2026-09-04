<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Prodotto" %>

<!DOCTYPE html>
<html lang="it">

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
%>

<head>
    <meta charset="UTF-8">
    <title>GuitarGrove - <%= prodotto.getNome() %></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>

<body>

<jsp:include page="header.jsp" />

<!-- BOTTONE TORNA INDIETRO -->
<div class="back-container">
    <a href="<%=request.getContextPath()%>/view/catalogo.jsp">
        <button type="button">← Torna indietro</button>
    </a>
</div>

<section class="prodotto">

    <div class="product-container">

        <div class="product-image-box">
            <div class="product-image-p">
                <img src="<%=request.getContextPath()%>/images/products/<%= prodotto.getImmagine()%>" alt="Immagine prodotto">
            </div>
        </div>

        <div class="product-info">

            <h1><%= prodotto.getNome() %></h1>

            <p><strong>Marca:</strong> <%= prodotto.getMarca() %></p>

            <p><strong>Categoria:</strong> <%= prodotto.getCategoria() %></p>

            <p class="description">
                <%= prodotto.getDescrizione() %>
            </p>

            <h2 class="price">
                € <%= prodotto.getPrezzo() %>
            </h2>

            <% if (prodotto.getStock() > 0) { %>

            <form action="<%=request.getContextPath()%>/Carrello" method="post">

                <input type="hidden" name="action" value="add">
                <input type="hidden" name="id" value="<%= prodotto.getProductId() %>">

                <button type="submit" class="btn-add-cart">
                    Aggiungi al carrello
                </button>

            </form>

            <% } else { %>

            <p style="color:red; font-weight:bold;">
                Prodotto non disponibile
            </p>

            <% } %>

        </div>

    </div>

</section>

<jsp:include page="footer.jsp" />

</body>
</html>