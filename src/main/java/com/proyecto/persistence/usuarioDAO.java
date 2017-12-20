package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.proyecto.transferObject.usuarioTO;

public class usuarioDAO {
	
	private static final String VERIFICAR_USUARIO="select * from usuario where email=? and password=?";
   private static final String DB_NAME="bddjd_nueva";
   private static final String PORT="3306";
   private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
   private static final String USER="root";
   private static final String PASSWORD="";
   
   private static Connection conexion = null;
   
	public usuarioTO verificar(usuarioTO usuario) {
		Connection conn = null;
		usuarioTO result = null;
		try {
			conn = getConnection();

			PreparedStatement ps = conn.prepareStatement(VERIFICAR_USUARIO);
			ps.setString(1, usuario.getEmailUsuario());
			ps.setString(2, usuario.getPasswordUsuario());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new usuarioTO();
				result.setIdUsuario(rs.getInt("idusuario"));
				result.setEmailUsuario(rs.getString("email"));
				result.setRolUsuario(rs.getString("rol"));

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
