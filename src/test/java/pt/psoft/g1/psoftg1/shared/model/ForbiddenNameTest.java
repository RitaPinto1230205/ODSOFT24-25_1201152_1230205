package pt.psoft.g1.psoftg1.shared.model;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ForbiddenNameTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testForbiddenNameConstructor() {
        ForbiddenName forbiddenName = new ForbiddenName("testName");
        assertEquals("testName", forbiddenName.getForbiddenName());
    }

    @Test
    public void testForbiddenNameSetter() {
        ForbiddenName forbiddenName = new ForbiddenName();
        forbiddenName.setForbiddenName("newName");
        assertEquals("newName", forbiddenName.getForbiddenName());
    }

    @Test
    public void testForbiddenNameGetter() {
        ForbiddenName forbiddenName = new ForbiddenName("getNameTest");
        assertEquals("getNameTest", forbiddenName.getForbiddenName());
    }



    @Test
    public void testForbiddenNameSizeConstraint() {
        ForbiddenName forbiddenName = new ForbiddenName("");

        Set<ConstraintViolation<ForbiddenName>> violations = validator.validate(forbiddenName);
        assertFalse(violations.isEmpty());
        assertEquals("tamanho deve estar entre 1 e 2147483647", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidForbiddenName() {
        ForbiddenName forbiddenName = new ForbiddenName("ValidName");
        Set<ConstraintViolation<ForbiddenName>> violations = validator.validate(forbiddenName);
        assertTrue(violations.isEmpty());  // Expect no violations
    }

    @Test
    public void testEmptyForbiddenName() {
        ForbiddenName forbiddenName = new ForbiddenName("");
        Set<ConstraintViolation<ForbiddenName>> violations = validator.validate(forbiddenName);
        assertFalse(violations.isEmpty());  // Expect a violation due to @Size(min = 1)
    }


}
