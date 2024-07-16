<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Transferencia</title>
		<link href="<c:url value='/css/style.css' />" rel="stylesheet">
  		
	</head>
	<body>
		<main>
			<form action="#" method="post">
				<div>
					<p>Realizar transferencia</p>
					<div>
						<label for="cantidad">Cantidad</label><br>
						<input type="number" name="cantidad" id="cantidad" min="1" required>
					</div>
					<div>
						<label for="destino">Cuenta de destino</label><br>
						<input type="number" name="destino" id="destino" min="1" required>
					</div>
					</br>
					<div>
						<button type="submit" class="boton_operar">Enviar</button> </br></br>
						<a href="/cliente" class="boton_entrar">Volver al menu</a>
					</div>				
				</div>
				<p class="mensaje-error">${ mensajeError }</p>
				<p class="mensaje-error">${ mensajeok }</p>
			</form>
		</main>
	</body>
</html>