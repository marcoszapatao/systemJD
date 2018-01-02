/**
 * 
 */	
   function comprobar() {
   		var fecha = jQuery("#dateIn").val();
   		fechaS = fecha.split("-");
   		fechaPuesta= new Date(fechaS[0],fechaS[1]-1,fechaS[2]);
  
   		fechaActual = new Date();
   		if(fechaPuesta>=fechaActual){
   			toastr["error"]("ERROR: La fecha ingresada es posterior a la actual");
   		}
   		
   	    
   }
   
   function comprobarEditar() {
  		var fecha = jQuery("#fecha_ing").val();
  		fechaS = fecha.split("-");
  		fechaPuesta= new Date(fechaS[0],fechaS[1]-1,fechaS[2]);
 
  		fechaActual = new Date();
  		if(fechaPuesta>=fechaActual){
  			toastr["error"]("ERROR: La fecha editada es posterior a la actual");
  		}   
  }
   
   function comprobarGrupo() {
 		var fecha = jQuery("#datepicker").val();
 		fechaS = fecha.split("-");
 		fechaPuesta= new Date(fechaS[0],fechaS[1]-1,fechaS[2]);

 		fechaActual = new Date();
 		if(fechaPuesta>=fechaActual){
 			toastr["error"]("ERROR: La fecha ingresada es posterior a la actual");
 			document.getElementById('datepicker').value = '';
 		}   
 }
   
   function comprobarGrupoEditar() {
		var fecha = jQuery("#fechaInE").val();
		fechaS = fecha.split("-");
		fechaPuesta= new Date(fechaS[0],fechaS[1]-1,fechaS[2]);

		fechaActual = new Date();
		if(fechaPuesta>=fechaActual){
			toastr["error"]("ERROR: La fecha editada es posterior a la actual");
			document.getElementById('fechaInE').value = '';
		}   
}
   
   function comprobarGrupoSalida() {
		var fecha = jQuery("#fechaSaE").val();
		fechaS = fecha.split("-");
		fechaPuesta= new Date(fechaS[0],fechaS[1]-1,fechaS[2]);

		var fechaIngreso = jQuery("#fechaInE").val();
		fechaIn = fechaIngreso.split("-");
		fechaIngresoV = new Date(fechaIn[0],fechaIn[1]-1,fechaIn[2]);
		
		console.log(fechaIngreso);
        console.log(fecha);
        
        console.log(fechaIngresoV);
        console.log(fechaPuesta);
        
		if(fechaPuesta <= fechaIngresoV){
			toastr["error"]("ERROR: La fecha de salida es anterior a la fecha de ingreso");
			document.getElementById('fechaSaE').value = '';
		}   
}