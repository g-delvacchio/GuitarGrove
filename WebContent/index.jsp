<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuitarGrove</title>
    <link rel="stylesheet" href="styles/style.css">
</head>

<body>

    <jsp:include page="view/header.jsp" />

    <main>
    <!-- PRODOTTI -->
    <section class="featured-products">

        <h1>Prodotti in evidenza</h1>

        <div class="product-grid">
            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/gibson/Les_Paul_Standard_50s.jpeg" alt="Immagine prodotto"></div>
                <h3>Les Paul Standard 50s</h3>
                <p>Gibson</p>
                <p>Chitarra elettrica</p>
                <p class="price">€ 2699.00</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=8">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/fender/American_Professional_II_Telecaster.jpeg" alt="Immagine prodotto"></div>
                <h3>American Professional II Telecaster</h3>
                <p>Fender</p>
                <p>Chitarra elettrica</p>
                <p class="price">€ 1949</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=4">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/roland/FP-10.jpeg" alt="Immagine prodotto"></div>
                <h3>FP-10</h3>
                <p>Roland</p>
                <p>Tastiere</p>
                <p class="price">€ 499</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=23">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/roland/KC-200.jpeg" alt="Immagine prodotto"></div>
                <h3>KC-200</h3>
                <p>Roland</p>
                <p>Amplificatori</p>
                <p class="price">€ 549</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=26">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/pearl/Roadshow_RS525SC.jpeg" alt="Immagine prodotto"></div>
                <h3>Roadshow RS525SC</h3>
                <p>Pearl</p>
                <p>Batterie</p>
                <p class="price">€ 699</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=31">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/boss/CH-1_Super_Chorus.jpeg" alt="Immagine prodotto"></div>
                <h3>CH-1 Super Chorus</h3>
                <p>Boss</p>
                <p>Effetti</p>
                <p class="price">€ 119</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=42">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/shure/SM58.jpeg" alt="Immagine prodotto"></div>
                <h3>SM58</h3>
                <p>Shure</p>
                <p>Microfoni</p>
                <p class="price">€ 109</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=46">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/fender/Chitarra_Elettrica_Stratocaster.jpeg" alt="Immagine prodotto"></div>
                <h3>Chitarra Elettrica Stratocaster</h3>
                <p>Fender</p>
                <p>Chitarra elettrica</p>
                <p class="price">€ 799</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=1">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/gibson/SG_Standard.jpeg" alt="Immagine prodotto"></div>
                <h3>SG Standard 1599</h3>
                <p>Gibson</p>
                <p>Chitarra elettrica</p>
                <p class="price">€ 1599</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=10">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/gibson/J-45_Standard.jpeg" alt="Immagine prodotto"></div>
                <h3>J-45 Standard'1</h3>
                <p>Gibson</p>
                <p>Chitarra Acustica</p>
                <p class="price">€ 2899</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=12">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/yamaha/P-225.jpeg" alt="Immagine prodotto"></div>
                <h3>P-225</h3>
                <p>Yamaha</p>
                <p>Tastiere</p>
                <p class="price">€ 699</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=21">
                    Vedi prodotto </a>
            </div>

            <div class="product-card">
                <div class="product-image"><img src="<%=request.getContextPath()%>/images/products/tama/Iron_Cobra_900.jpeg" alt="Immagine prodotto"></div>
                <h3>Iron Cobra 900</h3>
                <p>Tama</p>
                <p>Accessori</p>
                <p class="price">€ 469</p>
                <a href="<%=request.getContextPath()%>/ProdottoGuitarGrove?id=37">
                    Vedi prodotto </a>
            </div>
        </div>

    </section>

    <!-- CATEGORIE VISIBILI (HOME) -->
    <section class="categories-section">

        <h1>Categorie</h1>

        <div class="categories-grid">


            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Elettriche" class="category-card">
                <img src="images/categorie/chitarra_elettrica.jpeg" alt="Chitarre Elettriche">
                Chitarre Elettriche
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Chitarre%20Acustiche" class="category-card">
                <img src="images/categorie/chitarra_acustica.jpeg" alt="Chitarre Acustiche">
                Chitarre Acustiche
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Bassi" class="category-card">
                <img src="images/categorie/basso.jpeg" alt="Bassi">
                Bassi
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Tastiere" class="category-card">
                <img src="images/categorie/tastiera.jpeg" alt="Tastiere">
                Tastiere
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Batterie" class="category-card">
                <img src="images/categorie/batteria.jpeg" alt="Batterie">
                Batterie
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Percussioni" class="category-card">
                <img src="images/categorie/percussioni.jpeg" alt="Percussioni">
                Percussioni
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Amplificatori" class="category-card">
                <img src="images/categorie/amplificatore.jpeg" alt="Amplificatori">
                Amplificatori
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Accessori" class="category-card">
                <img src="images/categorie/accessori.jpeg" alt="Accessori">
                Accessori
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Effetti" class="category-card">
                <img src="images/categorie/effetti.jpeg" alt="Effetti">
                Effetti
            </a>

            <a href="<%=request.getContextPath()%>/Catalogo?categoria=Microfoni" class="category-card">
                <img src="images/categorie/microfono.jpeg" alt="Microfoni">
                Microfoni
            </a>

        </div>

    </section>

        <!-- MARCHE -->
        <section class="brands-section">

            <h1>Marche</h1>

            <div class="brands-grid">

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Yamaha" class="brand-card">
                    <img src="images/marche/yamaha.jpeg" alt="Yamaha">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Fender" class="brand-card">
                    <img src="images/marche/fender.jpeg" alt="Fender">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Gibson" class="brand-card">
                    <img src="images/marche/gibson.jpeg" alt="Gibson">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Roland" class="brand-card">
                    <img src="images/marche/roland.jpeg" alt="Roland">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Pearl" class="brand-card">
                    <img src="images/marche/pearl.jpeg" alt="Pearl">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Tama" class="brand-card">
                    <img src="images/marche/tama.jpeg" alt="Tama">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Boss" class="brand-card">
                    <img src="images/marche/boss.jpeg" alt="Boss">
                </a>

                <a href="<%=request.getContextPath()%>/Catalogo?marca=Shure" class="brand-card">
                    <img src="images/marche/shure.jpeg" alt="Shure">
                </a>

            </div>

        </section>

    </main>
    <jsp:include page="view/footer.jsp" />
</body>
</html>