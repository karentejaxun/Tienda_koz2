package Report;

import java.sql.Connection;
import Modelo.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Graficas {

    public static void Grafs(String fecha) {
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "select total from ventas where fechaV = ?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultPieDataset datset = new DefaultPieDataset();
            while (rs.next()) {
                datset.setValue(rs.getString("total"), rs.getDouble("total"));
            }

            JFreeChart free = ChartFactory.createPieChart("REPORTES DE LAS VENTAS", datset);
            ChartFrame cf = new ChartFrame("CANTIDAD DE VENTA DIARIA", free);
            cf.setSize(1000, 500);
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.toString());

        }

    }
}
