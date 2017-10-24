package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.inventarioTO;

public class inventarioDAO {
    private static final String INSERT_QUERY ="insert into grupo(id_grupo,estado,fecha_ingreso,fecha_salida) values(?,?,?,?)";
    private static final String DELETE_QUERY="DELETE FROM task WHERE id=?";
    private static final String UPDATE_QUERY="UPDATE task SET start_date = ?,end_date = ?,description = ?,estado = ? WHERE id=?";
    private static final String READ_QUERY="select * from task where id=?";
    private static final String READ_ALL ="select idPinio,nombrePinio,estado,fechaIngreso,fechaSalida,count(vacuno_idvacuno) as nroVacunos from `pinio` join `pinio_has_vacuno` on(pinio.idPinio = pinio_has_vacuno.Pinio_idPinio) group by idPinio";
    private static final String READ_PEND ="select * from task WHERE estado = FALSE";
    private static final String DB_NAME="bddjd";
    private static final String PORT="3306";
    private static final String URL="jdbc:mysql://localhost/"+DB_NAME;    
    private static final String USER="root";
    private static final String PASSWORD="";
    private static Connection conexion = null;
    
    public LinkedList<inventarioTO> readAllInven() throws SQLException{
        LinkedList<inventarioTO> list = new LinkedList<>();
        inventarioTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new inventarioTO();
                result.setId_grupo(rs.getInt("idPinio"));
                result.setNombre(rs.getString("nombrePinio"));
                result.setNroAnimales(rs.getInt("nroVacunos"));
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