
package Modelo;

public class login {

private String usuario;
private String password;
private String nombre;
private String rol;

    public login() {

    }

    public login(String usuario, String password, String nombre, String rol) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    }

    



