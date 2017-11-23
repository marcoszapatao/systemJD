package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.insumoTO;
import com.proyecto.transferObject.vacunoTO;

public class insumoDAO {
	private static final String INSERT_QUERY="insert into insumo(nombreInsumo,descripcion,tipoInsumo_idtipoInsumo) values(?,?,?)";
	private static final String READ_ALL ="select idinsumo,insumo.nombreInsumo as nombre,insumo.descripcion,tipoinsumo.nombreInsumo as tipo from insumo join tipoinsumo on(insumo.tipoInsumo_idtipoInsumo = tipoinsumo.idtipoInsumo)";  
	private static final String READ_TIPOS ="select nombreInsumo from tipoinsumo";
	private static final String UPDATE_QUERY="UPDATE insumo SET nombreInsumo = ?,descripcion = ?,tipoInsumo_idtipoInsumo = ? WHERE idinsumo=?";
    private static final String READ_QUERY="select idinsumo,insumo.nombreInsumo as nombre,insumo.descripcion,tipoinsumo.nombreInsumo as tipo from insumo join tipoinsumo on(insumo.tipoInsumo_idtipoInsumo = tipoinsumo.idtipoInsumo) and idinsumo=?";
	private static final String DELETE_QUERY="DELETE FROM insumo WHERE idinsumo=?";
	private static final String READ_NOMBRETIPO="select idtipoInsumo from tipoinsumo where nombreInsumo=?";
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
    private static final String DB_NAME="bddjd_nueva";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
	
	public LinkedList<insumoTO> readAllI() throws SQLException{
    LinkedList<insumoTO> lista = new LinkedList<>();
    insumoTO result = null;
    Connection conn = null;
    try {
        conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(READ_ALL);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            result= new insumoTO();
            result.setId_insumo(rs.getInt("idinsumo"));
            result.setNombre_insumo(rs.getString("nombre"));
            result.setDescripcion_insumo(rs.getString("descripcion"));
            result.setTipoInsumo(rs.getString("tipo"));
            lista.add(result);
        }
    } catch (SQLException ex) {
        Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally{
        conn.close();
    }
    return lista;
}
	
	
	public int createInsumo(insumoTO insumo) throws SQLException{
        int result =0;
        try{
        conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(READ_NOMBRETIPO);
        ps1.setString(1, insumo.getTipoInsumo());
        ResultSet rs = ps1.executeQuery();
        int idtipo=0;
        if(rs.next()) {
            idtipo =rs.getInt(1);
            System.out.println(idtipo);
        }
        
        PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
        ps.setString(1, insumo.getNombre_insumo());
        ps.setString(2,insumo.getDescripcion_insumo());
        ps.setInt(3, idtipo);
        ps.executeUpdate();
        result = 1;
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            if(conexion!=null)
                conexion.close();
        }
        return result;
    }
	
	
    public boolean deleteInsumo(int id) throws SQLException{
        boolean resultado = false;
        try{
          conexion = getConnection();
          PreparedStatement ps = conexion.prepareStatement(DELETE_QUERY);
          ps.setInt(1,id);
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
    
    
    public insumoTO read(int id) throws SQLException{
        insumoTO result = null;
       
       try {
           getConnection();
           PreparedStatement ps = conexion.prepareStatement(READ_QUERY);
           ps.setInt(1,id);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               result= new insumoTO();
               result.setId_insumo(rs.getInt("idinsumo"));
               result.setNombre_insumo(rs.getString("nombre"));
               result.setDescripcion_insumo(rs.getString("descripcion"));
               result.setTipoInsumo(rs.getString("tipo"));
           }
       } catch (SQLException ex) {
           Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           //conexion.close();
       }
       return result;
   }
    
    
    public boolean update(insumoTO insumo) throws SQLException{
        boolean resultado = false;
        
        try{
          conexion = getConnection();
          PreparedStatement ps1=conexion.prepareStatement(READ_NOMBRETIPO);
          ps1.setString(1, insumo.getTipoInsumo());
          ResultSet rs = ps1.executeQuery();
          int idtipo=0;
          if(rs.next()) {
              idtipo =rs.getInt(1);
              System.out.println("id insumo------------"+idtipo);
          }
        	
          
          conexion = getConnection();
          PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
          ps.setString(1,insumo.getNombre_insumo());
          ps.setString(2,insumo.getDescripcion_insumo());
          ps.setInt(3,idtipo);
          ps.setInt(4,insumo.getId_insumo());
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
