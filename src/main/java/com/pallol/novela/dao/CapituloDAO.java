package com.pallol.novela.dao;

import com.pallol.novela.entities.Capitulo;
import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CapituloDAO {

    public List<Capitulo> obtienePCapitulo(int novelaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Capitulo> lstCapituloNovela = session.createQuery("FROM Capitulo capitulo WHERE capitulo.novela.novelaId = " + novelaId).list();
        session.close();
        return lstCapituloNovela;
    }
    

        public Capitulo obtienvapituloPorCapituloId(int capituloId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Capitulo capitulo = (Capitulo) session.createQuery("FROM Capitulo capitulo WHERE capitulo.capituloId = " + capituloId).uniqueResult();
        session.close();
        return capitulo;
    }   
    
    
   public Capitulo insertaCapitulo(Capitulo capitulo) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Integer CapituloId = (Integer) session.save(capitulo);
        session.flush();
        tx.commit();
        session.close();

        return capitulo;
    }
    
        public Capitulo actualizaCapitulo(Capitulo capitulo) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(capitulo);
        session.flush();
        tx.commit();
        session.close();

        return capitulo;
    }
        
        
    public void eliminaCapitulo(Capitulo Capitulo) throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(Capitulo);
        session.flush();
        tx.commit();
        session.close();
    }
    
    
    
    
}
