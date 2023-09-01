package dao;

import entity.Category;
import entity.Option;
import entity.Product;
import entity.Value;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class ValueDao {
    private final EntityManagerFactory entityManagerFactory;

    public ValueDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public Value createValue(String value, Long productId, Long oprionId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Product product = entityManager.find(Product.class, productId);
        Option option= entityManager.find(Option.class, oprionId);
        Value value1 = new Value();
        value1.setCharacteristicsName(option);
        value1.setValue(value);
        value1.setProduct(product);
        entityManager.persist(value1);

        transaction.commit();
        entityManager.close();
        return value1;
    }
    public void deleteValue(Long valueId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Value value = entityManager.find(Value.class, valueId);
        if (value != null) {
            entityManager.remove(value);
        }
        transaction.commit();
        entityManager.close();
    }

    public void updateValue(Value value) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(value);
        transaction.commit();
        entityManager.close();
    }

    public List<Value> listValues() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Value> values = entityManager.createQuery("SELECT v FROM Value v", Value.class)
                .getResultList();
        entityManager.close();
        return values;
    }
    public List<Value> listValuesByOptionIdAndByProductId(Long optionId, Long productId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Option option = entityManager.find(Option.class, optionId);
            Product product = entityManager.find(Product.class, productId);

            List<Value> values = new ArrayList<>();
            if (option != null && product != null) {
                for (Value value : option.getValues()) {
                    if (value.getProduct().equals(product)) {
                        values.add(value);
                    }
                }
            }
            return values;
        } finally {
            entityManager.close();
        }
    }

    public Value findValueById(Long valueId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Value value = entityManager.find(Value.class, valueId);
        entityManager.close();
        return value;
    }
    public List<Value> getAllValue() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Value> query = entityManager.createQuery("SELECT c FROM Value c", Value.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
