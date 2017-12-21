package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proyecto.transferObject.insumoTO;
import com.proyecto.transferObject.trabajadorTO;
import com.proyecto.transferObject.usuarioTO;

public class usuarioDAO {
	
	private static final String VERIFICAR_USUARIO="select * from usuario where email=? and password=?";
	private static final String BUSCA_USUARIO="select * from usuario where idusuario=?";
    private static final String INSERT_USUARIO="insert into usuario(email,password,rol) values(?,?,?)";
    private static final String READ_IDUSUARIO="select idusuario from usuario where email=?";
    private static final String INSERT_TRABAJADOR="insert into trabajador(nombre,cargo,usuario_idusuario) values(?,?,?)";
	private static final String DELETE_TRABAJADOR ="delete from trabajador where idtrabajador=?";
	private static final String DELETE_USUARIO = "delete from usuario where idusuario=?";
	private static final String UPDATE_QUERY = "update usuario set email=?,rol=? where idusuario=?";
    
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
	
	   public usuarioTO read(int idUsuario) {
			Connection conn = null;
			usuarioTO result = null;
			try {
				conn = getConnection();

				PreparedStatement ps = conn.prepareStatement(BUSCA_USUARIO);
				ps.setInt(1, idUsuario);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result = new usuarioTO();
					result.setIdUsuario(rs.getInt("idusuario"));
					result.setEmailUsuario(rs.getString("email"));
					result.setPasswordUsuario(rs.getString("password"));
					result.setRolUsuario(rs.getString("rol"));
					
				}
			} catch (SQLException e) {

			}
			return result;
	   }
   
	   public int create(trabajadorTO trabajador, usuarioTO usuario) throws SQLException {
		   int resul = 0;
		   try {
		        PreparedStatement ps = conexion.prepareStatement(INSERT_USUARIO);
		        ps.setString(1,usuario.getEmailUsuario());
		        ps.setString(2,usuario.getPasswordUsuario());
		        ps.setString(3,usuario.getRolUsuario());
		        ps.executeUpdate();
		        
		        
		        PreparedStatement ps2 = conexion.prepareStatement(READ_IDUSUARIO);
		        ps2.setString(1, usuario.getEmailUsuario());
		        ResultSet rs2 = ps2.executeQuery();
		        int idUsuario=0;
		        if(rs2.next()) {
		        	idUsuario = rs2.getInt(1);
		        }
		        
		        PreparedStatement ps1 = conexion.prepareStatement(INSERT_TRABAJADOR);
		        ps1.setString(1,trabajador.getNombreTrabajador());
		        ps1.setString(2,trabajador.getCargoTrabajador());
		        ps1.setInt(3,idUsuario);
		        ps1.executeUpdate();
		        
		        
			   
		   }finally{
	            if(conexion!=null)
	                conexion.close();
	        }
		   return resul;
	   }
	   
	   public int delete(int idTrabajador) throws SQLException {
		   int result = 0;
		   
		   trabajadorDAO trabajador = new trabajadorDAO();
		   trabajadorTO to = trabajador.obtieneTrabajadorXID(idTrabajador);
		   int idUsuario = to.getIdUsuario();
		   
           conexion = getConnection();
           PreparedStatement ps1 = conexion.prepareStatement(DELETE_TRABAJADOR);
           ps1.setInt(1,idTrabajador);
           ps1.executeUpdate();
           
           
           PreparedStatement ps2 = conexion.prepareStatement(DELETE_USUARIO);
           ps2.setInt(1,idUsuario);
           ps2.executeUpdate();
		   
		   return result;
	   }
	   
	    public boolean update(usuarioTO usuario) throws SQLException{
	        boolean resultado = false;
	        
	        try{
	            conexion = getConnection();
	            PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
	            ps.setString(1,usuario.getEmailUsuario());
	            ps.setString(2,usuario.getRolUsuario());
	            ps.setInt(3, usuario.getIdUsuario());
	            ps.executeUpdate();
	            resultado = true;
	        	
	        	}catch(SQLException e){
	                System.out.println("Error: " + e.getMessage());
	            }finally{
	                if(conexion!=null)
	                    conexion.close();
	            }
	            return resultado;
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
