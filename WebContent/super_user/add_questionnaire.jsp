<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Ajout d'un questionnaire</title>
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
			<form method="post" action="<c:url value="/super_user/forms_management"/>">
				<div class="row">
					<div class="col-md-4 col-lg-offset-4 " >
						<div class="panel panel-default">
							<div class="panel-heading">
								Ajout d'un questionnaire
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="topicName">Nom du sujet : </label>
									<input type="text" id="topicName" class="form-control" placeholder="Nom du sujet" name="topicName" value="${ topic_name }" autofocus required readonly/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="questionnaireName">Nom du questionnaire : </label>
									<input type="text" id="questionnaireName" class="form-control" placeholder="Nom du questionnaire" name="questionnaireName" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="questionnaireState">Etat du questionnaire : </label>
									<input type="text" id="questionnaireState" class="form-control" placeholder="Prénom" name="questionnaireState" value="Inactif" required readonly/>
								</div>	
								<input type="hidden" name="paction" value="add_questionnaire" />
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
		</div> 
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>