package pl.kurs.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.kurs.models.EquationHistory;

@Repository
public class JpaEquationHistoryDao implements IEquationHistoryDao {

    @Autowired
    private EntityManagerFactory factory;

    @Override
    public EquationHistory insert(EquationHistory equationHistory) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(equationHistory);
        tx.commit();
        entityManager.close();
        return equationHistory;
    }
}
