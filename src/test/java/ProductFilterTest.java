import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import com.example.test.bean.Product;
import com.example.test.service.ProductFilter;
import com.example.test.service.XHTMLParser;

import lombok.val;

public class ProductFilterTest {

    private List<Product> products;

    @Before
    public void init() {
        val testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
        products = XHTMLParser.parse(testFilePath);
    }

    @Test
    public void testFilterProductsByCountMore(){
        String filterExpression = "count>9";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
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
        Assert.assertEquals(3, filteredProducts.size());
    }

    @Test
    public void testFilterProductsByPrice(){
        String filterExpression = "price>15.51";
        List<Product> filteredProducts = ProductFilter.filterProducts(products, filterExpression);
        Assert.assertEquals(3, filteredProducts.size());
    }

}
