<div id="menu">	
	<c:choose>
		<c:when test="${! empty sessionScope.sessionUtilisateur}">
   			<span class="info">Vous �tes connect�(e) en tant que ${sessionScope.sessionUtilisateur.login}</span>
 			<input type="button" value="D�connexion" onclick="self.location.href='<c:url value='/deconnexion'/>'" />
		</c:when>
		<c:otherwise>
			<span class="info">Vous n'�tes pas connect�(e).</span>
		</c:otherwise>
	</c:choose>
</div>