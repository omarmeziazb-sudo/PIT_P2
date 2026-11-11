
package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class EstrategiaPrecioTest {

    private Vehiculo crearVehiculo() {
        return new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
    }

    private LocalDate fechaInicio() {
        return LocalDate.of(2026, 11, 10);
    }

    @Test
    public void testPrecioNormal() {
        assertEquals(150, new CalculadorPrecioNormal().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), crearVehiculo()));
    }

    @Test
    public void testTemporadaAlta() {
        assertEquals(300, new CalculadorPrecioTemporadaAlta().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), crearVehiculo()));
    }

    @Test
    public void testOfertonSieteDias() {
        assertEquals(245, new CalculadorPrecioOferton().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(6), crearVehiculo()));
    }

    @Test
    public void testOfertonOchoDias() {
        assertEquals(252, new CalculadorPrecioOferton().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(7), crearVehiculo()));
    }

    @Test
    public void testClienteFrecuenteTresDias() {
        assertEquals(120, new CalculadorPrecioClienteFrecuente().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), crearVehiculo()));
    }

    @Test
    public void testClienteFrecuenteCuatroDias() {
        assertEquals(150, new CalculadorPrecioClienteFrecuente().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(3), crearVehiculo()));
    }

    @Test
    public void testAlquilerSeleccionaPolitica() {
        Vehiculo vehiculo = crearVehiculo();
        Cliente cliente = new Cliente("Juan", "12345678A");
        Alquiler alquiler = new Alquiler(vehiculo, cliente, fechaInicio(), fechaInicio().plusDays(2));
        assertEquals(150, alquiler.calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), PoliticasDePrecios.Normal, vehiculo));
        assertEquals(300, alquiler.calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), PoliticasDePrecios.TemporadaAlta, vehiculo));
        assertEquals(105, alquiler.calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), PoliticasDePrecios.Oferton, vehiculo));
        assertEquals(120, alquiler.calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), PoliticasDePrecios.ClienteFrecuente, vehiculo));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFechaFinalAnteriorAInicial() {
        new CalculadorPrecioNormal().calcularPrecioAlquiler(fechaInicio().plusDays(2), fechaInicio(), crearVehiculo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVehiculoNulo() {
        new CalculadorPrecioNormal().calcularPrecioAlquiler(fechaInicio(), fechaInicio().plusDays(2), null);
    }
}
