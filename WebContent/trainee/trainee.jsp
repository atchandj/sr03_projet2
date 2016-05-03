<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
	    <c:if test="${ !empty sessionScope.traineeSurname && !empty sessionScope.traineeName }">
	        <p>Vous êtes le stagiaire : ${ sessionScope.traineeUserName } ${ sessionScope.traineeSurname } !</p>
	    </c:if>  
	    <nav>
	   	    <ul>
		    	<li><a href="#" title="Vers la liste des questionnaires">Liste des questionnaires</a></li>
		    	<li><a href="#" title="Vers les résultats obtenus">Résultats obtenus</a></li>
		    </ul>
	    </nav>
	</body>
</html>