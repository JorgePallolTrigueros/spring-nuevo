package com.pallol.novela.entities;
// Generated 25/07/2019 01:09:25 PM by Hibernate Tools 4.3.1

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProductoCompra generated by hbm2java
 */
@Entity
@Table(name = "producto_compra",
         catalog = "noveladb"
)
public class ProductoCompra implements java.io.Serializable {

    private Integer id;
    private CompraRealizada compraRealizada;
    private Producto producto;
    private Integer cantidad;

    public ProductoCompra() {
    }

    public ProductoCompra(CompraRealizada compraRealizada, Producto producto) {
        this.compraRealizada = compraRealizada;
        this.producto = producto;
    }

    public ProductoCompra(CompraRealizada compraRealizada, Producto producto, Integer cantidad) {
        this.compraRealizada = compraRealizada;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idcompra", nullable = false)
    public CompraRealizada getCompraRealizada() {
        return this.compraRealizada;
    }

    public void setCompraRealizada(CompraRealizada compraRealizada) {
        this.compraRealizada = compraRealizada;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproducto", nullable = false)
    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Column(name = "cantidad")
    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
