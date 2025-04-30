/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ProveedorDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProveedor(Proveedor pr){
        String sql = "insert into proveedores(codProveedor, proveedor, correo, telefono) VALUES (?,?,?,?)";
        try {
          con = cn.getConnection();
          ps = con.prepareStatement(sql);
          ps.setInt(1, pr.getCodProveedor());
          ps.setString(2, pr.getProveedor());
          ps.setString(3, pr.getCorreo());
          ps.setString(4, pr.getTelefono());
          ps.execute();
          return true;
        }catch (Exception e){
          System.out.println(e.toString());
          return false;
        }finally{
         try{
            con.close();
         }catch (Exception e){
            System.out.println(e.toString());
         }
       }
    }
    
    public List ListarProveedor(){
        List<Proveedor> Listapr = new ArrayList();
        String sql = "select * from proveedores";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            Proveedor pr = new Proveedor();
            pr.setIdProveedor(rs.getInt("idProveedor"));
            pr.setCodProveedor(rs.getInt("codProveedor"));
            pr.setProveedor(rs.getString("proveedor"));
            pr.setCorreo(rs.getString("correo"));
            pr.setTelefono(rs.getString("telefono"));
            Listapr.add(pr);
            }
        }catch(Exception e){
         System.out.println(e.toString());
        }
        return Listapr;
}
    
    public boolean EliminarProveedor(int idProveedor){
      String sql = "delete from proveedores where idProveedor = ?";
      try{
          con = cn.getConnection();
          ps = con.prepareStatement(sql);
          ps.setInt(1, idProveedor);
          ps.execute();
          return true;
      }catch(SQLException e){
          System.out.println(e.toString());
          return false;
        }finally{
         try{
            con.close();
         }catch(SQLException e) {
         } 
        }
      }
    
    public boolean ModificarProveedor(Proveedor pr){
     String sql = "update proveedores set codProveedor=?,proveedor=?, correo=?, telefono=? where idProveedor=?";
     try{
         con = cn.getConnection();
         ps = con.prepareStatement(sql);
         ps.setInt(1,pr.getCodProveedor());
         ps.setString(2, pr.getProveedor());
         ps.setString(3,pr.getCorreo());
         ps.setString(4,pr.getTelefono());
         ps.setInt(5,pr.getIdProveedor());
         ps.execute();
         return true;

     }catch(SQLException e){
        System.out.println(e.toString());
        return false;
     }finally{
          try{
             con.close();             
          }catch(SQLException e){
              System.out.println(e.toString());
          }
     }
    }
    
    
    
    
}
