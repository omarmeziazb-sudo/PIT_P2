
package telem;

import java.time.LocalDate;

public class CalculadorPrecioOferton implements ICalculadorPrecio {

    @Override
    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        validarVehiculo(vehiculo);
        int dias = calcularDias(fechaInicio, fechaFin);
        double precioTotal = vehiculo.getPrecioBase() * 0.70 * dias;
        if (dias > 7) {
            precioTotal = precioTotal * 0.90;
        }
        return (int) Math.round(precioTotal);
    }
}
