package Modelo;

public class Prendas {

    private int idPrenda;
    private String codigo;
    private String marca;
    private String proveedor;
    private int cantidad;
    private int precio;
    private String descripcion;

    public Prendas() {
    }

    public Prendas(int idPrenda, String codigo, String marca, String proveedor, int cantidad, int precio, String descripcion) {
        this.idPrenda = idPrenda;
        this.codigo = codigo;
        this.marca = marca;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(int idPrenda) {
        this.idPrenda = idPrenda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

 
}
