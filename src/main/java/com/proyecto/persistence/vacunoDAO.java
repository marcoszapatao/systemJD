package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.proyecto.transferObject.vacunoTO;

public class vacunoDAO {
    private static final String INSERT_QUERY ="insert into vacuno(diio,tipo,fechaIngreso,raza_idraza) values(?,?,?,?)";
    private static final String DELETE_QUERY="DELETE FROM vacuno WHERE diio=?";
    private static final String TOTAL_QUERY="SELECT count(*) as numeroV FROM `vacuno`";
    private static final String UPDATE_QUERY="UPDATE vacuno SET diio = ?,tipo = ?,raza_idraza = ?,fechaIngreso = ? WHERE diio=?";
    private static final String READ_QUERY="select diio,tipo,fechaIngreso,nombre from vacuno join raza on(vacuno.raza_idraza = raza.idraza) and diio=?";
    private static final String READ_RAZA="select idraza from raza where nombre=?";
    private static final String READ_ALL ="select diio,tipo,fechaIngreso,nombre from vacuno join raza on(vacuno.raza_idraza = raza.idraza)";
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
    private static final String DB_NAME="bddjd";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
    public int createVacuno(vacunoTO vacuno) throws SQLException{
        int result =0;
        try{
        conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(READ_RAZA);
        ps1.setString(1, vacuno.getRaza());
        ResultSet rs = ps1.executeQuery();
        int idraza=0;
        if(rs.next()) {
            idraza =rs.getInt(1);
            System.out.println(idraza);
        }
        
        PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
        ps.setString(1, vacuno.getDiio());
        ps.setString(2,vacuno.getTipo());
        
        Date fe =vacuno.getFechaIngreso();
        ps.setDate(3, fe);
        ps.setInt(4, idraza);
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
    
        public LinkedList<vacunoTO> readAllV() throws SQLException{
        LinkedList<vacunoTO> lista = new LinkedList<>();
        vacunoTO result = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(READ_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new vacunoTO();
                result.setDiio(rs.getString("diio"));
                result.setTipo(rs.getString("tipo"));
                result.setRaza(rs.getString("nombre"));
                result.setFechaIngreso(rs.getDate("fechaIngreso"));
                System.out.println("paso aqui");
                lista.add(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conn.close();
        }
        return lista;
    }
        public vacunoTO read(String id) throws SQLException{
         vacunoTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_QUERY);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new vacunoTO();
                result.setDiio(rs.getString("diio"));
                result.setTipo(rs.getString("tipo"));
                result.setRaza(rs.getString("nombre"));
                result.setFechaIngreso(rs.getDate("fechaIngreso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            //conexion.close();
        }
        return result;
    }
        
        
    public boolean update(vacunoTO vacuno) throws SQLException{
        boolean resultado = false;
        try{
          conexion = getConnection();
          
          PreparedStatement ps1=conexion.prepareStatement(READ_RAZA);
          ps1.setString(1, vacuno.getRaza());
          ResultSet rs = ps1.executeQuery();
          int idraza=0;
          if(rs.next()) {
              idraza =rs.getInt(1);
              System.out.println(idraza);
          }
          
          
          PreparedStatement ps = conexion.prepareStatement(UPDATE_QUERY);
          ps.setString(1,vacuno.getDiio());
          ps.setString(2,vacuno.getTipo());
          ps.setInt(3,idraza);
          ps.setDate(4, vacuno.getFechaIngreso());
          ps.setString(5,vacuno.getDiio());
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
    
    
        public boolean delete(vacunoTO vacuno) throws SQLException{
        boolean resultado = false;
        try{
          conexion = getConnection();
          PreparedStatement ps = conexion.prepareStatement(DELETE_QUERY);
          ps.setString(1,vacuno.getDiio());
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
    
    public int totalVacunos() throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(TOTAL_QUERY);
        //ps1.setString(1, vacuno.getRaza());
        ResultSet rs = ps1.executeQuery();
        int nro=0;
        if(rs.next()) {
            nro =rs.getInt(1);
            System.out.println(nro);
        }
        return nro;
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
