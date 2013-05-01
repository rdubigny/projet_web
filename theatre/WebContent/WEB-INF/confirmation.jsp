<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Confirmation de votre commande</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<!-- Ici, on résume la commande, qui est issue soit d'une réservation, soit d'un achat -->
	<c:import url="/inc/header.jsp" />
	<fieldset>
		<legend>Confirmation</legend>
		<!-- affichage du message de résultat -->
		<h3>${ requestScope.form.resultat }</h3>
	
		<c:choose>
			<%-- affichage de l'erreur s'il y a lieu --%>
			<c:when test="${ ! empty requestScope.form.erreur }">
				<p class="erreur">${ requestScope.form.erreur }</p>
			</c:when>
			<c:otherwise>
				<%--  sinon affichage de la liste des tickets si elle existe --%>
				<c:if test="${requestScope.tickets != null }">
					Voici la liste
					${fn:length(tickets) > 1 ? "des tickets achetés" : "du ticket acheté"}.
					Imprimez cette page elle vous sera demandée au guichet pour accèder a la représentation.
					<table>
                		<tr>
                    		<th>Numéro de série</th>
	                    	<th>Date d'émission</th>                    
                		</tr>
						<c:forEach items="${ requestScope.tickets }" var="ticket" varStatus="boucle">
							<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    			<%-- Affichage du numéro de série --%>
                    			<td>
                    				<c:out value="${ ticket.id }"/>
                    			</td>
                    			<%-- Affichage de la date d'émission --%>
                    			<td>
		                        	<joda:format value="${ ticket.date }" pattern="EEEE dd MMMM yyyy 'à' HH:mm:ss"/>
        			            </td>
                			</tr>
						</c:forEach>
					</table>
				</c:if>
			</c:otherwise>
		</c:choose>
		<p><input type="button" value="Retour à l'espace client" onclick="self.location.href='<c:url value='/espaceClient'/>'" /><p>
	</fieldset>
</body>
</html>