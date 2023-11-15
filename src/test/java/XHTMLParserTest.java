import java.util.List;

import org.junit.Test;

import com.example.test.bean.Product;
import com.example.test.service.XHTMLParser;

import org.junit.Assert;
import org.junit.Before;

public class XHTMLParserTest {

    private String testFilePath;

    @Before
    public void init() {
        testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
    }


    @Test
    public void testXHTMLParser(){
        List<Product> products = XHTMLParser.parse(this.testFilePath);
        Assert.assertEquals(7, products.size());
        Assert.assertEquals(1, (int) products.get(0).getId());
        Assert.assertEquals("Product 1", products.get(0).getName());
    }
}
