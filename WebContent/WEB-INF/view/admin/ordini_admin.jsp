<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.bean.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(
                request.getContextPath() + "/index.jsp"
        );
        return;
    }

    List<Map<String, Object>> ordini =
            (List<Map<String, Object>>)
                    request.getAttribute("ordini");

    List<Utente> utenti =
            (List<Utente>)
                    request.getAttribute("utenti");

    String dataDa =
            request.getParameter("dataDa");

    String dataA =
            request.getParameter("dataA");

    String cliente =
            request.getParameter("cliente");

    String error =
            request.getParameter("error");
%>


<!DOCTYPE html>
<html lang="it">

<head>

    <meta charset="UTF-8">

    <title>
        GuitarGrove - Ordini Admin
    </title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/ordini_admin.css">

</head>


<body>


<jsp:include page="../header.jsp"/>


<h1>Ordini effettuati</h1>

<div class="back-container">

    <a href="<%=request.getContextPath()%>/Admin">

        <button type="button">
            ← Torna indietro
        </button>

    </a>

</div>

<% if (error != null) { %>

    <p style="color:red; font-weight:bold;">

        <% if ("date".equals(error)) { %>

            Formato data non valido.

        <% } else if ("interval".equals(error)) { %>

            La data iniziale non può essere successiva
            alla data finale.

        <% } else if ("cliente".equals(error)) { %>

            Cliente non valido.

        <% } else { %>

            Errore durante il filtraggio degli ordini.

        <% } %>

    </p>

<% } %>

<div class="filtri-ordini">

    <h2>Filtra ordini</h2>


    <form action="<%=request.getContextPath()%>/AdminOrdiniServlet"
          method="get">

        <label for="dataDa">
            Data da:
        </label>

        <input type="date"
               id="dataDa"
               name="dataDa"
               value="<%= dataDa != null ? dataDa : "" %>">

        <label for="dataA">
            Data a:
        </label>

        <input type="date"
               id="dataA"
               name="dataA"
               value="<%= dataA != null ? dataA : "" %>">

        <label for="cliente">
            Cliente:
        </label>

        <select id="cliente"
                name="cliente">

            <option value="">
                Tutti i clienti
            </option>


            <% if (utenti != null) {

                for (Utente utente : utenti) {
            %>

                <option
                    value="<%= utente.getUserId() %>"

                    <%= String.valueOf(
                            utente.getUserId()
                        ).equals(cliente)
                            ? "selected"
                            : ""
                    %>>

                    <%= utente.getUsername() %>
                    -
                    <%= utente.getEmail() %>

                </option>

            <%
                }
            }
            %>

        </select>

        <button type="submit">
            Filtra
        </button>

        <a href="<%=request.getContextPath()%>/AdminOrdiniServlet">

            <button type="button">
                Azzera filtri
            </button>

        </a>


    </form>

</div>

<% if (ordini == null || ordini.isEmpty()) { %>


    <p class="empty">
        Nessun ordine trovato con i filtri selezionati.
    </p>


<% } else { %>

    <p>
        <strong>
            Ordini trovati:
        </strong>

        <%= ordini.size() %>
    </p>


    <div class="ordini-container">


        <% for (Map<String, Object> o : ordini) {

            Acquisto a =
                    (Acquisto) o.get("ordine");

            Utente utente =
                    (Utente) o.get("utente");

            List<Map<String, Object>> prodotti =
                    (List<Map<String, Object>>)
                            o.get("prodotti");
        %>


        <div class="ordine-card">

            <h3>
                Ordine #<%= a.getOrderId() %>
            </h3>

            <div class="ordine-info">


                <p>

                    <strong>
                        Utente:
                    </strong>

                    <%= utente.getUsername() %>

                </p>


                <p>

                    <strong>
                        Email:
                    </strong>

                    <%= utente.getEmail() %>

                </p>


                <p>

                    <strong>
                        Totale:
                    </strong>

                    € <%= String.format(
                            "%.2f",
                            a.getTotale()
                    ) %>

                </p>


                <p>

                    <strong>
                        Data di acquisto:
                    </strong>

                    <%= a.getDataAcquisto() %>

                </p>


                <p>

                    <strong>
                        Stato:
                    </strong>

                    <%= a.getStato() %>

                </p>


                <p>

                    <strong>
                        Pagamento:
                    </strong>

                    <%= a.getPagamento() %>

                </p>

                <p>

                    <strong>
                        Indirizzo di spedizione:
                    </strong>

                    <%= a.getViaSpedizione() %>

                    <%= a.getCivicoSpedizione() %>,

                    <%= a.getCapSpedizione() %>

                    <%= a.getCittaSpedizione() %>,

                    <%= a.getPaeseSpedizione() %>

                </p>


            </div>

            <div class="ordine-prodotti">


                <h4>
                    Prodotti:
                </h4>


                <% if (prodotti == null
                        || prodotti.isEmpty()) { %>


                    <p>
                        Nessun prodotto presente.
                    </p>


                <% } else { %>


                    <ul>


                        <% for (Map<String, Object> p : prodotti) {

                            Prodotto prodotto =
                                    (Prodotto)
                                            p.get("prodotto");

                            int quantita =
                                    (Integer)
                                            p.get("quantita");

                            double prezzo =
                                    (Double)
                                            p.get("prezzo");
                        %>


                        <li>

                            <%= prodotto.getNome() %>

                            -

                            Prezzo:

                            € <%= String.format(
                                    "%.2f",
                                    prezzo
                            ) %>

                            -

                            Qta:

                            <%= quantita %>

                        </li>


                        <% } %>


                    </ul>


                <% } %>


            </div>


        </div>


        <% } %>


    </div>


<% } %>



<jsp:include page="../footer.jsp"/>


</body>

</html>