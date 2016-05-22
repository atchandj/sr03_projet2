<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Question</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="formsManagement" value="formsManagement" />
		</jsp:include>
		<div class="container-fluid">
			<form method="post" action="<c:url value="/super_user/questions_management"/>">
				<div class="row">
					<div class="col-md-4 col-md-offset-4">
						<div class="panel panel-default">
							<div class="panel-heading">
								Question
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="questionValue">Question :</label>
									<input type="text" id="questionValue" class="form-control" placeholder="Question" name="questionValue" value="<c:out value="${ question.value }"/>" autofocus required/>
								</div>
								<c:choose>
									<c:when test="${ !empty question }"><input type="hidden" name="question_id" value="${ question.id }" /></c:when>									
									<c:otherwise><input type="hidden" name="questionnaire_id" value="<c:out value="${ questionnaireId }"/>" /></c:otherwise>
								</c:choose>
								<input type="hidden" name="paction" value="<c:out value="${ paction }"/>" />
								<input type="hidden" name="topic_name" value="<c:out value="${ topicName }"/>" />
								<input type="hidden" name="questionnaire_name" value="<c:out value="${ questionnaireName }"/>" />
								<input type="submit" class="btn btn-default" value="Envoyer"/>
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
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>