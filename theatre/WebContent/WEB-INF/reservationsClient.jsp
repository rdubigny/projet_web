<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mes réservations</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<%-- En-tête de connexion et deconnexion --%>
	<c:import url="/inc/header.jsp" />
			<fieldset>
			<legend>Vos Réservations</legend>
			<h3>Choisissez les réservations que vous voulez payer ou annuler.</h3>	
			<div id="corps">
				<p>Attention, vos éventuelles réservations de places pour des représentations 
				qui ont déjà eu lieu, ou qui ont lieu dans moins d'une heure, ont été 
				annulées.
			</p>
       </div>		
	</fieldset>
</body>
</html>