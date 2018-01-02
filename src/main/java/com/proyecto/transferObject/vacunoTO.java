package com.proyecto.transferObject;
import java.sql.Date;
public class vacunoTO {
	   private int id_vacuno;
	   private String diio;
	    private String tipo;
	    private Date fechaI;
	    private String raza;

	    public void setId_vacuno(int id_vacuno) {
	    	this.id_vacuno=id_vacuno;
	    }
	    public void setDiio(String diio) {
	        this.diio = diio;
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

        
	    public int getIdVacuno() {
	    	return id_vacuno;
	    }
	    
	    public String getDiio() {
	        return diio;
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
