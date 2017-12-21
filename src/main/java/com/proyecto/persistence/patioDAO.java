package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.patioTO;



public class patioDAO {
	private static final String READ_ALL="select * from patio";
	private static final String INSERT_QUERY="insert into patio(nombrePatio,nroNaves) values(?,?)";
	private static final String READ_QUERY="select * from patio where idpatio=?";
	private static final String DELETE_QUERY="delete from patio where idpatio=?";
	private static final String UPDATE_QUERY="update patio set nombrePatio=?,nroNaves=? where idpatio=?";
	
    private static final String DB_NAME="bddjd_nueva";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
    
	public LinkedList<patioTO> readAllP() throws SQLException{
	    LinkedList<patioTO> lista = new LinkedList<>();
	    patioTO result = null;
	    Connection conn = null;
	    try {
	        conn = getConnection();
	        PreparedStatement ps = conn.prepareStatement(READ_ALL);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()){
	            result= new patioTO();
	            result.setIdPatio(rs.getInt("idpatio"));
	            result.setNombrePatio(rs.getString("nombrePatio"));
	            result.setNroNavesPatio(rs.getInt("nroNaves"));
	            lista.add(result);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally{
	        conn.close();
	    }
	    return lista;
	}
	
    public int createPatio(patioTO patio) throws SQLException {
    	int r = 0;
    	try {
    	conexion = getConnection();
        PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
        ps.setString(1, patio.getNombrePatio());
        ps.setInt(2, patio.getNroNavesPatio());
        ps.executeUpdate();
    	 }catch(SQLException e){
             System.out.println(e);
             r=1;
         }finally{
             if(conexion!=null)
                 conexion.close();
         }
    	return r;
    }
	
    public patioTO read(int id) {
        patioTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_QUERY);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new patioTO();
                result.setIdPatio(rs.getInt("idpatio"));
                result.setNombrePatio(rs.getString("nombrePatio"));
                result.setNroNavesPatio(rs.getInt("nroNaves"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            //conexion.close();
        }
        return result;
    }
    
    public int delete(patioTO patio) throws SQLException{
    int resultado = 0;
    try{
      conexion = getConnection();
      PreparedStatement ps = conexion.prepareStatement(DELETE_QUERY);
      ps.setInt(1,patio.getIdPatio());
      ps.executeUpdate();
    }catch(SQLException e){
        System.out.println("Error: " + e.getMessage());
        resultado = 1;
        return resultado;
    }finally{
        if(conexion!=null)
            conexion.close();
    }
   return resultado;
   }
    
    public int update(patioTO patio) throws SQLException {
    	int r=0;
    	try {
    	conexion= getConnection();
        PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
        ps.setString(1,patio.getNombrePatio());
        ps.setInt(2,patio.getNroNavesPatio());
        ps.setInt(3, patio.getIdPatio());
        ps.executeUpdate();
        
    	 }catch(SQLException e){
             System.out.println("Error: " + e.getMessage());
         }finally{
             if(conexion!=null)
                 conexion.close();
         }
    	return r;
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
