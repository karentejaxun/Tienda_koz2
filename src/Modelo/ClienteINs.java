package Modelo;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClienteINs {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean insertCliente(Cliente cli) {

        try {
            String sql = "insert into clientes (nit,nombre,telefono,direccion,correo) values (?,?,?,?,?)";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cli.getNit());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getTelefono());
            ps.setString(4, cli.getDireccion());
            ps.setString(5, cli.getCorreo());
            ps.execute();
            return true;

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.toString());

            return false;
        } finally {
            try {
                con.close();

            } catch (Exception e) {

                System.out.println(e.toString());
            }
        }
    }

    public List ListarCliente() {

        List<Cliente> LisCl = new ArrayList();
        String sql = "SELECT * FROM clientes";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cli = new Cliente();
                cli.setIdCliente(rs.getInt("idCliente"));
                cli.setNit(rs.getNString("nit"));
                cli.setNombre(rs.getString("nombre"));
                cli.setTelefono(rs.getString("telefono"));
                cli.setDireccion(rs.getString("direccion"));
                cli.setCorreo(rs.getString("correo"));
                LisCl.add(cli);

            }
        } catch (Exception e) {
            System.out.println(e.toString());

        }
        return LisCl;
    }
    
    public boolean DeleteCliente(int idCliente){
    String sql = "delete from clientes where idcliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }finally{
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        }
    }
    
    public boolean updCliente(Cliente cli){
        String sql = "update clientes set nit=?, nombre=?, telefono=?, direccion=?, correo=? where idCliente=? ";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, cli.getNit());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getTelefono());
            ps.setString(4, cli.getDireccion());
            ps.setString(5, cli.getCorreo());
            ps.setInt(6, cli.getIdCliente());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        return false;
        }finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
                
            }
        }
    }
    
    public Cliente Buscarcliente(int Nit){
         Cliente Cli = new Cliente();
         String sql = "Select * from clientes where nit = ?";
         try{
             con =cn.getConnection();
             ps = con.prepareStatement(sql);
             ps.setInt(1, Nit);
             rs = ps.executeQuery();
             if(rs.next()){
                Cli.setNombre(rs.getString("nombre"));
                Cli.setTelefono(rs.getString("telefono"));
                Cli.setDireccion(rs.getString("direccion"));
                Cli.setCorreo(rs.getString("correo"));
                
             }
         }catch(SQLException e){
             System.out.println(e.toString());
         }
         return Cli;
    }
    
}
