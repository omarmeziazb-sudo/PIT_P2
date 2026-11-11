
package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactoriaVehiculosTest {

    @Test
    public void testDeserializarTurismo() {
        Turismo original = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        original.setDisponible(true);
        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(original.toString());
        assertTrue(recuperado instanceof Turismo);
        assertEquals("ABC123", recuperado.getMatricula());
        assertEquals(5, ((Turismo) recuperado).getPlazas());
        assertEquals(400.0, ((Turismo) recuperado).getCapacidadMaletero(), 0.001);
        assertTrue(recuperado.isDisponible());
    }

    @Test
    public void testDeserializarMotocicleta() {
        Motocicleta original = new Motocicleta("MOTO123", Categoria.NORMAL, 2022, 600);
        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(original.toString());
        assertTrue(recuperado instanceof Motocicleta);
        assertEquals(600, ((Motocicleta) recuperado).getCilindrada());
    }

    @Test
    public void testDeserializarIndustrial() {
        VehiculoIndustrial original = new VehiculoIndustrial("IND123", Categoria.NORMAL, 2021, 1000, 3);
        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(original.toString());
        assertTrue(recuperado instanceof VehiculoIndustrial);
        assertEquals(1000, ((VehiculoIndustrial) recuperado).getCapacidadCarga());
        assertEquals(3, ((VehiculoIndustrial) recuperado).getNumeroEjes());
    }

    @Test
    public void testDeserializarCliente() {
        Cliente original = new Cliente("Juan Perez", "12345678A");
        Cliente recuperado = new Cliente(original.toString());
        assertEquals(original.getNombre(), recuperado.getNombre());
        assertEquals(original.getDni(), recuperado.getDni());
    }

    @Test
    public void testDeserializarAlquiler() {
        Turismo turismo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        java.time.LocalDate inicio = java.time.LocalDate.of(2026, 11, 10);
        java.time.LocalDate fin = java.time.LocalDate.of(2026, 11, 12);
        Alquiler original = new Alquiler(turismo, cliente, inicio, fin);
        String json = original.toString();
        Alquiler recuperado = new Alquiler(json);
        assertEquals(cliente.getDni(), recuperado.getCliente().getDni());
        assertEquals(turismo.getMatricula(), recuperado.getVehiculo().getMatricula());
        assertEquals(inicio, recuperado.getFechaInicio());
        assertEquals(fin, recuperado.getFechaFin());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTipoDesconocido() {
        FactoriaVehiculos.getVehiculo("{\"tipo\":\"Avion\"}");
    }
}
