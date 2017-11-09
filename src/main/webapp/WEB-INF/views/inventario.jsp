<%-- 
    Document   : adminGrupos
    Created on : 16-10-2017, 11:05:14
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.inventarioTO"%>
<%@page import="com.proyecto.persistence.vacunoDAO"%>

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
            .modal-header {
    background-color: #dd4b39;

    }
    .modal-body{
    background-color:#ecf0f5;
    }
    .modal-footer{
    background-color:#ecf0f5;
    }
    .box-footer{
    background-color:#ecf0f5;
    }
    .table{
    background-color:#ffffff;
    }
  </style>
  <script>
     function botonVer(id){
   		 console.log("IDEEE "+id);
   		 $.ajax({
   			 type:'GET',
   			 url:"/systemjd/verInventa.htm?id="+id,
   		     //dataType:'json',
   		     success:function(data){
                var result = "<thead><tr><th>Diio</th><th>Tipo</th><th>Raza</th><th>Fecha</th><th>Nro. de dias</th></tr></thead>";
                result += "<tbody>";
                $.each(data,function(k,v){
                	fecha = new Date();
                	fecha2 = new Date(v.fechaIngreso);
                	
                	one_day=1000*60*60*24;
                	diferencia = Math.ceil((fecha.getTime()-fecha2.getTime())/(one_day));
                	
                	result += "<tr>";
                	result += "<td>";
                	result += v.diio
                	result += "</td>";
                	result += "<td>";
                	result += v.tipo
                	result += "</td>";
                	result += "<td>";
                	result += v.raza
                	result += "</td>";
                	result += "<td>";
                	result += v.fechaIngreso
                	result += "</td>";
                	result += "<td>";
                	result += diferencia
                	result += "</td>";
                	result += "</tr>";
                })
                
                result += "</tbody>";
                $("#result").html(result);
                
                $('#myModalVer').modal('show');
   		     },
   		     error:function(jqXHR,errorThrown){
   		    	 alert("Alerta "+errorThrown);
   		     }
   		 });
   	 }
  </script>
  
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
      <%vacunoDAO vacunito=new vacunoDAO();%>
      <br></br>
       <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3><%=vacunito.totalVacunos()%></h3>

              <p>Total de vacunos</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>

          </div>
        </div>
 
    </section>
        <div >

        </div>

    <!-- Main content -->
    <br></br>
    <br></br>
    <section class="content">
        <br></br>
            <div class="box">
            <div class="box-header">
              <h3 class="box-title">Inventario</h3>
              
	        
            </div>  

            <!-- /.box-header -->
            <div class="box-body">
            
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  
                  <th>Nombre Grupo</th>
                  <th>Nro. de animales</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th> 
                  <th>Fecha Salida</th>
                  
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<inventarioTO> list = (LinkedList<inventarioTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                            inventarioTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getNombre()%></td><td><%=task.getNroAnimales()%></td> <td><%=task.getEstado()%></td> <td><%=task.getFecha_ingreso()%></td> <td><%=task.getFecha_Salida()%></td> 
                    <!-- td><a href="verInven.htm?id=<%=task.getId_grupo()%>" class="btn btn-success"><i class="fa fa-edit"> Ver</i></a>
                    </td-->
                    <td><button type="button" class="btn btn-success"  onclick="botonVer('<%=task.getId_grupo()%>');">Ver</button></td>  </tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  
                  <th>Nombre</th>
                  <th>Nro. de animales</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th>
                  <th>Fecha Salida</th>
                  <th>Acciones</th>
            
                  
                </tr>
                </tfoot>
              </table>
              
            </div>
            <!-- Comienza modal-------------- -->
            <div id="myModalVer" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Vacunos en grupo:</font></h4>
			      </div>
			      <div class="modal-body">
			       <center>
			          <table id="result" class="table table-bordered table-hover">
			             <thead>
			                <tr>
			                   <th>Diio</th>
			                   <td>Tipo</td>
			                   <td>Raza</td>
			                   <td>Fecha</td>
			                   <td>Nro. de dias</td>
			                </tr>
			             </thead>
			             <tbody>
			             </tbody>
			          </table>
                   </center>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			      </div>
			    </div>
			
			  </div>
          </div>
            <!-- Tremina modal -->
			
			  </div>
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
      $('#result').DataTable({
      'paging'      : false,
      'lengthChange': true,
      'searching'   : false,
      'ordering'    : false,
      'info'        : false,
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