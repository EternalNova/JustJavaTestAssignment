import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.example.test.bean.GroupedProduct;
import com.example.test.bean.Product;
import com.example.test.enums.Currency;
import com.example.test.service.ProductGrouper;
import com.example.test.service.XHTMLParser;

import lombok.val;

import org.junit.Assert;
import org.junit.Before;

public class ProductGroupTest {

    private List<Product> products;

    @Before
    public void init() {
        val testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
        products = XHTMLParser.parse(testFilePath);
    }
    
    @Test
    public void testGroupProductsLengthByCategories(){
        String groupby = "category";
        Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, groupby);
        Assert.assertEquals(3, groupedProducts.get("Category1").getTotalOrders());
        Assert.assertEquals(2, groupedProducts.get("Category2").getTotalOrders());
        Assert.assertEquals(1, groupedProducts.get("Category3").getTotalOrders());
        Assert.assertEquals(1, groupedProducts.get("Category4").getTotalOrders());
    }

    @Test
    public void testGroupProductsTotalPriceByCategories(){
        String groupby = "category";
        Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, groupby);
        Assert.assertEquals(new BigDecimal(10*8+30*3+12.50*9).setScale(2),  groupedProducts.get("Category1").getTotalPriceSum().get(Currency.USD));
    }

}
