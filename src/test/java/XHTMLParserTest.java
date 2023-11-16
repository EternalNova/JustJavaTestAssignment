import java.util.List;

import org.junit.Test;

import com.example.test.bean.Product;
import com.example.test.service.XHTMLParser;

import org.junit.Assert;
import org.junit.Before;

public class XHTMLParserTest {

    private String testFilePath;
    private String failPriceExample;
    private String failCurrencyExample;
    private String failCountExample;
    private String failDateExample;
    private String incorrectFile;
    
    @Before
    public void init() {
        testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
        failPriceExample = getClass().getClassLoader().getResource("failPriceExample1.xhtml").getFile();
        failCurrencyExample = getClass().getClassLoader().getResource("failCurrencyExample.xhtml").getFile();
        failCountExample = getClass().getClassLoader().getResource("failCountExample.xhtml").getFile();
        failDateExample = getClass().getClassLoader().getResource("failDateExample.xhtml").getFile();
        incorrectFile = getClass().getClassLoader().getResource("incorrectFile.json").getFile();
    }


    @Test
    public void testXHTMLParser(){
        List<Product> products = XHTMLParser.parse(this.testFilePath);
        Assert.assertEquals(7, products.size());
        Assert.assertEquals(1, (int) products.get(0).getId());
        Assert.assertEquals("Product 1", products.get(0).getName());
    }

    @Test
    public void testXHTMLParserFailPrice(){
        List<Product> products = XHTMLParser.parse(this.failPriceExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserFailCurrency(){
        List<Product> products = XHTMLParser.parse(this.failCurrencyExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserFailCount(){
        List<Product> products = XHTMLParser.parse(this.failCountExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserFailDate(){
        List<Product> products = XHTMLParser.parse(this.failDateExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserIncorrectFile(){
        List<Product> products = XHTMLParser.parse(this.incorrectFile);
        Assert.assertEquals(0, products.size());
    }

}
