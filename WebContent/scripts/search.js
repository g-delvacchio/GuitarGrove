document.addEventListener("DOMContentLoaded", function () {

    const input = document.getElementById("searchInput");
    const box = document.getElementById("suggestions");

    let timeout = null;

    input.addEventListener("input", function () {

        clearTimeout(timeout);

        let query = input.value.trim();

        if (query.length < 2) {
            box.innerHTML = "";
            box.classList.remove("show");
            return;
        }

        timeout = setTimeout(() => {

            //Utilizzo di AJAX
            fetch(contextPath + "/SearchServlet?q=" + encodeURIComponent(query))
                .then(res => res.json()) //Utilizzo di JSON
                .then(data => {

                    box.innerHTML = "";

                    if (data.length === 0) {
                        box.classList.remove("show");
                        return;
                    }

                    data.forEach(p => {

                        let div = document.createElement("div");
                        div.classList.add("suggestion-item");

                        div.innerHTML = `<strong>${p.nome}</strong> - ${p.marca}`;

                        div.onclick = () => {
                            window.location.href = contextPath + "/ProdottoGuitarGrove?id=" + p.id;
                        };

                        box.appendChild(div);
                    });

                    box.classList.add("show");
                });

        }, 200);
    });

    // chiudi dropdown cliccando fuori
    document.addEventListener("click", function (e) {
        if (!e.target.closest(".search-form")) {
            box.classList.remove("show");
        }
    });

});