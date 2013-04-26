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
	<c:choose>
	<%-- Vérification de la présence d'un objet utilisateur en session --%>
    <c:when test="${!empty sessionScope.sessionUtilisateur}">
     			<%-- Si l'utilisateur existe en session, alors on affiche une page de déconnection. --%>
    	<p class="info">(Vous êtes connecté(e) en tant que ${sessionScope.sessionUtilisateur.login})</p>
    	<input type="button" value="Déconnexion" onclick="self.location.href='<c:url value='/deconnexion'/>'" />
    </c:when>
    <c:otherwise>
	<form method="post" action="<c:url value="/identification"/>">
	<fieldset>
			<legend>Identification</legend>			
			<c:choose>
    			<c:when test="${!empty form.erreurs}">
    				<p class="erreur">${form.resultat}</p>
    			</c:when>
      			<c:otherwise>
      				<p>Identifiez vous pour réserver vos places.</p>
      			</c:otherwise>
			</c:choose>

			<label for="nom">login <span class="requis">*</span></label>
			<input type="text" id="login" name="login" value="" 
				size="20" maxlength="60" />
			<span class="erreur">${form.erreurs['login']}</span> <br />
			
			<label for="motdepasse">Mot de passe <span class="requis">*</span></label> 
			<input type="password" id="motdepasse"
				name="motdepasse" value="" size="20" maxlength="20" /> <span class="erreur">${form.erreurs['motdepasse']}</span>
			<br /> 
                 
      		<br />
      		<label for="memoire">Se souvenir de moi</label>
      		<input type="checkbox" id="memoire" name="memoire" />
      		<br />
			
			<input type="submit" value="Connexion" class="sansLabel" /> <br />
   	</fieldset>
	</form>
    </c:otherwise>
	</c:choose>
</body>
</html>