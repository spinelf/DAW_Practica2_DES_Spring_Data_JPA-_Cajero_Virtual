<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Transacción</title>
		<link href="<c:url value='/css/style.css' />" rel="stylesheet">
  		
	</head>
	<body>
		<main>
			<form action="/cliente/${ transaccion }" method="post">
				<div>
					<p> ${ transaccion eq "extraccion" ? 'Extraer' : 'Ingresar' } Efectivo</p>
					<div>
						<label for="cantidad">Cantidad</label><br>
						<input type="number" name="cantidad" id="cantidad" step="10" min="10" required>
					</div>
					<div>
						<input type="hidden" name="operacion" value="${ transaccion eq 'extraccion' ? 'cargo' : 'abono' }"></br>
						<button type="submit" class="boton_operar">${ transaccion eq "extraccion" ? 'Extraer' : 'Ingresar' }</button> </br></br>
						<a href="/cliente" class="boton_entrar">Volver al menu</a>
					</div>				
				</div>
				<p class="mensaje-error">${ mensajeError }</p>
				<p class="mensaje-error">${ mensajeOk }</p>
				
			</form>
		</main>
	</body>
</html>