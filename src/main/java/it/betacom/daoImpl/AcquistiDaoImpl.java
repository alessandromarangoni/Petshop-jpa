package it.betacom.daoImpl;

import java.util.List;
import javax.persistence.EntityManager;
import it.betacom.dao.DAOacquisti;
import it.betacom.singleton.JPAUtil;
import model.Acquistianimali;

public class AcquistiDaoImpl implements DAOacquisti {

    @Override
    public List<Acquistianimali> getAllAcquisti() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM Acquistianimali a", Acquistianimali.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void createAcquisto(Acquistianimali acquisto) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(acquisto);
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
    public Acquistianimali readAcquisto(int id) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            return entityManager.find(Acquistianimali.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateAcquisto(Acquistianimali acquisto) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            //questo merge dovrebbe aggiornare l istanza e nel caso non ci sia ne carica una VERIFICARE CORRETTEZZA
            entityManager.merge(acquisto);
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
    public void deleteAcquisto(Acquistianimali acquisto) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Acquistianimali acquistoDb = entityManager.find(Acquistianimali.class, acquisto.getId());
            if (acquistoDb != null) {
                entityManager.remove(acquistoDb);
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
