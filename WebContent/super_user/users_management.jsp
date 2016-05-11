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
			<jsp:param name="usersManagement" value="usersManagement" />
		</jsp:include>
		<div class="container-fluid">
	    <c:if test="${ !empty sessionScope.superUser }">
	        <section>
				<c:if test="${! empty superUsers}">					
					<div class="row">
					<h1>Administrateur</h1>					
					<div class="table-responsive"> 
					<table class="table table-hover table-bordered title="Cliquer pour me modifier ou me consulter">
			        	<colgroup>
				            <col class="col-md-2">
				            <col class="col-md-2">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-2">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-1">
        				</colgroup>
						<thead>
							<tr>
								<th>Nom</th>
								<th>Prénom</th>
								<th>E-mail</th>
								<th>Téléphone</th>
								<th>Entreprise</th>
								<th>Date de création de compte</th>
								<th>Statut</th>
								<th>Modification de statut</th>
								<th>Suppression compte</th>
							</tr>
						</thead>
				        <c:forEach var="superUser" items="${ superUsers }">
				        	<tbody>
					        	<tr class="clickable-row" data-href="<c:url value="/super_user/user_data?action=consult&type=super_user&email=${ superUser.email }"/>" title="Cliquer pour me modifier ou me consulter">
					        		<td><c:out value="${ superUser.surname }" /></td>
					        		<td><c:out value="${ superUser.name }" /></td>
					        		<td><c:out value="${ superUser.email }" /></td>
					        		<td><c:out value="${ superUser.phone }" /></td>
					        		<td><c:out value="${ superUser.company }" /></td>
					        		<td><c:out value="${ superUser.accountCreation }" /></td>
					        		<td><c:out value="${ superUser.accountStatus }" /></td>
						        	<td>
			        					<c:choose>
											<c:when test="${ superUser.accountStatus == 'Le compte est activé' }">
												<a href="<c:url value="/super_user/users_management?action=modify_status&email=${ superUser.email }&user_type=super_user&activer=false"/>">Désactiver</a>
											</c:when>
											<c:otherwise>	
												<a href="<c:url value="/super_user/users_management?action=modify_status&email=${ superUser.email }&user_type=super_user&activer=true"/>">Activer</a>			
											</c:otherwise>
										</c:choose>		        		
					        		</td>
					        		<td><a href="<c:url value="/super_user/users_management?action=delete&email=${ superUser.email }&user_type=super_user"/>">Supprimer</a></td> 
					        	</tr>
				        	</tbody>
				        </c:forEach>						
			        </table>
			        </div>
			        </div>
		        </c:if>
		        
    			<c:if test="${! empty trainees}">
    				<div class="row">
    				<h1>Stagiaire</h1>
					<div class="table-responsive"> 
					<table class="table table-hover table-bordered">
			        	<colgroup>
				            <col class="col-md-2">
				            <col class="col-md-2">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-2">
				            <col class="col-md-1">
				            <col class="col-md-1">
				            <col class="col-md-1">
        				</colgroup>
						<thead>
							<tr>
								<th>Nom</th>
								<th>Prénom</th>
								<th>E-mail</th>
								<th>Téléphone</th>
								<th>Entreprise</th>
								<th>Date de création de compte</th>
								<th>Statut</th>
								<th>Modification de statut</th>
								<th>Suppression compte</th>
							</tr>
						</thead>
				        <c:forEach var="trainee" items="${ trainees }">
				        	<tbody>
					        	<tr class="clickable-row" data-href="<c:url value="/super_user/user_data?action=consult&type=trainee&email=${ trainee.email }"/>" title="Cliquer pour me modifier ou me consulter">
					        		<td><c:out value="${ trainee.surname }" /></td>
					        		<td><c:out value="${ trainee.name }" /></td>
					        		<td><c:out value="${ trainee.email }" /></td>
					        		<td><c:out value="${ trainee.phone }" /></td>
					        		<td><c:out value="${ trainee.company }" /></td>
					        		<td><c:out value="${ trainee.accountCreation }" /></td>
					        		<td><c:out value="${ trainee.accountStatus }" /></td>
					        		<td>
			        					<c:choose>
											<c:when test="${ trainee.accountStatus == 'Le compte est activé' }">
												<a href="<c:url value="/super_user/users_management?action=modify_status&email=${ trainee.email }&user_type=trainee&activer=false"/>">Désactiver</a>
											</c:when>
											<c:otherwise>	
												<a href="<c:url value="/super_user/users_management?action=modify_status&email=${ trainee.email }&user_type=trainee&activer=true"/>">Activer</a>			
											</c:otherwise>
										</c:choose>			        		
					        		</td>
					        		<td><a href="<c:url value="/super_user/users_management?action=delete&email=${ trainee.email }&user_type=trainee"/>">Supprimer</a></td> 
					        	</tr>
				        	</tbody>
				        </c:forEach>						
			        </table>
			        </div>
			        </div>
		        </c:if>
		        <p><a href="<c:url value="/super_user/users_management?action=add"/>">Ajouter un utilisateur</a></p>
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	    <script src="<c:url value="/js/script.js"/>"></script>
	</body>
</html>