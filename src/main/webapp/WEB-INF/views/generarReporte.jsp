<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.proyecto.transferObject.insumoTO"%>
<%@page import="com.proyecto.transferObject.proveedorTO"%>
<%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
      <%@ include file="cabecera.jsp"%>
      <script type="text/javascript" src="assets/comprueba.js"></script>

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

    <!-- Main content -->
    <section class="content">

                   <!-- Horizontal Form -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">Generar Reporte</h3>
            </div>
            
          <c:if test="${not empty seleccion}">
								<script>
									toastr
											.error("Debe seleccionar todos los datos requeridos");
								</script>
		   </c:if>
		   
		   <c:if test="${not empty fechaAnteriorI}">
								<script>
									toastr
											.error("Fecha de inicio posterior a la actual");
								</script>
		   </c:if>
		   
		  <c:if test="${not empty fechaAnteriorT}">
								<script>
									toastr
											.error("Fecha de término posterior a la actual");
								</script>
		   </c:if>
            <!-- /.box-header -->
            <form class="form-horizontal" action="descargaInforme.htm" method="POST">
              <div class="box-body">


              <div class="form-horizontal">
                  <label  class="col-sm-2 control-label">Tipo reporte:</label>
                <div class="col-sm-10">
                  <select name="tipo" class="form-control select2" style="width: 100%;" required>
                  <option value="" disabled selected>Seleccione una opción</option>
                  <option value="1">Inventario Animales</option>
                  </select>
                </div>
              </div>
                  <br></br>

                 
             <div class="form-horizontal">
			 <label  class="col-sm-2 control-label">Fecha Inicio  <i class="fa fa-calendar"></i></label>         
			      <div class="col-sm-10">
			          <input type="date" name="fechaI" class="form-control pull-right" id="fechaIni" onchange="comprobarFInicio()" required>
			      </div>
			 </div>
			 <br></br>
			 
			 <div class="form-horizontal">
			 <label  class="col-sm-2 control-label">Fecha Término  <i class="fa fa-calendar"></i></label>         
			      <div class="col-sm-10">
			          <input type="date" name="fechaT" class="form-control pull-right" id="fechaSal" onchange="comprobarFSalida()" required>
			      </div>
			 </div>
			 <br></br>
                  
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                
                
                <button type="submit" class="btn btn-info pull-right"><i class="fa fa-download"> Generar</i></button>
              </div>
              <!-- /.box-footer -->
            </form>
			            
            
            
            
            
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

<!-- ./wrapper -->


<!-- jQuery 3 -->
<script src="assets/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="assets/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- Select2 -->
<script src="assets/bower_components/select2/dist/js/select2.full.min.js"></script>
<!-- InputMask -->
<script src="assets/plugins/input-mask/jquery.inputmask.js"></script>
<script src="assets/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="assets/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<!-- date-range-picker -->
<script src="assets/bower_components/moment/min/moment.min.js"></script>
<script src="assets/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="assets/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<!-- bootstrap color picker -->
<script src="assets/bower_components/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
<!-- bootstrap time picker -->
<script src="assets/plugins/timepicker/bootstrap-timepicker.min.js"></script>
<!-- SlimScroll -->
<script src="assets/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="assets/plugins/iCheck/icheck.min.js"></script>
<!-- FastClick -->
<script src="assets/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="assets/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="assets/dist/js/demo.js"></script>
<!-- Page script -->
</body>
</html>
