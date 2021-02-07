/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Usuario;
import com.pallol.novela.entities.Categoria;
import com.pallol.novela.entities.Comentario;
import com.pallol.novela.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;


public class AutorNovelaDAO {
            public List<Usuario> obtieneAutorNovela() {
        System.out.println("Muestra novela");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> novelasList = session.createQuery("FROM AutorNovela").list(); // SELECT * FROM AutorNovela;
        session.close();

        return novelasList;
    }
}
