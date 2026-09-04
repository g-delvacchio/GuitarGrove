<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon">
    <title>GuitarGrove</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/header.css">
    <script src="<%=request.getContextPath()%>/scripts/menu.js" defer></script>
</head>

<%
    model.bean.Utente u = (model.bean.Utente) session.getAttribute("user");
%>

<header>

    <!-- contextPath JS -->
    <script>
        const contextPath = "<%=request.getContextPath()%>";
    </script>

    <div class="top-header">

        <!-- LOGO -->
        <div class="logo-container">
            <div class="logo">
                <a href="<%=request.getContextPath()%>/index.jsp">
                    <img src="<%=request.getContextPath()%>/images/logo.png" alt="GuitarGrove" class="logo-img">
                </a>
            </div>
        </div>

        <!-- USER ICONS -->
        <div class="user-actions">

            <!-- ACCOUNT / LOGIN -->
            <a href="<%=request.getContextPath()%>/<%= (u != null ? "Account" : "view/login.jsp") %>"
               class="icon-user">

                <img src="<%=request.getContextPath()%>/images/profilo.png" alt="Account">
            </a>

            <!-- CART -->
            <a href="<%=request.getContextPath()%>/Carrello"
               class="icon-cart">

                <img src="<%=request.getContextPath()%>/images/carrello.png" alt="Carrello">
            </a>

        </div>

    </div>

    <!-- SEARCH BAR -->
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

            <!-- AJAX suggestions -->
            <div id="suggestions" class="suggestions"></div>

        </form>
    </div>

    <!-- SIDEBAR -->
    <div id="sidebar" class="sidebar">

        <button class="close-btn" id="closeMenu">✕</button>

        <h3>Categorie</h3>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Elettriche">
            <img src="<%=request.getContextPath()%>/images/categorie/chitarra_elettrica.jpeg">
            Chitarre Elettriche
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Acustiche">
            <img src="<%=request.getContextPath()%>/images/categorie/chitarra_acustica.jpeg">
            Chitarre Acustiche
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Bassi">
            <img src="<%=request.getContextPath()%>/images/categorie/basso.jpeg">
            Bassi
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Tastiere">
            <img src="<%=request.getContextPath()%>/images/categorie/tastiera.jpeg">
            Tastiere
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Batterie">
            <img src="<%=request.getContextPath()%>/images/categorie/batteria.jpeg">
            Batterie
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Percussioni">
            <img src="<%=request.getContextPath()%>/images/categorie/percussioni.jpeg">
            Percussioni
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Amplificatori">
            <img src="<%=request.getContextPath()%>/images/categorie/amplificatore.jpeg">
            Amplificatori
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Accessori">
            <img src="<%=request.getContextPath()%>/images/categorie/accessori.jpeg">
            Accessori
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Effetti">
            <img src="<%=request.getContextPath()%>/images/categorie/effetti.jpeg">
            Effetti
        </a>

        <a href="<%=request.getContextPath()%>/Catalogo?categoria=Microfoni">
            <img src="<%=request.getContextPath()%>/images/categorie/microfono.jpeg">
            Microfoni
        </a>

    </div>

    <div id="overlay" class="overlay"></div>

</header>