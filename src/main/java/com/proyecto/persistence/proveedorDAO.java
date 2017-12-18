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
import com.proyecto.transferObject.razaTO;

public class proveedorDAO {
	private static final String READ_ALL="select * from proveedor";
	private static final String INSERT_QUERY ="insert into proveedor(nombre,rubro,rut,direccion) values(?,?,?,?)";
	private static final String READ_QUERY ="select * from proveedor where idproveedor=?";
	private static final String DELETE_QUERY ="delete from proveedor where idproveedor=?";
	private static final String UPDATE_QUERY ="update proveedor set nombre=?,rubro=?,rut=?,direccion=? where idproveedor=?";
	
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
	            result.setRutProveedor(rs.getString("rut"));
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
	
    public int createProveedor(proveedorTO proveedor) throws SQLException {
    	int r = 0;
    	try {
    	conexion = getConnection();
        PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
        ps.setString(1, proveedor.getNombreProveedor());
        ps.setString(2, proveedor.getRubroProveedor());
        ps.setString(3, proveedor.getRutProveedor());
        ps.setString(4, proveedor.getDireccionProveedor());
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
    
    public proveedorTO read(int id) {
        proveedorTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_QUERY);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new proveedorTO();
                result.setIdProveedor(rs.getInt("idproveedor"));
                result.setNombreProveedor(rs.getString("nombre"));
                result.setRubroProveedor(rs.getString("rubro"));
                result.setRutProveedor(rs.getString("rut"));
                result.setDireccionProveedor(rs.getString("direccion"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            //conexion.close();
        }
        return result;
    }
    
    public int delete(proveedorTO proveedor) throws SQLException{
    int resultado = 0;
    try{
      conexion = getConnection();
      PreparedStatement ps = conexion.prepareStatement(DELETE_QUERY);
      ps.setInt(1,proveedor.getIdProveedor());
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
    
    public int update(proveedorTO proveedor) throws SQLException {
    	int r=0;
    	try {
    	conexion= getConnection();
        PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
        ps.setString(1,proveedor.getNombreProveedor());
        ps.setString(2,proveedor.getRubroProveedor());
        ps.setString(3,proveedor.getRutProveedor());
        ps.setString(4,proveedor.getDireccionProveedor());
        ps.setInt(5, proveedor.getIdProveedor());
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
