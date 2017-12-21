package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.cantidadDisponibleTO;
import com.proyecto.transferObject.trabajadorTO;
import com.proyecto.transferObject.usuarioTO;


public class trabajadorDAO {
	   private static final String BUSCA_TRABAJADOR ="select * from trabajador where usuario_idusuario=?";
	   private static final String BUSCA_TRABAJADORXID= "select * from trabajador where idtrabajador=?";
	   private static final String READ_TRABAJADOR ="select * from trabajador";
	   private static final String UPDATE_QUERY="update trabajador set nombre=?,cargo=? where idtrabajador=?";
	   
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
	   
	   public trabajadorTO obtieneTrabajadorXID(int idTrabajador) {
			Connection conn = null;
			trabajadorTO result = null;
			try {
				conn = getConnection();

				PreparedStatement ps = conn.prepareStatement(BUSCA_TRABAJADORXID);
				ps.setInt(1, idTrabajador);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					result = new trabajadorTO();
					result.setIdTrabajador(rs.getInt("idtrabajador"));
					result.setNombreTrabajador(rs.getString("nombre"));
					result.setCargoTrabajador(rs.getString("cargo"));
					result.setIdUsuario(rs.getInt("usuario_idusuario"));
					
				}
			} catch (SQLException e) {

			}
			return result;
	   }
	   
	   public LinkedList<trabajadorTO> obtieneTrabajadores() throws SQLException{
	        LinkedList<trabajadorTO> lista = new LinkedList<>();
	        trabajadorTO result = null;
	        Connection conn = null;
	        try {
	            conn = getConnection();
	            PreparedStatement ps = conn.prepareStatement(READ_TRABAJADOR);
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                result= new trabajadorTO();
	                result.setIdTrabajador(rs.getInt("idtrabajador"));
	                result.setNombreTrabajador(rs.getString("nombre"));
	                result.setCargoTrabajador(rs.getString("cargo"));
	                result.setIdUsuario(rs.getInt("usuario_idusuario"));
	                
	                lista.add(result);
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
	        } finally{
	            conn.close();
	        }
	        return lista;
	    }
	    
	   
	    public boolean update(trabajadorTO trabajador) throws SQLException{
	        boolean resultado = false;
	        
	        try{
	            conexion = getConnection();
	            PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
	            ps.setString(1,trabajador.getNombreTrabajador());
	            ps.setString(2,trabajador.getCargoTrabajador());
	            ps.setInt(3, trabajador.getIdTrabajador());
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
