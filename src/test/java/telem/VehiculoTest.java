
package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class VehiculoTest {

    @Test
    public void testTurismo() {
        Turismo turismo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        assertEquals("ABC123", turismo.getMatricula());
        assertEquals(Categoria.NORMAL, turismo.getCategoria());
        assertEquals(2020, turismo.getAnnio());
        assertEquals(5, turismo.getPlazas());
        assertEquals(400.0, turismo.getCapacidadMaletero(), 0.001);
        assertEquals("Turismo", turismo.getTipo());
    }

    @Test
    public void testPrecioBaseTurismoNormal() {
        Turismo turismo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        assertEquals(50, turismo.getPrecioBase());
    }

    @Test
    public void testPrecioBaseTurismoEspecial() {
        Turismo turismo = new Turismo("ABC123", Categoria.ESPECIAL, 2020, 5, 400.0);
        assertEquals(80, turismo.getPrecioBase());
    }

    @Test
    public void testPrecioBaseTurismoSuperior() {
        Turismo turismo = new Turismo("ABC123", Categoria.SUPERIOR, 2020, 5, 400.0);
        assertEquals(100, turismo.getPrecioBase());
    }

    @Test
    public void testDisponibilidad() {
        Turismo turismo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        assertFalse(turismo.isDisponible());
        turismo.setDisponible(true);
        assertTrue(turismo.isDisponible());
        turismo.setDisponible(false);
        assertFalse(turismo.isDisponible());
    }

    @Test
    public void testMotocicleta() {
        Motocicleta moto = new Motocicleta("XYZ123", Categoria.NORMAL, 2022, 600);
        assertEquals("Motocicleta", moto.getTipo());
        assertEquals(600, moto.getCilindrada());
        assertEquals("XYZ123", moto.getMatricula());
    }

    @Test
    public void testVehiculoIndustrial() {
        VehiculoIndustrial industrial = new VehiculoIndustrial("DEF456", Categoria.NORMAL, 2021, 1000, 3);
        assertEquals("VehiculoIndustrial", industrial.getTipo());
        assertEquals(1000, industrial.getCapacidadCarga());
        assertEquals(3, industrial.getNumeroEjes());
        assertEquals("DEF456", industrial.getMatricula());
    }
}
