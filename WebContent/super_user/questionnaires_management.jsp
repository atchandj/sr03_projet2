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
	    		<h1>Questionnaire : ${ questionnaireName }</h1>
				<form method="post" action="<c:url value="/super_user/questions_management"/>">
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title text-center">
									Echange ordre questions
								</h4>
							</div>
							<div class="panel-body">
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
					 				<label for="question1OrderNumber">Question 1 :</label>
						 			<select class="form-control" id="question1OrderNumber" name="question1OrderNumber">
										<c:forEach var="question" items="${ questionnaire.questions }" varStatus="status">	
											<c:if test="${question.active == false}">
												<option value="${ question.orderNumber }">${ question.value }</option>
											</c:if>
			  							</c:forEach>
					 				</select>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
					 				<label for="question2OrderNumber">Question 2 :</label>
						 			<select class="form-control" id="question2OrderNumber" name="question2OrderNumber">
										<c:forEach var="question" items="${ questionnaire.questions }" varStatus="status">	
											<c:if test="${question.active == false}">
												<option value="${ question.orderNumber }">${ question.value }</option>
											</c:if>
			  							</c:forEach>
					 				</select>
								</div>
								<input type="hidden" name="paction" value="exchange_questions_order" />	
								<input type="hidden" name="questionnaire_id" value="${ questionnaire.id }" />
								<input type="hidden" name="topic_name" value="${ topicName }" />
								<input type="hidden" name="questionnaire_name" value="${ questionnaireName }" />
								<input type="submit" class="btn btn-default" value="Echanger"/>
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
									<a data-toggle="collapse" href="#<c:out value="${ status.index }" />">${ status.count }. <c:out value="${ question.value }" /></a> 
									<c:if test="${question.activable}">
										<a href="<c:url value="/super_user/questions_management?action=activate_question&questionnaire_id=${ question.questionnaireId }&question_order_number=${ question.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-success" role="button">Activer</a>
									</c:if> 
									<c:if test="${question.active == false}">
										<a href="<c:url value="/super_user/questions_management?action=modify_question&questionnaire_id=${ question.questionnaireId }&question_order_number=${ question.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-warning" role="button">Modifier</a>
									</c:if>
									<c:if test="${question.deletable}">
										<a href="<c:url value="/super_user/questions_management?action=delete_question&questionnaire_id=${ question.questionnaireId }&question_order_number=${ question.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-danger" role="button">Supprimer</a>
									</c:if> 
								</h4>
								</div>
								<div id="${status.index}" class="panel-collapse collapse">
									<ul class="list-group">
										<c:forEach var="answer" items="${ question.answers }" varStatus="status">
											<li class="list-group-item 
												<c:choose>
													<c:when test="${ answer.active == true }">list-group-item-success</c:when>
													<c:otherwise>list-group-item-danger</c:otherwise>
												</c:choose>
											" >
												${ status.count }) <c:out value="${ answer.value }" />
												<c:choose>
													<c:when test="${ answer['class'] == 'class beans.trainee.GoodAnswer' }"><span class="label label-success">V</span></c:when>
													<c:otherwise><span class="label label-danger">F</span></c:otherwise>
												</c:choose>	
												<c:if test="${ question.trueAnswerChangeable and answer['class'] == 'class beans.trainee.BadAnswer' and answer.active == false }">
													<a href="<c:url value="/super_user/questions_management?action=set_true_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-primary" role="button">Bonne réponse</a>
												</c:if>										
												<c:if test="${ answer.active == false }">
													<a href="<c:url value="/super_user/questions_management?action=activate_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-success" role="button">Activer</a>
													<a href="<c:url value="/super_user/questions_management?action=modify_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-warning" role="button">Modifier</a>
													<a href="<c:url value="/super_user/questions_management?action=delete_answer&question_id=${ question.id }&answer_order_number=${ answer.orderNumber }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" class="btn btn-danger" role="button">Supprimer</a>
												</c:if>
											</li>
										</c:forEach>
										<li>
											<form method="post" action="<c:url value="/super_user/questions_management"/>">
												<div class="panel panel-default">
													<div class="panel-heading">
														<h4 class="panel-title text-center">
															Echange ordre réponses
														</h4>
													</div>
													<div class="panel-body">
														<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
											 				<label for="answer1OrderNumber">Réponse 1 :</label>
												 			<select class="form-control" id="answer1OrderNumber" name="answer1OrderNumber">
																<c:forEach var="answer" items="${ question.answers }">	
																	<c:if test="${answer.active == false}">
																		<option value="${ answer.orderNumber }">${ answer.value }</option>
																	</c:if>
									  							</c:forEach>
											 				</select>
														</div>
														<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
											 				<label for="answer2OrderNumber">Réponse 2 :</label>
												 			<select class="form-control" id="answer2OrderNumber" name="answer2OrderNumber">
																<c:forEach var="answer" items="${ question.answers }">	
																	<c:if test="${answer.active == false}">
																		<option value="${ answer.orderNumber }">${ answer.value }</option>
																	</c:if>
									  							</c:forEach>
											 				</select>
														</div>
														<input type="hidden" name="paction" value="exchange_answers_order" />
														<input type="hidden" name="question_id" value="${ question.id }" />
														<input type="hidden" name="topic_name" value="${ topicName }" />
														<input type="hidden" name="questionnaire_name" value="${ questionnaireName }" />
														<input type="submit" class="btn btn-default" value="Echanger"/>				
													</div>
												</div>
											</form>
										</li>
									</ul>
									<c:if test="${question.active == false}">
										<div class="panel-footer">
											<h5>
												<a href="<c:url value="/super_user/questions_management?action=add_answer&question_id=${ question.id }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" title="Cliquez si vous souhaitez en ajouter une">Ajouter une réponse</a>
											</h5>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
				<c:if test="${questionnaire.active == false}">
					<h4><a href="<c:url value="/super_user/questions_management?action=add_question&questionnaire_id=${ questionnaire.id }&topic_name=${ topicName }&questionnaire_name=${ questionnaireName }"/>" title="Cliquez si vous souhaitez en ajouter une">Ajouter une question</a></h4>
	    		</c:if>
	    	</section>
	    </c:if>
	    </div>
	    <%@ include file="/footer.jsp" %>
	</body>
</html>