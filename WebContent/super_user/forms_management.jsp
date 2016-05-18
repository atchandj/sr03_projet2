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
		<c:set var="formsUrl" value="/super_user/forms_management" scope="request" />
		<c:set var="questionsUrl" value="/super_user/questions_management" scope="request" />
		<%@ include file="/header.jsp" %>   
		<jsp:include page="./menu.jsp" >
			<jsp:param name="formsManagement" value="formsManagement" />
		</jsp:include>
		<div class="container-fluid">
	    <c:if test="${ !empty sessionScope.superUser }">
	        <section>
	        	<h1>Sujets</h1>
				<c:forEach var="topic" items="${ topics }" varStatus="status">
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel-group">
							<div class="panel panel-default">
								<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" href="#<c:out value="${ status.index }" />"><c:out value="${ topic.name }" /></a> 
									<c:url value="${ formsUrl }" var="url1">
										<c:param name="action" value="modify_topic" />
										<c:param name="topic_name" value="${ topic.name }" />
									</c:url>
									<a href="${ url1 }" class="btn btn-warning" role="button">Modifier</a>
									<c:if test="${empty topic.questionnaires}">
										<c:url value="${ formsUrl }" var="url2">
											<c:param name="action" value="delete_topic" />
											<c:param name="topic_name" value="${ topic.name }" />
										</c:url>
										<a href="${ url2 }" class="btn btn-danger" role="button">Supprimer</a>
									</c:if> 
								</h4>
								</div>
								<div id="${status.index}" class="panel-collapse collapse">
									<ul class="list-group">
										<c:forEach var="questionnaire" items="${ topic.questionnaires }">
											<li class="list-group-item 
												<c:choose>
													<c:when test="${ questionnaire.active == true }">list-group-item-success</c:when>
													<c:otherwise>list-group-item-danger</c:otherwise>
												</c:choose>
											" >
												<c:url value="${ questionsUrl }" var="url3">
													<c:param name="questionnaire_name" value="${ questionnaire.name }" />
													<c:param name="topic_name" value="${ topic.name }" />
												</c:url>
												<a href="${ url3 }" title="Cliquez pour me consulter"><c:out value="${ questionnaire.name }" /></a>
												<c:if test="${ questionnaire.activable == true }">
													<c:url value="${ formsUrl }" var="url4">
														<c:param name="action" value="activate_questionnaire" />
														<c:param name="questionnaire_name" value="${ questionnaire.name }" />
														<c:param name="topic_name" value="${ topic.name }" />
													</c:url>
													<a href="${ url4 }" class="btn btn-success" role="button">Activer</a>
												</c:if>
												<c:if test="${ questionnaire.active == false }">
													<c:url value="${ formsUrl }" var="url5">
														<c:param name="action" value="modify_questionnaire" />
														<c:param name="questionnaire_name" value="${ questionnaire.name }" />
														<c:param name="topic_name" value="${ topic.name }" />
													</c:url>
													<a href="${ url5 }" class="btn btn-warning" role="button">Modifier</a>
												</c:if>
												<c:if test="${ questionnaire.deletable == true }">
													<c:url value="${ formsUrl }" var="url6">
														<c:param name="action" value="delete_questionnaire" />
														<c:param name="questionnaire_name" value="${ questionnaire.name }" />
														<c:param name="topic_name" value="${ topic.name }" />
													</c:url>
													<a href="${ url6 }" class="btn btn-danger" role="button">Supprimer</a>
												</c:if>
											</li>
										</c:forEach>
									</ul>
									<div class="panel-footer">
										<c:url value="${ formsUrl }" var="url7">
											<c:param name="action" value="add_questionnaire" />
											<c:param name="topic_name" value="${ topic.name }" />
										</c:url>
										<a href="${ url7 }" title="Cliquez si vous souhaitez en ajouter un">Ajouter un questionnaire </a>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
				<c:url value="${ formsUrl }" var="url8">
					<c:param name="action" value="add_topic" />
				</c:url>
				<h4><a href="${ url8 }" title="Cliquez si vous souhaitez en ajouter un">Ajouter un sujet</a></h4>
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>