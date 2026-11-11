
package telem;

import java.time.LocalDate;

public class CalculadorPrecioClienteFrecuente implements ICalculadorPrecio {

    @Override
    public int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        validarVehiculo(vehiculo);
        int dias = calcularDias(fechaInicio, fechaFin);
        int primerosDias = Math.min(dias, 3);
        int diasRestantes = Math.max(dias - 3, 0);
        double precioTotal = vehiculo.getPrecioBase() * 0.80 * primerosDias;
        precioTotal += vehiculo.getPrecioBase() * 0.60 * diasRestantes;
        return (int) Math.round(precioTotal);
    }
}

