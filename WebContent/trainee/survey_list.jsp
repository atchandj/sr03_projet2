<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Liste des questionnaires</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="surveyList" value="surveyList" />
		</jsp:include>
		
		<div class="container-fluid">
			<h1> Questionnaires </h1>
		</div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>