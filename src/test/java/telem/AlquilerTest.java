
package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlquilerTest {

    @Test
    public void testConstructorYGetters() {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        LocalDate inicio = LocalDate.of(2026, 11, 10);
        LocalDate fin = LocalDate.of(2026, 11, 12);
        Alquiler alquiler = new Alquiler(vehiculo, cliente, inicio, fin);
        assertSame(vehiculo, alquiler.getVehiculo());
        assertSame(cliente, alquiler.getCliente());
        assertEquals(inicio, alquiler.getFechaInicio());
        assertEquals(fin, alquiler.getFechaFin());
    }

    @Test
    public void testPrecioAlquilerUnDia() {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan", "12345678A");
        Alquiler alquiler = new Alquiler(vehiculo, cliente, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 10));
        int precio = alquiler.calcularPrecioAlquiler(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 10));
        assertEquals(50, precio);
    }

    @Test
    public void testPrecioAlquilerTresDias() {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan", "12345678A");
        Alquiler alquiler = new Alquiler(vehiculo, cliente, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        int precio = alquiler.calcularPrecioAlquiler(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        assertEquals(150, precio);
    }

    @Test
    public void testPrecioAlquilerSieteDias() {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan", "12345678A");
        Alquiler alquiler = new Alquiler(vehiculo, cliente, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 16));
        int precio = alquiler.calcularPrecioAlquiler(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 16));
        assertEquals(350, precio);
    }
}
