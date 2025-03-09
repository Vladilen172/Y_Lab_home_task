import com.example.Budget.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagerTest {
    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    void setMonthlyBudget() {
        budgetManager.setMonthlyBudget(1, 1000.00);
        assertEquals(1000.00, budgetManager.getMonthlyBudgets().get(1));
    }

    @Test
    void checkBudgetExceeded_ExceedsBudget() {
        budgetManager.setMonthlyBudget(1, 1000.00);
        assertTrue(budgetManager.checkBudgetExceeded(1, 1200.00)); // Current expenses exceed budget
    }

    @Test
    void checkBudgetExceeded_DoesNotExceedBudget() {
        budgetManager.setMonthlyBudget(1, 1000.00);
        assertFalse(budgetManager.checkBudgetExceeded(1, 800.00)); // Current expenses do not exceed budget
    }

    @Test
    void checkBudgetExceeded_NoBudgetSet() {
        assertFalse(budgetManager.checkBudgetExceeded(1, 500.00)); // No budget set for user
    }

    @Test
    void checkBudgetExceeded_ExactlyAtBudget() {
        budgetManager.setMonthlyBudget(1, 1000.00);
        assertFalse(budgetManager.checkBudgetExceeded(1, 1000.00)); // Current expenses equal budget
    }
}
