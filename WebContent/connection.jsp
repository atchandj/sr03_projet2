<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>
	    <%@ include file="/header.jsp" %>   
		<div class="container-fluid">
			<div role="tabpanel" class="tab-pane active">
				<form method="post" action="<c:url value="/connection"/>">
					<div class="row">
						<div class="col-md-4 col-md-offset-4 " >
							<div class="panel panel-default">
								<div class="panel-heading">
									Connexion
								</div>
								<div class="panel-body">
									<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
										<label class="control-label" for="email">E-mail : </label>
										<input type="text" id="email" class="form-control" placeholder="E-mail" name="email" autofocus/>
									</div>		
									<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
										<label class="control-label" for="password">Mot de passe : </label>
										<input type="password" id="password" class="form-control" placeholder="Mot de passe" name="password"/>
									</div>
									<input type="submit" class="btn btn-default" value="Me connecter"/>
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