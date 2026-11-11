
package telem;

import java.time.LocalDate;

public class EjemploEstrategias {

    public static void main(String[] args) {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        Cliente cliente = new Cliente("Juan Perez", "12345678A");
        LocalDate inicio = LocalDate.of(2026, 11, 10);
        LocalDate fin = LocalDate.of(2026, 11, 17);

        Alquiler alquiler = new Alquiler(vehiculo, cliente, inicio, fin);

        for (PoliticasDePrecios politica : PoliticasDePrecios.values()) {
            int precio = alquiler.calcularPrecioAlquiler(inicio, fin, politica, vehiculo);
            System.out.println(politica + ": " + precio + " euros");
        }
    }
}
