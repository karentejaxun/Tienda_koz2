
package Modelo;


public class Detalle_Venta {

private int idDetalle_venta;
private int codPrenda;
private int cantidad;
private double precio;
private int idVenta;

    public Detalle_Venta() {
    }

    public Detalle_Venta(int idDetalle_venta, int codPrenda, int cantidad, double precio, int idVenta) {
        this.idDetalle_venta = idDetalle_venta;
        this.codPrenda = codPrenda;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idVenta = idVenta;
    }

    public int getIdDetalle_venta() {
        return idDetalle_venta;
    }

    public void setIdDetalle_venta(int idDetalle_venta) {
        this.idDetalle_venta = idDetalle_venta;
    }

    public int getCodPrenda() {
        return codPrenda;
    }

    public void setCodPrenda(int codPrenda) {
        this.codPrenda = codPrenda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }



    
}
