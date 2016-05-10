<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Ajout d'un utilisateur</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
	    <%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="usersManagement" value="usersManagement" />
		</jsp:include>
		<div role="tabpanel" class="tab-pane active">
			<form method="post" action="<c:url value="/super_user/users_management"/>">
				<div class="row">
					<div class="col-md-4 col-lg-offset-4 " >
						<div class="panel panel-default">
							<div class="panel-heading">
								Ajout d'un utilisateur
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="email">E-mail de l'utilisateur : </label>
									<input type="email" id="email" class="form-control" placeholder="E-mail" name="email" autofocus required/>
								</div>		
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="password">Mot de passe de l'utilisateur : </label>
									<input type="password" id="password" class="form-control" placeholder="Mot de passe" name="password" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="surname">Nom de famille : </label>
									<input type="text" id="surname" class="form-control" placeholder="Nom de famille" name="surname" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="name">Prénom de l'utilisateur : </label>
									<input type="text" id="name" class="form-control" placeholder="Prénom" name="name" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="phone">Téléphone de l'utilisateur : </label>
									<input type="tel" id="phone" class="form-control" placeholder="Téléphone" name="phone" required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="company">Entreprise de l'utilisateur : </label>
									<input type="password" id="company" class="form-control" placeholder="Entreprise" name="company" required/>
								</div>		
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="type">Type d'utilisateur : </label>
									<select name="type" id="type" required>
										<option value="super_user">Administrateur</option>
										<option value="trainee">Stagiaire</option>
									</select>
								</div>	
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">								
									<label class="control-label" for="status">Statut du compte de l'utilisateur: </label>
									<select name="status" id="status" required>
										<option value="active">Actif</option>
										<option value="inactive">Inactif</option>
									</select>
								</div>		
								<input type="submit" class="btn btn-default" value="envoyer"/>
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