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
		<c:set var="usersDataUrl" value="/super_user/user_data" scope="request" />
		<c:set var="usersManagementUrl" value="/super_user/users_management" scope="request" />
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
				        <c:forEach var="superUser" items="${ superUsers }">
				        	<tbody>
								<c:url value="${ usersDataUrl }" var="url1">
									<c:param name="action" value="consult" />
									<c:param name="type" value="super_user" />
									<c:param name="email" value="${ superUser.email }" />
								</c:url>
					        	<tr class="clickable-row" data-href="${ url1 }" title="Cliquer pour me modifier ou me consulter">
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
												<c:url value="${ usersManagementUrl }" var="url2">
													<c:param name="action" value="modify_status" />
													<c:param name="user_type" value="super_user" />
													<c:param name="activer" value="false" />
													<c:param name="email" value="${ superUser.email }" />
												</c:url>
												<a href="${ url2 }">Désactiver</a>
											</c:when>
											<c:otherwise>	
												<c:url value="${ usersManagementUrl }" var="url3">
													<c:param name="action" value="modify_status" />
													<c:param name="user_type" value="super_user" />
													<c:param name="activer" value="true" />
													<c:param name="email" value="${ superUser.email }" />
												</c:url>
												<a href="${ url3 }">Activer</a>			
											</c:otherwise>
										</c:choose>		        		
					        		</td>
									<c:url value="${ usersManagementUrl }" var="url4">
										<c:param name="action" value="delete" />
										<c:param name="user_type" value="super_user" />
										<c:param name="email" value="${ superUser.email }" />
									</c:url>
					        		<td><a href="${ url4 }">Supprimer</a></td> 
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
								<c:url value="${ usersDataUrl }" var="url5">
									<c:param name="action" value="consult" />
									<c:param name="type" value="trainee" />
									<c:param name="email" value="${ trainee.email }" />
								</c:url>
					        	<tr class="clickable-row" data-href="${ url5 }" title="Cliquer pour me modifier ou me consulter">
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
												<c:url value="${ usersManagementUrl }" var="url6">
													<c:param name="action" value="modify_status" />
													<c:param name="user_type" value="trainee" />
													<c:param name="activer" value="false" />
													<c:param name="email" value="${ trainee.email }" />
												</c:url>
												<a href="${ url6 }">Désactiver</a>
											</c:when>
											<c:otherwise>
												<c:url value="${ usersManagementUrl }" var="url7">
													<c:param name="action" value="modify_status" />
													<c:param name="user_type" value="trainee" />
													<c:param name="activer" value="true" />
													<c:param name="email" value="${ trainee.email }" />
												</c:url>
												<a href="${ url7 }">Activer</a>			
											</c:otherwise>
										</c:choose>			        		
					        		</td>
									<c:url value="${ usersManagementUrl }" var="url8">
										<c:param name="action" value="delete" />
										<c:param name="user_type" value="trainee" />
										<c:param name="email" value="${ trainee.email }" />
									</c:url>
					        		<td><a href="${ url8 }">Supprimer</a></td> 
					        	</tr>
				        	</tbody>
				        </c:forEach>						
			        </table>
			        </div>
			        </div>
		        </c:if>
		        <h4>
					<c:url value="${ usersManagementUrl }" var="url9">
						<c:param name="action" value="add" />
					</c:url>
		        	<a href="${ url9 }">Ajouter un utilisateur</a>
		        </h4>
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	    <script src="<c:url value="/js/script.js"/>"></script>
	</body>
</html>