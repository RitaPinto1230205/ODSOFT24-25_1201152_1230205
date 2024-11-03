package pt.psoft.g1.psoftg1.usermanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTest {

    private User user;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        user = new User("test@example.com", "Password123");
    }

    @Test
    public void testConstructor() {
        assertEquals("test@example.com", user.getUsername());
        assertTrue(passwordEncoder.matches("Password123", user.getPassword()));
    }

    @Test
    public void testFactoryMethodWithRole() {
        User adminUser = User.newUser("admin@example.com", "AdminPass123", "Admin", Role.ADMIN);
        assertEquals("admin@example.com", adminUser.getUsername());
        assertTrue(adminUser.getAuthorities().contains(new Role(Role.ADMIN)));
    }

    @Test
    public void testAddAuthority() {
        Role newRole = new Role("CUSTOM_ROLE");
        user.addAuthority(newRole);
        assertTrue(user.getAuthorities().contains(newRole));
    }

    @Test
    public void testUserDetailsMethods() {
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
    //BlackBox

    @Test
    public void testUserWithValidDetails() {
        User user = new User("validUser@example.com", "Password1!");
        assertEquals("validUser@example.com", user.getUsername());
        assertTrue(user.isEnabled());
    }


   /* @Test
    public void testUserWithInvalidEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new User(null, "Password1!"));
        assertEquals("Invalid email format", exception.getMessage());
    }
    */

}
