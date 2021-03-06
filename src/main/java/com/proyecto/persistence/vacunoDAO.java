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
    private static final String INSERT_QUERY ="insert into vacuno(diio,tipo,fechaIngreso,raza_idraza,sexo) values(?,?,?,?,?)";
    private static final String DELETE_QUERY="DELETE FROM vacuno WHERE diio=?";
    private static final String TOTAL_QUERY="SELECT count(*) as numeroV FROM `vacuno`";
    private static final String TOTAL_QUERYTIPO="SELECT count(*) as numeroV FROM `vacuno` where tipo=?";
    private static final String UPDATE_QUERY="UPDATE vacuno SET tipo=?,diio=?,raza_idraza=?,fechaIngreso=?, sexo=? WHERE diio=?";
    private static final String READ_QUERY="select diio,tipo,fechaIngreso,nombre,sexo from vacuno join raza on(vacuno.raza_idraza = raza.idraza) and diio=?";
    private static final String READ_RAZA="select idraza from raza where nombre=?";
    private static final String READ_ALL ="select diio,tipo,fechaIngreso,nombre,sexo from vacuno join raza on(vacuno.raza_idraza = raza.idraza)";
    private static final String READ_VSINGRUPO =
    		"SELECT diio,tipo,fechaIngreso,nombre FROM vacuno join raza on(vacuno.raza_idraza=raza.idraza) WHERE not vacuno.idvacuno in(select pinio_has_vacuno.vacuno_idvacuno from pinio_has_vacuno)";
    private static final String READ_VSINGRUPOYENGRUPO=
    		"SELECT diio,tipo,fechaIngreso,nombre FROM `vacuno` join `raza` on(vacuno.raza_idraza=raza.idraza) WHERE not vacuno.idvacuno in(select pinio_has_vacuno.vacuno_idvacuno from `pinio_has_vacuno` where pinio_has_vacuno.Pinio_idPinio<>?)";
    
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
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
    
    /*Ingresa nuevo vacuno al sistema*/
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
        ps.setString(5,vacuno.getSexo());
        ps.executeUpdate();
        
        }catch(SQLException e){
            System.out.println(e);
            result=1;
        }finally{
            if(conexion!=null)
                conexion.close();
        }
        return result;
    }
        /*Lista todos los vacunos registrados*/
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
                result.setSexo(rs.getString("sexo"));
                lista.add(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conn.close();
        }
        return lista;
    }
        /*Lista todos los vacunos que no estan ingresados en ningun grupo*/
        public LinkedList<vacunoTO> readVsinG() throws SQLException{
        LinkedList<vacunoTO> lista = new LinkedList<>();
        vacunoTO result = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(READ_VSINGRUPO);
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
        
        
        
        /*Devuelve objeto vacuno segun ID*/
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
                result.setSexo(rs.getString("sexo"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            //conexion.close();
        }
        return result;
    }
        
     /*Actualiza información de un vacuno*/   
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
          System.out.println("diio en dao "+vacuno.getDiio());
          ps.setString(1,vacuno.getTipo());
          ps.setString(2,vacuno.getDiio());
          ps.setInt(3,idraza);
          ps.setDate(4, vacuno.getFechaIngreso());
          ps.setString(5, vacuno.getSexo());
          ps.setString(6, vacuno.getDiio());
          
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
    
    /*Elimina vacuno registrado*/
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
    /*Total de vacunos registrados en el sistema*/
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
    
    public int totalVacunosTipo(String tipo) throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(TOTAL_QUERYTIPO);
        ps1.setString(1, tipo);
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
