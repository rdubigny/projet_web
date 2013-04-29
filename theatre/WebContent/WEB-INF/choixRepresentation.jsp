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
		Liste des représentations à venir pour ${ sessionScope.spectacle.nom }.
	</div>
	<div id="corps">
        <c:choose>
            <%-- Si aucune représentation n'est transmise en requète, affichage d'un message par défaut. --%>
            <c:when test="${ empty requestScope.representations }">
                <p class="erreur">Aucune représentation prévue pour le moment.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Date</th>
                    <th class="action">Acheter / Réserver</th>            
                </tr>
                <%-- Parcours de la listes des représentations en requête, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ requestScope.representations }" var="representation" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage du nom des spectacles --%>
                    <td><joda:format value="${ representation.date }" pattern="EEEE dd MMMM yyyy 'à' HH 'heures'"/></td>
                    <%-- Lien vers la page de réservation de la représentation appropriée. --%>
                    <td class="action">
                        <input type="button" value="Sélectionner" 
                        	onclick="self.location.href=
                        	'<c:url value='/choixPlace'>
                        		<c:param name='id' value='${ representation.id }' />
                        	</c:url>'"
                       	/>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
	</div>
</body>
</html>