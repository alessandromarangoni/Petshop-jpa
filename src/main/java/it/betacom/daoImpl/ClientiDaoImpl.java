package it.betacom.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import model.Clienti;
import it.betacom.dao.DAOClienti;
import it.betacom.singleton.JPAUtil;

public class ClientiDaoImpl implements DAOClienti {

    @Override
    public List<Clienti> getAllClienti() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            return entityManager.createQuery("SELECT c FROM Clienti c", Clienti.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void CreateCliente(Clienti clienti) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(clienti);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Clienti readCliente(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            return entityManager.find(Clienti.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateCliente(Clienti clienti) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(clienti);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteCliente(Clienti clienti) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Clienti cliente = entityManager.find(Clienti.class, clienti.getId());
            if (cliente != null) {
                entityManager.remove(cliente);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}
