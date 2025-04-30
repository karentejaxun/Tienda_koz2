package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class PrendasIns {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarProducto(Prendas pre) {

        String sql = "insert into prendas (codigo, marca, proveedor, cantidad, precio, descripcion) values (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pre.getCodigo());
            ps.setString(2, pre.getMarca());
            ps.setString(3, pre.getProveedor());
            ps.setInt(4, pre.getCantidad());
            ps.setInt(5, pre.getPrecio());
            ps.setString(6, pre.getDescripcion());

            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;

        }

    }

    public void ConsultarProve(JComboBox prove) {

        String sql = "select proveedor from proveedores";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                prove.addItem(rs.getString("proveedor"));
            }

        } catch (SQLException e) {

            System.out.println(e.toString());
        }
    }

    public List ListarPrendas() {

        List<Prendas> LisPre = new ArrayList();
        String sql = "SELECT * FROM prendas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Prendas pre = new Prendas();
                pre.setIdPrenda(rs.getInt("idPrenda"));
                pre.setCodigo(rs.getString("codigo"));
                pre.setMarca(rs.getString("marca"));
                pre.setProveedor(rs.getString("proveedor"));
                pre.setCantidad(rs.getInt("cantidad"));
                pre.setPrecio(rs.getInt("precio"));
                pre.setDescripcion(rs.getString("descripcion"));
                LisPre.add(pre);

            }
        } catch (Exception e) {
            System.out.println(e.toString());

        }
        return LisPre;
    }

    public boolean DeletePrendas(int idPrenda) {
        String sql = "delete from prendas where idPrenda = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPrenda);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public boolean updPrendas(Prendas pre) {
        String sql = "update prendas set codigo=?, marca=?, proveedor=?, cantidad=?, precio=?, descripcion=? where idPrenda=? ";
        //String sql = "update proveedores set codProveedor=?,proveedor=?, correo=?, telefono=? where idProveedor=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pre.getCodigo());
            ps.setString(2, pre.getMarca());
            ps.setString(3, pre.getProveedor());
            ps.setInt(4, pre.getCantidad());
            ps.setInt(5, pre.getPrecio());
            ps.setString(6, pre.getDescripcion());
            ps.setInt(7, pre.getIdPrenda());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());

            }
        }
    }
    
    public Prendas BuscarPre(String cod){
           
           Prendas prenda = new Prendas();
           String sql = "Select * from prendas where codigo = ?";
           try{
               con = cn.getConnection();
               ps = con.prepareStatement(sql);
               ps.setString(1, cod);
               rs = ps.executeQuery();
               if(rs.next()){
                  prenda.setMarca(rs.getString("marca"));
                  prenda.setPrecio(rs.getInt("precio"));
                  prenda.setCantidad(rs.getInt("cantidad"));
               }
           }catch(SQLException e){
              System.out.println(e.toString());
           }
           return prenda;
    }
    
   public EmpresaDatos BuscarDatos(){
           EmpresaDatos ed = new EmpresaDatos();
           String sql = "select * from datos";

           try{
               con = cn.getConnection();
               ps = con.prepareStatement(sql);               
               rs = ps.executeQuery();
               if(rs.next()){
                  ed.setIdDatos(rs.getInt("idDatos"));
                  ed.setEmpresa(rs.getString("empresa"));
                  ed.setTelefono(rs.getInt("telefono"));
                  ed.setDireccion(rs.getString("direccion"));
                  ed.setCorreo(rs.getString("correo"));
               }
           }catch(SQLException e){
              System.out.println(e.toString());
           }
           return ed;
    }
   
       public boolean updDatos(EmpresaDatos datos) {
        String sql = "update datos set codigo=?, marca=?, proveedor=?, cantidad=?, precio=?, descripcion=? where idDatos=? ";
        //String sql = "update proveedores set codProveedor=?,proveedor=?, correo=?, telefono=? where idProveedor=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, datos.getIdDatos());
            ps.setString(2, datos.getEmpresa());
            ps.setInt(3, datos.getTelefono());
            ps.setString(4, datos.getDireccion());
            ps.setString(5, datos.getCorreo());

            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());

            }
        }
    }
   
}
