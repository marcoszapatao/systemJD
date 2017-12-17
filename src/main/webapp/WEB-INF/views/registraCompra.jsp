<%-- 
    Document   : IngresarVacuno
    Created on : 09-10-2017, 16:37:15
    Author     : Marcosz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.proyecto.transferObject.insumoTO"%>
<%@page import="com.proyecto.transferObject.proveedorTO"%>
<%@page import="java.util.LinkedList"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Sistema JD</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="assets/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="assets/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="assets/bower_components/Ionicons/css/ionicons.min.css">
  <!-- daterange picker -->
  <link rel="stylesheet" href="assets/bower_components/bootstrap-daterangepicker/daterangepicker.css">
  <!-- bootstrap datepicker -->
  <link rel="stylesheet" href="assets/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
  <!-- iCheck for checkboxes and radio inputs -->
  <link rel="stylesheet" href="assets/plugins/iCheck/all.css">
  <!-- Bootstrap Color Picker -->
  <link rel="stylesheet" href="assets/bower_components/bootstrap-colorpicker/dist/css/bootstrap-colorpicker.min.css">
  <!-- Bootstrap time Picker -->
  <link rel="stylesheet" href="assets/plugins/timepicker/bootstrap-timepicker.min.css">
  <!-- Select2 -->
  <link rel="stylesheet" href="assets/bower_components/select2/dist/css/select2.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="assets/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="assets/dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
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
              <h3 class="box-title">Registrar compra de insumo</h3>
            </div>
            
            <!-- /.box-header -->
            <form class="form-horizontal" action="compra.htm" method="POST">
              <div class="box-body">

                   <% 
                    LinkedList<insumoTO> list = (LinkedList<insumoTO>) request.getAttribute("lista");
                    String arr[]=new String[list.size()];
                    if(list != null){
                        
                    	for (int i = 0; i < list.size(); i++) {
                            insumoTO insumo = list.get(i);
                            arr[i]=insumo.getNombre_insumo();
                        }
                    }
                    %>
              <div class="form-horizontal">
                  <label  class="col-sm-2 control-label">Insumo</label>
                <div class="col-sm-10">
                  <select name="insumo" class="form-control select2" style="width: 100%;">
                  <option selected="selected">Seleccione una opción</option>
                  <%for(int j=0; j<arr.length;j++){ %>
                  <option><%=arr[j]%></option>
                   <%} %>
                  </select>
                </div>
              </div>
                  <br></br>
              
              
                <% 
                    LinkedList<proveedorTO> lista = (LinkedList<proveedorTO>) request.getAttribute("proveedores");
                    String arreglo[]=new String[lista.size()];
                    if(lista != null){
                        
                    	for (int i = 0; i < lista.size(); i++) {
                            proveedorTO pro = lista.get(i);
                            arreglo[i]=pro.getNombreProveedor();
                        }
                    }
                    %>
              <div class="form-horizontal">
                  <label  class="col-sm-2 control-label">Proveedor</label>
                <div class="col-sm-10">
                  <select name="proveedor" class="form-control select2" style="width: 100%;">
                  <option selected="selected">Seleccione una opción</option>
                  <%for(int p=0; p<arreglo.length;p++){ %>
                  <option><%=arreglo[p]%></option>
                   <%} %>
                  </select>
                </div>
              </div>
                  <br></br> 
              
                 
             <div class="form-horizontal">
			 <label  class="col-sm-2 control-label">Fecha  <i class="fa fa-calendar"></i></label>         
			      <div class="col-sm-10">
			          <input type="date" name="fechaCompra" class="form-control pull-right" id="datepicker">
			      </div>
			 </div>
			 <br></br>
			 <div class="form-horizontal">
			 <label class="col-sm-2 control-label">Nro. documento</label>
			        <div class="col-sm-10">
			            <input type="text" class="form-control" id="inputEmail" name="documento" required>
			        </div>
			  </div>
			  <br></br>
             <div class="form-horizontal">
			 <label class="col-sm-2 control-label">Cantidad (Kg)</label>
			        <div class="col-sm-10">
			            <input type="number" min="0" class="form-control" id="inputEmail3" name="cantidad" required>
			        </div>
			  </div>
			  <br></br>
			 <div class="form-horizontal">
			 <label class="col-sm-2 control-label">Precio ($)</label>
			        <div class="col-sm-10">
			            <input type="number" min="0" class="form-control" id="inputEmail4" name="precio" required>
			        </div>
			  </div>
			  <br></br>
                  
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button href="#" type="submit" class="btn btn-default">Cancelar</button>
                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
                <button type="submit" class="btn btn-info pull-right"><i class="fa fa-floppy-o"> Registrar</i></button>
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
