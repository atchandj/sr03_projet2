<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<c:url value="/datatables/datatables.min.css"/>"/>
		<script type="text/javascript" src="<c:url value="/datatables/datatables.min.js"/>"></script>
		<script src="<c:url value="/js/script.js"/>"></script>
		<title>Résultat obtenu</title>
	</head>
	<body>
		<c:set var="attemptUrl" value="/trainee/display_result" scope="request" />
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="displayResult" value="displayResult" />
		</jsp:include>
		
		<div class="container-fluid">
			<h3>Résultat obtenus </h3>
			<c:if test="${ !empty sessionScope.trainee }">
				<section>
					<c:if test="${ !empty errorMessage }">
				  	    <div id="subErrorMsg" class="alert alert-danger" role="alert"> 
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span><c:out value="${ errorMessage }" />
						</div>
					</c:if>
				
				</section>
			</c:if>		
			<table class="table table-hover table-bordered dataTable" >
				<thead>
		            <tr>
		                <th>Thème</th>
		                <th>Questionnaire</th>
		                <th>Score</th>
		                <th>Début</th>
		                <th>Fin</th>
		                <th>Durée en secondes</th>
		                <th> Action </th>
		            </tr>
		        </thead>
		        <tbody>
	                <c:forEach var="attempt" items="${ attempts }" varStatus="status">
	                	<c:url value="${ attemptUrl }" var="url">
							<c:param name="attemptId" value="${ attempt.id }" />
						</c:url>
	                	<tr class="clickable-row" data-href="${ url }" title="Cliquer pour voir les détails du parcours">
	                		<td>${ attempt.topicName }</td>
	                		<td>${ attempt.questionnaireName }</td>
	                		<td>${ attempt.score }</td>
	                		<td>${ attempt.begining }</td>
	                		<td>${ attempt.end }</td>
	                		<td>${ attempt.durationInSeconds }</td>
	                		<td> <a href=${ url }> Détail du parcours </a> </td> 
	                	</tr>
	                </c:forEach>
		        </tbody>	
			</table>
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>