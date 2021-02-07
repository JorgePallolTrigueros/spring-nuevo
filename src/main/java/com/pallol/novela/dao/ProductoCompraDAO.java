/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.CompraRealizada;
import com.pallol.novela.entities.Producto;
import com.pallol.novela.entities.ProductoCompra;
import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author casa pallol
 */
public class ProductoCompraDAO {

    public ProductoCompra obtieneProductoCompraPorId(int productoCompraId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductoCompra productoCompra = (ProductoCompra) session.createQuery("FROM ProductoCompra pc WHERE pc.id = " + productoCompraId).uniqueResult();
        session.close();
        return productoCompra;
    }

    public List<ProductoCompra> obtieneProductoCompraPorUsuarioId(Integer usuarioId) {
        CompraRealizadaDAO compraRealizadaDAO = new CompraRealizadaDAO();
        CompraRealizada compraRealizada = compraRealizadaDAO.obtieneCompraRealizadaPorUsuarioId(usuarioId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductoCompra> lstProductos = session.createQuery("FROM ProductoCompra pc WHERE pc.compraRealizada.id = " + compraRealizada.getId()).list();
        session.close();
        return lstProductos;
    }

    public Integer obtieneTotalProductoCompraPorUsuarioId(Integer usuarioId) {
        CompraRealizadaDAO compraRealizadaDAO = new CompraRealizadaDAO();
        CompraRealizada compraRealizada = compraRealizadaDAO.obtieneCompraRealizadaPorUsuarioId(usuarioId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Long totalProductoCompra = (Long)session.createQuery("SELECT SUM(pc.cantidad * p.precio) FROM ProductoCompra pc INNER JOIN pc.producto p WHERE pc.compraRealizada.id = " + compraRealizada.getId()).uniqueResult();
        session.close();
        return totalProductoCompra.intValue();
    }

    public ProductoCompra insertaProductoCompra(CompraRealizada compraRealizada, Integer productoId, Integer cantidad) {

        
        Producto producto = new Producto();
        producto.setIdproducto(productoId);

        ProductoCompra productoCompra = new ProductoCompra();
        productoCompra.setCompraRealizada(compraRealizada);
        productoCompra.setProducto(producto);
        productoCompra.setCantidad(cantidad);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer productoCompraId = (Integer) session.save(productoCompra);
        session.flush();
        tx.commit();
        session.close();

        return productoCompra;
    }

    public void actualizaProductoCompra(ProductoCompra productoCompra) throws HibernateException {

        ProductoCompra productoCompraBD = obtieneProductoCompraPorId(productoCompra.getId());
        productoCompraBD.setCantidad(productoCompra.getCantidad());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(productoCompraBD);
        session.flush();
        tx.commit();
        session.close();

    }

    public void eliminaProductoCompra(Integer productoCompraId) throws HibernateException {

        ProductoCompra productoCompra = new ProductoCompra();
        productoCompra.setId(productoCompraId);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(productoCompra);
        session.flush();
        tx.commit();
        session.close();
    }
}
