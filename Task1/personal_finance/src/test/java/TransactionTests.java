import com.example.Transaction.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionManagerTest {
    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        transactionManager = new TransactionManager();
    }

    @Test
    void addTransaction() {
        transactionManager.addTransaction(1001, 250.75, "Food",
                LocalDate.of(2025, 3, 9), "Lunch", TypeTransaction.EXPENSE);
        assertEquals(1, transactionManager.getTransactionEveryoneUsers().get(1001).size());
    }

    @Test
    void deleteTransaction_Success() {
        transactionManager.addTransaction(1001, 250.75, "Food",
                LocalDate.of(2025, 3, 9), "Lunch", TypeTransaction.EXPENSE);
        assertTrue(transactionManager.deleteTransaction(1001, 1000000));
        assertEquals(0, transactionManager.getTransactionEveryoneUsers().get(1001).size());
    }

    @Test
    void deleteTransaction_Failure() {
        assertFalse(transactionManager.deleteTransaction(1001, 1000000)); // No transactions to delete
    }

    @Test
    void editTransaction_Success() {
        transactionManager.addTransaction(1001, 250.75, "Food",
                LocalDate.of(2025, 3, 9), "Lunch", TypeTransaction.EXPENSE);
        assertTrue(transactionManager.editTransaction(1001, 1000000, 300.00, "Groceries", "Weekly groceries"));

        Transaction transaction = transactionManager.getTransactionEveryoneUsers().get(1001).get(0);
        assertEquals(300.00, transaction.getAmount());
        assertEquals("Groceries", transaction.getCategory());
        assertEquals("Weekly groceries", transaction.getDescription());
    }

    @Test
    void editTransaction_Failure_TransactionNotFound() {
        assertFalse(transactionManager.editTransaction(1001, 1000000, 300.00, "Groceries", "Weekly groceries"));
    }

    @Test
    void viewTransactions_Success() {
        transactionManager.addTransaction(1001, 250.75, "Food",
                LocalDate.of(2025, 3, 9), "Lunch", TypeTransaction.EXPENSE);
        transactionManager.addTransaction(1001, 150.00, "Transport",
                LocalDate.of(2025, 3, 10), "Bus ticket", TypeTransaction.EXPENSE);

        List<Transaction> transactions = transactionManager.viewTransactions(1001,
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 11), null, null);
        assertEquals(2, transactions.size());
    }

    @Test
    void viewTransactions_NoTransactions() {
        List<Transaction> transactions = transactionManager.viewTransactions(1001,
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 11), null, null);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void viewTransactions_FilteredByCategory() {
        transactionManager.addTransaction(1001, 250.75, "Food",
                LocalDate.of(2025, 3, 9), "Lunch", TypeTransaction.EXPENSE);
        transactionManager.addTransaction(1001, 150.00, "Transport",
                LocalDate.of(2025, 3, 10), "Bus ticket", TypeTransaction.EXPENSE);

        List<Transaction> transactions = transactionManager.viewTransactions(1001,
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 11), "Food", null);
        assertEquals(1, transactions.size());
        assertEquals("Food", transactions.get(0).getCategory());
    }
}
