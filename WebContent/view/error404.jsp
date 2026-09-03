<%@ page contentType="text/html; charset=UTF-8" language="java" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Error 404</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="image-error">
    <img src="<%= request.getContextPath() %>/images/error404.png" alt="error404">
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>