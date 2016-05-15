<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Administrateur</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
		<%@ include file="/header.jsp" %>
		<jsp:include page="./menu.jsp" >
			<jsp:param name="superUserHome" value="superUserHome" />
		</jsp:include>
		<div class="container-fluid">
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
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>