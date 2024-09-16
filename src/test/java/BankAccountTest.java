import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    void shouldNotBeBlockedWhenCreated() {
        BankAccount account = new BankAccount("a", "b");
        assertFalse(account.isBlocked());
    }

    @Test
    public void shouldReturnZeroAmountAfterActivation() {
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        assertEquals(0, account.getAmount());
        assertEquals("KZT", account.getCurrency());
    }

    @Test
    public void shouldBeBlockedAfterBlockIsCalled() {
        // должен проверять, что счёт заблокирован, после вызова метода block()
        BankAccount account = new BankAccount("a", "b");
        account.block();
        assertTrue(account.isBlocked());
    }

    @Test
    public void shouldReturnFirstNameThenSecondName() {
        // должен проверять, что при вызове метода getFullName() возвращается правильный массив строк
        BankAccount account = new BankAccount("a", "b");
        String[] expectedFullName = {"a", "b"};
        assertArrayEquals(expectedFullName, account.getFullName());
    }

    @Test
    public void shouldReturnNullAmountWhenNotActive() {
        // должен проверять, что при вызове метода getAmount() для неактивного счёта, значение currency равно null,
        // а также выбрасывается исключение IllegalStateException с соответствующим сообщением
        BankAccount account = new BankAccount("a", "b");
        String expectedMsg = "Счёт не активирован.";
        IllegalStateException exception = assertThrows(IllegalStateException.class, account::getAmount);
        assertEquals(expectedMsg, exception.getMessage());
    }
}