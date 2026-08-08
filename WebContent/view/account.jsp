<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.bean.Utente" %>
<%@ page import="model.bean.Indirizzo" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuitarGrove - Account</title>
</head>
<body>

<jsp:include page="header.jsp"/>

<%
    Utente user = (Utente) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
        return;
    }

    Indirizzo indirizzo = (Indirizzo) session.getAttribute("indirizzo");

    String error = request.getParameter("error");
    String success = request.getParameter("success");

%>



<jsp:include page="footer.jsp"/>

</body>
</html>