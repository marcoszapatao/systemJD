<%-- 
    Document   : barraSuperior
    Created on : 09-10-2017, 16:19:59
    Author     : Marcosz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
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
  <head>
  <script language="JavaScript" type="text/JavaScript">
function verif(formu){	
	if (formu.pass.value==formu.pass1.value) { 
		return true 
		}else{ 
			toastr["error"]("ERROR: Las contraseñas no coinciden");
			return false 
		}
}
</script>
</head>
     <%
 	HttpSession ses = request.getSession();
 	String rolU = (String) ses.getAttribute("rol");
 	%>
    <!-- Logo -->
    <a  href="pagPrincipal.htm?rol=<%=rolU%>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b></b>JD</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Sistema</b>JD</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">

          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="assets/dist/img/user1-128x128.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><%
 	HttpSession sesion = request.getSession();
 	String nombre = (String) sesion.getAttribute("nombre");
 	out.print(nombre);
 %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="assets/dist/img/user1-128x128.jpg" class="img-circle" alt="User Image">

                <p>
                  <%
 	HttpSession sesionn = request.getSession();
 	String cargo = (String) sesionn.getAttribute("cargo");
 	int iduser = (int) sesionn.getAttribute("idusuario");
 	String rolUser = (String) sesionn.getAttribute("rol");
 	out.print(cargo);
 %> - <%out.print(nombre); %>
        
                </p>
              </li>
              <!-- Menu Body -->

              <!-- Menu Footer-->
              <li class="user-footer">
                <!-- div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Perfil</a>
                </div-->
                <div class="pull-left">
                  <a class="btn btn-default btn-flat" data-toggle="modal" data-target="#myModalPass" data-backdrop="false">Cambiar contraseña</a>
                </div>
                <div class="pull-right">
                  <a href="login.htm" class="btn btn-default btn-flat">Salir</a>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
          <div id="myModalPass" class="modal fade" role="dialog">
			  <div class="modal-dialog modal-lg">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title"><font color="white">Ingrese Nuevo Contraseña</font></h4>
			      </div>
			      <div class="modal-body">
			      
			              <form class="form-horizontal" action="cambiarPass.htm" method="POST">
			              <div class="box-body">
			              <input type="hidden" id ="iduser" name="iduser" value=<%=iduser%>>
			              <input type="hidden" id ="roluser" name="roluser" value=<%=rolUser%>>
			                <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Nueva contraseña</label>
			                  <div class="col-sm-9">
			                    <input type="password" class="form-control" id="pass" name="pass"  required>
			                  </div>
			                </div>
			                <br></br>
                            <div class="form-horizontal">
			                  <label class="col-sm-3 control-label">Ingrese nuevamente</label>
			                  <div class="col-sm-9">
			                    <input type="password" class="form-control" id="pass1" name="pass1" required>
			                  </div>
			                </div>
			                <br></br>
			                  
			              </div>
			              <!-- /.box-body -->
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			                <!-- input type="hidden" name="action" value="saveVacuno.htm"-->
			                <button type="submit" class="btn btn-info pull-right" onclick='return(verif(this.form)); MM_validateForm()'><i class="fa fa-floppy-o"></i> Guardar</button>
			              </div>
			              <!-- /.box-footer -->
			            </form>
			      </div>

			    </div>
			
			  </div>
          </div>
    </nav>
