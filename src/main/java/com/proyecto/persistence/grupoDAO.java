package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.proyecto.transferObject.grupoTO;

public class grupoDAO {
    private static final String INSERT_QUERY ="insert into pinio(nombrePinio,estado,fechaIngreso) values(?,?,?)";
    private static final String INSERT_PINIOVACUNO="insert into pinio_has_vacuno(Pinio_idPinio,vacuno_idvacuno,fechaMov) values (?,?,?)";
    private static final String READ_IDVACUNO="select idvacuno from vacuno where diio=?";
    private static final String READ_MAX="select MAX(idPinio) from pinio";
    private static final String DELETE_QUERY="DELETE FROM task WHERE id=?";
    private static final String UPDATE_QUERY="UPDATE task SET start_date = ?,end_date = ?,description = ?,estado = ? WHERE id=?";
    private static final String READ_QUERY="select * from task where id=?";
    private static final String READ_ALL ="select * from pinio";
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
    private static final String DB_NAME="bddjd";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
    public LinkedList<grupoTO> readAllG() throws SQLException{
        LinkedList<grupoTO> list = new LinkedList<>();
        grupoTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new grupoTO();
                result.setId_grupo(rs.getInt("idPinio"));
                result.setNombre(rs.getString("nombrePinio"));
                result.setEstado(rs.getString("estado"));
                result.setFecha_ingreso(rs.getDate("fechaIngreso"));
                result.setFecha_Salida(rs.getDate("fechaSalida"));
                
                list.add(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(grupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conexion.close();
        }
        return list;
    }
    
    public void createGrupo(grupoTO grupo,String[]vacunos) throws SQLException {
    	try{ 
    	conexion = getConnection();
    	 PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
         ps.setString(1, grupo.getNombre());
         ps.setString(2,grupo.getEstado());
         Date fe =grupo.getFecha_ingreso();
         ps.setDate(3, fe);
         ps.executeUpdate();
         
         conexion = getConnection();
    	 PreparedStatement ps2 = conexion.prepareStatement(READ_MAX);
         ResultSet rs2 = ps2.executeQuery();
         int idpinio=0;
         if(rs2.next()) {
             idpinio =rs2.getInt(1);
             System.out.println("id piño"+idpinio);
         }
         
         java.util.Date utilDate = new java.util.Date();
         java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
         System.out.println("fecha "+sqlDate);
         conexion = getConnection();
         for(int i=0;i<vacunos.length;i++) {
        	 PreparedStatement ps1 = conexion.prepareStatement(READ_IDVACUNO);
             ps1.setString(1,vacunos[i+1]);
             ResultSet rs = ps1.executeQuery();
             int idvacuno=0;
             if(rs.next()) {
                 idvacuno =rs.getInt(1);
                 System.out.println("id vacuno "+idvacuno);
             }
             conexion = getConnection();
             PreparedStatement ps3 = conexion.prepareStatement(INSERT_PINIOVACUNO);
             ps3.setInt(1,idpinio);
             ps3.setInt(2, idvacuno);
             ps3.setDate(3, sqlDate);
             ps3.executeUpdate();
             

             
         }
         
    	}catch(SQLException e){
            System.out.println(e);
        }finally{
            if(conexion!=null)
                conexion.close();
        }
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
