package pt.psoft.g1.psoftg1.usermanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void testRoleCreation() {
        Role role = new Role(Role.ADMIN);
        assertEquals(Role.ADMIN, role.getAuthority());
    }

    @Test
    public void testRoleEquality() {
        Role role1 = new Role("USER");
        Role role2 = new Role("USER");
        assertEquals(role1, role2);
    }
}
