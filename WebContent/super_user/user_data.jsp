<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Administrateur</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="<c:url value="/datatables/datatables.min.css"/>"/>
		<script type="text/javascript" src="<c:url value="/datatables/datatables.min.js"/>"></script>
		<script src="<c:url value="/js/script.js"/>"></script>
	</head>
	<body>
		<%@ include file="/header.jsp" %>
		<jsp:include page="./menu.jsp" >
			<jsp:param name="usersManagement" value="usersManagement" />
		</jsp:include>
		<div class="container-fluid">
			<div class="row">
			<h1>Informations sur un utilisateur</h1>
			<h2>Données personnelles</h2>
				<form method="post" action="<c:url value="/super_user/user_data"/>">				
					<div class="col-md-6 col-md-offset-3 " >
						<div class="panel panel-default">
							<div class="panel-heading">
								Modification(s) d'un utilisateur
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="email">E-mail de l'utilisateur : </label>
									<input type="email" id="email" class="form-control" placeholder="E-mail" name="email" value="${user.email}" autofocus required readonly/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="surname">Nom de famille : </label>
									<input type="text" id="surname" class="form-control" placeholder="Nom de famille" name="surname" value="${user.surname}" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="name">Prénom de l'utilisateur : </label>
									<input type="text" id="name" class="form-control" placeholder="Prénom" name="name" value="${user.name}" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="phone">Téléphone de l'utilisateur : </label>
									<input type="tel" id="phone" class="form-control" placeholder="Téléphone" name="phone" value="${user.phone}" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="company">Entreprise de l'utilisateur : </label>
									<input type="text" id="company" class="form-control" placeholder="Entreprise" name="company" value="${user.company}" required/>
								</div>		
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="type">Type d'utilisateur : </label>
									<input type="text" id="type" class="form-control" placeholder="Type d'utilisateur" name="type" value="${type}" required readonly/>
								</div>	
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">								
									<label class="control-label" for="status">Statut du compte de l'utilisateur: </label>
									<input type="text" id="status" class="form-control" placeholder="Statut de l'utilisateur" name="status" value="${user.accountStatus}" required readonly/>
								</div>
								<a class="btn btn-default" href="<c:url value="/super_user/users_management" />" role="button">Retour</a>		
								<input type="submit" class="btn btn-default" value="Modifier"/>
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
				</form>
			</div>
			<c:if test="${ type == 'Stagiaire' }">
				<h2>Parcours</h2>		
				<table class="table table-bordered dataTable">
					<thead>
			            <tr>
			                <th>Thème</th>
			                <th>Questionnaire</th>
			                <th>Score</th>
			                <th>Début</th>
			                <th>Fin</th>
			                <th>Durée en secondes</th>
			            </tr>
			        </thead>
			        <tbody>
		                <c:forEach var="attempt" items="${ attempts }" varStatus="status">
		                	<tr>
		                		<td>${ attempt.topicName }</td>
		                		<td>${ attempt.questionnaireName }</td>
		                		<td>${ attempt.score }</td>
		                		<td>${ attempt.begining }</td>
		                		<td>${ attempt.end }</td>
		                		<td>${ attempt.durationInSeconds }</td>
		                	</tr>
		                </c:forEach>
			        </tbody>	
				</table>
			</c:if>
		</div>		
	    <%@ include file="/footer.jsp" %>		
	</body>
</html>