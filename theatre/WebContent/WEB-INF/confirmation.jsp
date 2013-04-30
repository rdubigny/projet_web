<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Confirmation de votre commande</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<!-- Ici, on résume la commande, qui est issue soit d'une réservation, soit d'un achat -->
	
	<!-- affichage du message de résultat -->
	<p></p>
	
	<!-- affichage de l'erreur s'il y a lieu -->
	
	<!--  sinon affichage de la liste des tickets si elle existe -->
	<c:if test="${requestScope.tickets != null }">
		
	</c:if>
	
	<input type="button" value="Retour à l'espace client" onclick="self.location.href='<c:url value='/espaceClient'/>'" />
</body>
</html>