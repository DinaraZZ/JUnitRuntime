import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FortuneCookieFactoryTest {

    FortuneCookieFactory factory;

    @BeforeEach
    public void beforeEach() {
        List<String> goodFortune = List.of("Good");
        List<String> badFortune = List.of("Bad");
        factory = new FortuneCookieFactory(
                new FortuneConfig(true), goodFortune, badFortune
        );
    }

    @Test
    public void shouldIncrementCountByOneAfterOneCookieBaked() {
        // проверит, что счётчик печенек в фабрике увеличивается на единицу после одной испечённой печеньки
        int expectedAmount = 1;

        factory.bakeFortuneCookie();

        assertEquals(expectedAmount, factory.getCookiesBaked());
    }

    @Test
    public void shouldIncrementCountByTwoAfterTwoCookiesBaked() {
        // проверит, что после двух испечённых печенек счёт увеличится на два
        int expectedAmount = 2;

        for (int i = 0; i < 2; i++) factory.bakeFortuneCookie();

        assertEquals(expectedAmount, factory.getCookiesBaked());
    }

    @Test
    public void shouldSetCounterToZeroAfterResetCookieCreatedCall() {
        // проверит, что после вызова resetCookiesCreated счётчик станет равным нулю
        int expectedAmount = 0;

        factory.bakeFortuneCookie();
        factory.resetCookiesCreated();

        assertEquals(expectedAmount, factory.getCookiesBaked());
    }
}