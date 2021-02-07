/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Categoria;
import com.pallol.novela.entities.Comentario;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;


public class CategoriaDAO {
   public List<Categoria> obtieneCategoria() {
        System.out.println("Muestra novela");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categoria> novelasList = session.createQuery("FROM Categoria").list(); // SELECT * FROM Categoria;
        session.close();

        return novelasList;
    }
   
   
       public Categoria obtieneCategoriaPorId(int categoriaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categoria categoria = (Categoria) session.createQuery("FROM Categoria categoria WHERE categoria.categoriaId = " + categoriaId).uniqueResult();
        session.close();
        return categoria;
    }
   
}
