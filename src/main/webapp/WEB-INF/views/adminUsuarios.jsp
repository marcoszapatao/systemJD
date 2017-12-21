<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.trabajadorTO"%>
<%@page import="com.proyecto.transferObject.usuarioTO"%>
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
   			 url:"/systemjd/editarUser.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var traId = data.idTrabajador;
   		    	 var traNombre = data.nombreTrabajador;
   		    	 var traCargo = data.cargoTrabajador;
   		    	 var traUsuario = data.idUsuario;
   		    	$("input[name*='idTraba']" ).val(traId);
   		    	 $("input[name*='nombreTra']" ).val(traNombre);
   		    	 $("input[name*='cargoTra']" ).val(traCargo);
   		    	 $.ajax({
   		    		type:'GET',
	   	   			 url:"/systemjd/editarU.htm?id="+traUsuario,
	   	   		     dataType:'json',
	   	   		     success:function(data){
	   	   		         var idUser = data.idUsuario;
	   	   		         var emailUser = data.emailUsuario;
	   	   		         var rolUser = data.rolUsuario;
	   	   		         $("input[name*='idUser']" ).val(idUser);
	   	   		    	 $("input[name*='emailU']" ).val(emailUser);
	   	   		    	 $("#rolU").html(rolUser); 
	   	   		     }
   		    	 })
   		    	
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
              <h3 class="box-title">Administrar Usuarios</h3>
              
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
                  <th>Cargo</th> 
                  <th>Email</th>
                  <th>Rol</th>
                  <th>Password</th>
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<trabajadorTO> list = (LinkedList<trabajadorTO>) request.getAttribute("cantidad");
                    LinkedList<usuarioTO> list2 = (LinkedList<usuarioTO>) request.getAttribute("lista");
                    if(list != null && list2 != null)
                        for (int i = 0; i < list.size(); i++) {
                            trabajadorTO tra = list.get(i);
                            usuarioTO user = list2.get(i);
                    %>
                    <tr> <td><%=tra.getNombreTrabajador()%></td> <td><%=tra.getCargoTrabajador()%></td> <td><%=user.getEmailUsuario()%></td> <td><%=user.getRolUsuario()%></td> <td><%=user.getPasswordUsuario()%></td>
                    <td>
                     <!--input type="hidden" id="idvacuno" value="<%=tra.getIdTrabajador()%>"/-->
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=tra.getIdTrabajador()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deleteUsuario.htm?id=<%=tra.getIdTrabajador()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar el usuario:  <%=tra.getNombreTrabajador()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  
                  <th>Nombre</th>
                  <th>Cargo</th>
                  <th>Email</th>
                  <th>Rol</th>
                  <th>Password</th>
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
			        <h4 class="modal-title"><font color="white">Ingrese Nuevo Usuario</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="saveUsuario.htm" method="POST">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Nombre</label>
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="inputEmail3" name="nombre" placeholder="Ej: Juan Perez" required>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Cargo</label>
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="inputEmail3" name="cargo" placeholder="Ej: Agrónomo" required>
			                  </div>
			                </div>
			                <br></br>
			                
			               <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Email</label>
			                  <div class="col-sm-9">
			                    <input type="email" class="form-control" id="descrip" name="email" placeholder="Ej: juan@gmail.com" required>
			                  </div>
			                </div>
			                <br></br>
			                
			              <div class="form-horizontal">
			                
			                <label  class="col-sm-3 control-label">Seleccione Rol</label>
			                <div class="col-sm-9">
			                  <select name="rol" class="form-control select2" style="width: 100%;">
			                  <option selected="selected">Seleccione una opción</option>
			                  <option>Usuario</option>
			                  <option>Administrador</option>
			                  </select>
			                </div>
			              </div>
			                  <br></br>
			               <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese Contraseña</label>
			                  <div class="col-sm-9">
			                    <input type="password" class="form-control" id="descrip" name="contrasena"  required>
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
			        <h4 class="modal-title"><font color="white">Editar Usuario</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="actualizarUser.htm" method="POST">
			              <div class="box-body">
			              
			              <input type="hidden" id ="idTraba" name="idTraba">
			              <input type="hidden" id="idUser" name="idUser">
			              
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Nombre</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="nombreTra"></input>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Cargo</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="cargoTra"></input>
			                  </div>
			                </div>
			                <br></br>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Email</label>
			
			                  <div class="col-sm-9">
			                    <input type="email" class="form-control" name="emailU"></input>
			                  </div>
			                </div>
			                <br></br>
			                  
			           <div class="form-horizontal">
			                
			               <label  class="col-sm-3 control-label">Rol</label>
			                <div class="col-sm-9">
			                  <select name="rolU" class="form-control select2" style="width: 100%;">
			                  <option id="rolU" selected="selected"></option>
			                  <option>Usuario</option>
			                  <option>Administrador</option>
			                  
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
