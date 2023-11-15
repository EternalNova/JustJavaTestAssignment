import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.example.test.bean.Currency;
import com.example.test.bean.GroupedProduct;
import com.example.test.bean.Product;
import com.example.test.service.ProductGrouper;
import com.example.test.service.XHTMLParser;

import org.junit.Assert;
import org.junit.Before;

public class ProductGroupTest {

    private List<Product> products;

    @Before
    public void init() {
        products = new ArrayList<>();
        products.add(XHTMLParser.parseFromStrings(1, "Product1", "10.00", "USD", "Category1", "8", "Store1", "01.01.2023"));
        products.add(XHTMLParser.parseFromStrings(2, "Product2", "15.50", "USD", "Category2", "12", "Store2", "02.01.2023"));
        products.add(XHTMLParser.parseFromStrings(3, "Product1", "30.00", "USD", "Category1", "3", "Store2", "05.05.2023"));
        products.add(XHTMLParser.parseFromStrings(4, "Product3", "20.00", "USD", "Category3", "6", "Store3", "03.01.2023"));
        products.add(XHTMLParser.parseFromStrings(5, "Product4", "25.00", "USD", "Category4", "10", "Store2", "04.01.2023"));
        products.add(XHTMLParser.parseFromStrings(6, "Product5", "12.50", "USD", "Category1", "9", "Store1", "01.02.2023"));
        products.add(XHTMLParser.parseFromStrings(7, "Product6", "1.50", "USD", "Category2", "15", "Store2", "02.01.2023"));
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
