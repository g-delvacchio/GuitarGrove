<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove - Registrazione</title>
    
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
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
                  onsubmit="return checkSignup(this)">

                <!-- USERNAME -->
                <label for="username">Username:</label>
                <input class="inputField" type="text" id="username" name="username"
                       required oninput="validateUsername()">
                <span id="errorUsername"></span><br>

                <!-- NOME -->
                <label for="nome">Nome:</label>
                <input class="inputField" type="text" id="nome" name="nome"
                       required oninput="validateNome()">
                <span id="errorName"></span><br>

                <!-- COGNOME -->
                <label for="cognome">Cognome:</label>
                <input class="inputField" type="text" id="cognome" name="cognome"
                       required oninput="validateCognome()">
                <span id="errorLastname"></span><br>

                <!-- EMAIL -->
                <label for="email">Email:</label>
                <input class="inputField" type="email" id="email" name="email"
                       required oninput="validateEmail()">
                <span id="errorEmail"></span><br>

                <!-- PASSWORD -->
                <label for="password">Password:</label>
                <input class="inputField" type="password" id="password" name="password"
                       required oninput="validatePassword()">
                <span id="errorpswd"></span><br>

                <!-- CONFERMA PASSWORD -->
                <label for="conferma_password">Conferma Password:</label>
                <input class="inputField" type="password" id="conferma_password"
                       name="conferma_password"
                       required oninput="pswMatching()">
                <span id="matchError"></span><br>

                <!-- TELEFONO -->
                <label for="telefono">Telefono:</label>
                <input class="inputField" type="tel" id="telefono" name="telefono"
                       required oninput="validateTelefono()">
                <span id="errorTelefono"></span><br>

                <!-- INDIRIZZO -->
                <h3>Indirizzo</h3>

                <label for="paese">Paese:</label>
                <input class="inputField" type="text" id="paese" name="paese"
                       required oninput="validatePaese()">
                <span id="errorPaese"></span><br>

                <label for="citta">Città:</label>
                <input class="inputField" type="text" id="citta" name="citta"
                       required oninput="validateCitta()">
                <span id="errorCitta"></span><br>

                <label for="cap">CAP:</label>
                <input class="inputField" type="text" id="cap" name="cap"
                       required oninput="validateCAP()">
                <span id="errorCAP"></span><br>

                <label for="via">Via:</label>
                <input class="inputField" type="text" id="via" name="via"
                       required oninput="validateVia()">
                <span id="errorVia"></span><br>

                <label for="civico">Civico:</label>
                <input class="inputField" type="text" id="civico" name="civico"
                       required oninput="validateCivico()">
                <span id="errorCivico"></span><br>

                <input class="btn btn-primary"
                       type="submit"
                       value="Registrati">

            </form>

            <p>
                Hai già un account?
                <a href="<%=request.getContextPath()%>/view/login.jsp">Accedi</a>
            </p>

        </div>

    </section>

</main>

<jsp:include page="footer.jsp" />

</body>
</html>