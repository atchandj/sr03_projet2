<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Administrateur</title>
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
	    <c:if test="${ !empty sessionScope.superUser }">
	        <section>
				<c:forEach var="topic" items="${ topics }" varStatus="status">
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel-group">
							<div class="panel panel-default">
								<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" href="#<c:out value="${ status.index }" />"><c:out value="${ topic.name }" /></a>
								</h4>
								</div>
								<div id="${status.index}" class="panel-collapse collapse">
									<c:if test="${ !empty sessionScope.superUser }">
										<ul class="list-group">
											<c:forEach var="questionnaire" items="${ topic.questionnaires }">
												<li class="list-group-item 
													<c:choose>
														<c:when test="${ questionnaire.active == true }">list-group-item-success</c:when>
														<c:otherwise>list-group-item-warning</c:otherwise>
													</c:choose>
												" >
													<a href="#" title="Cliquez pour me consulter"><c:out value="${ questionnaire.name }" /></a>
													<c:if test="${ questionnaire.activable == true }">
														<a href="#" class="btn btn-info" role="button">Activer le questionnaire</a>
													</c:if>
												</li>
											</c:forEach>
										</ul>
									</c:if>
									<div class="panel-footer">
										<a href="#" title="Cliquez si vous souhaitez en ajouter un">Ajouter un questionnaire </a>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
				<form method="post" action="<c:url value="/super_user/forms_management"/>">
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title text-center">
									Ajout d'un sujet
								</h4>
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="newTopicName">Nom du sujet : </label>
									<input type="text" id="newTopicName" class="form-control" placeholder="Nom du sujet" name="newTopicName" autofocus required/>
								</div>	
								<input type="submit" class="btn btn-default" value="Ajouter"/>
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
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>