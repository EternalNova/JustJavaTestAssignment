import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.example.test.Product.Product;
import com.example.test.FileProcessing.XHTMLParser;

import org.junit.Assert;
import org.junit.Before;

public class XHTMLParserTest {

    private Path testFilePath;

    @Before
    public void init() {
        this.testFilePath = Paths.get("src\\test\\java\\testFiles\\inputExample.xhtml");
    }


    @Test
    public void testXHTMLParser(){
        List<Product> products = XHTMLParser.parse(this.testFilePath.toString());
        Assert.assertEquals(7, products.size());
        Assert.assertEquals(1, (int) products.get(0).getId());
        Assert.assertEquals("Product 1", products.get(0).getName());
    }
}
