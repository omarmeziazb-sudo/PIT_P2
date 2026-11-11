
package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void testConstructorYGetters() {
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        assertEquals("Juan Perez", cliente.getNombre());
        assertEquals("12345678A", cliente.getDni());
    }

    @Test
    public void testToString() {
        Cliente cliente = new Cliente("Ana", "87654321B");
        String json = cliente.toString();
        assertTrue(json.contains("Ana"));
        assertTrue(json.contains("87654321B"));
    }
}
