<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Liste des questionnaires</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value="/datatables/datatables.min.css"/>"/>
		<script type="text/javascript" src="<c:url value="/datatables/datatables.min.js"/>"></script>
		<script src="<c:url value="/js/script.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
		<c:set var="questionnaireUrl" value="/trainee/survey_list" scope="request" />
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="surveyList" value="surveyList" />
		</jsp:include>
		
		<div class="container-fluid">
			<h3>Listes des questionnaires </h3>
			
			<c:if test="${ !empty sessionScope.trainee }">
				<section>
					<c:if test="${ !empty errorMessage }">
				  	    <div id="subErrorMsg" class="alert alert-danger" role="alert"> 
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<span class="sr-only">Error:</span><c:out value="${ errorMessage }" />
						</div>
					</c:if>
					<table class="table table-bordered dataTable">
						<thead>
				            <tr>
				                <th>Thème</th>
				                <th>Questionnaire</th>
				                <th> Action </th>
				            </tr>
				        </thead>
				        <tbody>
				        <c:forEach var="topic" items="${ topics }" varStatus="status">
					        
				                <c:forEach var="questionnaire" items="${ topic.questionnaires }">
				                	<c:url value="${ questionnaireUrl }" var="url5">
										<c:param name="questionnaireId" value="${ questionnaire.id }" />
									</c:url>
				                	<tr class="clickable-row" data-href="${ url5 }" title="Cliquer pour lancer un parcours">
				                		<td>${ topic.name }</td>
				                		<td>${ questionnaire.name }</td>
				                		<td> <a href="${ url5 }"> Lancer un parcours </a> </td>
				                	</tr>
				                </c:forEach>
					        
				        </c:forEach>
				        </tbody>	
					</table>
	    	</section>
			</c:if>
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>