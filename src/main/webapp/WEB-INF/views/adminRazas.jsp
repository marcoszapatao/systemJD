<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.razaTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <%@ include file="cabecera.jsp"%>
    <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script>
    //$(document).ready(function(){
    	//console.log( "document loaded" );
   	    
    	//$('#botonEdit').click(function(){
   	     function botonEdit(id){
   		 //var id=$('#idvacuno').val();
   		 console.log("IDEEE "+id);
   		 $.ajax({
   			 type:'GET',
   			 url:"/systemjd/editarRa.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var razaId = data.idRaza;
   		    	 var razaNombre = data.nombreRaza;
   		    	 var razaDescrip = data.descripcionRaza;
   		    	 alert(razaId+razaNombre+" "+razaDescrip);
   		    	 $("input[name*='id']" ).val(razaId);
   		    	 $("input[name*='diioo']" ).val(razaNombre);
   		    	 $("input[name*='descrip']" ).val(razaDescrip);
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
              <h3 class="box-title">Administrar razas</h3>
              
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
											.error("Error: No se puedo eliminar, existe al menos un vacuno con esa raza");
								</script>
		   </c:if>

            <!-- /.box-header -->
            <div class="box-body">
              <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Descripción</th> 
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<razaTO> list = (LinkedList<razaTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                            razaTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getIdRaza()%></td> <td><%=task.getNombreRaza()%></td> <td><%=task.getDescripcionRaza()%></td> 
                    <td>
                     <!--input type="hidden" id="idvacuno" value="<%=task.getIdRaza()%>"/-->
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=task.getIdRaza()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deleteRaza.htm?id=<%=task.getIdRaza()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar la raza:  <%=task.getNombreRaza()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Descripción</th>
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
			        <h4 class="modal-title"><font color="white">Ingrese Nueva Raza</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="saveRaza.htm" method="POST">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="nombreRaza" name="nombre" placeholder="Ej: Angus" required>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Descripción</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="descripcionRaza" name="descripcion" required>
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
			        <h4 class="modal-title"><font color="white">Editar Vacuno</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="actualizarRaza.htm" method="POST">
			              <div class="box-body">
			               <input type="hidden" class="form-control" name="id"></input>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="diioo"></input>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Descripción</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="descrip"></input>
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
