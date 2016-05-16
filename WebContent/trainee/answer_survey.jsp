<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>R�pondre au questionnaire</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<script src="<c:url value="surveyList.js"/>"></script>
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
								Question n� <c:out value="${ index + 1 }" />
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
							R�sultat
						</div>
						<div class="panel-body">
							<dl class="dl-horizontal">
							  <dt>Score</dt>
							  <dd><c:out value="${ attempt.score } (${attempt.score * 100/ attempt.attemptedAnswers.size() }%)" /></dd>
							  <dt>D�but</dt>
							  <dd><c:out value="${ attempt.begining }" /></dd>
							  <dt>Fin</dt>
							  <dd><c:out value="${ attempt.end }" /></dd>
							</dl>
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