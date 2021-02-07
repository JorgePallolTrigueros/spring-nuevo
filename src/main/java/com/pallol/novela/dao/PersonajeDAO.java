/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Personaje;
import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonajeDAO {

    public List<Personaje> obtienePersonajePorNovelaId(int novelaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Personaje> lstPersonajeNovela = session.createQuery("FROM Personaje personaje WHERE personaje.novela.novelaId = " + novelaId).list();
        session.close();
        return lstPersonajeNovela;
    }

    public Personaje obtienePersonajePorPersonajeId(int personajeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Personaje personaje = (Personaje) session.createQuery("FROM Personaje personaje WHERE personaje.personajeId = " + personajeId).uniqueResult();
        session.close();
        return personaje;
    }

    public Personaje insertaPersonaje(Personaje personaje) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Integer personajeId = (Integer) session.save(personaje);
        session.flush();
        tx.commit();
        session.close();

        return personaje;
    }

    public Personaje actualizaPersonaje(Personaje personaje) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(personaje);
        session.flush();
        tx.commit();
        session.close();

        return personaje;
    }

    public void eliminaPersonaje(Personaje personaje) throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(personaje);
        session.flush();
        tx.commit();
        session.close();
    }

    public List<Personaje> obtienePersonajePorProductoId(Integer productoId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
