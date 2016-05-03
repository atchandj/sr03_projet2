<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Connection</title>
	</head>
	<body>
  	    <c:if test="${ !empty errorMessage }"><p style="color:red;"><c:out value="${ errorMessage }" /></p></c:if>
	    <form method="post" action=".">
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
	</body>
</html>