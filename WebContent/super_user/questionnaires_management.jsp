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
				<c:forEach var="question" items="${ questionnaire.questions }" varStatus="status">	
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel-group">
							<div class="panel 
								<c:choose>
									<c:when test="${ question.active == true }">panel-success</c:when>
									<c:otherwise>panel-danger</c:otherwise>
								</c:choose>						
							">
								<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" href="#<c:out value="${ status.index }" />"><c:out value="${ question.value }" /></a> 
									<c:if test="${question.activable}">
										<a href="<c:url value="/super_user/questions_management?action=activate_question&questionnaire_id=${ question.questionnaireId }&question_order_number=${ question.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-success" role="button">Activer</a>
									</c:if> 
									<c:if test="${question.deletable}">
										<a href="<c:url value="/super_user/questions_management?action=delete_question&questionnaire_id=${ question.questionnaireId }&question_order_number=${ question.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-warning" role="button">Supprimer</a>
									</c:if> 
								</h4>
								</div>
								<div id="${status.index}" class="panel-collapse collapse">
									<ul class="list-group">
										<c:forEach var="answer" items="${ question.answers }">
											<li class="list-group-item 
												<c:choose>
													<c:when test="${ answer.active == true }">list-group-item-success</c:when>
													<c:otherwise>list-group-item-danger</c:otherwise>
												</c:choose>
											" >
												<c:out value="${ answer.value }" />
												<c:choose>
													<c:when test="${ answer['class'] == 'class beans.trainee.GoodAnswer' }"><span class="label label-success">V</span></c:when>
													<c:otherwise><span class="label label-danger">F</span></c:otherwise>
												</c:choose>	
												<c:if test="${ question.trueAnswerChangeable and answer['class'] == 'class beans.trainee.BadAnswer' and answer.active == false }">
													<a href="<c:url value="/super_user/questions_management?action=set_true_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-primary" role="button">Bonne réponse ?</a>
												</c:if>										
												<c:if test="${ answer.active == false }">
													<a href="<c:url value="/super_user/questions_management?action=activate_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-success" role="button">Activer</a>
													<a href="<c:url value="/super_user/questions_management?action=delete_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-warning" role="button">Supprimer</a>
												</c:if>
											</li>
										</c:forEach>
									</ul>
									<div class="panel-footer">
										<a href="<c:url value="/super_user/questions_management?action=add_answer&question_id=${ question.id }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" title="Cliquez si vous souhaitez en ajouter une">Ajouter une réponse</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
				<h4><a href="<c:url value="/super_user/questions_management?action=add_question&questionnaire_id=${ questionnaire.id }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" title="Cliquez si vous souhaitez en ajouter une">Ajouter une question</a></h4>
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>