<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
</head>

<%
    model.bean.Utente u = (model.bean.Utente) session.getAttribute("user");
%>

<header>

    <div class="top-header">

        <div class="logo-container">
            <div class="logo">
                <a href="<%=request.getContextPath()%>/index.jsp">
                    <img src="" alt="GuitarGrove" class="logo-img">
                </a>
            </div>
        </div>

        <div class="user-actions">

            <a href="<%=request.getContextPath()%>/<%= (u != null ? "Account" : "view/login.jsp") %>"
               class="icon-user">

                <img src="" alt="Account">
            </a>

            <a href="<%=request.getContextPath()%>/Carrello"
               class="icon-cart">

                <img src="" alt="Carrello">
            </a>

        </div>

    </div>

    <div class="search-bar-container">

        <button class="menu-button" id="openMenu">☰</button>

        <form action="<%=request.getContextPath()%>/view/catalogo.jsp"
              method="GET"
              class="search-form"
              id="searchForm">

            <label for="searchInput"></label>
            <input type="text"
                   id="searchInput"
                   name="q"
                   placeholder="Cerca strumenti, accessori e marche...">

            <span>&nbsp;</span>

            <button type="submit">Cerca</button>

        </form>
    </div>

    <div id="sidebar" class="sidebar">

        <button class="close-btn" id="closeMenu">✕</button>

        <h3>Categorie</h3>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Elettriche">
            <img src="">
            Chitarre Elettriche
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Acustiche">
            <img src="">
            Chitarre Acustiche
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Bassi">
            <img src="">
            Bassi
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Tastiere">
            <img src="">
            Tastiere
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Batterie">
            <img src="">
            Batterie
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Percussioni">
            <img src="">
            Percussioni
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Amplificatori">
            <img src="">
            Amplificatori
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Accessori">
            <img src="">
            Accessori
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Effetti">
            <img src="">
            Effetti
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Microfoni">
            <img src="">
            Microfoni
        </a>

    </div>

    <div id="overlay" class="overlay"></div>

</header>