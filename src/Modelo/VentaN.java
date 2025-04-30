package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaN {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int res;

    //Entero pra usar IDVenta
    public int RegVenta(Venta v) {
        String sql = "insert into ventas (cliente, vendedor, total, fechaV) values (?,?,?,?)";
        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.toString());

        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }

    public int RegDetalleVenta(Detalle_Venta dv) {
        String sql = "insert into detalle_venta (codPrenda, cantidad, precio, idVenta) values (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getCodPrenda());
            ps.setInt(2, dv.getCantidad());
            ps.setDouble(3, dv.getPrecio());
            ps.setInt(4, dv.getIdDetalle_venta());
            ps.execute();

        } catch (Exception e) {
            System.out.println(e.toString());

        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }

    public int IdDeVenta() {
        int idV = 0;
        String sql = "select max(idVenta) from ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                idV = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return idV;

    }

    public boolean updateStock(int can, String cod) {
        String sql = "update prendas set cantidad = ? where codigo = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, can);
            ps.setString(2, cod);            
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
        public List Listarventas() {

        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT * FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta vent = new Venta();
                vent.setIdVenta(rs.getInt("idventa"));
                vent.setCliente(rs.getString("cliente"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getDouble("total"));
                ListaVenta.add(vent);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());

        }
        return ListaVenta;
    }
    
}
