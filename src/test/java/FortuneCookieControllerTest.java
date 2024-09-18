import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FortuneCookieControllerTest {

    static FortuneCookieFactory positiveFactory;
    static FortuneCookieFactory negativeFactory;

    @BeforeAll
    public static void beforeAll() {
        List<String> goodFortune = List.of("Good");
        List<String> badFortune = List.of("Bad");
        positiveFactory = new FortuneCookieFactory(
                new FortuneConfig(true), goodFortune, badFortune
        );
        negativeFactory = new FortuneCookieFactory(
                new FortuneConfig(false), goodFortune, badFortune
        );
    }

    @Test
    public void shouldReturnPositiveFortune() {
        // должен проверять, что фабрика может испечь печеньку с хорошим предсказанием
        FortuneCookieController cookieController = new FortuneCookieController(positiveFactory);
        String expectedFortune = "Good";

        String fortune = cookieController.tellFortune().getFortuneText();

        assertEquals(expectedFortune, fortune);
    }

    @Test
    public void shouldReturnNegativeFortune() {
        // проверит, что фабрика также умеет печь печеньки с негативными предсказаниями
        FortuneCookieController cookieController = new FortuneCookieController(negativeFactory);
        String expectedFortune = "Bad";

        String fortune = cookieController.tellFortune().getFortuneText();

        assertEquals(expectedFortune, fortune);
    }
}