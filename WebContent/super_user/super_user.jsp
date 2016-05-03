<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
		<%@ include file="./menu.jsp" %>
	    <c:if test="${ !empty sessionScope.superUser }">
	        <section>
		        <p>Vous êtes l'administrateur : ${ sessionScope.superUser.name } ${ sessionScope.superUser.surname } !</p>
		    	<p>Mail : <c:out value="${sessionScope.superUser.email}" /></p>
		    	<p>Phone : <c:out value="${sessionScope.superUser.phone}" /></p>
		    	<p>Company : <c:out value="${sessionScope.superUser.company}" /></p>
		    	<p>Data de création : <c:out value="${sessionScope.superUser.accountCreation}" /></p>
		    	<p>Etat : <c:out value="${sessionScope.superUser.accountStatus}" /></p>
	    	</section>
	    </c:if>
	    <%@ include file="../footer.jsp" %>
	</body>
</html>