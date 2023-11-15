import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import com.example.test.bean.Product;
import com.example.test.service.ProductFilter;
import com.example.test.service.XHTMLParser;

public class ProductFilterTest {

    private List<Product> products;

    @Before
    public void init() {
        products = new ArrayList<>();
        products.add(XHTMLParser.parseFromStrings(1, "Product1", "10.00", "USD", "Category1", "8", "Store1", "01.01.2023"));
        products.add(XHTMLParser.parseFromStrings(2, "Product2", "15.50", "USD", "Category2", "12", "Store2", "02.01.2023"));
        products.add(XHTMLParser.parseFromStrings(3, "Product1", "30.00", "USD", "Category1", "3", "Store2", "05.05.2023"));
        products.add(XHTMLParser.parseFromStrings(4, "Product3", "20.00", "USD", "Category3", "6", "Store3", "03.01.2023"));
        products.add(XHTMLParser.parseFromStrings(5, "Product4", "25.00", "USD", "Category4", "10", "Store2", "04.01.2023"));
    }

    @Test
    public void testFilterProductsByCountMore(){
        String filterExpression = "count>9";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(2, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCountEquals(){
        String filterExpression = "count=6";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(1, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCountLess(){
        String filterExpression = "count<9";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByCategory(){
        String filterExpression = "category=Category1";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(2, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByPrice(){
        String filterExpression = "price>15.51";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
    }

}
