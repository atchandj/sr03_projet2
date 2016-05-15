<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Répondre au questionnaire</title>
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
			<form method="post" action="<c:url value="/super_user/users_management"/>">
				<div class="row">
					<div class="col-md-4 col-lg-offset-4 " >
						<div class="panel panel-default">
							<div class="panel-heading">
								Question n° <c:out value="${ index + 1 }" />
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="question"><c:out value="${ questions.get(index).value }" /> </label>
									<input type="text" id="question" class="form-control" placeholder="Réponse" name="question" autofocus required/>
								</div>		
								
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
		</div>
		
	    <%@ include file="/footer.jsp" %>
	</body>
</html>