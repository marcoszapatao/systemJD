<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.patioTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <%@ include file="cabecera.jsp"%>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script>
   	     function botonEdit(id){
   		 console.log("IDEEE "+id);
   		 $.ajax({
   			 type:'GET',
   			 url:"/systemjd/editarPa.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var paId = data.idPatio;
   		    	 var paNombre = data.nombrePatio;
   		    	 var paNro = data.nroNavesPatio;
 
   		    	 $("input[name*='idP']" ).val(paId);
   		    	 $("input[name*='nombreP']" ).val(paNombre);
   		    	 $("input[name*='nroP']" ).val(paNro);
   		    	 $('#myModalEdit').modal('show');
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
              <h3 class="box-title">Administrar Patio de Alimentación</h3>
              
            <div class="agre">
            <div class="col-sm-2">
	        <td>
	            <!--a href="create.htm" class="btn btn-block btn-info"><i class="fa fa-plus"> Agregar</i></a-->
	            <a class="btn btn-block btn-info btn-sm" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>  Agregar</a>
	            
	        </td>
	        </div>
	        </div>
	        
            </div> 
           <c:if test="${not empty correcto}">
								<script>
									toastr
											.success("Inserción correcta");
								</script>
		   </c:if> 
		   <c:if test="${not empty incorrecta}">
								<script>
									toastr
											.error("Error: Inserción incorrecta");
								</script>
		   </c:if>
            		   <c:if test="${not empty elimino}">
								<script>
									toastr
											.error("Error: No se puedo eliminar, el proveedor está en uso");
								</script>
		   </c:if>

            <!-- /.box-header -->
            <div class="box-body">
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Nro. de naves</th>
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<patioTO> list = (LinkedList<patioTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                           patioTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getIdPatio()%></td> <td><%=task.getNombrePatio()%></td><td><%=task.getNroNavesPatio()%></td>
                    <td>
                 
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=task.getIdPatio()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deletePatio.htm?id=<%=task.getIdPatio()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar el patio:  <%=task.getNombrePatio()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Nro. de neves</th>
                  <th>Acciones</th>
            
                  
                </tr>
                </tfoot>
              </table>
              
            </div>
            
            <!--------- Comienzo modal Agregar Proveedor ------------->
            <div id="myModal" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Ingrese Nuevo Patio de Alimentación</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="savePatio.htm" method="POST">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="nombreP" name="nombre" placeholder="Ej: PatioUno" required>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese N° de Naves</label>
			
			                  <div class="col-sm-9">
			                    <input type="number" class="form-control" id="rubro" name="nro" placeholder="Ej: 6" required>
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
          <div id="myModalEdit" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Editar Patio de Alimentación</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="actualizarPatio.htm" method="POST">
			              <div class="box-body">
			               <input type="hidden" class="form-control" name="idP"></input>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="nombreP" required></input>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Rubro</label>
			
			                  <div class="col-sm-9">
			                    <input type="number" class="form-control" name="nroP" required></input>
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
