<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Statistiques</title>
</head>
<body>
	<c:import url="/inc/header.jsp" />
<div id=menu>
<p> Total de places vendues sur la saison : ${placesVendues }</p>
</div>

<div id=menu>
<p> Total de places vendues par spectacle </p>
<c:choose>
            <%-- Si aucun spectacle n'est transmit en requète, affichage d'un message par défaut. --%>
            <c:when test="${ empty requestScope.placesVenduesParSpectacle }">
                <p class="erreur">Aucun spectacle à l'affiche.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th>
                    <th>Places Vendues</th>                    
                </tr>
                <%-- Parcours de la listes des spectacles en requête, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ requestScope.placesVenduesParSpectacle }" var="spectacle" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage du nom des spectacles --%>
                    <td><c:out value="${ spectacle.nom }"/></td>
                    <%-- Places vendues --%>
                    <td><c:out value="${ spectacle.placesVendues }"/></td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
       
       

</body>
</html>