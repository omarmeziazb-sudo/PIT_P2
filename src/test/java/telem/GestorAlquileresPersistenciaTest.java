
package telem;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;

public class GestorAlquileresPersistenciaTest {

    @Test
    public void testGuardarDatosJSON() throws Exception {
        GestorAlquileres gestor = new GestorAlquileres();
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        Vehiculo vehiculo = new Turismo("TEST123", Categoria.NORMAL, 2026, 5, 400.0);
        Alquiler alquiler = new Alquiler(vehiculo, cliente, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        gestor.agregarAlquiler(alquiler);

        File fichero = File.createTempFile("alquileres", ".json");

        try {
            gestor.guardarDatos(fichero.getAbsolutePath());

            String contenido = new String(Files.readAllBytes(fichero.toPath()), StandardCharsets.UTF_8);
            JSONObject datos = new JSONObject(contenido);

            assertEquals(6, datos.getJSONArray("clientes").length());
            assertEquals(9, datos.getJSONArray("vehiculos").length());
            assertEquals(1, datos.getJSONArray("alquileres").length());
        } finally {
            fichero.delete();
        }
    }

    @Test
    public void testRecuperarDatosJSON() throws Exception {
        GestorAlquileres original = new GestorAlquileres();
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        Vehiculo vehiculo = new Turismo("TEST123", Categoria.NORMAL, 2026, 5, 400.0);
        Alquiler alquiler = new Alquiler(vehiculo, cliente, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        original.agregarAlquiler(alquiler);

        File ficheroOriginal = File.createTempFile("origen", ".json");
        File ficheroRecuperado = File.createTempFile("recuperado", ".json");

        try {
            original.guardarDatos(ficheroOriginal.getAbsolutePath());

            GestorAlquileres recuperado = new GestorAlquileres();
            recuperado.cargarDatos(ficheroOriginal.getAbsolutePath());

            Alquiler[] alquileres = recuperado.obtenerAlquileres();
            int contador = 0;

            for (Alquiler a : alquileres) {
                if (a != null) {
                    contador++;
                    assertEquals("12345678A", a.getCliente().getDni());
                    assertEquals("TEST123", a.getVehiculo().getMatricula());
                    assertEquals(LocalDate.of(2026, 11, 10), a.getFechaInicio());
                    assertEquals(LocalDate.of(2026, 11, 12), a.getFechaFin());
                }
            }

            assertEquals(1, contador);

            recuperado.guardarDatos(ficheroRecuperado.getAbsolutePath());

            JSONObject datosOriginales = new JSONObject(new String(Files.readAllBytes(ficheroOriginal.toPath()), StandardCharsets.UTF_8));
            JSONObject datosRecuperados = new JSONObject(new String(Files.readAllBytes(ficheroRecuperado.toPath()), StandardCharsets.UTF_8));

            assertTrue(datosOriginales.similar(datosRecuperados));
        } finally {
            ficheroOriginal.delete();
            ficheroRecuperado.delete();
        }
    }

    @Test(expected = IOException.class)
    public void testRecuperarFicheroInexistente() throws Exception {
        File fichero = File.createTempFile("inexistente", ".json");
        fichero.delete();
        new GestorAlquileres().cargarDatos(fichero.getAbsolutePath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGuardarNombreVacio() throws Exception {
        new GestorAlquileres().guardarDatos("");
    }
}
