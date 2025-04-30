/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Karen -laptop-
 */
public class Proveedor {
    private int idProveedor;
    private int codProveedor;
    private String proveedor;
    private String correo;
    private String telefono;
 
    
    public Proveedor(){
    
    }

    public Proveedor(int idProveedor, int codProveedor, String proveedor, String correo, String telefono) {
        this.idProveedor = idProveedor;
        this.codProveedor = codProveedor;
        this.proveedor = proveedor;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(int codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
 
}
