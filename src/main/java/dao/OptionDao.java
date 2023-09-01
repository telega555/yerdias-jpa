package dao;

import entity.Category;
import entity.Option;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class OptionDao {
    private final EntityManagerFactory entityManagerFactory;

    public OptionDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public Option createOption(String name, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, id);
        Option option1 = new Option();
        option1.setName(name);
        option1.setCategory(category);
        entityManager.persist(option1);
        transaction.commit();
        entityManager.close();
        return option1;
    }

    public List<Option> listOptions(Long categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Category category = entityManager.find(Category.class, categoryId);
        List<Option> options = new ArrayList<>();
        if (category != null) {
            for (Option option : category.getOptions()) {
                options.add(option);
            }
        }
        entityManager.close();
        return options;
    }
    public Option findOptionById(Long optionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Option option = entityManager.find(Option.class, optionId);
        entityManager.close();
        return option;
    }
    public void updateOption(Option option) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(option);
        transaction.commit();
        entityManager.close();
    }
    public void deleteOption(Long optionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Option option = entityManager.find(Option.class, optionId);
        if (option != null) {
            entityManager.remove(option);
        }
        transaction.commit();
        entityManager.close();
    }
    public List<Option> getAllOptions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Option> query = entityManager.createQuery("SELECT c FROM Option c", Option.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }


}
