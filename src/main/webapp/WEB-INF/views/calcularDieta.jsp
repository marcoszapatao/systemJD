<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.proyecto.transferObject.grupoTO"%>
<%@page import="com.proyecto.transferObject.insumoTO"%>
<%@page import="com.proyecto.transferObject.resultadoDietaTO"%>
<%@page import="java.util.LinkedList"%>
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
        <script src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
        <script>
        /*
          function botonCalcula(tamano){
        	  var uno = 1;
        	  var size = (parseInt(tamano) + parseInt(uno));
        	  var checkedValue = ["Inicio"];
        	 for(var i=1; i<size; i++){
            	 //alert(i);
        		 var message = "messageCheckbox";
        		 var letra = i;
        		 var res = message.concat(letra);
        		 var valor =$('.'+res+':checked').val();
        		 if(valor === undefined){
                	 
                 }else{
            		 checkedValue.push(valor);
                 }
        	 }
        	  var grupo = jQuery("#grupo").val();
        	  //alert("ID:" + grupo);
        	  var semana = jQuery("#semana").val();
        	  //alert("grupo: "+grupo+"semana: "+semana + "insumos: " + checkedValue);
        	  var object = {grupo:grupo,semana:semana};
        	  jQuery.ajax(
              	    {
                        url:"/systemjd/calculando.htm?insumos="+checkedValue,        		  
               	        type:"POST",
              	        data: object,
                        
              	    });
          }*/
          function botonCalcula(tamano){
        	  var uno = 1;
        	  var size = (parseInt(tamano) + parseInt(uno));
        	  var checkedValue = ["Inicio"];
        	 for(var i=1; i<size; i++){
            	 //alert(i);
        		 var message = "messageCheckbox";
        		 var letra = i;
        		 var res = message.concat(letra);
        		 var valor =$('.'+res+':checked').val();
        		 if(valor === undefined){
                	 
                 }else{
            		 checkedValue.push(valor);
                 }
        	 }
        	  var grupo = jQuery("#grupo").val();
        	  //alert("ID:" + grupo);
        	  var semana = jQuery("#semana").val();
        	  var object = {grupo:grupo,semana:semana};
        		 $.ajax({
           			 type:'POST',
           			 url:"/systemjd/calculando.htm?insumos="+checkedValue,
           			 data: object,
           		     //dataType:'json',
           		     success:function(data){
                         var result = "<thead><tr><th>Insumo</th><th>Semana</th><th>Resultado</th></tr></thead>";
                         result += "<tbody>";
                         $.each(data,function(k,v){
                            
                         	result += "<tr>";
                         	result += "<td class=numero >";
                         	result += v.nombreInsumo
                         	result += "</td>";
                         	result += "<td>";
                         	result += v.semana
                         	result += "</td>";
                         	result += "<td>";
                         	result += v.resultado
                         	result += "</td>";
                         	result += "</tr>";
                         })
                         
                         result += "</tbody>";
                         $("#result").html(result);
           		    	 $('#myModalDieta').modal('show');
           		     },
           		     error:function(jqXHR,errorThrown){
           		    	 alert("Alerta "+errorThrown);
           		     }
           		     
        		 });
          }
          $(document).ready(function() {
              $("#ok").click(function() {
                var valores = "";

        				$(".numero").parent("tr").find("td").each(function() {
                		if($(this).html() != "hola"){
                    	 valores += $(this).html() + "iii";
                    }
                });
                
                valores = valores + "\n";
                //alert(valores);
                peticion(valores);
              });
          }); 
          
          function peticion(valor){
        	     alert(valor);
	           	 $.ajax({
	       			 type:'POST',
	       			 url:"/systemjd/descontar.htm?valores="+valor,
	       		     success:function(data){
	       		    	 
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

    </section>

    <!-- Main content -->
    <section class="content">

                   <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Calcular Dieta</h3>
            </div>
            
            <!-- /.box-header -->
            <form class="form-horizontal">
              <div class="box-body">

                   <% 
                    LinkedList<grupoTO> list = (LinkedList<grupoTO>) request.getAttribute("lista");
                    String arr[]=new String[list.size()];
                    int arrID[]=new int[list.size()];
                    if(list != null){
                        
                    	for (int i = 0; i < list.size(); i++) {
                            grupoTO grupo = list.get(i);
                            arr[i]=grupo.getNombre();
                            arrID[i]=grupo.getId_grupo();
                        }
                    }
                    %>
              <div class="form-horizontal">
                  <label  class="col-sm-2 control-label">Grupo</label>
                <div class="col-sm-10">
                  <select name="grupo" id="grupo" class="form-control select2" style="width: 100%;">
                  <option selected="selected">Seleccione una opción</option>
                  <%for(int j=0; j<arr.length;j++){ %>
                  <option value="<%=arrID[j]%>"><%=arr[j]%></option>
                   <%} %>
                  </select>
                </div>
              </div>
               <br></br>
               
                <div class="form-horizontal">
			                
			                <label  class="col-sm-2 control-label">Semana</label>
			                <div class="col-sm-10">
			                  <select name="raza" id="semana" class="form-control select2" style="width: 100%;">
			                  <option selected="selected"></option>
			                  <option value="1">1</option>
			                  <option value="2">2</option>
			                  <option value="3">3</option>
			                  <option value="4">4 o más</option>
			                  </select>
			                </div>
			              </div>
			   <br></br>
              
              
             <label  class="col-sm-2 control-label">Seleccione insumos</label>
              <!--------------------------- Aqui comienza la tabla------------------- -->
             <div class=col-sm-10>
                <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th></th>
                  <th>Insumo</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<insumoTO> lista = (LinkedList<insumoTO>) request.getAttribute("listaI");
                    int tamano = lista.size();
                    String m = "messageCheckbox";
                    if(lista != null)
                        for (int i = 0; i < lista.size(); i++) {
                            insumoTO task = lista.get(i);
                            int j = i+1;
                    %>
                    <tr><td><center><input class="<%=m+j%>" type="checkbox" value="<%=task.getId_insumo()%>"></center></td> <td><%=task.getNombre_insumo()%></td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th></th>
                  <th>Insumo</th>

            
                  
                </tr>
                </tfoot>
              </table>
              </div>
              
                  
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="button" class="btn btn-default">Cancelar</button>
                <button type="button" onclick="botonCalcula('<%=tamano%>');" class="btn btn-info pull-right" ><i class="fa fa-floppy-o"> Calcular</i></button>
                
              </div>
              <!-- /.box-footer -->
            </form> 
          </div>
          
                     <!-- Comienza modal-------------- -->
            <div id="myModalDieta" class="modal fade" role="dialog">
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
					                   <th>Insumo</th>
					                   <td>Semana</td>
					                   <td>Resultado</td>
					                </tr>
					             </thead>
					             <tbody>
					             </tbody>
					          </table>
		                   </center>
					      </div>
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
			                <button type="button" value="ok" id="ok" class="btn btn-info pull-right"><i class="fa fa-floppy-o"></i> Registrar</button>
			              </div>
			    </div>
			
			  </div>
          </div>
            <!-- Tremina modal -->
          
          
	       <div class="box">
	        <div class="box-header with-border">
	          <h3 class="box-title">¿Qué significa calcular una dieta?</h3>
	
	          <div class="box-tools pull-right">
	            <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
	              <i class="fa fa-minus"></i></button>
	            <button type="button" class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove">
	              <i class="fa fa-times"></i></button>
	          </div>
	        </div>
	        <div class="box-body">
	          Start creating your amazing application!
	        </div>
	        <!-- /.box-body -->
	      </div>
      
      
 

      
      
    </section>
        <!-- /.Left col -->
        <!-- right col (We are only adding the ID to make the widgets sortable)-->

        <!-- right col -->
      </div>
      <!-- /.row (main row) -->

    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <%@ include file="pieDePagina.jsp"%>
  </footer>

  <div class="control-sidebar-bg"></div>

<%@ include file="scripts.jsp"%>
</body>
</html>
