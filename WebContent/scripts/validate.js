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

function getAddressForm() {

    return document.getElementById("regForm")
        || document.getElementById("checkoutForm");
}

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

function validateUsername() {
    let form = document.getElementById("regForm");
    return validateField(
        form.username,
        usernamePattern,
        document.getElementById("errorUsername"),
        usernameError
    );
}

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

function validateEmail() {
    let form = document.getElementById("regForm");
    return validateField(
        form.email,
        emailPattern,
        document.getElementById("errorEmail"),
        emailError
    );
}

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

function validateTelefono() {
    let form = document.getElementById("regForm");
    return validateField(
        form.telefono,
        phonePattern,
        document.getElementById("errorTelefono"),
        phoneError
    );
}

function validatePaese() {
	let form = getAddressForm();
    let span = document.getElementById("errorPaese");

    let paese = form.paese.value.trim();

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

function validateCitta() {
	let form = getAddressForm();
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

function validateCAP() {
	let form = getAddressForm();
    return validateField(
        form.cap,
        capPattern,
        document.getElementById("errorCAP"),
        capError
    );
}

function validateVia() {
	let form = getAddressForm();
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

function validateCivico() {
	let form = getAddressForm();
    let span = document.getElementById("errorCivico");

    let civico = form.civico.value.trim();

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

function checkSignup(form) {

    const usernameOk = validateUsername();
    const nomeOk = validateNome();
    const cognomeOk = validateCognome();
    const emailOk = validateEmail();
    const passwordOk = validatePassword();
    const matchOk = pswMatching();
    const telefonoOk = validateTelefono();
    const paeseOk = validatePaese();
    const cittaOk = validateCitta();
    const capOk = validateCAP();
    const viaOk = validateVia();
    const civicoOk = validateCivico();

    return usernameOk &&
           nomeOk &&
           cognomeOk &&
           emailOk &&
           passwordOk &&
           matchOk &&
           telefonoOk &&
           paeseOk &&
           cittaOk &&
           capOk &&
           viaOk &&
           civicoOk;
}

function validateOldPassword() {

    const form = document.getElementById("changePasswordForm");
    const span = document.getElementById("errorOldPassword");

    if (form.oldPassword.value.trim() === "") {

        span.innerHTML = "Inserisci la password attuale";
        span.style.color = "red";

        return false;
    }

    span.innerHTML = "";

    return true;
}

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

function checkChangePassword(form) {

    const oldOk = validateOldPassword();
    const newOk = validateNewPassword();
    const matchOk = matchNewPassword();

    return oldOk &&
           newOk &&
           matchOk;
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

    const emailOk = validateLoginEmail();
    const passwordOk = validateLoginPassword();

    return emailOk && passwordOk;
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

function checkCheckout(form) {

    const paeseOk = validatePaese();
    const cittaOk = validateCitta();
    const capOk = validateCAP();
    const viaOk = validateVia();
    const civicoOk = validateCivico();

    const cardOk = validateCardNumber();
    const expiryOk = validateExpiry();
    const cvvOk = validateCVV();

    return paeseOk &&
           cittaOk &&
           capOk &&
           viaOk &&
           civicoOk &&
           cardOk &&
           expiryOk &&
           cvvOk;
}