<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Répondre au questionnaire</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<script src="<c:url value="/script.js"/>"></script>
	</head>
	<body>
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="surveyList" value="surveyList" />
		</jsp:include>
		
		<div class="container-fluid">
		<c:choose>
		<c:when test="${ end == false }">
			<form method="post" action="<c:url value="/trainee/survey_list"/>">
				<div class="row">
					<div class="col-md-4 col-lg-offset-4 " >
						<div class="panel panel-default">
							<div class="panel-heading">
								Question n° <c:out value="${ index + 1 }" />
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="question"><c:out value="${ question.value }" /> </label>
									<c:forEach var="answer" items="${ question.answers }" >
										<div class="radio">
										  <label>
										    <input type="radio" name="answerId" value="<c:out value="${answer.id }" />">
										    	<c:out value="${answer.value }" />
										  </label>
										</div>
									</c:forEach>
								</div>		
								<input type="hidden" name="index" value="<c:out value="${index }" />">
								<input type="submit" class="btn btn-default" value="Suivant"/>
								<c:if test="${ !empty errorMessage }">
							  	    <div id="subErrorMsg" class="alert alert-danger" role="alert"> 
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										<span class="sr-only">Error:</span><c:out value="${ errorMessage }" />
									</div>
								</c:if>					
							</div>
						</div>
					</div>
				</div>
			</form>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="col-md-4 col-lg-offset-4 " >
					<div class="panel panel-default">
						<div class="panel-heading">
							Résultat
						</div>
						<div class="panel-body">
							<dl class="dl-horizontal">
							  <dt>Score</dt>
							  <dd><c:out value="${ attempt.score } (${attempt.score * 100/ attempt.attemptedAnswers.size() }%)" /></dd>
							  <dt>Début</dt>
							  <dd><c:out value="${ attempt.begining }" /></dd>
							  <dt>Fin</dt>
							  <dd><c:out value="${ attempt.end }" /></dd>
							  <dt>Durée</dt>
							  <dd><c:out value="${ attempt.durationInSeconds }" /> sec</dd>
							</dl>
							<c:if test="${ questions.size() > 10 }">
								<a class="btn btn-default" href="<c:url value="/trainee/survey_list" />" role="button">Retour à la liste des questionnaires</a>
							</c:if>
							<c:forEach var="question" items="${ questions }" >
								<ul class="list-group">
									<li class="list-group-item">
										<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${question.id }"/>" aria-expanded="false" aria-controls="collapse<c:out value="${question.id }"/>">
											<c:out value="${question.value }" />
										</a>									
									</li>
									<div class="panel-collapse collapse" id="collapse<c:out value="${question.id }"/>">
										<li class="list-group-item">
											<c:forEach var="answer" items="${ attempt.compareAnswer(question) }" >
												<c:choose>
													<c:when test="${answer.key == 'zfalseAnswer' }">
														<p class="text-danger">Ta réponse est fausse : <c:out value="${answer.value.value }" />. </p>
													</c:when>
													<c:when test="${answer.key == 'answerGood' }">
														<p class="text-success">La bonne réponse est : <c:out value="${answer.value.value }" />. </p>
													</c:when>
													<c:otherwise>
														<p class="text-success">Ta réponse est bonne : <c:out value="${answer.value.value }" />. </p>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</li>
									</div>
									
								</ul>
							</c:forEach>
							<a class="btn btn-default" href="<c:url value="/trainee/survey_list" />" role="button">Retour à la liste des questionnaires</a>
						</div>
					</div>
				</div>
			</div>
		</c:otherwise>
		</c:choose>	
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>