<%-- 
    Document   : listAll
    Created on : 27-09-2016, 1:17:01
    Author     : object
--%>

<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.grupoTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <tr><th>identificador</th><th>estasdo</th><th>fecha comienzo</th><th>fecha termino</th></tr>
            <% 
            LinkedList<grupoTO> list = (LinkedList<grupoTO>) session.getAttribute("list");
            if(list != null)
                for (int i = 0; i < list.size(); i++) {
                    grupoTO task = list.get(i);
            %>
            <tr> <td><%=task.getId_grupo()%></td> <td><%=task.getEstado()%></td> <td><%=task.getFecha_ingreso()%></td> <td><%=task.getFecha_Salida()%></td>  </tr>
            <%} else{%>
                <h1>No hay datos</h1>
            <%}%>    
        </table>
        <a href="Controller?action=menu"><input type="button" value="Menu"/></a>
    </body>
</html>
