<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove - Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <script src="<%=request.getContextPath()%>/scripts/validate.js" defer></script>
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
                      method="post"
                      onsubmit="return checkLogin()">

                    <label for="email">Email</label>
                    <input type="email"
                           id="email"
                           name="email"
                           required
                           oninput="validateLoginEmail()">

                    <span id="errorLoginEmail"></span>

                    <label for="password">Password</label>
                    <input type="password"
                           id="password"
                           name="password"
                           required
                           oninput="validateLoginPassword()">

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