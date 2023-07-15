import entity.Category;
import jakarta.persistence.*;
import operation.MyClass;

import java.util.List;
import java.util.Scanner;

public class CatalogJpa {
    private static final Scanner IN = new Scanner(System.in);

    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("main");

    public static void read(EntityManager entityManager){
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Category> categoryTypedQuery = entityManager.createQuery(
                    "select c from Category c order by c.name", Category.class
            );
            List<Category> categories = categoryTypedQuery.getResultList();
            for (int i = 0; i < categories.size(); i++) {

                System.out.printf("- %s [%d]\n", categories.get(i).getName(), i);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    public static void delete(EntityManager entityManager, Long id){
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("DELETE FROM Category c WHERE c.id = :id");
            query.setParameter("id", id);
            int rowsDeleted = query.executeUpdate();
            System.out.println(rowsDeleted + " записей удалено");

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

    }
    public static void updateName(EntityManager entityManager, Long id, String newName) {
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("UPDATE Category c SET c.name = :newName WHERE c.id = :id");
            query.setParameter("newName", newName);
            query.setParameter("id", id);
            int rowsUpdated = query.executeUpdate();
            System.out.println(rowsUpdated + " записей обновлено");

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    public static void create(EntityManager entityManager,  String name){
        try {
            entityManager.getTransaction().begin();
            Category category = new Category();

            category.setName(name);

            entityManager.persist(category);

            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManager entityManager = FACTORY.createEntityManager();
        Long id = sc.nextLong();
//        String name = sc.next();

//        create(entityManager,name);
//         read(entityManager);
//         updateName(entityManager,id,name);
         delete(entityManager,id);
        }
    }

