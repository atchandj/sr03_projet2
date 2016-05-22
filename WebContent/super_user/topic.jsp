<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Sujet</title>
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
			<form method="post" action="<c:url value="/super_user/forms_management"/>">
				<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title text-center">
								Sujet
							</h4>
						</div>
						<div class="panel-body">
							<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
								<label class="control-label" for="newTopicName">Nom du sujet : </label>
								<input type="text" id="newTopicName" class="form-control" placeholder="Nom du sujet" name="newTopicName" value="<c:out value="${ topic_name }"/>" autofocus required/>
							</div>
							<c:if test="${ !empty topic_name }">
								<input type="hidden" name="oldTopicName" value="${ topic_name }" />
							</c:if>
							<input type="hidden" name="paction" value="${ paction }" />	
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