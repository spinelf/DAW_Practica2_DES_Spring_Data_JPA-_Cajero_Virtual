<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Menu cajero</title>
		<link href="<c:url value='/css/style.css' />" rel="stylesheet">
		 <style>A {text-decoration: none;} </style>
	</head>
	<body>
		
		<h2>Seleccione una operación a realizar</h2>
		<nav class="menu">
			<a class="opcion_menu" href="/cliente/ingresar">Ingresar Efectivo</a></br></br>
			<a class="opcion_menu" href="/cliente/extraer">Retirada Efectivo</a></br></br>
			<a class="opcion_menu" href="/cliente/transferencia">Transferencia</a></br></br>
			<a class="opcion_menu" href="/cliente/movimientos">Ver movimientos</a></br></br>
			<a class="opcion_menu" href="/cerrarSesion">Cerrar sesión</a></br>
		</nav>
		<p class="mensajeok">${ mensajeok }</p>
		<p class="mensaje-error">${ mensajeError }</p>
	</body>
</html>