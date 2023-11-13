import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.example.test.Product.GroupedProduct;
import com.example.test.Product.Product;
import com.example.test.Product.ProductGrouper;
import com.example.test.Currency.Currency;

import org.junit.Assert;

public class ProductGroupTest {
    
    private List<Product> createTestProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", "10.00", "USD", "Category1", "8", "Store1", "01.01.2023"));
        products.add(new Product(2, "Product2", "15.50", "USD", "Category2", "12", "Store2", "02.01.2023"));
        products.add(new Product(3, "Product1", "30.00", "USD", "Category1", "3", "Store2", "05.05.2023"));
        products.add(new Product(4, "Product3", "20.00", "USD", "Category3", "6", "Store3", "03.01.2023"));
        products.add(new Product(5, "Product4", "25.00", "USD", "Category4", "10", "Store2", "04.01.2023"));
        products.add(new Product(6, "Product5", "12.50", "USD", "Category1", "9", "Store1", "01.02.2023"));
        products.add(new Product(7, "Product6", "1.50", "USD", "Category2", "15", "Store2", "02.01.2023"));
        
        return products;
    }

    @Test
    public void testGroupProductsLengthByCategories(){
        List<Product> products = createTestProducts();
        String groupby = "category";
        Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, groupby);
        Assert.assertEquals(3, groupedProducts.get("Category1").getTotalOrders());
        Assert.assertEquals(2, groupedProducts.get("Category2").getTotalOrders());
        Assert.assertEquals(1, groupedProducts.get("Category3").getTotalOrders());
        Assert.assertEquals(1, groupedProducts.get("Category4").getTotalOrders());
    }

    @Test
    public void testGroupProductsTotalPriceByCategories(){
        List<Product> products = createTestProducts();
        String groupby = "category";
        Map<String, GroupedProduct> groupedProducts = ProductGrouper.groupProductsByField(products, groupby);
        Assert.assertEquals(new BigDecimal(10*8+30*3+12.50*9).setScale(2),  groupedProducts.get("Category1").getTotalPriceSum().get(Currency.USD));
    }

}
