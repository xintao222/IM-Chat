package com.stanthinking.im.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class ValidatorTest {

    @Test
    public void testValidEmail() {
        assertTrue(Validator.validateEmail("test@examle.com"));
        assertFalse(Validator.validateEmail("testexamle.com"));
        assertFalse(Validator.validateEmail("test@examlecom"));
    }

    @Test
    public void testValidatePresenceOf() {
        assertTrue(Validator.validatePresenceOf("test"));
        assertFalse(Validator.validatePresenceOf(""));
    }
}
