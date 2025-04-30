
package Modelo;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class loginP2 {

Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    
    public login logs(String usuario, String password){
    login l = new login();
    String sql = "select * from usuarios where usuario = ? and password = ?";
    
    try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1,usuario);
        ps.setString(2, password);
        rs = ps.executeQuery();
        
        if (rs.next()) {            
           l.setUsuario(rs.getString("usuario"));
           l.setPassword(rs.getString("password"));
           l.setRol(rs.getString("rol"));
            
        }
    }catch(Exception e){
        System.out.println(e.toString());
        
    }
    return l;
    }
    
    public boolean usuer (login UsLog){
    String sql = "insert into usuarios (nombre, usuario, password, rol) values (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, UsLog.getNombre()); 
            ps.setString(2, UsLog.getUsuario());
            ps.setString(3, UsLog.getPassword());                       
            ps.setString(4, UsLog.getRol());
            ps.execute();
            return true;
            
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        
        }
    
    }
}
