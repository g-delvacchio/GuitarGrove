<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuitarGrove - Sign up</title>
</head>
<body>

<jsp:include page="header.jsp" />

<main class="signup">

    <section>

        <%
            String error = (String) request.getAttribute("error");
            if (error == null) error = "";
        %>

        <p style="color:red;"><%= error %></p>

        <div id="signupDiv">

            <h2>Registrati su GuitarGrove</h2>

            <form action="<%=request.getContextPath()%>/Signup"
                  method="post"
                  id="regForm">

                <!-- USERNAME -->
                <label for="username">Username:</label>
                <input class="inputField" type="text" id="username" name="username">
                <span id="errorUsername"></span><br>

                <!-- NOME -->
                <label for="nome">Nome:</label>
                <input class="inputField" type="text" id="nome" name="nome">
                <span id="errorName"></span><br>

                <!-- COGNOME -->
                <label for="cognome">Cognome:</label>
                <input class="inputField" type="text" id="cognome" name="cognome">
                <span id="errorLastname"></span><br>

                <!-- EMAIL -->
                <label for="email">Email:</label>
                <input class="inputField" type="email" id="email" name="email">
                <span id="errorEmail"></span><br>

                <!-- PASSWORD -->
                <label for="password">Password:</label>
                <input class="inputField" type="password" id="password" name="password">
                <span id="errorpswd"></span><br>

                <!-- CONFERMA PASSWORD -->
                <label for="conferma_password">Conferma Password:</label>
                <input class="inputField" type="password" id="conferma_password"
                       name="conferma_password">
                <span id="matchError"></span><br>

                <!-- TELEFONO -->
                <label for="telefono">Telefono:</label>
                <input class="inputField" type="tel" id="telefono" name="telefono">
                <span id="errorTelefono"></span><br>

                <!-- INDIRIZZO -->
                <h3>Indirizzo</h3>

                <label for="paese">Paese:</label>
                <input class="inputField" type="text" id="paese" name="paese">
                <span id="errorPaese"></span><br>

                <label for="citta">Città:</label>
                <input class="inputField" type="text" id="citta" name="citta">
                <span id="errorCitta"></span><br>

                <label for="cap">CAP:</label>
                <input class="inputField" type="text" id="cap" name="cap">
                <span id="errorCAP"></span><br>

                <label for="via">Via:</label>
                <input class="inputField" type="text" id="via" name="via">
                <span id="errorVia"></span><br>

                <label for="civico">Civico:</label>
                <input class="inputField" type="text" id="civico" name="civico">
                <span id="errorCivico"></span><br>

                <input class="btn btn-primary"
                       type="submit"
                       value="Registrati">

            </form>

            <p>
                Hai già un account?
                <a href="">Accedi</a>
            </p>

        </div>

    </section>

</main>

<jsp:include page="footer.jsp" />

</body>
</html>