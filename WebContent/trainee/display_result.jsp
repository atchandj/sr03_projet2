<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Résultat obtenu</title>
</head>
<body>
	<%@ include file="/header.jsp" %>   
	<jsp:include page="./menu.jsp" >
		<jsp:param name="displayResult" value="displayResult" />
	</jsp:include>
	
	<div class="container-fluid">
		<h1>Résultat obtenus</h1>
	</div>
	
	<%@ include file="/footer.jsp" %>
</body>
</html>