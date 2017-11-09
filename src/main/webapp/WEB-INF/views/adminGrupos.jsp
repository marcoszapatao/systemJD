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
    .table{
    background-color:#ffffff;
    }
  </style>
  
  <script>
  var cont =0;
  function botonAgregarGru(){
		 $.ajax({
			 type:'GET',
			 url:"/systemjd/crearGrupo.htm",
		     //dataType:'json',
		     success:function(data){
                  
		    	  var result = "<thead><tr><th>Seleccione</th><th>Diio</th></tr></thead>";
                  result += "<tbody>";
                  var num = 1;
                  $.each(data,function(k,v){
                  	cont++;
                	var id = v.diio;
                	result += "<tr>";
                  	result += "<td>";
                  	result += "<center>";
                  	result += "<input class=messageCheckbox";
                  	result += num
                  	result +=" type=checkbox value='";
                  	num++;
                  	result += id
                  	result += "'>";
                  	result += "</center>";
                  	result += "</td>";
                  	result += "<td>";
                  	result += v.diio
                  	result += "</td>";
                  	result += "</tr>";
                  })
                  result += "</tbody>";
                  $("#resultado").html(result);
             
             $('#myModalAgregarG').modal('show');
		     },
		     error:function(jqXHR,errorThrown){
		    	 alert("Alerta "+errorThrown);
		     }
		 });
	 }

     function botonCrearG(){
    	 /*INCLUIR DIIO DE VACUNOS SELECCIONADOS EN EL FORM EN UNA ARREGLO*/
    	 var checkedValue = ["Inicio"];
    	 for(var i=1; i<(cont+1); i++){
    		 var message = "messageCheckbox";
    		 var letra = i;
    		 var res = message.concat(letra);
    		 //alert(res);
    		 var valor =$('.'+res+':checked').val();
    		 //alert("valor "+valor);
    		 checkedValue.push(valor);

    	 }
    	 //for(var i=0; i<6; i++){
    		 //alert(checkedValue[i]);
    	 //}
         //var checkedValue = $('.messageCheckbox:checked').val();
         //alert(checkedValue);
         
        /*----PRUEBA SERIALIZANDO DATOS DEL FORM---*/
         //var dataString = $('#form_crearG').serialize();
         //alert('Datos serializados: '+dataString);
        /*----------------OTRAS PRUEBAS------------*/
        var name = jQuery("#nombreGrupo").val();
        var estado = jQuery("#estadoGrupo").val();
        var fecha = jQuery("#datepicker").val();
        var peso = jQuery("#pesoGrupo").val();
        var object = {name:name,estado:estado,fecha:fecha,peso:peso};
        jQuery.ajax("/systemjd/newG.htm?diio="+checkedValue,
        	    {
        	        type:"POST",
        	        data: object
        	    }); 
        window.location.reload(true);
        /*------------------FIN PRUEBAS-------------*/
        
        /*Usando ajax*/
        //$.ajax({
        //	 type: 'GET',
        //	 url:"/systemjd/nuevoGrupo.htm?diio="+checkedValue,
        //	 success:function(data){
        //		 alert("Entro al success");
        //	 },
		 //    error:function(jqXHR,errorThrown){
		  //  	 alert("Alerta "+errorThrown);
		   //  }
        // });
         /*FIN usando ajax*/
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
	            <!-- a href="createGrupo.htm" class="btn btn-block btn-info"><i class="fa fa-plus"> Agregar</i></a-->
	            <button type="button" class="btn btn-block btn-info"  onclick="botonAgregarGru();"><i class="fa fa-plus"></i>  Agregar</button>
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
                    <td><a href="editarGrupo.htm?id=<%=task.getId_grupo()%>" class="btn btn-success"><i class="fa fa-edit"></i>  Editar</a> &nbsp; &nbsp; &nbsp;
                    <a href="deleteGrupo.htm?id=<%=task.getId_grupo()%>" class="btn btn-danger"  onclick="return confirm('¿Está seguro que desea eliminar el grupo con ID:  <%=task.getId_grupo()%>?');"><i class="fa fa-close"></i>  Eliminar</a></td>  </tr>
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
            
            <!--------------------------------- Comienxo modal ------------------->
            
            <div id="myModalAgregarG" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Crear Grupo:</font></h4>
			      </div>
			      <div class="modal-body">
                      <!-- action="saveGrupo.htm" method="GET" esto va abajo -->
		           <form class="form-horizontal" id="form_crearG">
		              <div class="box-body">
		                <div class="form-horizontal">
		                  <label for="inputEmail3" class="col-sm-3 control-label">Ingrese Nombre</label>
		
		                  <div class="col-sm-9">
		                    <input type="text" class="form-control" id="nombreGrupo" name="nombre" placeholder="Ej: GrupoUno">
		                  </div>
		                </div> 
		                
		                <br></br>
		              
		              <div class="form-horizontal">
		                
		                  <label  class="col-sm-3 control-label">Estado</label>
		                <div class="col-sm-9">
		                  <select name="estado" id="estadoGrupo" class="form-control select2" style="width: 100%;">
		                  <option selected="selected">Seleccione una opción</option>
		                  <option>Engorda</option>
		                  <option>Pradera</option>
		                  <option>Vendido</option>
		                  </select>
		                </div>
		              </div>
		                  <br></br>
		                  
		            
		             
		                <div class="form-horizontal">
		                  <label for="inputEmail3" class="col-sm-3 control-label">Peso Total(Kg)</label>
		
		                  <div class="col-sm-9">
		                    <input type="text" class="form-control" id="pesoGrupo" name="peso" placeholder="Ej: 1000">
		                  </div>
		                </div> 
		                
		                <br></br>
		              
		            <div class="form-horizontal">
		                <label  class="col-sm-3 control-label">Fecha de ingreso  <i class="fa fa-calendar"></i></label>
		                
		                <div class="col-sm-9">
		                <!--div class="input-group date"-->
		                  <!--div class="input-group-addon"-->
		                    
		                  <!--/div-->
		                  <input type="date" name="fecha_in" class="form-control pull-right" id="datepicker">
		                </div>
		                <!--/div>
		                <!-- /.input group -->
		              </div>
		              <br></br>
		           
		              <label  class="col-sm-3 control-label">Seleccione vacunos</label>
		              <!--------------------------- Aqui comienza la tabla------------------- -->
		              <div class="col-sm-9">
		              <table id="resultado" class="table table-bordered table-hover">
			             <thead>
			                <tr>
			                   <th>Seleccione</th>
			                   <th>Diio</th>
			                </tr>
			             </thead>
			             <tbody>
			             </tbody>
			          </table>
		              </div>    
		              </div>
		              <!-- /.box-body -->
		              <div class="box-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
		                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
		                <!-- button type="submit" class="btn btn-info pull-right"><i class="fa fa-floppy-o"> Guardar</i></button-->
		                <button type="submit" class="btn btn-info pull-right"  onclick="botonCrearG();"><i class="fa fa-floppy-o"></i>  Guardar</button>
		              </div>
		              <!-- /.box-footer -->
		            </form>
                      
                      
			      </div>
			    </div>
			
			  </div>
          </div>
            <!-- Fin modal -->
            
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
      $('#resultado').DataTable({ 
      'paging'      : true,
      'lengthChange': false,
      'searching'   : false,
      'ordering'    : true,
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