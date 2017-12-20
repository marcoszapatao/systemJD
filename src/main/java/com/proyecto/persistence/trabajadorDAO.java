package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proyecto.transferObject.trabajadorTO;


public class trabajadorDAO {
	   private static final String BUSCA_TRABAJADOR ="select * from trabajador where usuario_idusuario=?";
	
	   private static final String DB_NAME="bddjd_nueva";
	   private static final String PORT="3306";
	   private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
	   private static final String USER="root";
	   private static final String PASSWORD="";
	   private static Connection conexion = null;
	   
	   public trabajadorTO obtieneTrabajador(int idUsuario) {
			Connection conn = null;
			trabajadorTO result = null;
			try {
				conn = getConnection();

				PreparedStatement ps = conn.prepareStatement(BUSCA_TRABAJADOR);
				ps.setInt(1, idUsuario);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result = new trabajadorTO();
					result.setIdTrabajador(rs.getInt("idtrabajador"));
					result.setNombreTrabajador(rs.getString("nombre"));
					result.setCargoTrabajador(rs.getString("cargo"));
					
				}
			} catch (SQLException e) {

			}
			return result;
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
