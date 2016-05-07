<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
	    <%@ include file="../header.jsp" %>   
  	    <c:if test="${ !empty errorMessage }"><p style="color:red;"><c:out value="${ errorMessage }" /></p></c:if>
	    <form method="post" action="./connection">
			<p>
				<label for="email">E-mail : </label>
				<input type="text" id="email" name="email"/>
			</p>
			<p>
				<label for="password">Mot de passe : </label>
				<input type="password" id="password" name="password"/>
			</p>
			<input type="submit" value="envoyer"/>
	    </form>	 
	    <%@ include file="../footer.jsp" %>   
	</body>
</html>