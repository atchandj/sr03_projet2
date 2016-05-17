<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<title>Résultat obtenu</title>
	</head>
	<body>
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
			<c:set var="previousTopic" value="" scope="page" />
			<c:forEach var="attempt" items="${ attempts }" varStatus="status">	
				<c:if test="${ previousTopic != attempt.topicName }">
					<h3><c:out value="${ attempt.topicName }"/></h3>
					<c:set var="previousTopic" value="${ attempt.topicName }" scope="page" />		
				</c:if>
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel panel-default text-center">
							<div class="panel-heading">
								<h4><c:out value="${ attempt.questionnaireName }"/></h4>
							</div>
							<div class="panel-body">
								<dl>
								  <dt>Score (S) :</dt>
								  <dd><c:out value="${ attempt.score }"/></dd>
								  <dt>Début :</dt>
								  <dd><c:out value="${ attempt.begining }"/></dd>
								  <dt>Fin :</dt>
								  <dd><c:out value="${ attempt.end }"/></dd>
								  <dt>Durée en secondes (D) :</dt>
								  <dd><c:out value="${ attempt.durationInSeconds }"/></dd>
								  <dt>Score divisé par la durée multiplié par 100 ((S/D)*100) :</dt>
								  <dd><c:out value="${ attempt.scoreDivByDurationTimes100 }"/> </dd>
							  	</dl>
						  	</div>
				  		</div>
			  		</div>
		  		</div>
 			</c:forEach>
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>