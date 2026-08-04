<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuitarGrove</title>
</head>
<body>

	<jsp:include page="view/header.jsp"/>

	<main>
	<section class="products">
	
		<h1>Prodotti in evidenza</h1>
		
		<div class="griglia-prodotti">
		
			<div class="product">
                <div class="product-image"><img src=""></img></div>
                <h3>Modello1</h3>
                <p>Marca1</p>
                <p>Chitarra elettrica</p>
                <p class="price">€ 200</p>
                <a href="">Vedi prodotto </a>
            </div>

            <div class="product">
                <div class="product-image"><img src=""></img></div>
                <h3>Modello2</h3>
                <p>Marca2</p>
                <p>Chitarra acustica</p>
                <p class="price">€ 100</p>
                <a href="">Vedi prodotto </a>
            </div>
            
            <div class="product">
                <div class="product-image"><img src=""></img></div>
                <h3>Modello3</h3>
                <p>Marca3</p>
                <p>Chitarra classica</p>
                <p class="price">€ 80</p>
                <a href="">Vedi prodotto </a>
            </div>
		
		</div>
     
	
	</section>
	
	<section class="categories">
	
		<h1>Categorie prodotti</h1>
		
		<div class="griglia-categorie">
		
			<a href="" class="category">Chitarre Elettriche</a>
			
			<a href="" class="category">Pedaliere</a>
			
			<a href="" class="category">Accessori</a>
			
			<a href="" class="category">Casse</a>
		
		</div>
	
	</section>
	
	<section class="brands">
	
		<h1>Brand</h1>
		
		<div class="griglia-brand">
		
			<a href="" class="category">Yamaha</a>
			
			<a href="" class="category">Fender</a>
			
			<a href="" class="category">Gibson</a>
			
			<a href="" class="category">Roland</a>
		
		</div>
	
	</section>
	</main>
	
	<jsp:include page="view/footer.jsp"/>


</body>
</html>