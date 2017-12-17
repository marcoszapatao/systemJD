<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.vacunoTO"%>
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
   			 url:"/systemjd/editarVa.htm?id="+id,
   		     dataType:'json',
   		     success:function(data){
   		    	 console.log(data);
   		    	 var vacunoDiio = data.diio;
   		    	 var vacunoTipo = data.tipo;
   		    	 var vacunoRaza = data.raza;
   		    	 var vacunoFIn = data.fechaIngreso;
   		    	 console.log("FECHA....."+vacunoFIn);
   		    	 $("input[name*='diioo']" ).val(vacunoDiio);
   		    	 $("#tipoV").html(vacunoTipo);
   		    	 $("#razaV").html(vacunoRaza);
   		    	 $("#fecha_ing" ).val(vacunoFIn);
   		    	 //"input[name*='fecha_ing']"
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
              <h3 class="box-title">Administrar vacunos</h3>
              
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
                  <th>DIIO</th>
                  <th>Tipo</th>
                  <th>Raza</th> 
                  <th>Fecha Ingreso</th>
                  <th>Acciones</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<vacunoTO> list = (LinkedList<vacunoTO>) request.getAttribute("lista");
                    if(list != null)
                        for (int i = 0; i < list.size(); i++) {
                            vacunoTO task = list.get(i);
                    %>
                    <tr> <td><%=task.getDiio()%></td> <td><%=task.getTipo()%></td> <td><%=task.getRaza()%></td> <td><%=task.getFechaIngreso()%></td> 
                    <td>
                     <!--input type="hidden" id="idvacuno" value="<%=task.getDiio()%>"/-->
                     <button type="button" class="btn btn-success btn-xs"  onclick="botonEdit('<%=task.getDiio()%>');"><i class="fa fa-edit"></i> Editar</button>
                     &nbsp; &nbsp; &nbsp;
                     <a href="deleteVacuno.htm?id=<%=task.getDiio()%>" class="btn btn-danger btn-xs"  onclick="return confirm('¿Está seguro que desea eliminar el vacuno con DIIO:  <%=task.getDiio()%>?');"><i class="fa fa-close"></i>  Eliminar</a>
                     </td></tr>
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
			        <h4 class="modal-title"><font color="white">Ingrese Nuevo Vacuno</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="saveVacuno.htm" method="POST">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese DIIO</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" id="inputEmail3" name="diio" placeholder="Ej: 00 658 4484" required>
			                  </div>
			                </div>
			                <br></br>
			                
			                   <% 
			                    LinkedList<razaTO> listR = (LinkedList<razaTO>) request.getAttribute("raza");
			                    String arr[]=new String[listR.size()];
			                    if(listR != null){
			                        
			                    	for (int i = 0; i < listR.size(); i++) {
			                            razaTO raza = listR.get(i);
			                            arr[i]=raza.getNombreRaza();
			                        }
			                    }
			                    %>
			              <div class="form-horizontal">
			                  <label  class="col-sm-3 control-label">Raza</label>
			                <div class="col-sm-9">
			                  <select name="raza" class="form-control select2" style="width: 100%;">
			                  <option selected="selected">Seleccione una opción</option>
			                  <%for(int j=0; j<arr.length;j++){ %>
			                  <option><%=arr[j]%></option>
			                   <%} %>
			                  </select>
			                </div>
			              </div>
			                  <br></br>
			                  
			           <div class="form-horizontal">
			                
			               <label  class="col-sm-3 control-label">Tipo</label>
			                <div class="col-sm-9">
			                  <select name="tipo" class="form-control select2" style="width: 100%;">
			                  <option selected="selected">Seleccione una opción</option>
			                  <option>Vacuno</option>
			                  <option>Vaquilla</option>
			                  <option>Toro</option>
			                  
			                  </select>
			                </div>
			            </div>
			                <br></br>
			                
			            <div class="form-horizontal">
			                <label  class="col-sm-3 control-label">Fecha  <i class="fa fa-calendar"></i></label>         
			                <div class="col-sm-9">
			                  <input type="date" name="fecha_in" class="form-control pull-right" id="datepicker">
			                </div>
			             </div>
			                  
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
			      
			              <form class="form-horizontal" action="actualizarVacuno.htm" method="GET">
			              <div class="box-body">
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese DIIO</label>
			
			                  <div class="col-sm-9">
			                    <input type="text" class="form-control" name="diioo"></input>
			                  </div>
			                </div>
			                <br></br>
			                

			              			                   <% 
			                    LinkedList<razaTO> listRa = (LinkedList<razaTO>) request.getAttribute("raza");
			                    String arr1[]=new String[listRa.size()];
			                    if(listRa != null){
			                        
			                    	for (int i = 0; i < listRa.size(); i++) {
			                            razaTO raza = listRa.get(i);
			                            arr1[i]=raza.getNombreRaza();
			                        }
			                    }
			                    %>
			              <div class="form-horizontal">
			                  <label  class="col-sm-3 control-label">Raza</label>
			                <div class="col-sm-9">
			                  <select name="raza" class="form-control select2" style="width: 100%;">
			                  <option id="razaV" selected="selected"></option>
			                  <%for(int j=0; j<arr1.length;j++){ %>
			                  <option><%=arr1[j]%></option>
			                   <%} %>
			                  </select>
			                </div>
			              </div>
			                  <br></br>
			                

			                  
			           <div class="form-horizontal">
			                
			               <label  class="col-sm-3 control-label">Tipo</label>
			                <div class="col-sm-9">
			                  <select name="tipo" class="form-control select2" style="width: 100%;">
			                  <option id="tipoV" selected="selected"></option>
			                  <option>Vacuno</option>
			                  <option>Vaquilla</option>
			                  <option>Toro</option>
			                  
			                  </select>
			                </div>
			            </div>
			                <br></br>
			                
			            <div class="form-horizontal">
			                <label  class="col-sm-3 control-label">Fecha  <i class="fa fa-calendar"></i></label>
			                <div class="col-sm-9">
			                  <input id="fecha_ing" type="date" name="fecha_in" class="form-control pull-right">
			                </div>
			            </div>
			                  
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
