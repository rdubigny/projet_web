<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<title>Reservations effectués par les clients </title>
</head>
<body>
	<c:import url="/inc/header.jsp" />

	<c:choose>
		<%-- Si aucune représentation n'est transmise en requète, affichage d'un message par défaut. --%>
		<c:when
			test="${ empty requestScope.reservationsAdmin && suppression != 1}">
			<p class="erreur">Aucune réservations pour le moment.</p>
		</c:when>
		<%-- Sinon, affichage du tableau. --%>
		<c:otherwise>
			<c:if test="${ param.suppression == 1 }">
				<p class="succes">La réservation a bien été annulée</p>
			</c:if>
			<table>
				<%-- Titre des colonnes --%>
				<tr>
					<th>Client</th>
					<th>Représentation</th>
					<th>Spectacle</th>
					<th>Numéro de Rang</th>
					<th>Numéro de Siège</th>
					<th>Zone</th>
					<th>Prix</th>
					<th class="action">Annuler</th>
				</tr>
				<%-- Parcours de la listes des reservations en requête, et utilisation de l'objet varStatus. --%>
				<c:forEach items="${requestScope.reservationsAdmin }"
					var="reservation" varStatus="boucle">
					<%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
					<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
						<%-- Colonne Client --%>
						<td><c:out
								value="${ reservation.prenomClient} ${reservation.nomClient}" /></td>
						<%-- Colonne Représenation --%>
						<td><joda:format value="${ reservation.date }"
								pattern="EEEE dd MMMM yyyy 'à' HH 'heures'" /></td>
						<%-- Colonne Spectacle --%>
						<td><c:out value="${ reservation.spectacle }" /></td>
						<%-- Colonne Numéro de Rang --%>
						<td><c:out value="${ reservation.rang }" /></td>
						<%-- Colonne Numéro de Siège --%>
						<td><c:out value="${ reservation.siege }" /></td>
						<%-- Colonne Zone --%>
						<td><c:out value="${ reservation.zone }" /></td>
						<%-- Colonne Prix --%>
						<td><c:out value="${ reservation.prix }" /></td>
						<%-- Colonne Annuler --%>
						<td>
							<%-- Bouton pour annuler --%> <input type="button"
							value="Annuler"
							onclick="self.location.href='<c:url value='/admin/annulationReservation'><c:param name='idReservation' value='${ reservation.id }' /></c:url>'" />
						</td>

					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>