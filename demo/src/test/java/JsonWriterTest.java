import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.example.test.FileProcessing.JsonWriter;
import com.example.test.FileProcessing.LocalDateDeserializer;
import com.example.test.Product.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class JsonWriterTest {

    private String tmpFileName;

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Before
    public void init() {
        tmpFileName = "tmpOutput.json";
    }

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
    public void testJsonWrite() throws IOException{
        List<Product> products = createTestProducts();
        String outputFolder = tmpFolder.newFolder("tmpOutput")
                .getAbsolutePath()
                .toString();
        JsonWriter writer = new JsonWriter(
            outputFolder,
            tmpFileName
            );
        writer.writeToJson(products);

        Gson gson =  new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        JsonReader reader = new JsonReader(new FileReader(Paths.get(outputFolder, tmpFileName).toString()));
        Product[] jsonProducts = gson.fromJson(reader, Product[].class);
        Assert.assertEquals(products.size(), jsonProducts.length);
    }

}
