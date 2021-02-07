/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dao;

import com.pallol.novela.entities.Persona;
import com.pallol.novela.entities.Rol;
import com.pallol.novela.entities.Usuario;
import com.pallol.novela.entities.UsuarioNovela;

import com.pallol.novela.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author casa pallol
 */
public class UsuarioDAO {

    public Usuario validaUsuario(Usuario usuario) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuarioValidado = (Usuario) session.createQuery(
                "FROM Usuario u WHERE "
                + "u.codigo='" + usuario.getCodigo() + "' AND "
                + "u.contrasena='" + usuario.getContrasena() + "' AND "
                + "u.estado = true ").uniqueResult();
        session.close();

        return usuarioValidado;
    }

    public Persona obtieneDetallesUsuario(Usuario usuario) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Persona persona = (Persona) session.createQuery(
                "FROM Persona p WHERE "
                + "p.usuario.usuarioId =" + usuario.getUsuarioId()).uniqueResult();
        session.close();

        return persona;
    }

    public Usuario obtieneUsuarioPorId(Integer usuarioId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = (Usuario) session.createQuery(
                "FROM Usuario u WHERE "
                + "u.usuarioId =" + usuarioId).uniqueResult();
        session.close();

        return usuario;
    }

    public Usuario actualizacontrasena(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer usuarioId = (Integer) session.save(usuario);
        session.flush();
        tx.commit();
        session.close();
        return usuario;

    }

    public Rol obtieneRolUsuario(Usuario usuario) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Rol rol = (Rol) session.createQuery(
                "FROM Rol r WHERE "
                + "r.rolId =" + usuario.getRol().getRolId()).uniqueResult();
        session.close();

        return rol;
    }

    public List<Usuario> obtieneUsuario() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> autorlist = session.createQuery("FROM Usuario").list(); // SELECT * FROM UsuarioNovela;
        session.close();
        return autorlist;
    }

    public Usuario obtieneUsuarioPorCodigo(String codigo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Usuario usuario = (Usuario) session.createQuery("FROM Usuario u WHERE u.codigo = '" + codigo + "'").uniqueResult();
        session.close();
        return usuario;
    }

    public Usuario insertaUsuario(Usuario usuario) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer usuarioId = (Integer) session.save(usuario);
        session.flush();
        tx.commit();
        session.close();
        return usuario;
    }

    public UsuarioNovela insertaUsuarioNovela(UsuarioNovela usuarioNovela) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Integer usuarioNovelaId = (Integer) session.save(usuarioNovela);
        session.flush();
        tx.commit();
        session.close();
        return usuarioNovela;
    }

    public boolean eliminaUsuarioDeNovela(Integer usuarioId, Integer novelaId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        boolean eliminadoExitoso = true;
        try {
            Query query = session.createQuery("DELETE from UsuarioNovela un where un.usuario.usuarioId = :usuario_id and un.novela.novelaId = :novela_id");
            query.setParameter("usuario_id", usuarioId);
            query.setParameter("novela_id", novelaId);
            int result = query.executeUpdate();
            session.flush();
            tx.commit();
            if(result == 0){
                throw new Exception("No fue posible eliminar");
            }                        
        } catch(Exception ex){
            eliminadoExitoso = false;
            ex.printStackTrace();
        }
        session.close();
        return eliminadoExitoso;
    }

    public Usuario actualizaUsuario(Usuario usuario) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(usuario);

        session.flush();
        tx.commit();
        session.close();
        return usuario;
    }
}
