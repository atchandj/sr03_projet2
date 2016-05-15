<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>                        
			</button>
			<a class="navbar-brand" href="<c:url value="/connection"/>">Evaluation des stagiaires</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">			
			<ul class="nav navbar-nav">
				<li <c:if test="${ !empty param.superUserHome }">class="active"</c:if> ><a href="<c:url value="/super_user"/>" title="Vers l'accueil administrateur"><span class="glyphicon glyphicon-home"></span> Accueil administrateur</a></li>
				<li <c:if test="${ !empty param.usersManagement }">class="active"</c:if> ><a href="<c:url value="/super_user/users_management"/>" title="Vers la gestion des utilisateurs"><span class="glyphicon glyphicon-user"></span> Gestion des utilisateurs</a></li>
				<li <c:if test="${ !empty param.formsManagement }">class="active"</c:if> ><a href="<c:url value="/super_user/forms_management"/>" title="Vers la gestion des questionnaires"><span class="glyphicon glyphicon-file"></span> Gestion des questionnaires</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<c:url value="/deconnection"/>" title="Vers le formulaire de connexion"><span class="glyphicon glyphicon-log-out"></span> Me déconnecter</a></li>
			</ul>
		</div>
	</div>
</nav>