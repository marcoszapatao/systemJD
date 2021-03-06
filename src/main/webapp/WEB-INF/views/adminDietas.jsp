<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.insumoTO"%>
<%@page import="com.proyecto.transferObject.dietaTO"%>
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
   		 console.log("IDEEE "+id);
   		 $.ajax({
   			 type:'POST',
   			 url:"/systemjd/editarD.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var dietaId = data.idDieta;
   		    	 var dietaPro = data.proporcionDieta;
   		    	 var dietaSemana = data.semanaDieta;
   
   		    	$("input[name*='idDieta']" ).val(dietaId);
   		    	 $("input[name*='proporcion']" ).val(dietaPro);
   		    	 $("#semana").html(dietaSemana);
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
              <h3 class="box-title">Administrar Dietas</h3>
              
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
                  <th>Insumo</th>
                  <th>Semana</th>
                  <th>Proporción (Kg)</th> 
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<dietaTO> list = (LinkedList<dietaTO>) request.getAttribute("lista");
                    LinkedList<insumoTO> list2 = (LinkedList<insumoTO>) request.getAttribute("cantidad");
                    if(list != null && list2 != null)
                        for (int i = 0; i < list.size(); i++) {
                            dietaTO task = list.get(i);
                            insumoTO cantidad = list2.get(i);
                    %>
                    <tr> <td><%=cantidad.getNombre_insumo()%></td> <td><%=task.getSemanaDieta()%></td> <td><%=task.getProporcionDieta()%></td> 
                    <td>
                     <!--input type="hidden" id="idvacuno" value="<%=task.getIdDieta()%>"/-->
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=task.getIdDieta()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deleteDieta.htm?id=<%=task.getIdDieta()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar el insumo:  <%=task.getIdDieta()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>Insumo</th>
                  <th>Semana</th>
                  <th>Proporción (Kg)</th>
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
			        <h4 class="modal-title"><font color="white">Ingrese Nueva Dieta</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="saveDieta.htm" method="POST">
			              <div class="box-body">
			                                 <% 
                    LinkedList<insumoTO> li = (LinkedList<insumoTO>) request.getAttribute("li");
                    String arr[]=new String[li.size()];
                    int a[]=new int[li.size()];
                    if(li != null){
                        
                    	for (int i = 0; i < li.size(); i++) {
                            insumoTO insumo = li.get(i);
                            arr[i]=insumo.getNombre_insumo();
                            a[i]=insumo.getId_insumo();
                        }
                    }
                    %>
		              <div class="form-horizontal">
		                  <label  class="col-sm-3 control-label">Insumo</label>
		                <div class="col-sm-9">
		                  <select name="insumo" class="form-control select2" style="width: 100%;" required>
		                  <option value="" disabled selected>Seleccione una opción</option>
		                  <%for(int j=0; j<arr.length;j++){ %>
		                  <option value=<%=a[j]%>><%=arr[j]%></option>
		                   <%} %>
		                  </select>
		                </div>
		              </div>
		                  <br></br>
			                

			                
			              <div class="form-horizontal">
			                
			                <label  class="col-sm-3 control-label">Semana</label>
			                <div class="col-sm-9">
			                  <select name="semana" class="form-control select2" style="width: 100%;" required>
			                  <option value="" disabled selected>Seleccione una opción</option>
			                  <option>1</option>
			                  <option>2</option>
			                  <option>3</option>
			                  <option>4</option>
			                  </select>
			                </div>
			              </div>
			                  <br></br>
			               <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Proporción (Kg)</label>
			                  <div class="col-sm-9">
			                    
			                    <input type="number" class="form-control" required name="proporcion" min="0" value="0" step=".01" placeholder="Ej: 0.01">
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
			        <h4 class="modal-title"><font color="white">Editar Dieta</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="actualizarDieta.htm" method="GET">
			              <div class="box-body">
			              <input type="hidden" id ="idDieta" name="idDieta">
			                			                  
			           <div class="form-horizontal">
			                
			               <label  class="col-sm-3 control-label">Semana</label>
			                <div class="col-sm-9">
			                  <select name="semana" class="form-control select2" style="width: 100%;">
			                  <option id="semana" selected="selected"></option>
			                  <option>1</option>
			                  <option>2</option>
			                  <option>3</option>
			                  <option>4</option>
			                  </select>
			                </div>
			            </div>
			                <br></br>
			                
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Proporción</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="proporcion"></input>
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
