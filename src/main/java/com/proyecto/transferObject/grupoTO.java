package com.proyecto.transferObject;
import java.sql.Date;
public class grupoTO {
    private int id_grupo;
    private String nombre;
    private String estado;
    private String patio;
    private Date fecha_ingreso;
    private Date fecha_Salida;
    private int peso;
    
    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    public void setPatio(String patio) {
        this.patio = patio;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setFecha_Salida(Date fecha_Salida) {
        this.fecha_Salida = fecha_Salida;
    }


    public int getId_grupo() {
        return id_grupo;
    }
    public int getPeso() {
        return peso;
    }
    
    public String getPatio() {
        return patio;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public Date getFecha_Salida() {
        return fecha_Salida;
    }
    
}
