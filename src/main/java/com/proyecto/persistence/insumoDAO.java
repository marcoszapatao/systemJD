package com.proyecto.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proyecto.transferObject.cantidadDisponibleTO;
import com.proyecto.transferObject.insumoTO;
import com.proyecto.transferObject.vacunoTO;

public class insumoDAO {
	private static final String INSERT_QUERY="insert into insumo(nombreInsumo,descripcion,tipoInsumo_idtipoInsumo) values(?,?,?)";
	private static final String READ_ALL ="select idinsumo,insumo.nombreInsumo as nombre,insumo.descripcion,tipoinsumo.nombreInsumo as tipo from insumo join tipoinsumo on(insumo.tipoInsumo_idtipoInsumo = tipoinsumo.idtipoInsumo)";  
	private static final String READ_ALLA ="select idinsumo,insumo.nombreInsumo as nombre,insumo.descripcion,tipoinsumo.nombreInsumo as tipo from insumo join tipoinsumo on(insumo.tipoInsumo_idtipoInsumo = tipoinsumo.idtipoInsumo) where tipoInsumo_idtipoInsumo=1";  
	private static final String READ_TIPOS ="select nombreInsumo from tipoinsumo";
	private static final String READ_IDINSUMO ="select idinsumo from insumo where nombreInsumo=?";
	private static final String READ_DISPONIBLE="select cantidad_actual from cantidad_disponible where insumo_idinsumo=?";
	private static final String INSERT_COMPRA="insert into insumo_has_proveedor(insumo_idinsumo,proveedor_idproveedor,fechaCompra,nroDocumento,cantidad,precio) values(?,?,?,?,?,?)";
	private static final String READ_INSUMO = "select idinsumo from insumo where nombreInsumo=?";
	private static final String INSERT_STOCK="insert into cantidad_disponible(cantidad_actual,insumo_idinsumo) values(?,?)";
	private static final String READ_PROVEEDOR="select idproveedor from proveedor where nombre=?";
	private static final String UPDATE_DISPO="update cantidad_disponible set cantidad_actual=? where insumo_idinsumo=?";
	private static final String UPDATE_QUERY="UPDATE insumo SET nombreInsumo = ?,descripcion = ?,tipoInsumo_idtipoInsumo = ? WHERE idinsumo=?";
    private static final String READ_QUERY="select idinsumo,insumo.nombreInsumo as nombre,insumo.descripcion,tipoinsumo.nombreInsumo as tipo from insumo join tipoinsumo on(insumo.tipoInsumo_idtipoInsumo = tipoinsumo.idtipoInsumo) and idinsumo=?";
	private static final String DELETE_QUERY="DELETE FROM insumo WHERE idinsumo=?";
	private static final String READ_GASTOS="SELECT sum(precio) FROM `insumo_has_proveedor` WHERE `insumo_has_proveedor`.`fechaCompra` BETWEEN ? and ?";
	private static final String READ_NOMBRETIPO="select idtipoInsumo from tipoinsumo where nombreInsumo=?";
    private static final String READ_CANDIST="select * from cantidad_disponible";
    private static final String READ_S="SELECT * FROM cantidad_disponible join insumo on(cantidad_disponible.insumo_idinsumo=insumo.idinsumo)";
    private static final String DELETE_STOCK="delete from cantidad_disponible where insumo_idinsumo=?";
    private static final String DELETE_INSUMOPROVEEDOR ="delete from insumo_has_proveedor where insumo_idinsumo=?";
	private static final String READ_STOCK="select cantidad_actual from cantidad_disponible where insumo_idinsumo=?";
    private static final String TOTAL_STOCK ="SELECT sum(cantidad_actual) FROM cantidad_disponible";
	
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
	
	public LinkedList<insumoTO> readAllAlimento() throws SQLException{
	    LinkedList<insumoTO> lista = new LinkedList<>();
	    insumoTO result = null;
	    Connection conn = null;
	    try {
	        conn = getConnection();
	        PreparedStatement ps = conn.prepareStatement(READ_ALLA);
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
        
        PreparedStatement ps2 = conexion.prepareStatement(READ_IDINSUMO);
        ps2.setString(1, insumo.getNombre_insumo());
        ResultSet rs2 = ps2.executeQuery();
        int idInsumo=0;
        if(rs2.next()) {
        	idInsumo = rs2.getInt(1);
        }
        conexion = getConnection();
        PreparedStatement ps3 = conexion.prepareStatement(INSERT_STOCK);
        float j =0;
        ps3.setFloat(1,j);
        ps3.setInt(2,idInsumo);
        ps3.executeUpdate();
        
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
        	/*Elimina tupla en cantidad_disponible*/
            conexion = getConnection();
            PreparedStatement ps1 = conexion.prepareStatement(DELETE_STOCK);
            ps1.setInt(1,id);
            ps1.executeUpdate();
            
            /*Elimina desde insumo_has_proveedor*/
            conexion = getConnection();
            PreparedStatement ps2 = conexion.prepareStatement(DELETE_INSUMOPROVEEDOR);
            ps2.setInt(1,id);
            ps2.executeUpdate();
            
            
          /*Elimina desde tabla insumos*/  
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
    

    public int ingresaCompra(String insumo, String proveedor,Date fecha,String documento, int cantidad, int precio) throws SQLException {
    	int j = 0;
    	try {
    	conexion = getConnection();
        PreparedStatement ps1=conexion.prepareStatement(READ_INSUMO);
        ps1.setString(1,insumo);
        ResultSet rs = ps1.executeQuery();
        int idinsumo=0;
        if(rs.next()) {
            idinsumo =rs.getInt(1);
        }
        
    	conexion = getConnection();
        PreparedStatement ps2=conexion.prepareStatement(READ_PROVEEDOR);
        ps2.setString(1,proveedor);
        ResultSet rs2 = ps2.executeQuery();
        int idproveedor=0;
        if(rs2.next()) {
            idproveedor =rs2.getInt(1);
        }
        
    	conexion = getConnection();
        PreparedStatement ps3=conexion.prepareStatement(INSERT_COMPRA);
        ps3.setInt(1, idinsumo);
        ps3.setInt(2, idproveedor);
        ps3.setDate(3, fecha);
        ps3.setString(4,documento);
        ps3.setInt(5, cantidad);
        ps3.setInt(6, precio);
        ps3.executeUpdate();
        
    	conexion = getConnection();
        PreparedStatement ps4=conexion.prepareStatement(READ_DISPONIBLE);
        ps4.setInt(1,idinsumo);
        ResultSet rs3 = ps4.executeQuery();
        float disponible=0;
        if(rs3.next()) {
            disponible =rs3.getFloat(1);
        }
        
        float nuevaDisponibilidad = disponible+cantidad;
        
    	conexion = getConnection();
        PreparedStatement ps5=conexion.prepareStatement(UPDATE_DISPO);
        ps5.setFloat(1, nuevaDisponibilidad);
        ps5.setInt(2, idinsumo);
        ps5.executeUpdate();
    	}catch(SQLException e) {
            System.out.println("ERROR: "+e);
            j=1;
            return j;
    	}
    	return j;
    	
    }
	
    public String obtieneGastos() throws SQLException{
    	conexion = getConnection();
        PreparedStatement ps=conexion.prepareStatement(READ_GASTOS);
        /*************************/
        java.util.Date fechaActual = new java.util.Date();
        System.out.println(fechaActual);
        System.out.println("---------------------------------------------");
        
        //Formateando la fecha:
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Son las: "+formatoHora.format(fechaActual)+" de fecha: "+formatoFecha.format(fechaActual));
        
        //Fecha actual desglosada:
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        String inicio = año+"-"+mes+"-01";
        String fin = año+"-"+mes+"-30";

        /*************************/
        ps.setString(1,inicio);
        ps.setString(2,fin);
        ResultSet rs = ps.executeQuery();
        int gastos=0;
        if(rs.next()) {
            gastos =rs.getInt(1);
        }
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        String gastosF = formatea.format(gastos);
        return gastosF;
    }
    
    public LinkedList<cantidadDisponibleTO> verCantidadDisponible() throws SQLException{
        LinkedList<cantidadDisponibleTO> lista = new LinkedList<>();
        cantidadDisponibleTO result = null;
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(READ_CANDIST);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result= new cantidadDisponibleTO();
                result.setIdCantidadDisponible(rs.getInt("idcantidad_disponible"));
                result.setCantidadActual(rs.getFloat("cantidad_actual"));
                result.setIdInsumo(rs.getInt("insumo_idinsumo"));
                
                lista.add(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            conn.close();
        }
        return lista;
    }
    
    public float readStock(int idInsumo) throws SQLException {
     float result=0;
        
       try {
           getConnection();
           PreparedStatement ps = conexion.prepareStatement(READ_STOCK);
           ps.setInt(1,idInsumo);
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               result=rs.getFloat("cantidad_actual");
           }
       } catch (SQLException ex) {
           Logger.getLogger(vacunoDAO.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           conexion.close();
       }
       return result;
    }
    
    public LinkedList<insumoTO> stockIndex() throws SQLException{
    	
    	LinkedList<insumoTO> lista = new LinkedList<>();
        insumoTO result = null;
        Connection conn = null;
        try {
        	conexion = getConnection();
            PreparedStatement ps1=conexion.prepareStatement(TOTAL_STOCK);
            
            ResultSet rs = ps1.executeQuery();
            int total=0;
            if(rs.next()) {
                total =rs.getInt(1);               
            }
        	
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(READ_S);
            ResultSet rs1 = ps.executeQuery();
           
            while(rs1.next()){
            	
                result= new insumoTO();
                int cantidadA = rs1.getInt("cantidad_actual");                
                String nombre = rs1.getString("nombreInsumo");               
                int porcentaje = (cantidadA*100)/total;
 
                result.setId_insumo(porcentaje);
                result.setNombre_insumo(nombre);
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
