/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class EmpresaDatos {

private int idDatos;
private String empresa;
private int telefono;
private String direccion;
private String correo;

    public EmpresaDatos() {
    }

    public EmpresaDatos(int idDatos, String empresa, int telefono, String direccion, String correo) {
        this.idDatos = idDatos;
        this.empresa = empresa;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public int getIdDatos() {
        return idDatos;
    }

    public void setIdDatos(int idDatos) {
        this.idDatos = idDatos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    
}
