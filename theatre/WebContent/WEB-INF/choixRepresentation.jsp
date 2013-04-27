<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Choix de la représentation</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
<!-- ici affichage d'un "descriptif" du spectacle, suivi de la liste des représentations 
pour ce spectacle. Devant chaque représentation il y'aura un bouton "acheter/reserver" qui 
menera à l'url choixPlace/ -->
	<div id="menu">
		Liste des représentations pour le spectacle "${param.nomSpectacle }"
	</div>

</body>
</html>