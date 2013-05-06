<div id="menu">	
	<c:choose>
		<c:when test="${! empty sessionScope.sessionUtilisateur}">
   			<span class="info">Vous êtes connecté(e) en tant que ${sessionScope.sessionUtilisateur.login}</span>
 			<input type="button" value="Déconnexion" onclick="self.location.href='<c:url value='/deconnexion'/>'" />
 			<c:choose>
 				<c:when test="${ sessionScope.sessionUtilisateur.estResponsable() }">
 					<input type="button" value="Espace Responsable" onclick="self.location.href='<c:url value='/responsable/espaceResponsable'/>'" />
 				</c:when>
 				<c:otherwise>
					<input type="button" value="Espace Client" onclick="self.location.href='<c:url value='/espaceClient'/>'" />
				</c:otherwise>
 			</c:choose>
		</c:when>
		<c:otherwise>
			<span class="info">Vous n'êtes pas connecté(e).</span>
		</c:otherwise>
	</c:choose>
</div>