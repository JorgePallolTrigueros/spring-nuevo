package com.models;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.entities.Product;
import com.pallol.novela.util.HibernateUtil;
import org.hibernate.Query;

public class ProductModel {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Product> findAll() {
		List<Product> products = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Product");
			products = query.list();
			transaction.commit();
		} catch (Exception e) {
			products = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return products;
	}

	@SuppressWarnings({ "rawtypes"})
	public Product find(int id) {
		Product product = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Product where id = :id");
			query.setParameter("id", id);
			product = (Product) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			product = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return product;
	}

}