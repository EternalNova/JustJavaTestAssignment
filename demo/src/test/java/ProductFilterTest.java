import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Assert;
import com.example.test.Product.Product;
import com.example.test.Product.ProductFilter;

public class ProductFilterTest {

        private List<Product> createTestProducts() {
            List<Product> products = new ArrayList<>();
            products.add(new Product(1, "Product1", "10.00", "USD", "Category1", "8", "Store1", "01.01.2023"));
            products.add(new Product(2, "Product2", "15.50", "USD", "Category2", "12", "Store2", "02.01.2023"));
            products.add(new Product(3, "Product1", "30.00", "USD", "Category1", "3", "Store2", "05.05.2023"));
            products.add(new Product(4, "Product3", "20.00", "USD", "Category3", "6", "Store3", "03.01.2023"));
            products.add(new Product(5, "Product4", "25.00", "USD", "Category4", "10", "Store2", "04.01.2023"));
            return products;
    }

    @Test
    public void testFilterProductsByCountMore(){
        List<Product> products = createTestProducts();
        String filterExpression = "count>9";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(2, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCountEquals(){
        List<Product> products = createTestProducts();
        String filterExpression = "count=6";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(1, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCountLess(){
        List<Product> products = createTestProducts();
        String filterExpression = "count<9";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCategory(){
        List<Product> products = createTestProducts();
        String filterExpression = "category=Category1";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(2, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByPrice(){
        List<Product> products = createTestProducts();
        String filterExpression = "price>15.51";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
    }

    

}
