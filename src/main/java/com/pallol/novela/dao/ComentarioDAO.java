/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Comentario;
import com.pallol.novela.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ComentarioDAO {

    public List<Comentario> obtieneComentario(int novelaId) {
        System.out.println("Muestra novela");
        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Comentario> novelasList = session.createQuery("FROM Comentario").list(); // SELECT * FROM Comentario;
        List<Comentario> lstComentario = session.createQuery("FROM Comentario comentario join fetch comentario.novela join fetch comentario.usuario").list();
        session.close();

        return lstComentario;
    }

    public Integer insertaComentario(Comentario comentario) {
        System.out.println("Inserta Comentario");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer comentarioId = (Integer) session.save(comentario);
        session.flush();
        tx.commit();
        session.close();

        return comentarioId;
    }
}
