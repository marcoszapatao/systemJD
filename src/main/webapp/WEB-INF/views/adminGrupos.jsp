<%-- 
    Document   : adminGrupos
    Created on : 16-10-2017, 11:05:14
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.grupoTO"%>
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
              <h3 class="box-title">Administrar grupos</h3>
              
            <div class="agre">
            <div class="col-sm-2">
	        <td>
	            <a href="createGrupo.htm" class="btn btn-block btn-info"><i class="fa fa-plus"> Agregar</i></a>
	        </td>
	        </div>
	        </div>
	        
            </div>  

            <!-- /.box-header -->
            <div class="box-body">
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th> 
                  <th>Fecha Salida</th>
                  
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<grupoTO> list = (LinkedList<grupoTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                            grupoTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getId_grupo()%></td> <td><%=task.getNombre()%></td> <td><%=task.getEstado()%></td> <td><%=task.getFecha_ingreso()%></td> <td><%=task.getFecha_Salida()%></td> 
                    <td><a href="editarGrupo.htm?id=<%=task.getId_grupo()%>" class="btn btn-success"><i class="fa fa-edit"> Editar</i></a> &nbsp; &nbsp; &nbsp;
                    <a href="deleteGrupo.htm?id=<%=task.getId_grupo()%>" class="btn btn-danger"  onclick="return confirm('¿Está seguro que desea eliminar el grupo con ID:  <%=task.getId_grupo()%>?');"><i class="fa fa-close"> Eliminar</i></a></td>  </tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th>
                  <th>Fecha Salida</th>
                  <th>Acciones</th>
            
                  
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
      'autoWidth'   : true,
      "language": {
          "sProcessing":    "Procesando...",
          "sLengthMenu":    "Mostrar _MENU_ registros",
          "sZeroRecords":   "No se encontraron resultados",
          "sEmptyTable":    "Ningún dato disponible en esta tabla",
          "sInfo":          "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
          "sInfoEmpty":     "Mostrando registros del 0 al 0 de un total de 0 registros",
          "sInfoFiltered":  "(filtrado de un total de _MAX_ registros)",
          "sInfoPostFix":   "",
          "sSearch":        "Buscar:",
          "sUrl":           "",
          "sInfoThousands":  ",",
          "sLoadingRecords": "Cargando...",
          "oPaginate": {
              "sFirst":    "Primero",
              "sLast":    "Último",
              "sNext":    "Siguiente",
              "sPrevious": "Anterior"
          },
          "oAria": {
              "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
              "sSortDescending": ": Activar para ordenar la columna de manera descendente"
          }
      }
    })
  })
</script>
</body>
</html>