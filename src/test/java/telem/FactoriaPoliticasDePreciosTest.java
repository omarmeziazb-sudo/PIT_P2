
package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class FactoriaPoliticasDePreciosTest {

    private Vehiculo crearVehiculo() {
        return new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
    }

    private LocalDate inicio() {
        return LocalDate.of(2026, 11, 10);
    }

    @Test
    public void testCrearPoliticaNormal() {
        assertTrue(FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.Normal) instanceof CalculadorPrecioNormal);
    }

    @Test
    public void testCrearTemporadaAlta() {
        assertTrue(FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.TemporadaAlta) instanceof CalculadorPrecioTemporadaAlta);
    }

    @Test
    public void testCrearOferton() {
        assertTrue(FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.Oferton) instanceof CalculadorPrecioOferton);
    }

    @Test
    public void testCrearClienteFrecuente() {
        assertTrue(FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.ClienteFrecuente) instanceof CalculadorPrecioClienteFrecuente);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoliticaNula() {
        FactoriaPoliticasDePrecios.getCalculadorPrecio(null);
    }

    @Test
    public void testPreciosDeLasCuatroPoliticas() {
        Vehiculo vehiculo = crearVehiculo();
        LocalDate fin = inicio().plusDays(2);
        assertEquals(150, FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.Normal).calcularPrecioAlquiler(inicio(), fin, vehiculo));
        assertEquals(300, FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.TemporadaAlta).calcularPrecioAlquiler(inicio(), fin, vehiculo));
        assertEquals(105, FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.Oferton).calcularPrecioAlquiler(inicio(), fin, vehiculo));
        assertEquals(120, FactoriaPoliticasDePrecios.getCalculadorPrecio(PoliticasDePrecios.ClienteFrecuente).calcularPrecioAlquiler(inicio(), fin, vehiculo));
    }

    @Test
    public void testAlquilerUtilizaLaFactoria() {
        Vehiculo vehiculo = crearVehiculo();
        Cliente cliente = new Cliente("Juan", "12345678A");
        Alquiler alquiler = new Alquiler(vehiculo, cliente, inicio(), inicio().plusDays(2));
        assertEquals(150, alquiler.calcularPrecioAlquiler(inicio(), inicio().plusDays(2), PoliticasDePrecios.Normal, vehiculo));
        assertEquals(300, alquiler.calcularPrecioAlquiler(inicio(), inicio().plusDays(2), PoliticasDePrecios.TemporadaAlta, vehiculo));
    }
}
