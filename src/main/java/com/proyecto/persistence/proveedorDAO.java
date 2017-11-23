package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.proveedorTO;

public class proveedorDAO {
	private static final String READ_ALL="select * from proveedor";
	
    private static final String DB_NAME="bddjd_nueva";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
	public LinkedList<proveedorTO> readAllP() throws SQLException{
	    LinkedList<proveedorTO> lista = new LinkedList<>();
	    proveedorTO result = null;
	    Connection conn = null;
	    try {
	        conn = getConnection();
	        PreparedStatement ps = conn.prepareStatement(READ_ALL);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()){
	            result= new proveedorTO();
	            result.setIdProveedor(rs.getInt("idproveedor"));
	            result.setNombreProveedor(rs.getString("nombre"));
	            result.setRubroProveedor(rs.getString("rubro"));
	            result.setRutProveedor(rs.getString("rubro"));
	            result.setDireccionProveedor(rs.getString("direccion"));

	            lista.add(result);
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
	    } finally{
	        conn.close();
	    }
	    return lista;
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
