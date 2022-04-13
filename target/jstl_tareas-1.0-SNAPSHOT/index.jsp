<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.emergentes.modelo.Tarea"%>
<%@page import="com.emergentes.modelo.GestorTareas"%>
<%
    if (session.getAttribute("agenda") == null) {
            GestorTareas objeto1 = new GestorTareas();
            objeto1.insertarTarea(new Tarea(1,"Reunirse","Alta"));
            objeto1.insertarTarea(new Tarea(2,"Estudiar","Baja"));
            objeto1.insertarTarea(new Tarea(3,"Partido Futsal","Media"));
            
            session.setAttribute("agenda", objeto1);
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista Tareas</h1>
        <a href="Controller?op=nuevo">Nuevo</a>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Tarea</th>
                    <th>Prioridad</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <c:forEach var="item" items="${sessionScope.agenda.getLista()}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.tarea}</td>
                    <td>${item.prioridad}</td>
                    <td><a href="Controller?op=modificar&id=${item.id}">Modificar</a></td>
                    <td><a href="Controller?op=eliminar&id=${item.id}">Eliminar</a></td>
                </tr>
            </c:forEach>
            
        </table>

    </body>
</html>
