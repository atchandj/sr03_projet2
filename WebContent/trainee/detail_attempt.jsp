<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Répondre au questionnaire</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<script src="<c:url value="trainee.js"/>"></script>
	</head>
	<body>
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="surveyList" value="surveyList" />
		</jsp:include>
		
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-4 col-lg-offset-4 " >
					<div class="panel panel-default">
						<div class="panel-heading">
							Détail du questionnaire
						</div>
						<div class="panel-body">
							<dl class="dl-horizontal">
							  <dt>Questionnaire</dt>
							  <dd><c:out value="${ attempt.questionnaireName } " /></dd>
							  <dt>Score</dt>
							  <dd><c:out value="${ attempt.score } (${attempt.score * 100/ attempt.attemptedAnswers.size() }%)" /></dd>
							  <dt>Durée</dt>
							  <dd><c:out value="${ attempt.durationInSeconds }" /> sec</dd>
							</dl>
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
														<p class="text-danger">Ta réponse était fausse (<c:out value="${answer.value.value }" />.) </p>
													</c:when>
													<c:when test="${answer.key == 'answerGood' }">
														<p class="text-success">La bonne réponse est : <c:out value="${answer.value.value }" />. </p>
													</c:when>
													<c:otherwise>
														<p class="text-success">Ta réponse était bonne : <c:out value="${answer.value.value }" />. </p>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</li>
									</div>
									
								</ul>
							</c:forEach>
							<a class="btn btn-default" href="<c:url value="/trainee/display_result" />" role="button">Retour aux résultats</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>