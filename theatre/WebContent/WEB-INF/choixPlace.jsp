<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Choix des places</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
<!--  ici on affichera le nom du spectacle choisi, et un plan de la salle avec des
check box pour choisir la ou les places (sous la forme d'un formulaire). En bas de la page
deux boutons input = submit : un pour acheter, un pour reserver -->
	<div id="menu">
		Choisissez votre place pour ${ sessionScope.spectacle.nom } le
		<joda:format value="${ sessionScope.representation.date }" pattern="EEEE dd MMMM yyyy 'à' HH 'heures'"/>.
	</div>
	<div id="corps">
    	<table>
        	<c:forEach var="i" begin="0" end=${ places.length}>   
        		<tr>
<!--             	<tr background-color=#0af>              -->
         			<c:forEach var="j" begin="0" end=${ places[i].length}>
            			<td>${ place[i][j].zone }</td>
            		</c:forEach>
            	</tr>
        	</c:forEach>
       	</table>
	</div>
</body>
</html>