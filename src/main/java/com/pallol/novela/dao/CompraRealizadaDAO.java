/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.CompraRealizada;
import com.pallol.novela.entities.Usuario;
import com.pallol.novela.util.HibernateUtil;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompraRealizadaDAO {
    //Confirmar
    //En el perfil habra una forma de que se vea tus compras y en el caso de que venzan no pader evitarlos

    public CompraRealizada obtieneCompraRealizadaPorUsuarioId(Integer usuarioId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //Valida si hay una compra en proceso relacionada con el usuario, esto es
        //Determinando si esta realizada (1) o no (0)
        CompraRealizada compraRealizada = (CompraRealizada) session.createQuery("FROM CompraRealizada cp WHERE cp.usuario.usuarioId = " + usuarioId + " AND cp.realizada = 0").uniqueResult();
        session.close();
        return compraRealizada;
    }

    public CompraRealizada insertaCompra(Integer usuarioId) {

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);

        CompraRealizada compraRealizada = new CompraRealizada();
        compraRealizada.setUsuario(usuario);
        compraRealizada.setFechaCreacion(new Date());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer compraRealizadaId = (Integer) session.save(compraRealizada);
        session.flush();
        tx.commit();
        session.close();

        return compraRealizada;
    }

    public void actualizaCompraRealizada(Integer usuarioId, Integer montoTotal) throws HibernateException {

        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioId);

        CompraRealizada compraRealizada = obtieneCompraRealizadaPorUsuarioId(usuarioId);
        compraRealizada.setMontoTotal(montoTotal);
        compraRealizada.setRealizada(true);
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(compraRealizada);
        session.flush();
        tx.commit();
        session.close();

    }

    //Implementar DJGH
    public CompraRealizada anadirproductoalcarrito(Integer productoId) throws HibernateException {
        return null;

        //Actualizar el precio total del carrito cada vez que se añada un producto
        //Actualizar el precio total del carrito cada vez que se quite un producto
        //Lista producto tiene un boton para añadir productos
    }

    //Implementar DJGH
    public CompraRealizada eliminarproductoalalcarrito(Integer productoId) throws HibernateException {
        return null;
        //Actualizar el precio total del carrito cada vez que se quite un producto
        //Crear Boton en boton en carrito.xhtml la cual da aconfirmar compra. 
        // La cual no deja de ser un registro en compra que dentro del perfil.
        //habra uma posibilidad para borrar
    }

}
