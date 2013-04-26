<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Identification</title>
	<link type="text/css" rel="stylesheet" href="inc/form.css" />
</head>
<body>
	<form method="post" action="<c:url value="/identification"/>">
	<fieldset>
			<legend>Identification</legend>
			<p>Identifiez vous pour réserver vos places.</p>
			<%-- Vérification de la présence d'un objet utilisateur en session --%>
     		<c:if test="${!empty sessionScope.sessionUtilisateur}">
     			<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
         		<p class="info">(Vous êtes connecté(e) en tant que ${sessionScope.sessionUtilisateur.login})</p>
      		</c:if>

			<label for="nom">login <span class="requis">*</span></label>
			<input type="text" id="login" name="login" value="<c:out value="${requestScope.login}"/>" 
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
			
			<c:if test="${!empty form.resultat}">
         		<p class="erreur">${form.resultat}</p>
      		</c:if>
   	</fieldset>
	</form>
</body>
</html>