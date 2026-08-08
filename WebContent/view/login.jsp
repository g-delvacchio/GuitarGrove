<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuitarGrove - Login</title>
</head>
<body>

<jsp:include page="header.jsp" />

    <main class="login">
        <%
            String error = (String)request.getAttribute("error");
            if(error == null)
                error="";

        %>
        <section>

            <p  style="color:red "> <%=error %> </p>

            <div>
                <h2>Login</h2>
                <form id="loginForm"
                      action="<%=request.getContextPath()%>/Login"
                      method="post">

                    <label for="email">Email</label>
                    <input type="email"
                           id="email"
                           name="email">

                    <span id="errorLoginEmail"></span>

                    <label for="password">Password</label>
                    <input type="password"
                           id="password"
                           name="password">

                    <span id="errorLoginPassword"></span>

                    <button type="submit">Login</button>

                </form>
                <p>Non hai un account? <a href="<%=request.getContextPath()%>/view/signup.jsp">Registrati qui</a>.</p>
            </div>
        </section>
    </main>


    <jsp:include page="footer.jsp" />

</body>
</html>