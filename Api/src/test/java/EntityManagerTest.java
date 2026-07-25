import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {

    @Test
    void createEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Sipentas");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void transaction() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Sipentas");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            // TODO: Tambahkan operasi database di sini
            // Contoh: entityManager.persist(new Customer("C001", "Budi"));
            entityTransaction.commit();
            System.out.println("✅ Transaksi berhasil!");
        } catch (Throwable throwable) {
            entityTransaction.rollback();
            System.out.println("❌ Transaksi gagal!");
            throwable.printStackTrace();
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}