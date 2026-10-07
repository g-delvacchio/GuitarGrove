<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove - Registrazione</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/signup.css">
    <script src="<%=request.getContextPath()%>/scripts/validate.js"></script>
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
                  id="regForm"
                  novalidate
                  onsubmit="return checkSignup(this)">

                <label for="username">Username:</label>
                <input class="inputField" type="text" id="username" name="username"
                       required onchange="validateUsername()">
                <span id="errorUsername"></span><br>

                <label for="nome">Nome:</label>
                <input class="inputField" type="text" id="nome" name="nome"
                       required onchange="validateNome()">
                <span id="errorName"></span><br>

                <label for="cognome">Cognome:</label>
                <input class="inputField" type="text" id="cognome" name="cognome"
                       required onchange="validateCognome()">
                <span id="errorLastname"></span><br>

                <label for="email">Email:</label>
                <input class="inputField" type="email" id="email" name="email"
                       required onchange="validateEmail()">
                <span id="errorEmail"></span><br>

                <label for="password">Password:</label>
                <input class="inputField" type="password" id="password" name="password"
                       required onchange="validatePassword()">
                <span id="errorpswd"></span><br>

                <label for="conferma_password">Conferma Password:</label>
                <input class="inputField" type="password" id="conferma_password"
                       name="conferma_password"
                       required onchange="pswMatching()">
                <span id="matchError"></span><br>

                <label for="telefono">Telefono:</label>
                <input class="inputField" type="tel" id="telefono" name="telefono"
                       required onchange="validateTelefono()">
                <span id="errorTelefono"></span><br>

                <h3>Indirizzo</h3>

                <label for="paese">Paese:</label>
                <input class="inputField" type="text" id="paese" name="paese"
                       required onchange="validatePaese()">
                <span id="errorPaese"></span><br>

                <label for="citta">Città:</label>
                <input class="inputField" type="text" id="citta" name="citta"
                       required onchange="validateCitta()">
                <span id="errorCitta"></span><br>

                <label for="cap">CAP:</label>
                <input class="inputField" type="text" id="cap" name="cap"
                       required onchange="validateCAP()">
                <span id="errorCAP"></span><br>

                <label for="via">Via:</label>
                <input class="inputField" type="text" id="via" name="via"
                       required onchange="validateVia()">
                <span id="errorVia"></span><br>

                <label for="civico">Civico:</label>
                <input class="inputField" type="text" id="civico" name="civico"
                       required onchange="validateCivico()">
                <span id="errorCivico"></span><br>

                <input class="btn btn-primary"
                       type="submit"
                       value="Registrati">

            </form>

            <p>
                Hai già un account?
                <a href="<%=request.getContextPath()%>/Login">Accedi</a>
            </p>

        </div>

    </section>

</main>

<jsp:include page="footer.jsp" />

</body>
</html>