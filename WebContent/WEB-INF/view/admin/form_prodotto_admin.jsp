<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Prodotto" %>
<%@ page import="model.bean.Utente" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    Prodotto prodotto =
            (Prodotto) request.getAttribute("prodotto");

    boolean modifica = prodotto != null;

    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="it">

<head>

    <meta charset="UTF-8">

    <title>
        <%= modifica ? "Modifica prodotto" : "Inserisci prodotto" %>
    </title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/styles/style.css">

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/styles/prodotti_admin.css">

</head>

<body>

<jsp:include page="../header.jsp"/>

<section class="admin-panel">

    <div class="back-container">

        <a href="<%=request.getContextPath()%>/AdminProdottiServlet">
            <button type="button">
                ← Torna ai prodotti
            </button>
        </a>

    </div>


    <h1>
        <%= modifica ? "Modifica prodotto" : "Inserisci nuovo prodotto" %>
    </h1>


    <% if (error != null) { %>

        <p style="color:red;">
            <%= error %>
        </p>

    <% } %>


    <form
        action="<%=request.getContextPath()%>/<%=
            modifica
            ? "UpdateProdottoAdminServlet"
            : "InsertProdottoAdminServlet"
        %>"
        method="post">


        <% if (modifica) { %>

            <input type="hidden"
                   name="id"
                   value="<%= prodotto.getProductId() %>">

            <input type="hidden"
                   name="action"
                   value="completo">

        <% } %>

        <label for="nome">
            Nome:
        </label>

        <input type="text"
               id="nome"
               name="nome"
               maxlength="100"
               required
               value="<%= modifica ? prodotto.getNome() : "" %>">

        <br><br>

        <label for="marca">
            Marca:
        </label>

        <input type="text"
               id="marca"
               name="marca"
               maxlength="100"
               required
               value="<%= modifica ? prodotto.getMarca() : "" %>">

        <br><br>

        <label for="modello">
            Modello:
        </label>

        <input type="text"
               id="modello"
               name="modello"
               maxlength="100"
               required
               value="<%= modifica ? prodotto.getModello() : "" %>">

        <br><br>

        <label for="descrizione">
            Descrizione:
        </label>

        <textarea id="descrizione"
                  name="descrizione"
                  rows="5"
                  cols="50"><%= modifica && prodotto.getDescrizione() != null
                          ? prodotto.getDescrizione()
                          : ""
                  %></textarea>

        <br><br>

        <label for="prezzo">
            Prezzo:
        </label>

        <input type="number"
               id="prezzo"
               name="prezzo"
               min="0"
               step="0.01"
               required
               value="<%= modifica ? prodotto.getPrezzo() : "" %>">

        <br><br>

        <label for="stock">
            Stock:
        </label>

        <input type="number"
               id="stock"
               name="stock"
               min="0"
               required
               value="<%= modifica ? prodotto.getStock() : "0" %>">

        <br><br>

        <label for="categoria">
            Categoria:
        </label>

        <select id="categoria"
                name="categoria"
                required>

            <option value="">
                Seleziona categoria
            </option>

            <option value="Chitarre Elettriche"
                <%= modifica &&
                    "Chitarre Elettriche".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Chitarre Elettriche
            </option>

            <option value="Chitarre Acustiche"
                <%= modifica &&
                    "Chitarre Acustiche".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Chitarre Acustiche
            </option>

            <option value="Bassi"
                <%= modifica &&
                    "Bassi".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Bassi
            </option>

            <option value="Tastiere"
                <%= modifica &&
                    "Tastiere".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Tastiere
            </option>

            <option value="Batterie"
                <%= modifica &&
                    "Batterie".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Batterie
            </option>

            <option value="Percussioni"
                <%= modifica &&
                    "Percussioni".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Percussioni
            </option>

            <option value="Amplificatori"
                <%= modifica &&
                    "Amplificatori".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Amplificatori
            </option>

            <option value="Accessori"
                <%= modifica &&
                    "Accessori".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Accessori
            </option>

            <option value="Effetti"
                <%= modifica &&
                    "Effetti".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Effetti
            </option>

            <option value="Microfoni"
                <%= modifica &&
                    "Microfoni".equals(prodotto.getCategoria())
                    ? "selected" : "" %>>
                Microfoni
            </option>

        </select>

        <br><br>

        <label for="attivo">
            Stato:
        </label>

        <select id="attivo"
                name="attivo"
                required>

            <option value="1"
                <%= !modifica || prodotto.isAttivo()
                    ? "selected" : "" %>>
                Attivo
            </option>

            <option value="0"
                <%= modifica && !prodotto.isAttivo()
                    ? "selected" : "" %>>
                Non attivo
            </option>

        </select>

        <br><br>

        <label for="immagine">
            Percorso immagine:
        </label>

        <input type="text"
               id="immagine"
               name="immagine"
               maxlength="255"
               required
               placeholder="es. fender/nuova_chitarra.jpeg"
               value="<%= modifica ? prodotto.getImmagine() : "" %>">

        <br><br>


        <button type="submit">

            <%= modifica
                ? "Salva modifiche"
                : "Inserisci prodotto"
            %>

        </button>

    </form>

</section>


<jsp:include page="../footer.jsp"/>

</body>

</html>