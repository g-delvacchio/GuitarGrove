<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.Utente" %>

<%
    Utente u = (Utente) session.getAttribute("user");

    if (u == null || !u.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">

<head>
    <title>Admin Panel - GuitarGrove</title>
</head>

<body>

<jsp:include page="../header.jsp"/>

<section class="admin-panel">

    <div class="back-container">
        <a href="<%=request.getContextPath()%>/Account">
            <button type="button">← Torna indietro</button>
        </a>
    </div>

    <h1>GuitarGrove - Area Admin</h1>

    <div class="admin-buttons">

        <a href="<%=request.getContextPath()%>/AdminOrdiniServlet">
            <button>Visualizza ordini</button>
        </a>

        <a href="<%=request.getContextPath()%>/AdminProdottiServlet">
            <button>Visualizza prodotti</button>
        </a>

        <a href="<%=request.getContextPath()%>/AdminUtentiServlet">
            <button>Visualizza utenti</button>
        </a>

    </div>

</section>

<jsp:include page="../footer.jsp"/>

</body>
</html>