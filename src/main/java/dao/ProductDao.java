package dao;
import entity.Category;
import entity.Option;
import entity.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProductDao {

    private EntityManagerFactory entityManagerFactory;
    private Product product;

    public ProductDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Product createProduct(String name, BigDecimal price, String description, Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Category category = entityManager.find(Category.class, id);
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        entityManager.persist(product);

        transaction.commit();
        entityManager.close();

        return product;
    }

    public Product findProductById(Long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product = entityManager.find(Product.class, productId);
        entityManager.close();
        return product;
    }

    public void updateProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(product);

        transaction.commit();
        entityManager.close();
    }

    public void deleteProduct(Long productId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Product product1 = entityManager.find(Product.class, productId);
        entityManager.remove(product1);
        transaction.commit();
        entityManager.close();
    }
    public List<Product> listProductsByCategoryId(int categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Category category = entityManager.find(Category.class, categoryId);

        List<Product> products = new ArrayList<>();

        if (category != null) {
            for (Product product : category.getProducts()) {
                products.add(product);
            }
        }

        entityManager.close();
        return products;
    }
    public List<Product> getAllProducts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT c FROM Product c", Product.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }


}
