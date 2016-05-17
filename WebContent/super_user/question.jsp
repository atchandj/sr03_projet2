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
		<div role="tabpanel" class="tab-pane active">
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
									<input type="text" id="questionValue" class="form-control" placeholder="Question" name="questionValue" value="" autofocus required/>
								</div>
								<input type="hidden" name="paction" value="add_question" />
								<input type="hidden" name="topic_name" value="${ topicName }" />
								<input type="hidden" name="questionnaire_name" value="${ questionnaireName }" />
								<input type="hidden" name="questionnaire_id" value="${ questionnaireId }" />
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
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>