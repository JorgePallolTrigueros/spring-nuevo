/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Categoria;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.entities.Personaje;
import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NovelaDAO {

    public List<Novela> obtieneNovela() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Novela> novelasList = session.createQuery("FROM Novela").list(); // SELECT * FROM Novela;
        session.close();

        return novelasList;
    }

    public List<Novela> obtieneNovelaPorAutor(int usuarioId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Novela> lstNovelaAutor = session.createQuery("FROM Novela n JOIN FETCH n.usuarioNovelas nun JOIN FETCH nun.usuario u WHERE u.id= " + usuarioId).list();
        session.close();
        return lstNovelaAutor;
    }

    public Novela obtieneNovelaPorId(int novelaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Novela novela = (Novela) session.createQuery("FROM Novela novela WHERE novela.novelaId = " + novelaId).uniqueResult();
        session.close();
        return novela;
    }

    public Novela insertaNovela(Novela novela) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        /*Este codigo permite guardar sin consultar otro objeto*/
        novela.setCategoria((Categoria) session.load(Categoria.class, novela.getCategoria().getCategoriaId()));

        Integer novelaId = (Integer) session.save(novela);
        session.flush();
        tx.commit();
        session.close();

        return novela;
    }

    public Novela actualizaNovela(Novela novela) throws HibernateException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        /*Este codigo permite guardar sin consultar otro objeto*/
        novela.setCategoria((Categoria) session.load(Categoria.class, novela.getCategoria().getCategoriaId()));

        session.update(novela);
        session.flush();
        tx.commit();
        session.close();

        return novela;
    }

    public Novela eliminaNovela(Novela novela) throws HibernateException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        /*Este codigo permite guardar sin consultar otro objeto*/
        novela.setCategoria((Categoria) session.load(Categoria.class, novela.getCategoria().getCategoriaId()));
        eliminaUsuarioNovela(novela.getNovelaId());
        session.delete(novela);
        session.flush();
        tx.commit();
        session.close();

        return novela;
    }

    public boolean eliminaUsuarioNovela(Integer novelaId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        boolean eliminadoExitoso = true;
        try {
            Query query = session.createQuery("DELETE from UsuarioNovela un where un.novela.novelaId = :novela_id");
            query.setParameter("novela_id", novelaId);
            int result = query.executeUpdate();
            session.flush();
            tx.commit();
            if (result == 0) {
                throw new Exception("No fue posible eliminar");
            }
        } catch (Exception ex) {
            eliminadoExitoso = false;
            ex.printStackTrace();
        }
        session.close();
        return eliminadoExitoso;
    }

    public Novela obtieneNovelaPorNovelaId(int novelaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Novela novela = (Novela) session.createQuery("FROM Novela novela WHERE novela.novelaId = " + novelaId).uniqueResult();
        session.close();
        return novela;
    }

}
