const namePattern = /^[A-Za-zÀ-ÖØ-öø-ÿ' ]+$/;
const usernamePattern = /^[a-zA-Z0-9_]{3,20}$/;
const emailPattern = /^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()])[0-9a-zA-Z!@#$%^&*()]{8,}$/;
const phonePattern = /^[0-9]{10}$/;
const capPattern = /^[0-9]{5}$/;
const cardPattern = /^[0-9]{16}$/;
const cvvPattern = /^[0-9]{3}$/;

const usernameError = "Username 3-20 caratteri (lettere, numeri, underscore)";
const nameError = "Solo lettere e spazi";
const emailError = "Email non valida";
const passwordError = "Min 8 caratteri, maiuscola, minuscola, numero e simbolo";
const phoneError = "Numero di telefono non valido (10 cifre)";
const paeseError = "Inserisci un paese valido";
const cittaError = "Inserisci una città valida"
const capError = "CAP non valido (5 cifre)";
const matchError = "Le password non coincidono";
const civicoError = "Civico non valido (es: 12, 12A)";
const viaError = "Inserisci una via valida";
const cardError = "Il numero della carta deve contenere 16 cifre";
const expiryError = "La carta è scaduta";
const cvvError = "Il CVV deve contenere 3 cifre";

/* =========================
   GENERICA VALIDAZIONE
========================= */
function validateField(input, pattern, span, message) {
    if (input.value.match(pattern)) {
        span.innerHTML = "";
        span.style.color = "black";
        return true;
    } else {
        span.innerHTML = message;
        span.style.color = "red";
        return false;
    }
}

/* =========================
   USERNAME
========================= */
function validateUsername() {
    let form = document.getElementById("regForm");
    return validateField(
        form.username,
        usernamePattern,
        document.getElementById("errorUsername"),
        usernameError
    );
}

/* =========================
   NOME / COGNOME / CITTA / PAESE
========================= */
function validateNome() {
    let form = document.getElementById("regForm");
    return validateField(
        form.nome,
        namePattern,
        document.getElementById("errorName"),
        nameError
    );
}

function validateCognome() {
    let form = document.getElementById("regForm");
    return validateField(
        form.cognome,
        namePattern,
        document.getElementById("errorLastname"),
        nameError
    );
}

/* =========================
   EMAIL
========================= */
function validateEmail() {
    let form = document.getElementById("regForm");
    return validateField(
        form.email,
        emailPattern,
        document.getElementById("errorEmail"),
        emailError
    );
}

/* =========================
   PASSWORD
========================= */
function validatePassword() {
    let form = document.getElementById("regForm");

    if (form.password.value.match(passwordPattern)) {
        document.getElementById("errorpswd").innerHTML = "";
        return true;
    } else {
        document.getElementById("errorpswd").innerHTML = passwordError;
        return false;
    }
}

/* =========================
   CONFERMA PASSWORD
========================= */
function pswMatching() {
    let form = document.getElementById("regForm");

    if (form.password.value === form.conferma_password.value) {
        document.getElementById("matchError").innerHTML = "";
        return true;
    } else {
        document.getElementById("matchError").innerHTML = matchError;
        return false;
    }
}

/* =========================
   TELEFONO
========================= */
function validateTelefono() {
    let form = document.getElementById("regForm");
    return validateField(
        form.telefono,
        phonePattern,
        document.getElementById("errorTelefono"),
        phoneError
    );
}

/* =========================
   PAESE
========================= */
function validatePaese() {
    let form = document.getElementById("regForm");
    let span = document.getElementById("errorPaese");

    let paese = form.paese.value.trim();

    // permette lettere, spazi e accenti
    const paesePattern = /^[A-Za-zÀ-ÿ\s]{2,50}$/;

    if (paese.match(paesePattern)) {
        span.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }

    span.classList.add("error");
    span.innerHTML = paeseError;
    span.style.color = "red";
    return false;
}
/* =========================
   CITTA
========================= */
function validateCitta() {
    let form = document.getElementById("regForm");
    let span = document.getElementById("errorCitta");

    let citta = form.citta.value.trim();

    const cittaPattern = /^[A-Za-zÀ-ÿ\s]{2,50}$/;

    if (citta.match(cittaPattern)) {
        span.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }

    span.classList.add("error");
    span.innerHTML = cittaError;
    span.style.color = "red";
    return false;
}

/* =========================
   CAP
========================= */
function validateCAP() {
    let form = document.getElementById("regForm");
    return validateField(
        form.cap,
        capPattern,
        document.getElementById("errorCAP"),
        capError
    );
}

/* =========================
   VIA (minimo semplice)
========================= */
function validateVia() {
    let form = document.getElementById("regForm");
    let span = document.getElementById("errorVia");

    if (form.via.value.trim().length >= 2) {
        span.innerHTML = "";
        return true;
    } else {
        span.innerHTML = viaError;
        span.style.color = "red";
        return false;
    }
}

/* =========================
   CIVICO
========================= */
function validateCivico() {
    let form = document.getElementById("regForm");
    let span = document.getElementById("errorCivico");

    let civico = form.civico.value.trim();

    // numeri + eventuale lettera (es. 12, 12A, 5B)
    const civicoPattern = /^[0-9]{1,5}[A-Za-z]?$/;

    if (civico.length > 0 && civico.length <= 6 && civico.match(civicoPattern)) {
        span.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }

    span.classList.add("error");
    span.innerHTML = civicoError;
    span.style.color = "red";
    return false;
}

/* =========================
   SUBMIT CHECK
========================= */
function checkSignup(form) {

    return (
        validateUsername() &&
        validateNome() &&
        validateCognome() &&
        validateEmail() &&
        validatePassword() &&
        pswMatching() &&
        validateTelefono() &&
        validatePaese() &&
        validateCitta() &&
        validateCAP() &&
        validateVia() &&
        validateCivico()
    );
}

/* =========================
   CAMBIO PASSWORD ACCOUNT
========================= */

function validateNewPassword() {

    const form = document.getElementById("changePasswordForm");
    const span = document.getElementById("errorNewPassword");

    if (form.newPassword.value.match(passwordPattern)) {
        span.innerHTML = "";
        span.style.color = "black";
        return true;
    } else {
        span.innerHTML = passwordError;
        span.style.color = "red";
        return false;
    }
}

function matchNewPassword() {

    const form = document.getElementById("changePasswordForm");
    const span = document.getElementById("errorConfirmPassword");

    if (form.newPassword.value === form.confirmPassword.value) {
        span.innerHTML = "";
        span.style.color = "black";
        return true;
    } else {
        span.innerHTML = matchError;
        span.style.color = "red";
        return false;
    }
}

function checkChangePassword(form){

    return validateNewPassword() &&
        matchNewPassword();

}

function validateLoginEmail() {

    const form = document.getElementById("loginForm");

    return validateField(
        form.email,
        emailPattern,
        document.getElementById("errorLoginEmail"),
        emailError
    );
}

function validateLoginPassword() {

    const form = document.getElementById("loginForm");
    const span = document.getElementById("errorLoginPassword");

    if (form.password.value.match(passwordPattern)) {
        span.innerHTML = "";
        return true;
    }

    span.innerHTML = passwordError;
    span.style.color = "red";
    return false;
}

function checkLogin() {
    return validateLoginEmail() &&
        validateLoginPassword();
}




function validateExpiry() {

    let form = document.getElementById("checkoutForm");
    let span = document.getElementById("errorExpiry");

    if (form.expiry.value === "") {
        span.innerHTML = expiryError;
        span.style.color = "red";
        return false;
    }

    let today = new Date();

    today.setHours(0,0,0,0);

    let expiry = new Date(form.expiry.value);

    if (expiry >= today) {
        span.innerHTML = "";
        span.style.color = "black";
        return true;
    }

    span.innerHTML = expiryError;
    span.style.color = "red";
    return false;
}

function validateCVV() {

    let form = document.getElementById("checkoutForm");

    return validateField(
        form.cvv,
        cvvPattern,
        document.getElementById("errorCVV"),
        cvvError
    );
}

function validateCardNumber() {

    let form = document.getElementById("checkoutForm");

    return validateField(
        form.cardNumber,
        cardPattern,
        document.getElementById("errorCardNumber"),
        cardError
    );
}

function checkCheckout(form){

    return (
        validateCardNumber() &&
        validateExpiry() &&
        validateCVV()
    );

}