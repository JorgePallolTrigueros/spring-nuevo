package com.pallol.novela.dao;

import com.pallol.novela.entities.Galeria;

import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GaleriaDAO {

    public List<Galeria> obtienePGaleria(int novelaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Galeria> lstGaleriaNovela = session.createQuery("FROM Galeria galeria WHERE galeria.novela.novelaId = " + novelaId).list();
        session.close();
        return lstGaleriaNovela;

    }
    
    public Galeria obtienealeriaGPorGaleriaId(int galeriaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Galeria galeria = (Galeria) session.createQuery("FROM Galeria galeria WHERE galeria.galeriaId = " + galeriaId).uniqueResult();
        session.close();
        return galeria;
    }   
    
    
    
    
        public Galeria insertaGaleria(Galeria galeria) {

        System.out.println("Inserta Galeria");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Integer galeriaId = (Integer) session.save(galeria);
        session.flush();
        tx.commit();
        session.close();

        return galeria;
    }
        
        
        
        
        public void eliminaGaleria(Galeria galeria) throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(galeria);
        session.flush();
        tx.commit();
        session.close();
    }

}
