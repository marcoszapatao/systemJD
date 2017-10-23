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
  
</head>
<body class="hold-transition skin-blue sidebar-mini">
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
            </div>
                
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th>
                  <th>Fecha Salida</th>
                  <th>Editar</th>
                  <th>Eliminar</th>
                </tr>
                </thead>
                <tbody>
                                <% 
            LinkedList<grupoTO> list = (LinkedList<grupoTO>) session.getAttribute("list");
            if(list != null)
                for (int i = 0; i < list.size(); i++) {
                    grupoTO task = list.get(i);
            %>
            <tr> <td><%=task.getId_grupo()%></td> <td><%=task.getEstado()%></td> <td><%=task.getFecha_ingreso()%></td> <td><%=task.getFecha_Salida()%></td><td><a href="#" class="btn btn-success"><i class="fa fa-edit"> Editar</i></a></td><td><a href="#" class="btn btn-danger" data-toggle="modal" data-target="#modal-info"><i class="fa fa-close"> Eliminar</i></a></td>  </tr>
            <%} else{%>
                <h1>No hay datos</h1>
            <%}%> 
                </tbody>
                <tfoot>
                <tr>
                  <th>ID</th>
                  <th>Estado</th>
                  <th>Fecha Ingreso</th>
                  <th>Fecha Salida</th>
                  <th>Editar</th>
                  <th>Eliminar</th>
                  
                </tr>
                </tfoot>
              </table>
              
            </div>
            
            <!-- /.box-body -->
          </div>  
        <div class="col-sm-2">
        <td>
          
            <a href="vacunoController?action=create" class="btn btn-block btn-info"><i class="fa fa-plus"> Agregar</i></a>
        </td>
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
</body>
</html>

