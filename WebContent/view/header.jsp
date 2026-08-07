<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuitarGrove</title>
</head>

<header>

	<div class="top-header">
	
		<div class="logo-container">
            <div class="logo">
                <a href="<%=request.getContextPath()%>/index.jsp">
                    <img src="" alt="GuitarGrove" class="logo-img">
                </a>
            </div>
        </div>
	
	</div>
	
	<div class="search-bar">
	
		<button class="menu-button" id="openMenu">☰</button>

        <form action="<%=request.getContextPath()%>/view/catalogo.jsp"
              method="GET"
              class="search-form"
              id="searchForm">

            <label for="searchInput"></label>
            <input type="text"
                   id="searchInput"
                   name="q"
                   placeholder="Cerca strumenti, accessori e marche...">

            <span>&nbsp;</span>

            <button type="submit">Cerca</button>

            <!-- AJAX suggestions -->
            <div id="suggestions" class="suggestions"></div>

        </form>
	
	</div>
	
	<div class="side-bar"></div>

</header>
</html>