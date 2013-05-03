<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Espace client</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<fieldset>
		<legend>Votre Espace Client</legend>
		<div id="corps">
			<!-- affichage d'un message d'erreur en cas d'un accès illégal à une page admin -->
			<c:if test="${param.redirect == 1}">
				<p class="erreur">Vous n'avez pas les droits nécessaires pour acceder à cette page.</p>
			</c:if>
			<!-- affichage d'un message d'erreur si besion -->
			<c:if test="${! empty erreur}">
				<p class="erreur"><c:out value="${ erreur }"/></p>
			</c:if>
		</div>
	<h3>Sélectionnez un spectacle pour réserver une place</h3>	
	<div id="corps">
        <c:choose>
            <%-- Si aucun spectacle n'est transmit en requète, affichage d'un message par défaut. --%>
            <c:when test="${ empty requestScope.spectacles }">
                <p class="erreur">Aucun spectacle à l'affiche.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th class="action">Acheter / Réserver</th>                    
                </tr>
                <%-- Parcours de la listes des spectacles en requête, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ requestScope.spectacles }" var="spectacle" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage du nom des spectacles --%>
                    <td><c:out value="${ spectacle.nom }"/></td>
                    <%-- Lien vers la page de réservation du spectacle approprié. --%>
                    <td class="action">
                        <input type="button" value="Sélectionner" 
                        	onclick="self.location.href=
                        	'<c:url value='/choixRepresentation'>
                        		<c:param name='id' value='${spectacle.id }' />
                        	</c:url>'"
                       	/>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
	</div>
	<c:if test="${ ! sessionScope.sessionUtilisateur.estGuichet() }">
		<h3>Accèdez à vos réservations en attente de paiement</h3>	
    	<div id="corps">
	    	<input type="button" value="Espace Réservation" 
				onclick="self.location.href='<c:url value='/reservationsClient'/>'" />
   		</div>
   	</c:if>
	</fieldset>
</body>
</html>
