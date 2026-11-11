
package telem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface ICalculadorPrecio {

    int calcularPrecioAlquiler(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo);

    default int calcularDias(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser null.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial.");
        }
        return Math.toIntExact(ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1);
    }

    default void validarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null.");
        }
    }
}
