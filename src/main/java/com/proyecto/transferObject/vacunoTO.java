package com.proyecto.transferObject;
import java.sql.Date;
public class vacunoTO {
	   private String id_vacuno;
	    private String tipo;
	    private Date fechaI;
	    private String raza;

	    public void setDiio(String diio) {
	        this.id_vacuno = diio;
	    }

	    public void setTipo(String tipo) {
	        this.tipo = tipo;
	    }

	    public void setFechaIngreso(Date fechaIngreso) {
	        this.fechaI = fechaIngreso;
	    }

	    public void setRaza(String raza) {
	        this.raza = raza;
	    }


	    public String getDiio() {
	        return id_vacuno;
	    }

	    public String getTipo() {
	        return tipo;
	    }

	    public Date getFechaIngreso() {
	        return fechaI;
	    }

	    public String getRaza() {
	        return raza;
	    }
	    
}
