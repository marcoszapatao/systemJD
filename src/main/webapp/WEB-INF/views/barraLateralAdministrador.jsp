<%-- 
    Document   : barraLateral
    Created on : 09-10-2017, 16:23:50
    Author     : Marcosz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
     <div class="user-panel">
        <div class="pull-left image">
          <img src="assets/dist/img/logoJD.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>Carnes y Cecinas JD</p>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <!--li class="header">Menú</li-->
        <li class="treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>Vacunos</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="admin.htm"><i class="fa fa-circle-o"></i> Administrar</a></li>
           
          </ul>
        </li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-group"></i>
            <span>Grupos</span>
            <span class="pull-right-container">
              <!--span class="label label-primary pull-right"></span-->
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="adminGrupo.htm"><i class="fa fa-circle-o"></i> Administrar</a></li>
            
            
          </ul>
        </li>
        <li>
          <a href="inventario.htm">
            <i class="fa fa-th-list"></i> <span>Inventario</span>
          </a>
        </li>
        <li>
          <a href="dietas.htm">
            <i class="fa fa-pie-chart"></i>
            <span>Calcular Dieta</span>
          </a>
        </li>
        
        <li class="treeview">
          <a href="#">
            <i class="fa fa-leaf"></i>
            <span>Insumo</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="adminInsumo.htm"><i class="fa fa-circle-o"></i> Administrar</a></li>
            <li><a href="registrarCompra.htm"><i class="fa fa-circle-o"></i> Registrar compra</a></li>
 
          </ul>
        </li>
        <li>
          <a href="generarInforme.htm">
            <i class="fa fa-file-pdf-o"></i> <span>Generar Reporte</span>

          </a>

        </li>
        <li class="treeview">
          <a href="#">
            <i class="fa  fa-cog"></i> <span>Configuración</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="adminUsuarios.htm"><i class="fa fa-circle-o"></i> Usuarios</a></li>
            <li><a href="adminProveedor.htm"><i class="fa fa-circle-o"></i> Proveedores</a></li>
            <li><a href="adminRazas.htm"><i class="fa fa-circle-o"></i> Razas</a></li>
            <li><a href="adminPatios.htm"><i class="fa fa-circle-o"></i> Patios de alimentación</a></li>
          </ul>
        </li>
      </ul>
    </section>
