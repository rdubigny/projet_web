<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Choix des places</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<!--  ici on affichera le nom du spectacle choisi, et un plan de la salle avec des
check box pour choisir la ou les places (sous la forme d'un formulaire). En bas de la page
deux boutons input = submit : un pour acheter, un pour reserver -->
	<fieldset>
		<legend>Choix des places</legend>
		<h3>Choisissez votre place pour ${
			sessionScope.representation.nomSpectacle }</h3>
		<div id="corps">
			<c:choose>
				<c:when test="${ requestScope.placesRestantes > 0 }">
					<p>
						Il reste actuellement ${ requestScope.placesRestantes } ${
						requestScope.placesRestantes == 1 ? "place" : "places" } pour la
						séance du
						<joda:format value="${ sessionScope.representation.date }"
							pattern="EEEE dd MMMM yyyy 'à' HH 'heures'" />
						.
					</p>
					<p>Le prix des places en fonction des zones :</p>
					<table id="tableauZone">
						<tr>
							<c:forEach items="${ zones }" var="zone">
								<td align="center" valign="middle"><c:out value="${ zone.nom }" /></td>
							</c:forEach>
						</tr>
						<tr>
							<td bgcolor="#008080"></td>
							<td bgcolor="#DAA520"></td>
							<td bgcolor="#D2B48C"></td>
							<td bgcolor="#CD853F"></td>
						</tr>
						<tr>
							<c:forEach items="${ zones }" var="zone">
								<td><c:out value="${ zone.prix }" /></td>
							</c:forEach>
						</tr>
					</table>
					<p class="scene">SCENE</p>
					<form action="<c:url value='/confirmation'/>" method="post" name="formulaire">
						<table id="tableauPlace">
							<c:forEach items="${ places }" var="rang">
								<tr>
									<c:forEach items="${ rang }" var="place">
										<td width="16" height="16" align="center" valign="middle"
											bgcolor=<c:choose>
            										<c:when test="${place.zone == 1}">"#008080"</c:when>
            										<c:when test="${place.zone == 2}">"#DAA520"</c:when>
			            							<c:when test="${place.zone == 3}">"#D2B48C"</c:when>
            										<c:when test="${place.zone == 4}">"#CD853F"</c:when>
            										<c:otherwise>"#BC8F8F"</c:otherwise>
            									</c:choose>>
											<c:if test="${! place.occupe}">
												<input type="checkbox" name="id" value="${ place.id }" onclick="calculPrix(this);">
											</c:if>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
						<%-- Affichage nombre de places  et prix total  --%>
						<p>
							<span id="nbPlace">Nombre de places : 0</span><span>&nbsp; &nbsp; &nbsp; &nbsp;</span><span id="total">Prix Total : 00,00 euro(s)</span>
						</p>
						<br /> <input type="submit" value="Acheter">
						<c:if test="${! estGuichet}">
							<input type="checkbox" name="action" value="reservation"> je veux seulement réserver, je paierai plus tard
        			</c:if>
					</form>
				</c:when>
				<c:otherwise>
					<p class="erreur">
						La représentation du
						<joda:format value="${ sessionScope.representation.date }"
							pattern="EEEE dd MMMM yyyy 'à' HH 'heures'" />
						est complête.
					</p>
					<p>
						<input type="button" value="Retour au choix de la représentation"
							onclick="self.location.href=
                        	'<c:url value='/choixRepresentation'>
                        		<c:param name='id' value='${ sessionScope.representation.idSpectacle }' />						
                        	</c:url>'" />
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</fieldset>
	<script type="text/javascript">
	// fonction appelée pour calculer le prix total et le nombre de places lorsqu'on clique sur 
	// une checkbox de name=id
	function calculPrix(cell){
		var prixTotal = 0.0;
		var nbPlace = 0;
		//document.write("test");
		// récupération des prix des places
		var prixPlacePlatine = parseFloat(document.getElementById("tableauZone").rows[2].cells[0].innerHTML);
		var prixPlaceOr = parseFloat(document.getElementById("tableauZone").rows[2].cells[1].innerHTML);
		var prixPlaceArgent = parseFloat(document.getElementById("tableauZone").rows[2].cells[2].innerHTML);
		var prixPlaceCuivre = parseFloat(document.getElementById("tableauZone").rows[2].cells[3].innerHTML);
		// parcours les checkbox du tableau des places
		// en fonction de la valeur des checkbox qui est l'identifiant de la place
		// qui détermine en fait la position de la place dans la salle.
		// En regardant le plan de la salle :
		// - les identifaint de places vont de 1 à 600
		// - Commence à 1 en haut à gauche 
		// - S'incrémente de 1 de gauche à droite et de haut en bas dans une zone.
		// - commence par incrémenter le premier bloc de la zone or, puis le deuxième
		// bloc de la zone Or, puis la zone Platine, puis la zone Argent, puis la zone Cuivre
		// dépend de l'ordre d'insertion des places lors de l'initialisation de la table place.
		var prixPlace;
		var idPlace;
		for(var i=0;i<document.formulaire.id.length;i++){
			// calcul le prix de la place
			idPlace = document.formulaire.id[i].value;
			if (( 101 <= idPlace && idPlace <= 150)){
				// place Platine
				prixPlace = prixPlacePlatine;
			}else if(151 <= idPlace && idPlace <= 450){
				// place Argent
				prixPlace = prixPlaceArgent;
			}else if(451 <= idPlace && idPlace <= 600){
				// place Cuivre
				prixPlace = prixPlaceCuivre;
			} else {
				// place Or
				prixPlace = prixPlaceOr;
			}
			if(document.formulaire.id[i].checked){
				prixTotal += prixPlace;
				nbPlace++;
			} 
		}
		document.getElementById("total").innerHTML = "Prix Total : " + prixTotal.toFixed(2) + "euro(s)";
		document.getElementById("nbPlace").innerHTML = "Nombre de places : " + nbPlace;
	}
	</script>
</body>
</html>