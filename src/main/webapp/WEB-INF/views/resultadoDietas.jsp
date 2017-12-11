<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.proyecto.transferObject.resultadoDietaTO"%>
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
            <table id="example" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Estado</th>
                  
                </tr>
                </thead>
                <tbody>

                    <% 
                    LinkedList<resultadoDietaTO> listaD = (LinkedList<resultadoDietaTO>) request.getAttribute("listaDieta");
                    if(listaD != null)
                        for (int i = 0; i < listaD.size(); i++) {
                            resultadoDietaTO task = listaD.get(i);
                    %>
                    <tr> <td><%=task.getNombreInsumo()%></td> <td><%=task.getSemana()%></td><td><%=task.getResultado()%></td></tr>
                    <%} else{%>
                        <h1>No hay datos</h1>
                    <%}%>                
                </tbody>
                <tfoot>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Estado</th>

            
                  
                </tr>
                </tfoot>
              </table>
              

	        
            </div>  

            <!-- /.box-header -->
            <div class="box-body">

              
            </div>
            
            <!--------- Comienzo modal Agregar Vacuno ------------->
 
          <!--------- FIN modal Agregar Vacuno ------------->
          
          <!-- Molda editar vacuno -->

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
