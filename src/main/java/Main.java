import dao.CategoryDao;
import dao.OptionDao;
import dao.ProductDao;
import dao.ValueDao;
import entity.Category;
import entity.Option;
import entity.Product;
import entity.Value;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class Main {
    static Scanner sc = new Scanner(System.in);
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("main");
    static CategoryDao categoryDao = new CategoryDao(entityManagerFactory);
    static ProductDao productDao = new ProductDao(entityManagerFactory);
    static OptionDao optionDao = new OptionDao(entityManagerFactory);
    static ValueDao valueDao= new ValueDao(entityManagerFactory);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Menu:");
            int n = sc.nextInt();
            switch (n) {
                case 1:
                    showCatalog();
                    int b = sc.nextInt();
                    makeChoiceFromMenu(b);
                    break;
                default:
                    System.out.println("Oops, something went wrong!");
            }
        } finally {
            entityManagerFactory.close();
        }
    }
    private static void makeChoiceFromMenu( int b) {
        switch (b) {
            case 1:
                listCatalog();
                System.out.println("1.List Product by category id");
                System.out.println("2.List Option by category id");
                System.out.println("3.List Value by category id and option id");
                int m = sc.nextInt();
                switch (m){
                    case 1:
                        listProduct();
                        break;
                    case 2:
                        listOption();
                        break;
                    case 3:
                        listValue();
                        break;
                }
                showCatalogOrExit();
                break;
            case 2:
                System.out.println("0.Create category");
                System.out.println("1.Create product by category id");
                System.out.println("2.Create Option by category id");
                System.out.println("3.Create Value by product id");
                int t = sc.nextInt();
                switch (t){
                    case 0:
                        createCategory();
                        break;
                    case 1:
                        listCatalog();
                        createProduct();
                        break;
                    case 2:
                        listCatalog();
                        createOption();
                        break;
                    case 3:
                        createValue();
                        break;
                }
                showCatalogOrExit();
                break;
            case 3:
                System.out.println("1. Delete category");
                System.out.println("2. Delete option");
                System.out.println("3. Delete product");
                System.out.println("4. Delete value");
                int e = sc.nextInt();
                switch (e){
                    case 1:
                        listCatalog();
                        System.out.println("Enter id:");
                        Long id = sc.nextLong();
                        categoryDao.deleteCategory(id);
                        break;
                    case 2:
                        listAllOptions();
                        System.out.println("Enter id");
                        Long id1 = sc.nextLong();
                        optionDao.deleteOption(id1);
                    case 3:
                        listAllProduct();
                        System.out.println("Enter id:");
                        Long id2 = sc.nextLong();
                        productDao.deleteProduct(id2);
                    case 4:
                        listAllValue();
                        System.out.println("Enter id:");
                        Long id3 = sc.nextLong();
                        valueDao.deleteValue(id3);
                }
                showCatalogOrExit();
                break;

            case 5:
                System.out.println("Enter category id that you want change name : ");
                int d = sc.nextInt();
                System.out.println("Enter category name: ");
                String name1 = sc.next();
                Category category1 = categoryDao.updateCategory(name1, d);
                System.out.println(category1);
                showCatalogOrExit();
                break;
            case 6:
                System.out.println("Enter category id:");
                int f = sc.nextInt();
                Category category2 = categoryDao.findCategoryById(f);
                System.out.println(category2.getId() + "  " + category2.getName());
            default:
                System.out.println("Invalid option");
        }
    }
    private static void createCategory(){
        System.out.println("Enter the name of category");
        String name = sc.next();
        categoryDao.createCategory(name);


    }
    private static void createProduct(){
        System.out.println("Enter id of category");
        Long id = sc.nextLong();
        System.out.println("Enter the name of product");
        String nameProduct = sc.next();
        System.out.println("Enter description of product");
        String description = sc.next();
        System.out.println("Enter the price of product");
        BigDecimal price = sc.nextBigDecimal();
        productDao.createProduct(nameProduct,price,description,id);
    }
    private static void createOption(){
        System.out.println("Enter category ID:");
        Long categoryId = sc.nextLong();
        System.out.println("Enter option name:");
        String optionName = sc.next();
        optionDao.createOption(optionName, categoryId);


    }
    private static void createValue(){
        System.out.println("List catalog:");
        listCatalog();
        listProduct();
        listOption();
        System.out.println("Enter product ID:");
        Long productID = sc.nextLong();
        System.out.println("Enter option ID");
        Long optionID = sc.nextLong();
        System.out.println("Enter parameters:");
        String nameParam = sc.next();
        valueDao.createValue(nameParam, productID, optionID);
    }
    private static void listValue(){
        System.out.println("Enter product ID:");
        Long productID = sc.nextLong();
        System.out.println("Enter option ID");
        Long optionID = sc.nextLong();
        List<Value> values = valueDao.listValuesByOptionIdAndByProductId(optionID,productID);
        for (Value value : values){
            System.out.println("id: " + value.getId() + " characteristic name:" + value.getValue());
        }
    }
    private static void listProduct() {
        System.out.print("Enter category ID: ");
        int categoryId = sc.nextInt();
        List<Product> products = productDao.listProductsByCategoryId(categoryId);
        System.out.println("Products in the category:");
        for (Product product : products) {
            System.out.println("id: " + product.getId() + "; " + "name: "+ product.getName() + "; " + "description: "+ product.getDescription() + "; " + "price: "+ product.getPrice() + "; " + "category: " + product.getCategory().getName());
        }
    }
    private static void listOption(){
        System.out.println("Enter category ID:");
        Long categoryOfID = sc.nextLong();
        List<Option> options = optionDao.listOptions(categoryOfID);
        for (Option option : options){
            System.out.println("Category ID: " + option.getCategory().getId() + "; Option id: " + option.getId() + "; Option name: " + option.getName());
        }
    }

    private static void listCatalog() {
        List<Category> categories = categoryDao.getAllCategories();
        System.out.println("List of categories:");
        for (Category category : categories) {
            System.out.println(category.getId() + ": " + category.getName());
        }
    }
    private static void listAllOptions(){
        List<Option> options = optionDao.getAllOptions();
        System.out.println("List all options:");
        for (Option option: options) {
            System.out.println(option.getId() + "Name:" + option.getName());
        }
    }
    private static void listAllProduct(){
        List<Product> products = productDao.getAllProducts();
        System.out.println("List all products:");
        for (Product product: products) {
            System.out.println(product.getId() + " " + product.getName() + " " + product.getPrice() +" "+ product.getDescription());
        }
    }
    private static void listAllValue(){
        List<Value> values = valueDao.getAllValue();
        System.out.println("List all values:");
        for (Value value:
             values) {
            System.out.println(value.getId() + " " + value.getValue());

        }
    }


    private static void showCatalogOrExit(){
        System.out.println("1.Return to catalog ?");
        System.out.println("2.Exit");
        int n = sc.nextInt();
        switch (n){
            case 1:
                showCatalog();
                int g = sc.nextInt();
                makeChoiceFromMenu(g);
                break;
            case 2:
                break;
        }
    }
    private static void showCatalog(){
        System.out.println("1. List of categories");
        System.out.println("2. Create ");
        System.out.println("3. Delete ");
        System.out.println("5. Update category");
        System.out.println("6. Find category");
    }
    
   

}
//
//Menu
//1.List Catalog
//        1.[список]
//        2.List product
//            1.[список продуктов]
//
//        3.return
//        4.q(exit)
//
//
//
//2.Create Catalog
//        Creat option
//        Create product
//            Create value
//
//3.Delete Catalo
//
//4.Update Catalog
//5.Find Catalog
