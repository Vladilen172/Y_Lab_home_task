import com.example.Goal.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoalManagerTest {
    private GoalManager goalManager;

    @BeforeEach
    void setUp() {
        goalManager = new GoalManager();
    }

    @Test
    void setSavingsGoal() {
        goalManager.setSavingsGoal(1, 1000.00);
        assertEquals(1000.00, goalManager.getSavingsGoals().get(1));
    }

    @Test
    void trackGoalProgress_Success() {
        goalManager.setSavingsGoal(1, 1000.00);
        goalManager.trackGoalProgress(1, 1000.00); // Expect celebration message
    }

    @Test
    void trackGoalProgress_InProgress() {
        goalManager.setSavingsGoal(1, 1000.00);
        goalManager.trackGoalProgress(1, 500.00); // Expect progress message
    }

    @Test
    void trackGoalProgress_NoGoalSet() {
        goalManager.trackGoalProgress(1, 500.00); // Expect no goal message
    }

    @Test
    void trackGoalProgress_ZeroCurrentSavings() {
        goalManager.setSavingsGoal(1, 1000.00);
        goalManager.trackGoalProgress(1, 0.00); // Expect progress at 0%
    }
}
