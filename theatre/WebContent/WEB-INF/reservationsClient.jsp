<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Vos réservations</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<%-- En-tête de connexion et deconnexion --%>
	<c:import url="/inc/header.jsp" />
	<fieldset>
		<legend>Vos Réservations</legend>
		<h3>Choisissez les réservations que vous voulez payer ou annuler.</h3>
		<div id="corps">
			<p>Attention, vos éventuelles réservations de places pour des
				représentations qui ont déjà eu lieu, ou qui ont lieu dans moins
				d'une heure, ont été annulées.</p>
		</div>
		<c:choose>
			<%-- Si aucune réservation n'est transmise --%>
			<c:when test="${ empty requestScope.reservations }">
				<p class="erreur">Vous n'avez aucune réservation.</p>
			</c:when>
			<%-- Sinon, affichage du tableau de réservations. --%>
			<c:otherwise>
				<form action="<c:url value='/confirmation'/>" method="post">
					<table>
						<%-- Titre des colonnes --%>
						<tr>
							<th>Représentation</th>
							<th>Spectacle</th>
							<th>Numéro de Rang</th>
							<th>Numéro de Siège</th>
							<th>Zone</th>
							<th>Prix</th>
							<th class="action">Annuler</th>
							<th class="action">Choisir de Payer</th>
						</tr>
						<%-- Parcours de la listes des reservations en requête, et utilisation de l'objet varStatus. --%>
						<c:forEach items="${requestScope.reservations }" var="reservation" varStatus="boucle">
							<%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
							<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
								<%-- Colonne Représenation --%>
								<td><c:out value="${ reservation.representation }" /></td>
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
									<%-- Bouton pour annuler --%>
									<input type="button" value="Annuler" 
                        				   onclick="self.location.href='<c:url value='/reservationsClient'><c:param name='id' value='${ reservation.id }' /></c:url>'"
                       				/>   
								</td>
								<%-- Colonne Choisir de Payer --%>
								<td>
									<%-- Checkbox pour choisir de payer --%>
									<input type="checkbox" name="id" value="reservation" >
								</td>	
							</tr>
						</c:forEach>
					</table>
					<%-- Lien vers la page de confirmation Submit --%>
					<input type="submit" value="Payer le total">
				</form>
			</c:otherwise>
		</c:choose>
	</fieldset>
</body>
</html>
