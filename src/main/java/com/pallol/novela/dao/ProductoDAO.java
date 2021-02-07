/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Producto;
import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author casa pallol
 */
public class ProductoDAO {

    public List<Producto> obtieneProducto() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Producto> productosList = session.createQuery("FROM Producto").list(); // SELECT * FROM Producto;
        session.close();

        return productosList;
    }

    public Producto obtieneProductoPorId(int productoId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Producto producto = (Producto) session.createQuery("FROM Producto producto WHERE producto.productoId = " + productoId).uniqueResult();
        session.close();
        return producto;
    }

    public Producto insertaProducto(Producto producto, Integer usuarioId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer productoId = (Integer) session.save(producto);
        session.flush();
        tx.commit();
        session.close();

        return producto;
    }

    public Producto actualizaProducto(Producto producto) throws HibernateException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(producto);
        session.flush();
        tx.commit();
        session.close();

        return producto;
    }

    public Producto eliminaProducto(Producto producto) throws HibernateException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(producto);
        session.flush();
        tx.commit();
        session.close();

        return producto;
    }
}
