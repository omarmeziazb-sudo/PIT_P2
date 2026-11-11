
package telem;

import java.time.LocalDate;

public class FactoriaPoliticasDePrecios {

    public static ICalculadorPrecio getCalculadorPrecio(PoliticasDePrecios politica) {
        if (politica == null) {
            throw new IllegalArgumentException("La política no puede ser null.");
        }

        switch (politica) {
            case Normal:
                return new CalculadorPrecioNormal();
            case TemporadaAlta:
                return new CalculadorPrecioTemporadaAlta();
            case Oferton:
                return new CalculadorPrecioOferton();
            case ClienteFrecuente:
                return new CalculadorPrecioClienteFrecuente();
            default:
                throw new IllegalArgumentException("Política desconocida: " + politica);
        }
    }

    public static void main(String[] args) {
        Vehiculo vehiculo = new Turismo("ABC123", Categoria.NORMAL, 2020, 5, 400.0);
        LocalDate inicio = LocalDate.of(2026, 11, 10);
        LocalDate fin = LocalDate.of(2026, 11, 12);

        for (PoliticasDePrecios politica : PoliticasDePrecios.values()) {
            ICalculadorPrecio calculador = getCalculadorPrecio(politica);
            int precio = calculador.calcularPrecioAlquiler(inicio, fin, vehiculo);
            System.out.println(politica + ": " + precio + " euros");
        }
    }
}
