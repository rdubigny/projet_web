<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Identification</title>
	<link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<form method="post" action="<c:url value="/identification"/>">
	<fieldset>
		<legend>Identification</legend>			
			<c:choose>
				<c:when test="${ param.redirect == 0 }">
					<!-- Si on a été redirigé parce qu'on a essayé d'accéder à une page restreinte -->
					<p class ="erreur">Votre session à expiré. Veuillez vous identifier à nouveau</p>
				</c:when>
				<c:when test="${ param.redirect == 1 }">
					<!-- Si on a été redirigé parce qu'on a essayé d'accéder à une page restreinte -->
					<p class ="erreur">Vous devez vous identifier pour accéder à votre espace</p>
				</c:when>
				<c:otherwise>
					<c:choose>
    					<c:when test="${!empty form.erreurs}">
							<!-- Message affiché si les identifiants entrés par l'utilisateur  -->
							<!-- ne sont pas valides -->
    						<p class="erreur">${form.resultat}</p>
    					</c:when>
      					<c:otherwise>      						
							<!-- Message d'acceuil par défaut -->
      						<h3>Identifiez vous pour réserver vos places</h3>
      					</c:otherwise>
      				</c:choose>
      			</c:otherwise>
			</c:choose>
	<div id="corps">
			<label for="nom">Login <span class="requis">*</span></label>
			<input type="text" id="login" name="login" value="" 
				size="20" maxlength="60" />
			<span class="erreur">${form.erreurs['login']}</span> <br />
			
			<label for="motdepasse">Mot de passe <span class="requis">*</span></label> 
			<input type="password" id="motdepasse"
				name="motdepasse" value="" size="20" maxlength="20" /> <span class="erreur">${form.erreurs['motdepasse']}</span>
			<br /> 
                 
      		<br />
			
			<input type="submit" value="Connexion" class="sansLabel" /> <br />
			</div>
   	</fieldset>
	</form>
</body>
</html>