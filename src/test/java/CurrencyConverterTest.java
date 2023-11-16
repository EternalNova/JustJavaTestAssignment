import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.test.bean.Product;
import com.example.test.enums.Currency;
import com.example.test.service.CurrencyConverter;
import com.example.test.service.XHTMLParser;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyConverterTest {
    private List<Product> products;

    @Before
    public void init(){
        val testFilePath = getClass().getClassLoader().getResource("inputExample2.xhtml").getFile();
        products = XHTMLParser.parse(testFilePath);
    }

    @Test
    public void testCurrencyConverterAssertAllCurrency(){
        val product = products.get(0);

        Assert.assertNull(product.getPriceCurrency(Currency.EUR));
        Assert.assertNull(product.getPriceCurrency(Currency.RUB));

        CurrencyConverter.convertAll(product.getPrice());

        val usd = product.getPriceCurrency(Currency.USD);
        val eur = product.getPriceCurrency(Currency.EUR);
        val rub = product.getPriceCurrency(Currency.RUB);

        Assert.assertNotNull(usd);
        Assert.assertNotNull(eur);
        Assert.assertNotNull(rub);

    }

    @Test
    public void testCurrencyConverterUSD2Others(){
        val product = products.get(0);

        CurrencyConverter.convertAll(product.getPrice());

        val eur = product.getPriceCurrency(Currency.EUR);
        val rub = product.getPriceCurrency(Currency.RUB);

        Assert.assertEquals(new BigDecimal("900.00"), rub);;
        Assert.assertEquals(new BigDecimal("9.30"), eur);;

    }

    @Test
    public void testCurrencyConverterRUB2Others(){
        val product = products.get(1);
        CurrencyConverter.convertAll(product.getPrice());

        val eur = product.getPriceCurrency(Currency.EUR);
        val usd = product.getPriceCurrency(Currency.USD);

        Assert.assertEquals(new BigDecimal("10.00"), usd);;
        Assert.assertEquals(new BigDecimal("9.30"), eur);;

    }

}
