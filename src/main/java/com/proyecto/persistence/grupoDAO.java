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
import com.proyecto.transferObject.vacunoTO;

public class grupoDAO {
    private static final String INSERT_QUERY ="insert into pinio(nombrePinio,estado,fechaIngreso) values(?,?,?)";
    private static final String INSERT_PINIOVACUNO="insert into pinio_has_vacuno(Pinio_idPinio,vacuno_idvacuno,fechaMov) values (?,?,?)";
    private static final String INSERT_PESO="insert into peso(pesoPinio,fecha,trabajador_idtrabajador,Pinio_idPinio) values (?,?,?,?)";
    private static final String READ_IDVACUNO="select idvacuno from vacuno where diio=?";
    private static final String READ_MAX="select MAX(idPinio) from pinio";
    private static final String DELETE_PINIOVACUNO="DELETE FROM pinio_has_vacuno WHERE Pinio_idPinio=?";
    private static final String DELETE_PINIOPESO="DELETE FROM peso WHERE Pinio_idPinio=?";
    private static final String DELETE_PINIOPATIO="DELETE FROM patio_has_pinio where Pinio_idPinio=?";
    private static final String DELETE_PINIO="DELETE FROM pinio WHERE idPinio=?";
    private static final String UPDATE_GRUPO="UPDATE pinio SET nombrePinio = ?,estado = ?,fechaIngreso = ? WHERE idPinio=?";
    private static final String UPDATE_GRUPOS="UPDATE pinio SET nombrePinio = ?,estado = ?,fechaIngreso = ?,fechaSalida=? WHERE idPinio=?";
    private static final String UPDATE_PESO_GRUPO="update peso set pesoPinio = ? where Pinio_idPinio = ?";
    private static final String UPDATE_PATIO_GRUPO="update patio_has_pinio set patio_idpatio = ? where Pinio_idPinio = ?";
    private static final String READ_QUERY="select nombrePinio,estado,fechaIngreso,fechaSalida,pesoPinio from pinio join peso on(pinio.idPinio = peso.Pinio_idPinio) where idPinio=?";
    private static final String READ_QUERY_P="select nombrePinio,estado,fechaIngreso,fechaSalida,pesoPinio,nombrePatio from pinio join peso on(pinio.idPinio = peso.Pinio_idPinio) join `patio_has_pinio` join `patio` on(pinio.idPinio=patio_has_pinio.Pinio_idPinio and patio_has_pinio.patio_idpatio=patio.idpatio) where idPinio=?";
    private static final String READ_ALL ="select * from pinio";
    private static final String READ_ALL_PATIO = "SELECT * FROM pinio join patio_has_pinio join patio on(pinio.idPinio=patio_has_pinio.Pinio_idPinio and patio_has_pinio.patio_idpatio=patio.idpatio)";
    private static final String TOTAL_QUERYESTADO ="select count(*) from `pinio_has_vacuno` join `pinio` on (pinio.idPinio=pinio_has_vacuno.Pinio_idPinio) where estado=?";
    private static final String READ_VACUNOSGRUPO=
    		"SELECT vacuno.idvacuno,vacuno.diio FROM vacuno join pinio_has_vacuno on(vacuno.idvacuno=pinio_has_vacuno.vacuno_idvacuno) WHERE pinio_has_vacuno.Pinio_idPinio=?";
    private static final String INSERT_QUERY_PATIO="insert into patio_has_pinio(patio_idpatio,Pinio_idPinio,fechaMovimiento) values (?,?,?)";
    private static final String READ_PATIO ="select idpatio from patio where nombrePatio=?";
    
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
    
    /*Lista todos los grupos*/
    public LinkedList<grupoTO> readAllG() throws SQLException{
        LinkedList<grupoTO> list = new LinkedList<>();
        grupoTO result = null;
        
        try {
            getConnection();
            PreparedStatement ps = conexion.prepareStatement(READ_ALL_PATIO);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new grupoTO();
                result.setId_grupo(rs.getInt("idPinio"));
                result.setNombre(rs.getString("nombrePinio"));
                result.setEstado(rs.getString("estado"));
                result.setPatio(rs.getString("nombrePatio"));
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
    
    public void createGrupo(grupoTO grupo,String[]vacunos, int patio, int idusuario) throws SQLException {
    	try{ 
    	 /*Inserta datos en tabla pinio*/
    	 conexion = getConnection();
    	 PreparedStatement ps = conexion.prepareStatement(INSERT_QUERY);
         ps.setString(1, grupo.getNombre());
         ps.setString(2,grupo.getEstado());
         Date fe =grupo.getFecha_ingreso();
         ps.setDate(3, fe);
         ps.executeUpdate();
         /*Obtiene el id del pi�o que recien se inserto*/
         conexion = getConnection();
    	 PreparedStatement ps2 = conexion.prepareStatement(READ_MAX);
         ResultSet rs2 = ps2.executeQuery();
         int idpinio=0;
         if(rs2.next()) {
             idpinio =rs2.getInt(1);
             
         }
         /*Obtiene fecha del sistema*/
         java.util.Date utilDate = new java.util.Date();
         java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
         
         
         
       	 conexion = getConnection();
    	 PreparedStatement ps8 = conexion.prepareStatement(INSERT_QUERY_PATIO);
         ps8.setInt(1, patio);
         ps8.setInt(2, idpinio);
         ps8.setDate(3, sqlDate);
         ps8.executeUpdate();
         
         
         /*Obtiene el idvacuno segun su diio */
         conexion = getConnection();
         
         int tamano = vacunos.length;
         for(int i=1;i<tamano;i++) {
        	 PreparedStatement ps1 = conexion.prepareStatement(READ_IDVACUNO);
             
        	 ps1.setString(1,vacunos[i]);
             ResultSet rs = ps1.executeQuery();
             int idvacuno=0;
             if(rs.next()) {
                 idvacuno =rs.getInt(1);
                 
             }
             conexion = getConnection();
             /*Inserta una tupla por cada diio en la tabla pinio_has_vacuno*/
             PreparedStatement ps3 = conexion.prepareStatement(INSERT_PINIOVACUNO);
             ps3.setInt(1,idpinio);
             ps3.setInt(2, idvacuno);
             ps3.setDate(3, sqlDate);
             ps3.executeUpdate();   
         }
         
         insertapeso(grupo,sqlDate,idpinio,idusuario);   	

    	}catch(SQLException e){
            System.out.println(e);
        }finally{
            if(conexion!=null)
                conexion.close();
        }
    }
    
    public void insertapeso(grupoTO grupo,Date fecha,int id, int idusuario) throws SQLException {
    	
    	conexion = getConnection();
        PreparedStatement ps4 = conexion.prepareStatement(INSERT_PESO);
        ps4.setInt(1,grupo.getPeso());
        ps4.setDate(2,fecha);
        ps4.setInt(3,idusuario);
        ps4.setInt(4,id);
        ps4.executeUpdate();
    }
    /*Obtiene informacion del grupo segun su ID*/
    public grupoTO readG(int id) throws SQLException{
        grupoTO result = null;
        
       try {
           getConnection();
           PreparedStatement ps = conexion.prepareStatement(READ_QUERY_P);
           ps.setInt(1,id);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               result= new grupoTO();
               result.setNombre(rs.getString("nombrePinio"));
               result.setEstado(rs.getString("estado"));
               result.setPeso(rs.getInt("pesoPinio"));               
               result.setPatio(rs.getString("nombrePatio"));
               result.setFecha_ingreso(rs.getDate("fechaIngreso"));
               result.setFecha_Salida(rs.getDate("fechaSalida"));
           }
       } catch (SQLException ex) {
           Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           //conexion.close();
       }
       return result;
    }
    /*Actuliza la informacion de un grupo*/
    public void updateGrupo(grupoTO grupo) throws SQLException {
    	
    	if(grupo.getFecha_Salida()==null) {
    		
        	conexion = getConnection();
            PreparedStatement ps = conexion.prepareStatement(UPDATE_GRUPO);
            ps.setString(1,grupo.getNombre());
            ps.setString(2,grupo.getEstado());
            ps.setDate(3,grupo.getFecha_ingreso());
            ps.setInt(4, grupo.getId_grupo());
            ps.executeUpdate();
    	}else {
    		
        	conexion = getConnection();
            PreparedStatement ps = conexion.prepareStatement(UPDATE_GRUPOS);
            ps.setString(1,grupo.getNombre());
            ps.setString(2,grupo.getEstado());
            ps.setDate(3,grupo.getFecha_ingreso());
            ps.setDate(4,grupo.getFecha_Salida());
            ps.setInt(5, grupo.getId_grupo());
            ps.executeUpdate();
    	}

        
        conexion = getConnection();
        PreparedStatement ps1 = conexion.prepareStatement(UPDATE_PESO_GRUPO);
        ps1.setInt(1, grupo.getPeso());
        ps1.setInt(2, grupo.getId_grupo());
        ps1.executeUpdate();
        
        conexion = getConnection();
        PreparedStatement ps3 = conexion.prepareStatement(READ_PATIO);
        ps3.setString(1, grupo.getPatio());
        ResultSet rs3 = ps3.executeQuery();
        int idpatio=0;
        if(rs3.next()) {
            idpatio =rs3.getInt(1);
     
        }
        
        conexion = getConnection();
        PreparedStatement ps6 = conexion.prepareStatement(UPDATE_PATIO_GRUPO);
        ps6.setInt(1,idpatio);
        ps6.setInt(2, grupo.getId_grupo());
        ps6.executeUpdate();
        
 
    }
    
    /*Elimina toda la informacion de un grupo*/
    public void eliminarGrupo(int idGrupo) throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps = conexion.prepareStatement(DELETE_PINIOVACUNO);
        ps.setInt(1,idGrupo);
        ps.executeUpdate();
        
        conexion = getConnection();
        PreparedStatement ps1 = conexion.prepareStatement(DELETE_PINIOPESO);
        ps1.setInt(1,idGrupo);
        ps1.executeUpdate();
        
        conexion = getConnection();
        PreparedStatement ps3 = conexion.prepareStatement(DELETE_PINIOPATIO);
        ps3.setInt(1,idGrupo);
        ps3.executeUpdate();
        
        
        conexion = getConnection();
        PreparedStatement ps2 = conexion.prepareStatement(DELETE_PINIO);
        ps2.setInt(1,idGrupo);
        ps2.executeUpdate();
        
    }
    /*Calcula total de vacunos segun estado(engorda, pradera, vendido)*/
    public int totalVacunosEstado(String estado) throws SQLException {
    	conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(TOTAL_QUERYESTADO);
        ps1.setString(1, estado);
        ResultSet rs = ps1.executeQuery();
        int nro=0;
        if(rs.next()) {
            nro =rs.getInt(1);            
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
