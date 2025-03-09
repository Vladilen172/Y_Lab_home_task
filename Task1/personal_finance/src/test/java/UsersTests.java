import com.example.User.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void register_Success() {
        assertTrue(userManager.register("John", "Doe", "john.doe@example.com", "password123"));
        assertFalse(userManager.register("John", "Doe", "john.doe@example.com", "password123")); // Duplicate email
    }

    @Test
    void login_Success() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertTrue(userManager.login("john.doe@example.com", "password123"));
    }

    @Test
    void login_Failure() {
        assertFalse(userManager.login("nonexistent@example.com", "password123"));
    }

    @Test
    void deleteAccount_Success() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertTrue(userManager.deleteAccount("john.doe@example.com", "password123"));
        assertFalse(userManager.deleteAccount("john.doe@example.com", "password123")); // Account already deleted
    }

    @Test
    void deleteAccount_Failure_IncorrectPassword() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertFalse(userManager.deleteAccount("john.doe@example.com", "wrongPassword"));
    }

    @Test
    void changeFirstAndLastName_Success() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertTrue(userManager.changeFirstAndLastName("john.doe@example.com", "Jane", "Smith"));
        assertEquals("Jane", userManager.getUsers().get("john.doe@example.com").getFirstName());
        assertEquals("Smith", userManager.getUsers().get("john.doe@example.com").getLastName());
    }

    @Test
    void changeEmail_Success() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertTrue(userManager.changeEmail("john.doe@example.com", "jane.smith@example.com"));
        assertTrue(userManager.getUsers().containsKey("jane.smith@example.com"));
        assertFalse(userManager.getUsers().containsKey("john.doe@example.com"));
    }

    @Test
    void changeEmail_Failure_Duplicate() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        userManager.register("Jane", "Smith", "jane.smith@example.com", "password456");
        assertFalse(userManager.changeEmail("john.doe@example.com", "jane.smith@example.com"));
    }

    @Test
    void changePassword_Success() {
        userManager.register("John", "Doe", "john.doe@example.com", "password123");
        assertTrue(userManager.changePassword("john.doe@example.com", "newPassword"));
        assertTrue(userManager.login("john.doe@example.com", "newPassword"));
    }

    @Test
    void changePassword_Failure_UserNotFound() {
        assertFalse(userManager.changePassword("nonexistent@example.com", "newPassword"));
    }
}
