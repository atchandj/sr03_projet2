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
			<h1>Vous </h1>
			<h2>Donn�es personnelles</h2>
			<c:set var="user" value="${ sessionScope.superUser }" scope="request" />
			<c:set var="type" value="Administrateur" scope="request" />
			<jsp:include page="../WEB-INF/user.jsp" />
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>