import exceptions.InsufficientFundsException;
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
        assertNull(account.getCurrency());
        IllegalStateException exception = assertThrows(IllegalStateException.class, account::getAmount);
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotWithdrawIfNotActivated() {
        // не должен снимать деньги, если аккаунт не активирован.
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        String expectedMsg = "Счёт не активирован.";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.withdraw(10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldWithdrawIfActivated() {
        // должен снимать деньги, если аккаунт активирован
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.deposit(20);
        int expectedAmount = 10;
        int withdrawAmount = 10;

        account.withdraw(withdrawAmount);

        assertEquals(expectedAmount, account.getAmount());
    }

    @Test
    public void shouldNotWithdrawIfAmountIsInsufficient() {
        // не должен снимать деньги, если не хватает денег на счёте
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.deposit(20);
        int withdrawAmount = 40;

        assertThrows(InsufficientFundsException.class, () -> account.withdraw(withdrawAmount));
    }

    @Test
    public void shouldNotWithdrawIfBlocked() {
        // не должен снимать деньги, если аккаунт заблокирован
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        account.activate("KZT");
        account.block();
        String expectedMsg = "Счёт заблокирован.";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.withdraw(10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAccountNotActivated() {
        // не должен переводить деньги, если аккаунт отправителя не активирован
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов не активирован.";
        otherAccount.activate("KZT");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfOtherAccountNotActivated() {
        // не должен переводить деньги, если аккаунт получателя не активирован
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов не активирован.";
        account.activate("KZT");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAccountBlocked() {
        // не должен переводить деньги, если аккаунт отправителя заблокирован
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов заблокирован.";
        account.block();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfOtherAccountBlocked() {
        // не должен переводить деньги, если аккаунт получателя заблокирован
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        String expectedMsg = "Один из счетов заблокирован.";
        otherAccount.block();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> account.transfer(otherAccount, 10));

        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    public void shouldNotTransferIfAmountIsInsufficient() {
        // не должен переводить деньги, если не счёте недостаточно средств
        // выбрасывается исключение
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");

        account.activate("KZT");
        otherAccount.activate("KZT");

        assertThrows(InsufficientFundsException.class,
                () -> account.transfer(otherAccount, 10));
    }

    @Test
    public void shouldTransferIfAmountIsSufficient() {
        // должен переводить деньги, если не счёте достаточно средств
        BankAccount account = new BankAccount("a", "b");
        BankAccount otherAccount = new BankAccount("c", "d");
        int amount = 20;
        int transferAmount = 10;
        int expectedAmount = 10;

        account.activate("KZT");
        otherAccount.activate("KZT");
        account.deposit(amount);
        account.transfer(otherAccount, transferAmount);

        assertEquals(expectedAmount, account.getAmount());
    }
}