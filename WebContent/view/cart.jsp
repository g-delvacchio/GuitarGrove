<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <title>Carrello - GuitarGrvoe</title>
</head>

<body>

    <jsp:include page="header.jsp"/>
    <section class="cart">
        <h1>Carrello</h1>

        <%
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.getAttribute("items");

            double totale = (Double) request.getAttribute("totale");
        %>

        <% if (items == null || items.isEmpty()) { %>

        <p>Carrello vuoto</p>

        <% } else { %>

        <table>
            <tr>
                <th>Immagine</th>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo</th>
                <th>Subtotale</th>
                <th>Azioni</th>
            </tr>

            <%
                for (Map<String, Object> row : items) {

                    Prodotto p = (Prodotto) row.get("prodotto");
                    int qty = (Integer) row.get("quantita");
                    double subtotal = (Double) row.get("subtotal");
            %>

            <tr>
                <td><%= p.getNome() %></td>
                <td><%= qty %></td>
                <td><%= p.getPrezzo() %> €</td>
                <td><%= subtotal %> €</td>

                <td>
                    <form action="<%=request.getContextPath()%>/Carrello" method="post">
                        <input type="hidden" name="id" value="<%= p.getProductId() %>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit">-</button>
                    </form>

                    <form action="<%=request.getContextPath()%>/Carrello" method="post">
                        <input type="hidden" name="id" value="<%= p.getProductId() %>">
                        <input type="hidden" name="action" value="add">
                        <button type="submit">+</button>
                    </form>
                </td>
            </tr>

            <% } %>

        </table>

        <h2>Totale: <%= totale %> €</h2>

        <% } %>

        <%
            Utente user = (Utente) session.getAttribute("user");

            if (user == null) {
        %>

        <p style="color:red">Devi effettuare il login per procedere al checkout</p>

        <a href="<%=request.getContextPath()%>/view/login.jsp">
            Login
        </a>

        <%
        } else if (items == null || items.isEmpty()) {
        %>

        <p>Il tuo carrello è vuoto.</p>

        <a href="<%=request.getContextPath()%>/view/catalogo.jsp">
            Vai al catalogo
        </a>

        <%
        } else {
        %>
        
        <a href="<%=request.getContextPath()%>/Checkout">
            Procedi al checkout
        </a>

        <%
            }
        %>
    </section>
    <jsp:include page="footer.jsp"/>

</body>
</html>