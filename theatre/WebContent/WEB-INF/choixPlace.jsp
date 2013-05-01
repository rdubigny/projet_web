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
	<c:import url="/inc/header.jsp" />
<!--  ici on affichera le nom du spectacle choisi, et un plan de la salle avec des
check box pour choisir la ou les places (sous la forme d'un formulaire). En bas de la page
deux boutons input = submit : un pour acheter, un pour reserver -->	
	<fieldset>
		<legend>Choix des places</legend>
	<h3>Choisissez votre place pour ${ sessionScope.representation.nomSpectacle } le
		<joda:format value="${ sessionScope.representation.date }" pattern="EEEE dd MMMM yyyy 'à' HH 'heures'"/>.</h3>
	</div>
	<div id="corps">
		<form action="<c:url value='/confirmation'/>" method="post">
    	<table>
        	<c:forEach items="${ places }" var="rang">   
        		<tr>
         			<c:forEach items="${ rang }" var="place">
            			<td width="16" height="16" align="center" valign="middle" bgcolor=<c:choose>
            							<c:when test="${place.zone == 1}">"#DAA520"</c:when>
            							<c:when test="${place.zone == 2}">"#CC6666"</c:when>
            							<c:when test="${place.zone == 3}">"#D2B48C"</c:when>
            							<c:when test="${place.zone == 4}">"#BC8F8F"</c:when>
            							<c:when test="${place.zone == 5}">"#8FBC8F"</c:when>
            							<c:when test="${place.zone == 6}">"#CD853F"</c:when>
            						</c:choose>>
            				<c:if test="${! place.occupe}">
            					<input type="checkbox" name="id" value="${ place.id }" >
            				</c:if>
                        </td>
            		</c:forEach>
            	</tr>
        	</c:forEach>
       	</table>
       	<br/>
       		<input type="submit" value="Acheter">
       	<c:if test="${! estGuichet}">
        	<input type="checkbox" name="action" value="reservation" > je veux seulement réserver, je paierai plus tard
        </c:if>	
       	</form>
	</div>
	</fieldset>
</body>
</html>