<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />

<title>Espace Responsable</title>
</head>
<body>
	<c:import url="/inc/header.jsp" />	
	<fieldset>
	<legend>Espace Responsable de la Programmation</legend>
		<div id="corps">
			<p class="action"><input type="button" value="Gérer les réservations"
				onclick="self.location.href=
                        		'<c:url value='/responsable/reservationsResponsable'>
                        		</c:url>'" />
			</p>
			<p class="action"><input type="button" value="Gérer les représentations"
				onclick="self.location.href=
        	                	'<c:url value='/responsable/gererRepresentationsResponsable'>
                        		</c:url>'" />
			</p>
					<p class="action"><input type="button" value="Statistiques"
				onclick="self.location.href=
                        		'<c:url value='/responsable/statistiques'>
                        		</c:url>'" />
			</p>
		</div>
	</fieldset>
</body>
</html>