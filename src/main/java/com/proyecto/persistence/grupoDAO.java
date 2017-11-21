package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.proyecto.transferObject.grupoTO;
import com.proyecto.transferObject.vacunoTO;

public class grupoDAO {
    private static final String INSERT_QUERY ="insert into pinio(nombrePinio,estado,fechaIngreso) values(?,?,?)";
    private static final String INSERT_PINIOVACUNO="insert into pinio_has_vacuno(Pinio_idPinio,vacuno_idvacuno,fechaMov) values (?,?,?)";
    private static final String INSERT_PESO="insert into peso(pesoPinio,fecha,trabajador_idtrabajador,Pinio_idPinio) values (?,?,?,?)";
    private static final String READ_IDVACUNO="select idvacuno from vacuno where diio=?";
    private static final String READ_MAX="select MAX(idPinio) from pinio";
    private static final String DELETE_PINIOVACUNO="DELETE FROM pinio_has_vacuno WHERE Pinio_idPinio=?";
    private static final String DELETE_PINIOPESO="DELETE FROM peso WHERE Pinio_idPinio=?";
    private static final String DELETE_PINIO="DELETE FROM pinio WHERE idPinio=?";
    private static final String UPDATE_GRUPO="UPDATE pinio SET nombrePinio = ?,estado = ?,fechaIngreso = ? WHERE idPinio=?";
    private static final String UPDATE_PESO_GRUPO="update peso set pesoPinio = ? where Pinio_idPinio = ?";
    private static final String READ_QUERY="select nombrePinio,estado,fechaIngreso,fechaSalida,pesoPinio from pinio join peso on(pinio.idPinio = peso.Pinio_idPinio) where idPinio=?";
    private static final String READ_ALL ="select * from pinio";
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
    private static final String READ_VACUNOSGRUPO=
    		"SELECT vacuno.idvacuno,vacuno.diio FROM vacuno join pinio_has_vacuno on(vacuno.idvacuno=pinio_has_vacuno.vacuno_idvacuno) WHERE pinio_has_vacuno.Pinio_idPinio=?";
    
    private static final String DB_NAME="bddjd_nueva";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    /*private static final String DB_NAME="mezapata";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://146.83.196.166/"+DB_NAME;    
    private static final String USER="mezapata";
    private static final String PASSWORD="KrN5EfGXoBA3";*/
    private static Connection conexion = null;
    
    public LinkedList<grupoTO> readAllG() throws SQLException{
        LinkedList<grupoTO> list = new LinkedList<>();
        grupoTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new grupoTO();
                result.setId_grupo(rs.getInt("idPinio"));
                result.setNombre(rs.getString("nombrePinio"));
                result.setEstado(rs.getString("estado"));
                result.setFecha_ingreso(rs.getDate("fechaIngreso"));
                result.setFecha_Salida(rs.getDate("fechaSalida"));
                
                list.add(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(grupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conexion.close();
        }
        return list;
    }
    
    public void createGrupo(grupoTO grupo,String[]vacunos) throws SQLException {
    	try{ 
    	 /*Inserta datos en tabla pinio*/
    	 conexion = getConnection();
    	 PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
         ps.setString(1, grupo.getNombre());
         ps.setString(2,grupo.getEstado());
         Date fe =grupo.getFecha_ingreso();
         ps.setDate(3, fe);
         ps.executeUpdate();
         /*Obtiene el id del piño que recien se inserto*/
         conexion = getConnection();
    	 PreparedStatement ps2 = conexion.prepareStatement(READ_MAX);
         ResultSet rs2 = ps2.executeQuery();
         int idpinio=0;
         if(rs2.next()) {
             idpinio =rs2.getInt(1);
             System.out.println("id piño"+idpinio);
         }
         /*Obtiene fecha del sistema*/
         java.util.Date utilDate = new java.util.Date();
         java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
         System.out.println("fecha "+sqlDate);
         
         /*Obtiene el idvacuno segun su diio */
         conexion = getConnection();
         System.out.println("tamaño arr "+vacunos.length);
         int tamano = vacunos.length;
         for(int i=1;i<tamano;i++) {
        	 PreparedStatement ps1 = conexion.prepareStatement(READ_IDVACUNO);
             System.out.println("iiii"+i);
        	 ps1.setString(1,vacunos[i]);
             ResultSet rs = ps1.executeQuery();
             int idvacuno=0;
             if(rs.next()) {
                 idvacuno =rs.getInt(1);
                 System.out.println("id vacuno "+idvacuno);
             }
             conexion = getConnection();
             /*Inserta una tupla por cada diio en la tabla pinio_has_vacuno*/
             PreparedStatement ps3 = conexion.prepareStatement(INSERT_PINIOVACUNO);
             ps3.setInt(1,idpinio);
             ps3.setInt(2, idvacuno);
             ps3.setDate(3, sqlDate);
             ps3.executeUpdate();   
         }
         System.out.println("ANTES");
         insertapeso(grupo,sqlDate,idpinio);
    	System.out.println("DESPUES");
         /*Inserta el peso del grupo en la tabla peso
         conexion = getConnection();
         PreparedStatement ps4 = conexion.prepareStatement(INSERT_PESO);
         ps4.setInt(1,grupo.getPeso());
         ps4.setDate(2,sqlDate);
         int tra = 1;
         ps4.setInt(3,tra);
         ps4.setInt(4,idpinio);
         ps4.executeUpdate();
         */
    	}catch(SQLException e){
            System.out.println(e);
        }finally{
            if(conexion!=null)
                conexion.close();
        }
    }
    
    public void insertapeso(grupoTO grupo,Date fecha,int id) throws SQLException {
    	System.out.println("DENTRO");
    	conexion = getConnection();
        PreparedStatement ps4 = conexion.prepareStatement(INSERT_PESO);
        ps4.setInt(1,grupo.getPeso());
        ps4.setDate(2,fecha);
        int tra = 1;
        ps4.setInt(3,tra);
        ps4.setInt(4,id);
        ps4.executeUpdate();
    }
    
    public grupoTO readG(int id) throws SQLException{
        grupoTO result = null;
        
       try {
           getConnection();
           PreparedStatement ps = conexion.prepareStatement(READ_QUERY);
           ps.setInt(1,id);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               result= new grupoTO();
               result.setNombre(rs.getString("nombrePinio"));
               result.setEstado(rs.getString("estado"));
               result.setPeso(rs.getInt("pesoPinio"));
               result.setFecha_ingreso(rs.getDate("fechaIngreso"));
               result.setFecha_Salida(rs.getDate("fechaSalida"));
           }
       } catch (SQLException ex) {
           Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           //conexion.close();
       }
       return result;
    }
    
    public void updateGrupo(grupoTO grupo) throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps = conexion.prepareStatement(UPDATE_GRUPO);
        ps.setString(1,grupo.getNombre());
        ps.setString(2,grupo.getEstado());
        ps.setDate(3,grupo.getFecha_ingreso());
        ps.setInt(4, grupo.getId_grupo());
        ps.executeUpdate();
        
        conexion = getConnection();
        PreparedStatement ps1 = conexion.prepareStatement(UPDATE_PESO_GRUPO);
        ps1.setInt(1, grupo.getPeso());
        ps1.setInt(2, grupo.getId_grupo());
        ps1.executeUpdate();
        
        
        /*Obtiene fecha del sistema
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("fecha "+sqlDate);
        
        
        conexion = getConnection();
        PreparedStatement ps2 = conexion.prepareStatement(READ_VACUNOSGRUPO);
        ps2.setInt(1,grupo.getId_grupo());
        ResultSet rs = ps2.executeQuery();*/
       /*
        while(rs.next()){
        	int idvacuno=rs.getInt("idvacuno");
        	System.out.println(idvacuno);
        	String diioV = rs.getString("diio");
        	System.out.println(diioV);
        	for(int i=1;i<diio.length;i++) {
        		System.out.println("en el for"+diio[i]);
        		/*if(diio[i].equalsIgnoreCase(diioV)) {
        		System.out.println("Ambos diios son iguales"+diio[i]+" "+diioV);
        		}else {
        		System.out.println("Diios distintos insertas!!!"+diio[i]+" "+diioV);
        		conexion = getConnection();
        		/*Inserta una tupla por cada diio en la tabla pinio_has_vacuno
        		PreparedStatement ps3 = conexion.prepareStatement(INSERT_PINIOVACUNO);
        		ps3.setInt(1,grupo.getId_grupo());
        		ps3.setInt(2, idvacuno);
        		ps3.setDate(3, sqlDate);
        		ps3.executeUpdate();   
        		}
        	}
        }*/
    }
    
    public void eliminarGrupo(int idGrupo) throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps = conexion.prepareStatement(DELETE_PINIOVACUNO);
        ps.setInt(1,idGrupo);
        ps.executeUpdate();
        
        conexion = getConnection();
        PreparedStatement ps1 = conexion.prepareStatement(DELETE_PINIOPESO);
        ps1.setInt(1,idGrupo);
        ps1.executeUpdate();
        
        
        conexion = getConnection();
        PreparedStatement ps2 = conexion.prepareStatement(DELETE_PINIO);
        ps2.setInt(1,idGrupo);
        ps2.executeUpdate();
        
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
