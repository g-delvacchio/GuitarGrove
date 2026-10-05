<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    List<Prodotto> prodotti =
            (List<Prodotto>) request.getAttribute("prodotti");

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>


<!DOCTYPE html>
<html lang="it">

<head>

    <title>Prodotti Admin</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/styles/style.css">

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/styles/prodotti_admin.css">

</head>


<body>

<jsp:include page="../header.jsp"/>


<h1>Gestione Prodotti</h1>


<!-- ========================= -->
<!-- MESSAGGI -->
<!-- ========================= -->

<% if ("insert".equals(success)) { %>

    <p style="color:green;">
        Prodotto inserito con successo.
    </p>

<% } else if ("update".equals(success)) { %>

    <p style="color:green;">
        Prodotto modificato con successo.
    </p>

<% } %>


<% if (error != null) { %>

    <p style="color:red;">

        <% if ("notfound".equals(error)) { %>

            Prodotto non trovato.

        <% } else if ("format".equals(error)) { %>

            ID o valore non valido.

        <% } else if ("db".equals(error)) { %>

            Errore durante l'accesso al database.

        <% } else if ("action".equals(error)) { %>

            Operazione non valida.

        <% } else { %>

            Si è verificato un errore durante l'operazione.

        <% } %>

    </p>

<% } %>


<!-- ========================= -->
<!-- PULSANTI -->
<!-- ========================= -->

<div class="back-container">

    <a href="<%=request.getContextPath()%>/Admin">

        <button type="button">
            ← Torna indietro
        </button>

    </a>


    <a href="<%=request.getContextPath()%>/InsertProdottoAdminServlet">

        <button type="button">
            + Inserisci prodotto
        </button>

    </a>

</div>


<% if (prodotti == null || prodotti.isEmpty()) { %>

    <p>Nessun prodotto</p>

<% } else { %>

<table border="1">

    <tr>
        <th>Immagine</th>
        <th>Nome</th>
        <th>Marca</th>
        <th>Modello</th>
        <th>Prezzo</th>
        <th>Stock</th>
        <th>Attivo</th>
        <th>Azioni</th>
    </tr>

    <% for (Prodotto p : prodotti) { %>

    <tr>
        <td><div class="product-image"><img src="<%=request.getContextPath()%>/images/products/<%= p.getImmagine()%>" alt="Immagine prodotto"></div></td>
        <td><%= p.getNome() %></td>
        <td><%= p.getMarca() %></td>
        <td><%= p.getModello() %></td>
        <td><%= String.format("%.2f", p.getPrezzo()) %></td>

        <!-- STOCK -->
        <td>
            <form action="<%=request.getContextPath()%>/UpdateProdottoAdminServlet" method="post">
                <input type="hidden" name="id" value="<%= p.getProductId() %>">
                <input type="hidden" name="action" value="stock">

                <input type="number" name="stock" value="<%= p.getStock() %>" min="0">

                <button type="submit">Aggiorna</button>
            </form>
        </td>

        <!-- ATTIVO -->
        <td>
            <form action="<%=request.getContextPath()%>/UpdateProdottoAdminServlet" method="post">
                <input type="hidden" name="id" value="<%= p.getProductId() %>">
                <input type="hidden" name="action" value="attivo">

                <select name="attivo">
                    <option value="1" <%= p.isAttivo() ? "selected" : "" %>>Attivo</option>
                    <option value="0" <%= !p.isAttivo() ? "selected" : "" %>>Non attivo</option>
                </select>

                <button type="submit">Salva</button>
            </form>
        </td>

        <!-- AZIONI -->
		<td>
		
		    <!-- MODIFICA PRODOTTO -->
		
		    <a href="<%=request.getContextPath()%>/EditProdottoAdminServlet?id=<%=p.getProductId()%>">
		
		        <button type="button">
		            Modifica
		        </button>
		
		    </a>
		
		
		    <!-- ELIMINA PRODOTTO -->
		
		    <form
		        action="<%=request.getContextPath()%>/DeleteProdottoAdminServlet"
		        method="post"
		        onsubmit="return confirm('Sei sicuro di voler eliminare questo prodotto?');">
		
		        <input type="hidden"
		               name="id"
		               value="<%= p.getProductId() %>">
		
		        <button type="submit"
		                style="color:red;">
		            Elimina
		        </button>
		
		    </form>
		
		</td>
    </tr>

    <% } %>

</table>

<% } %>

<jsp:include page="../footer.jsp"/>

</body>
</html>