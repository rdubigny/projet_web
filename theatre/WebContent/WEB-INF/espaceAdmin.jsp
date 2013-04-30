<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />

<title>Espace Admin</title>
</head>
<body>
	<c:import url="/inc/header.jsp" />
	<div id="menu">Espace Administrateur</div>
	<div id="corps">
		<p class="action"><input type="button" value="Gérer les réservations"
			onclick="self.location.href=
                        	'<c:url value='/admin/reservationsAdmin'>
                        	</c:url>'" />
		</p>
		<p class="action"><input type="button" value="Gérer les représentations"
			onclick="self.location.href=
                        	'<c:url value='/admin/gererRepresentationsAdmin'>
                        	</c:url>'" />
		</p>
				<p class="action"><input type="button" value="Statistiques"
			onclick="self.location.href=
                        	'<c:url value='/admin/statistiques'>
                        	</c:url>'" />
		</p>
	</div>
</body>
</html>