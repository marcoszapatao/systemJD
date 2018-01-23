<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.insumoTO"%>
<%@page import="com.proyecto.transferObject.cantidadDisponibleTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <%@ include file="cabecera.jsp"%>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script>

   	     function botonEdit(id){
   		
   		 $.ajax({
   			 type:'GET',
   			 url:"/systemjd/editarIn.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var insumoId = data.id_insumo;
   		    	 var insumoNombre = data.nombre_insumo;
   		    	 var insumoDescripcion = data.descripcion_insumo;
   		    	 var insumoTipo = data.tipoInsumo;
   		    	$("input[name*='idInsumo']" ).val(insumoId);
   		    	 $("input[name*='nombreInsumo']" ).val(insumoNombre);
   		    	 $("input[name*='descripcion']" ).val(insumoDescripcion);
   		    	 $("#tipoI").html(insumoTipo);
   		    	 $('#myModalEditI').modal('show');
   		     },
   		     error:function(jqXHR,errorThrown){
   		    	 alert("Alerta "+errorThrown);
   		     }
   		 });
   	 }
   //});
    </script>
  
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
    .box-footer{
    background-color:#ecf0f5;
    }
    .modal-lg {
  max-width: 700px;
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
      <%
 	HttpSession sessionV = request.getSession();
 	String rol = (String) sessionV.getAttribute("rol");
 	if(rol.equalsIgnoreCase("usuario")){
	 %>
	    <%@ include file="barraLateralUsuario.jsp"%>
	<%}else{%>
	     <%@ include file="barraLateralAdministrador.jsp"%>
	<% }%>

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
              <h3 class="box-title">Administrar Insumos</h3>
              
            <div class="agre">
            <div class="col-sm-2">
	        <td>
	            <!--a href="create.htm" class="btn btn-block btn-info"><i class="fa fa-plus"> Agregar</i></a-->
	            <a class="btn btn-block btn-info btn-sm" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>  Agregar</a>
	            
	        </td>
	        </div>
	        </div>
	        
            </div>  

            <!-- /.box-header -->
            <div class="box-body">
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  
                  <th>Nombre</th>
                  <th>Descripción</th> 
                  <th>Tipo</th>
                  <th>Stock (Kg)</th>
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<insumoTO> list = (LinkedList<insumoTO>) request.getAttribute("lista");
                    LinkedList<Float> list2 = (LinkedList<Float>) request.getAttribute("cantidad");
                    if(list != null && list2 != null)
                        for (int i = 0; i < list.size(); i++) {
                            insumoTO task = list.get(i);
                            float cantidad = list2.get(i);
                    %>
                    <tr>  <td><%=task.getNombre_insumo()%></td> <td><%=task.getDescripcion_insumo()%></td> <td><%=task.getTipoInsumo()%></td> <%if(!task.getTipoInsumo().equals("Suplemento")){%><td><%=cantidad%></td><% }else{%><td> - </td><% }%></td> 
                    <td>
                     <!--input type="hidden" id="idvacuno" value="<%=task.getId_insumo()%>"/-->
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=task.getId_insumo()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deleteInsumo.htm?id=<%=task.getId_insumo()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar el insumo:  <%=task.getNombre_insumo()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  
                  <th>Nombre</th>
                  <th>Descripción</th>
                  <th>Tipo</th>
                  <th>Stock (Kg)</th>
                  <th>Acciones</th>
            
                  
                </tr>
                </tfoot>
              </table>
              
            </div>
            
            <!--------- Comienzo modal Agregar Vacuno ------------->
            <div id="myModal" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Ingrese Nuevo Insumo</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="saveInsumo.htm" method="POST">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="inputEmail3" name="nombre" placeholder="Ej: Liposal" required>
			                  </div>
			                </div>
			                <br></br>
			                
			               <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Descripción</label>
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="descrip" name="descripcion" placeholder="Descripción de insumo" required>
			                  </div>
			                </div>
			                <br></br>
			                
			              <div class="form-horizontal">
			                
			                <label  class="col-sm-3 control-label">Seleccione Tipo</label>
			                <div class="col-sm-9">
			                  <select name="tipo" class="form-control select2" style="width: 100%;" required>
			                  <option value="" disabled selected>Seleccione una opción</option>
			                  <option>Alimento</option>
			                  <option>Suplemento</option>
			                  </select>
			                </div>
			              </div>
			                  <br></br>
			                 

			                  
			              </div>
			              <!-- /.box-body -->
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
			                <button type="submit" class="btn btn-info pull-right"><i class="fa fa-floppy-o"></i> Guardar</button>
			              </div>
			              <!-- /.box-footer -->
			            </form>
			      </div>

			    </div>
			
			  </div>
          </div>

          <!--------- FIN modal Agregar Vacuno ------------->
          
          <!-- Molda editar vacuno -->
          <div id="myModalEditI" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Editar Insumo</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="actualizarInsumo.htm" method="GET">
			              <div class="box-body">
			              <input type="hidden" id ="idInsumo" name="idInsumo">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="nombreInsumo"></input>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Descripción</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="descripcion"></input>
			                  </div>
			                </div>
			                <br></br>
			            
			                  
			           <div class="form-horizontal">
			                
			               <label  class="col-sm-3 control-label">Tipo</label>
			                <div class="col-sm-9">
			                  <select name="tipo" class="form-control select2" style="width: 100%;">
			                  <option id="tipoI" selected="selected"></option>
			                  <option>Alimento</option>
			                  <option>Suplemento</option>
			                  
			                  </select>
			                </div>
			            </div>
			                <br></br>
			               
			                  
			              </div>
			              <!-- /.box-body -->
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
			                <button type="submit" class="btn btn-info pull-right"><i class="fa fa-floppy-o"></i> Guardar</button>
			              </div>
			              <!-- /.box-footer -->
			            </form>
			      </div>
			    </div>
			
			  </div>
          </div>
          <!-- FIN modal editar -->            
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
