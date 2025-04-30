package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Conexion {

    static Properties props = new Properties();

    String hostname = null;
    String port = null;
    String database = null;
    String username = null;
    String password = null;
    String jndi = null;

    public Conexion() {
        FileInputStream in = null;
        try {
            in = new FileInputStream("C:\\Users\\usuario\\OneDrive\\Escritorio\\Proyecto\\Tienda_koz2\\src\\Modelo\\props.properties"); // Reemplaza con la ruta correcta a tu archivo props.properties
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        loadProperties();
    }

    public void loadProperties() {
        hostname = props.getProperty("hostname");
        port = props.getProperty("port");
        database = props.getProperty("database");
        username = props.getProperty("username");
        password = props.getProperty("password");
        jndi = props.getProperty("jndi");
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        String jdbcUrl = props.getProperty("url");
        conn = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Conexión establecida");
        
        return conn;

        /*String jdbcUrl = "jdbc:mysql://" + this.hostname + ":" +
                this.port + "/" + this.database;
        conn = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Conexión establecida");
        return conn;*/
    }

    public Connection getDSConection() {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(this.jndi);
            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

}
