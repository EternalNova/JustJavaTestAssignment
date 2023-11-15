import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.example.test.bean.Product;
import com.example.test.service.JsonWriter;
import com.example.test.service.XHTMLParser;
import com.example.test.utils.LocalDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonWriterTest {

    private String tmpFileName;
    private List<Product> products;

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Before
    public void init() {
        tmpFileName = "tmpOutput.json";
        val testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
        products = XHTMLParser.parse(testFilePath);
    }

    @Test
    public void testJsonWrite() throws IOException{
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
        try(JsonReader reader = new JsonReader(new FileReader(Paths.get(outputFolder, tmpFileName).toString()))){
            Product[] jsonProducts = gson.fromJson(reader, Product[].class);
            Assert.assertEquals(products.size(), jsonProducts.length);
        }
        catch (Exception exc){
            log.error("Reader error", exc.getMessage());
            Assert.fail();
        }
    }

}
