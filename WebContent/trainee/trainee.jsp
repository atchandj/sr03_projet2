<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
		<%@ include file="./menu.jsp" %>
	    <c:if test="${ !empty sessionScope.trainee }">
		    <section>
		        <p>Vous êtes le stagiaire : ${ sessionScope.trainee.name } ${ sessionScope.trainee.surname } !</p>
		    	<p>Mail : <c:out value="${sessionScope.trainee.email}" /></p>
		    	<p>Phone : <c:out value="${sessionScope.trainee.phone}" /></p>
		    	<p>Company : <c:out value="${sessionScope.trainee.company}" /></p>
		    	<p>Data de création : <c:out value="${sessionScope.superuser.accountCreation}" /></p>
		    	<p>Etat : <c:out value="${sessionScope.trainee.accountStatus}" /></p>
	    	</section>
	    </c:if>
 	    <%@ include file="../footer.jsp" %>
	</body>
</html>