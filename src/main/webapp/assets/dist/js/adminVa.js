/**
 * 
 */
 $(document).ready(function(){
	 $('#botonEditar').click(function(){
		 var id=$('#botonEditar').val();
		 $.ajax({
			 type:'GET',
			 url:"/editarVa.htm?"+id,
		     dataType:'json',
		     success: function(result){
		    	 var vacuno = result.id_vacuno;
		    	 $("#diioE").html(vacuno);
		     },
		     error:function(jqXHR, testStatus, errorThrown){
		    	 alert("Alerta "+textStatus+" "+errorThrown);
		     }
		 });
	 });
 });