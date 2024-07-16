<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<link href="<c:url value='/css/style.css' />" rel="stylesheet">
		
  		</head>
	<body>
		<main>
			<form action="/login" method="post">
				<div>
					<p>Introduzca la cuenta para operar</p>
					<div>
						<label for="idCuenta">Número de cuenta</label><br>
						<input type="number" name="idCuenta" id="idCuenta" required>
					</div>
					</br>
					<div>
						<button type="submit" class="boton_entrar">Entrar</button>
					</div>				
				</div>
				<p class="m_error">${ mensajeError }</p>
			</form>
		</main>
	</body>
</html>