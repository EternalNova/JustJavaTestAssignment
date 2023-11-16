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
    private String emptyPriceExample;
    private String emptyCurrencyExample;
    private String emptyCountExample;
    private String emptyDateExample;

    @Before
    public void init() {
        testFilePath = getClass().getClassLoader().getResource("inputExample.xhtml").getFile();
        failPriceExample = getClass().getClassLoader().getResource("failPriceExample1.xhtml").getFile();
        failCurrencyExample = getClass().getClassLoader().getResource("failCurrencyExample.xhtml").getFile();
        failCountExample = getClass().getClassLoader().getResource("failCountExample.xhtml").getFile();
        failDateExample = getClass().getClassLoader().getResource("failDateExample.xhtml").getFile();
        incorrectFile = getClass().getClassLoader().getResource("incorrectFile.json").getFile();
        emptyPriceExample = getClass().getClassLoader().getResource("emptyPriceExample.xhtml").getFile();
        emptyCurrencyExample = getClass().getClassLoader().getResource("emptyCurrencyExample.xhtml").getFile();
        emptyCountExample = getClass().getClassLoader().getResource("emptyCountExample.xhtml").getFile();
        emptyDateExample = getClass().getClassLoader().getResource("emptyDateExample.xhtml").getFile();
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

    @Test
    public void testXHTMLParserEmptyPrice(){
        List<Product> products = XHTMLParser.parse(this.emptyPriceExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserEmptyCurrency(){
        List<Product> products = XHTMLParser.parse(this.emptyCurrencyExample);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void testXHTMLParserEmptyCount(){
        List<Product> products = XHTMLParser.parse(this.emptyCountExample);
        Assert.assertEquals(0, products.size());
    }
    
    @Test
    public void testXHTMLParserEmptyDate(){
        List<Product> products = XHTMLParser.parse(this.emptyDateExample);
        Assert.assertEquals(0, products.size());
    }

}
