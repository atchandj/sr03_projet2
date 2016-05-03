<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
	    <c:if test="${ !empty sessionScope.superUserSurname && !empty sessionScope.superUserName }">
	        <p>Vous êtes l'administrateur : ${ sessionScope.superUserName } ${ sessionScope.superUserSurname } !</p>
	    </c:if>
	    <nav>
	   	    <ul>
		    	<li><a href="#" title="Vers la gestion des utilisateurs">Gestion des utilisateurs</a></li>
		    	<li><a href="#" title="Vers la gestion des questionnaires">Gestion des questionnaires</a></li>
		    </ul>
	    </nav>
	</body>
</html>