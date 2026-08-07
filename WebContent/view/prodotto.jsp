<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.bean.Prodotto" %>

<!DOCTYPE html>
<html>

<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
%>

<head>
<meta charset="UTF-8">
<title>GuitarGrove - <%= prodotto.getNome() %></title>
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
                <img src="" alt="Immagine prodotto">
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

        </div>

    </div>

</section>

<jsp:include page="footer.jsp" />

</body>
</html>