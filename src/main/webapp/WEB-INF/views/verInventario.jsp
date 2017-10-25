<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.vacunoTO"%>
<%@page import="com.proyecto.persistence.inventarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="cabecera.jsp"%>
  <style type="text/css">
  .text { font-family: arial; font-size:11pt;  }
  div.agre {
    position: relative;
    left: 700px;
    
    }
  </style>
  
</head>
<body class="hold-transition skin-red sidebar-mini">
<div class="wrapper">

  <header class="main-header">
    <%@ include file="barraSuperior.jsp"%>
  </header>
    
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <%@ include file="barraLateral.jsp"%>

  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Sistema de Gestión de Engorda de Vacunos
      </h1>

 
    </section>
        <div >
          
        </div>

    <!-- Main content -->
    <section class="content">
        <br></br>
            <div class="box">
            <div class="box-header">
              <h3 class="box-title">Vacunos que actualmente se encuentran en el grupo</h3>
              
	        
            </div>  
            
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>DIIO</th>
                  <th>Tipo</th>
                  <th>Raza</th> 
                  <th>Fecha Ingreso</th>
                  <th>Nro. de Dias</th>
                  
                </tr>
                </thead>
                <tbody>
                    
                    <% 
                    inventarioDAO dao = new inventarioDAO(); 
                    LinkedList<vacunoTO> list = (LinkedList<vacunoTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                            vacunoTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getDiio()%></td> <td><%=task.getTipo()%></td> <td><%=task.getRaza()%></td> <td><%=task.getFechaIngreso()%></td><td><%=dao.calculadias(task.getFechaIngreso())%></td> 
                     </tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>DIIO</th>
                  <th>Tipo</th>
                  <th>Raza</th>
                  <th>Fecha Ingreso</th>
                  <th>Nro. de dias</th>
            
                  
                </tr>
                </tfoot>
              </table>
              
            </div>
            
           

            
            <!-- /.box-body -->
          </div>  

   
    </section>

    </div>
      

    <!-- /.content -->
  </div>

  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <%@ include file="pieDePagina.jsp"%>
  </footer>


  

  <div class="control-sidebar-bg"></div>

<!-- ./wrapper -->


<%@ include file="scripts.jsp"%>
<script>
$(function () {
    $('#example').DataTable({
      'paging'      : true,
      'lengthChange': true,
      'searching'   : false,
      'ordering'    : true,
      'info'        : true,
      'autoWidth'   : true
    })
  })
</script>
</body>
</html>
