package dao;
import entity.Category;
import jakarta.persistence.*;

import java.util.List;


public class CategoryDao {

    private final EntityManagerFactory entityManagerFactory;

    public  CategoryDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Category createCategory(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Category category = new Category();
        category.setName(name);

        entityManager.persist(category);

        transaction.commit();
        entityManager.close();

        return category;
    }

    public Category findCategoryById(int categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Category category = entityManager.find(Category.class, categoryId);
        entityManager.close();
        return category;
    }

    public Category updateCategory(String name, int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Category category = entityManager.find(Category.class, id);
        category.setName(name);
        transaction.begin();

        entityManager.merge(category);

        transaction.commit();
        entityManager.close();
        return category;
    }

    public void deleteCategory(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Category category = entityManager.find(Category.class, id);
        transaction.begin();

        entityManager.remove(entityManager.contains(category) ? category : entityManager.merge(category));

        transaction.commit();
        entityManager.close();
    }

    public List<Category> getAllCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}
