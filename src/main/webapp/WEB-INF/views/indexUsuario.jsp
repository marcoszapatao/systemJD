<!DOCTYPE html>
<html>
<head>
 <%@ include file="cabecera.jsp"%>
  <%@page import="java.util.LinkedList"%>
 <%@page import="com.proyecto.persistence.vacunoDAO"%>
  <%@page import="com.proyecto.persistence.insumoDAO"%>
  <%@page import="com.proyecto.persistence.grupoDAO"%>
   <%@page import="com.proyecto.transferObject.insumoTO"%>
</head>
<body class="hold-transition skin-red sidebar-mini">
<div class="wrapper">

  <header class="main-header">
     <%@ include file="barraSuperior.jsp"%>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <%@ include file="barraLateralUsuario.jsp"%>
    <!-- /.sidebar -->
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
      <!-- Small boxes (Stat box) -->
      <div class="row">
        <%vacunoDAO vacunito=new vacunoDAO();%>
        <%insumoDAO insumo= new insumoDAO(); %>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3><%=vacunito.totalVacunos()%><sup style="font-size: 20px"></sup></h3>

              <p>N° Total de Vacunos</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="inventario.htm" class="small-box-footer">Detalles <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>

        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>$<%=insumo.obtieneGastos()%></h3>

              <p>Gastos del mes actual</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="#" class="small-box-footer">Detalles <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
      </div>
        <!-- ./col -->
        <div class="row">


             <%grupoDAO grupo=new grupoDAO();
            int engorda = grupo.totalVacunosEstado("Engorda");
            int pradera = grupo.totalVacunosEstado("Pradera");
            int vendido = grupo.totalVacunosEstado("Vendido");
            int total = engorda+pradera+vendido;
            
            int porcentajeE = (engorda*100)/total;
            int porcentajeP = (pradera*100)/total;
            int porcentajeV = (vendido*100)/total;
            %>
        <!-- /.col -->
        <div class="col-md-6">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">Detalle por etapas</h3>


            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <table class="table">
                <tr>
                  <th style="width: 10px">#</th>
                  <th>Etapa</th>
                  <th>Progreso</th>
                  <th style="width: 40px">%</th>
                </tr>
                <tr>
                  <td>1.</td>
                  <td>Engorda</td>
                  <td>
                    <div class="progress progress-xs">
                      <div class="progress-bar progress-bar-danger" style="width: <%=porcentajeE%>%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-red"><%=porcentajeE%>%</span></td>
                </tr>
                <tr>
                  <td>2.</td>
                  <td>Pradera</td>
                  <td>
                    <div class="progress progress-xs">
                      <div class="progress-bar progress-bar-yellow" style="width: <%=porcentajeP%>%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-yellow"><%=porcentajeP%>%</span></td>
                </tr>
                <tr>
                  <td>3.</td>
                  <td>Vendido</td>
                  <td>
                    <div class="progress progress-xs progress-striped active">
                      <div class="progress-bar progress-bar-primary" style="width: <%=porcentajeV%>%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-light-blue"><%=porcentajeV%>%</span></td>
                </tr>

              </table>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
          

          
          <!-- /.box -->
        </div>
        <div class="col-md-6">
                      <div class="box">
            <div class="box-header">
              <h3 class="box-title">Detalle por insumos</h3>
              <div class="box-tools">
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <table class="table table-striped">
                <tr>
                  <th style="width: 10px">#</th>
                  <th>Insumo</th>
                  <th>Progreso</th>
                  <th style="width: 40px">%</th>
                </tr>
                 <% 
                    insumoDAO insum = new insumoDAO();
                    LinkedList<insumoTO> list = insum.stockIndex();
                        for (int i = 0; i < list.size(); i++) {
                            insumoTO task = list.get(i);                          
                    %>
                <tr>
                  <td><%=(i+1) %></td>
                  <td><%=task.getNombre_insumo()%></td>
                  <td>
                    <div class="progress progress-xs">
                      <div class="progress-bar progress-bar-primary" style="width: <%=task.getId_insumo()%>%"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-red"><%=task.getId_insumo()%>%</span></td>
                </tr>
                <%} %>
              </table>
            </div>
            <!-- /.box-body -->
          </div>
        </div>
        
        <!-- /.col -->
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
