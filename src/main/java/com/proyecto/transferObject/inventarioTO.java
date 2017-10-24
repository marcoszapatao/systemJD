package com.proyecto.transferObject;

import java.sql.Date;

public class inventarioTO {
    private int id_grupo;
	private String nombre;
    private int nroAnimales;
    private String estado;
    private Date fecha_ingreso;
    private Date fecha_Salida;
    
    public int getId_grupo() {
		return id_grupo;
	}
    
	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getNroAnimales() {
		return nroAnimales;
	}
	
	public void setNroAnimales(int nroAnimales) {
		this.nroAnimales = nroAnimales;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}
	
	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}
	
	public Date getFecha_Salida() {
		return fecha_Salida;
	}
	
	public void setFecha_Salida(Date fecha_Salida) {
		this.fecha_Salida = fecha_Salida;
	}
	

}
