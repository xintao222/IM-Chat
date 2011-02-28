package im.util.test;

import im.util.Hashs;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stanislav Peshterliev
 */
public class HashsTest {
    @Test
    public void testSha1() {
        assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", Hashs.sh1("test"));
    }
}
