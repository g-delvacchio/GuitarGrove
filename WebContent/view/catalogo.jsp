

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.bean.Prodotto, java.util.*" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove - Catalogo</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/catalogo.css">

</head>

<body>

    <jsp:include page="header.jsp" />

    <main>
    <%
        Collection<Prodotto> prodotti = (Collection<Prodotto>) request.getAttribute("prodotti");
		
        if (prodotti == null) {
            RequestDispatcher dispatcher = application.getRequestDispatcher("/Catalogo");
            dispatcher.forward(request, response);
            return;
        }
    %>

    <section class="catalog">


        <div id="filtri">

            <form action="<%=request.getContextPath()%>/Catalogo" method="post">

                <label>Marca:</label>
                <select name="marca" id="marca">

                    <option value="" <%= (request.getParameter("marca") == null || request.getParameter("marca").equals("")) ? "selected" : "" %>>
                        Tutte
                    </option>

                    <option value="Yamaha" <%= "Yamaha".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Yamaha
                    </option>

                    <option value="Fender" <%= "Fender".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Fender
                    </option>

                    <option value="Gibson" <%= "Gibson".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Gibson
                    </option>

                    <option value="Roland" <%= "Roland".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Roland
                    </option>

                    <option value="Pearl" <%= "Pearl".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Pearl
                    </option>

                    <option value="Tama" <%= "Tama".equals(request.getParameter("marca")) ? "selected" : "" %>>
                        Tama
                    </option>

                </select>

                <label>Categoria:</label>
                <select name="categoria" id="categoria">
                    <option value="" <%= (request.getParameter("categoria") == null || request.getParameter("categoria").equals("")) ? "selected" : "" %>>
                        Tutte
                    </option>

                    <option value="Chitarre Elettriche" <%= "Chitarre Elettriche".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Chitarre Elettriche
                    </option>

                    <option value="Chitarre Acustiche" <%= "Chitarre Acustiche".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Chitarre Acustiche
                    </option>

                    <option value="Bassi" <%= "Bassi".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Bassi
                    </option>

                    <option value="Pedaliere" <%= "Pedaliere".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Pedaliere
                    </option>

                    <option value="Casse" <%= "Casse".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Casse
                    </option>

                    <option value="Accessori" <%= "Accessori".equals(request.getParameter("categoria")) ? "selected" : "" %>>
                        Accessori
                    </option>

                </select>
                <label>Prezzo min:</label>
                <input type="number" name="prezzoMin"
                       value="<%= request.getParameter("prezzoMin") != null ? request.getParameter("prezzoMin") : "0" %>">

                <label>Prezzo max:</label>
                <input type="number" name="prezzoMax"
                       value="<%= request.getParameter("prezzoMax") != null ? request.getParameter("prezzoMax") : "5000" %>">

                <input type="submit" value="Filtra">
            </form>

        </div>

        <!-- PRODOTTI -->
        <div class="product-grid">

            <%
                for (Prodotto p : prodotti) {
            %>

            <div class="product-card">


                <div class="product-image"><img src="" alt="Immagine prodotto"></div>

                <h3><%= p.getNome()%></h3>

                <p><%= p.getMarca() %></p>
                <p><%= p.getCategoria() %></p>

                <p class="price">€ <%= p.getPrezzo() %></p>

                    <a href="">
                    Vedi prodotto
                </a>

            </div>

            <% } %>

        </div>

    </section>

    </main>
    <jsp:include page="footer.jsp" />

</body>
</html>