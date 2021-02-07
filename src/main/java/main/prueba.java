package main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.pallol.novela.entities.*;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("construccionsivefaces");
		EntityManager manager = managerFactory.createEntityManager();
		
		// Creamos nuevos objetos
		Novela novela = new Novela();
		novela.setAnotacion("anotado");
	
		novela.setDescripciong("Buena");
		novela.setDescripcionp("Buena");
		novela.setImagen("www");
		novela.setNombre("nom");
		novela.setNotas("dd");
		novela.setPrecio(44);
		novela.setVotos(33);
		
		

		

		// Guardar nuevos usuarios (método persist())
		try{
			// Se inicia una transacción
			manager.getTransaction().begin();   
			// Se persisten los objetos
			manager.persist(novela); 
			// Se realiza commit para hacer efectivas las inserciones
			manager.getTransaction().commit();    
		}catch(Exception ex){
			ex.printStackTrace();
			manager.getTransaction().rollback();   
		}finally{
			manager.close();
			managerFactory.close();
		}
	}

}
