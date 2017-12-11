package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.proyecto.transferObject.grupoTO;
import com.proyecto.transferObject.resultadoDietaTO;

public class dietaDAO {
	private static final String READ_NROV = "SELECT count(*) as numero FROM pinio_has_vacuno WHERE Pinio_idPinio=?";
	private static final String READ_PROPORCION="SELECT proporcion FROM dieta WHERE insumo_idinsumo=? and semana=?";
	private static final String READ_INSUMO="SELECT nombreInsumo FROM insumo WHERE idinsumo=?";
	private static final String READ_INSUMOXNOMBRE ="select idinsumo from insumo where nombreInsumo=?";
	private static final String READ_DISPONIBLE="select cantidad_actual from cantidad_disponible where insumo_idinsumo=?";
	private static final String UPDATE_DISPO="update cantidad_disponible set cantidad_actual=? where insumo_idinsumo=?";
	
    private static final String DB_NAME="bddjd_nueva";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
   public LinkedList<resultadoDietaTO> calculandoDieta(int idGrupo, int semana, String [] insumos) throws SQLException{
	   LinkedList<resultadoDietaTO> resultado = new LinkedList<resultadoDietaTO>();
	   /*Obtengo el numero de vacunos que tiene el grupo*/
	   conexion = getConnection();
  	   PreparedStatement ps = conexion.prepareStatement(READ_NROV);
       ps.setInt(1, idGrupo);
  	   ResultSet rs = ps.executeQuery();
       int nroVacunos=0;
       if(rs.next()) {
           nroVacunos =rs.getInt(1);
           System.out.println("nro Vacunos"+nroVacunos);
       }
       for(int i=1; i<insumos.length;i++) {
    	   System.out.println("i: "+i);
    	   int idInsumo=Integer.parseInt(insumos[i]);
    	   System.out.println("id insumo: "+idInsumo);
    	   resultadoDietaTO dieta = new resultadoDietaTO();
    	   conexion = getConnection();
    	   PreparedStatement ps1 = conexion.prepareStatement(READ_PROPORCION);
           ps1.setInt(1, idInsumo);
           ps1.setInt(2, semana);
      	   ResultSet rs1 = ps1.executeQuery();
           float proporcion=0;
           if(rs1.next()) {
               proporcion =rs1.getFloat(1);
               System.out.println("porporcion: "+proporcion);
           }
           /*Aqui calcula cuanto de cada insumo*/
           float resultadoCalculo = nroVacunos*proporcion*3;
           System.out.println("Resulatdo dietas: "+resultadoCalculo);
           /*---------------------------------*/
           conexion = getConnection();
           PreparedStatement ps2 = conexion.prepareStatement(READ_INSUMO);
           ps2.setInt(1, idInsumo);
           ResultSet rs2 = ps2.executeQuery();
           String nombreInsumo="";
           if(rs2.next()) {
        	  nombreInsumo = rs2.getString(1); 
           }
           
           dieta.setNombreInsumo(nombreInsumo);
           dieta.setResultado(resultadoCalculo);
           dieta.setSemana(semana);
           
           resultado.add(dieta);
           
       }
       return resultado;
	   
    }
   
   public void descuentaInsumos(String [] lista) throws SQLException {
	   int j=0;
	   String nombre = "";
	   int semana =0;
	   float resultado=0;
	   for(int i=0;i<lista.length;i++) {
		  j++;
		  switch (j) {
          case 1: 
          	System.out.println("caso 1 "+lista[i]);
          	nombre = lista[i];
          	break;
          case 2:
          	System.out.println("caso 2 "+lista[i]);
          	semana=Integer.parseInt(lista[i]);
          	break;
          case 3:
          	System.out.println("caso 3 "+lista[i]);
          	resultado = Float.parseFloat(lista[i]);
          	break;
          }
		  if(j==3) {
          	j=0;
          	
          	/*-----Obtiene id insumo por nombre---*/
            conexion = getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_INSUMOXNOMBRE);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            int idInsumo=0;
            if(rs.next()) {
         	  idInsumo = rs.getInt(1); 
            }
          	
            /*----Actualiza stock disponible de insumos----*/
        	conexion = getConnection();
            PreparedStatement ps4=conexion.prepareStatement(READ_DISPONIBLE);
            ps4.setInt(1,idInsumo);
            ResultSet rs3 = ps4.executeQuery();
            float disponible=0;
            if(rs3.next()) {
                disponible =rs3.getFloat(1);
            }
            
            float nuevaDisponibilidad = disponible-resultado;
            
        	conexion = getConnection();
            PreparedStatement ps5=conexion.prepareStatement(UPDATE_DISPO);
            ps5.setFloat(1, nuevaDisponibilidad);
            ps5.setInt(2, idInsumo);
            ps5.executeUpdate();
                        
          }
	   }
   }
    
    
    private static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion=(Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(ClassNotFoundException | SQLException e1){
            e1.printStackTrace();
            System.err.println("Error en BDD!!!");
        }
        return conexion;
    }
	
	
	
}
