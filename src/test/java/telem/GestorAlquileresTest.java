
package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class GestorAlquileresTest {

    @Test
    public void testObtenerVehiculosPorTipoYCategoria() {
        GestorAlquileres gestor = new GestorAlquileres();
        Vehiculo[] normales = gestor.obtenerVehiculos("Turismo", Categoria.NORMAL);
        assertEquals(1, normales.length);
        assertEquals("Turismo", normales[0].getTipo());
        assertEquals(Categoria.NORMAL, normales[0].getCategoria());
    }

    @Test
    public void testObtenerTodosLosTurismos() {
        GestorAlquileres gestor = new GestorAlquileres();
        Vehiculo[] turismos = gestor.obtenerVehiculos("Turismo");
        assertEquals(3, turismos.length);
    }

    @Test
    public void testObtenerVehiculosTipoDesconocido() {
        GestorAlquileres gestor = new GestorAlquileres();
        assertEquals(0, gestor.obtenerVehiculos("Avion").length);
    }

    @Test
    public void testObtenerAlquileresDeUnCliente() {
        GestorAlquileres gestor = new GestorAlquileres();
        Cliente cliente1 = new Cliente("Juan", "111");
        Cliente cliente2 = new Cliente("Ana", "222");
        Vehiculo vehiculo1 = new Turismo("AAA111", Categoria.NORMAL, 2024, 5, 400.0);
        Vehiculo vehiculo2 = new Motocicleta("BBB222", Categoria.NORMAL, 2024, 600);
        LocalDate fecha = LocalDate.of(2026, 11, 11);
        gestor.agregarAlquiler(new Alquiler(vehiculo1, cliente1, fecha, fecha));
        gestor.agregarAlquiler(new Alquiler(vehiculo2, cliente2, fecha, fecha));
        assertEquals(1, gestor.obtenerAlquileres(cliente1).length);
        assertEquals("AAA111", gestor.obtenerAlquileres(cliente1)[0].getVehiculo().getMatricula());
        assertEquals(1, gestor.obtenerAlquileres(cliente2).length);
    }

    @Test
    public void testClienteFrecuente() {
        GestorAlquileres gestor = new GestorAlquileres();
        Cliente cliente = new Cliente("Juan", "333");
        LocalDate fecha = LocalDate.of(2026, 11, 11);
        gestor.agregarAlquiler(new Alquiler(new Turismo("AAA111", Categoria.NORMAL, 2024, 5, 400.0), cliente, fecha, fecha));
        gestor.agregarAlquiler(new Alquiler(new Turismo("BBB222", Categoria.NORMAL, 2024, 5, 400.0), cliente, fecha, fecha));
        gestor.agregarAlquiler(new Alquiler(new Turismo("CCC333", Categoria.NORMAL, 2024, 5, 400.0), cliente, fecha, fecha));
        assertTrue(gestor.esClienteFrecuente(cliente, 3));
        assertFalse(gestor.esClienteFrecuente(cliente, 4));
    }

    @Test
    public void testDevolverVehiculo() {
        GestorAlquileres gestor = new GestorAlquileres();
        Cliente cliente = new Cliente("Juan", "444");
        Vehiculo vehiculo = new Turismo("DEV123", Categoria.NORMAL, 2024, 5, 400.0);
        LocalDate fecha = LocalDate.of(2026, 11, 11);
        gestor.agregarAlquiler(new Alquiler(vehiculo, cliente, fecha, fecha));
        assertFalse(vehiculo.isDisponible());
        assertTrue(gestor.devolverVehiculo(cliente, "DEV123"));
        assertTrue(vehiculo.isDisponible());
        assertFalse(gestor.devolverVehiculo(cliente, "DEV123"));
    }

    @Test
    public void testClienteSinAlquileres() {
        GestorAlquileres gestor = new GestorAlquileres();
        Cliente cliente = new Cliente("Pedro", "555");
        assertEquals(0, gestor.obtenerAlquileres(cliente).length);
        assertFalse(gestor.esClienteFrecuente(cliente, 3));
    }
}
