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
			<c:if test="${param.redirect == 1}">
				<p class="erreur">Vous n'avez pas les droits nécessaires pour acceder à cette page.</p>
			</c:if>
			<h3>Choisissez les réservations que vous voulez payer ou annuler.</h3>	
			<div id="corps">
				<p>Attention, vos éventuelles réservations de places pour des représentations 
				qui ont déjà eu lieu, ou qui ont lieu dans moins d'une heure, sont 
				annulées.
			</p>
        	<c:choose>
            	<%-- Si aucune reservation n'est transmis. --%>
            	<c:when test="${ empty requestScope.reservations }">
                <p class="erreur">Vous n'avez aucune réservation.</p>
            	</c:when>
            	<%-- Sinon, affichage du tableau de réservations. --%>
            	<c:otherwise>
            	<table>
            		<%-- Titre des colonnes --%>
                <tr>
                    <th>Représentation</th>
                    <th>Spectacle</th>
                    <th>Numéro de Rang</th>
                    <th>Numéro de Siège</th>
                    <th>Zone</th>
                    <th>Prix</th>
                    <th class="action">Annuler / Payer</th>                    
                </tr>
                <%-- Parcours de la listes des reservations en requête, et utilisation de l'objet varStatus. --%>
<%--                 <c:forEach items="${ requestScope.reservations }" var="reservation" varStatus="boucle"> --%>
<%--                 Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
<%--                 <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}"> --%>
<%--                     Colonne --%>
<%--                     <td><c:out value="${ reservation.nom }"/></td> --%>
<%--                     Lien vers la page de réservation du spectacle approprié. --%>
<!--                     <td class="action"> -->
<!--                         <input type="button" value="Paiement"  -->
<%--                         	onclick="self.location.href= --%>
<!--                         	'<c:url value='/confirmation'> -->
<%-- <%--                         		<c:param name='id' value='${spectacle.id }' /> --%> --%>
<%--                         	</c:url>'" --%>
<!--                        	/> -->
<!--                     </td> -->
<!--                 </tr> -->
<%--                 </c:forEach> --%>
            </table>
            </c:otherwise>
        </c:choose>
       </div>		
	</fieldset>
</body>
</html>