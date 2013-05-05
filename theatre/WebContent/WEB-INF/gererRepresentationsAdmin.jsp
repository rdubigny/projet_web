<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Gestion des représentations</title>
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<fieldset>
		<legend>Gestion des représentations</legend>
		<c:choose>
			<%-- Si aucune représentation n'est transmise en requète, affichage d'un message par défaut. --%>
			<c:when test="${ empty requestScope.representationsAdmin && suppression != 1}">
				<p class="erreur">Aucune représentation prévue pour le moment.</p>
			</c:when>
			<%-- Sinon, affichage du tableau. --%>
			<c:otherwise>
				<c:if test="${ param.suppression == 1 }"> 
					<p class = "succes"> La représentation a bien été annulée </p>
				</c:if>
				<table>
					<tr>
						<th>Spectacle</th>
						<th>Date</th>
						<th class="action">Annuler une représentation</th>
					</tr>	
					<%-- Parcours de la listes des représentations en requête, et utilisation de l'objet varStatus. --%>
					<c:forEach items="${ requestScope.representationsAdmin }"
						var="representation" varStatus="boucle">
						<%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
						<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
							<%-- Affichage du nom des spectacles --%>
							<td><c:out value="${representation.nomSpectacle}" /></td>
							<td><joda:format value="${ representation.date }"
									pattern="EEEE dd MMMM yyyy '-' HH'h'" /></td>
							<%-- Lien vers la page de réservation de la représentation appropriée. --%>
							<td class="action"><input type="button" value="Annuler"
								onclick="self.location.href=
                        		'<c:url value="/admin/annulationRepresentation">
                        		<c:param name="idRepresentation" value="${ representation.id }"/>
                        		</c:url>'" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	</fieldset>
</body>
</html>
