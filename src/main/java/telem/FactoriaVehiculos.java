
package telem;

import org.json.JSONObject;

public class FactoriaVehiculos {

    public static Vehiculo getVehiculo(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        String clase = json.getString("tipo");

        switch (clase) {
            case "Turismo":
                return new Turismo(jsonString);
            case "Motocicleta":
                return new Motocicleta(jsonString);
            case "VehiculoIndustrial":
                return new VehiculoIndustrial(jsonString);
            default:
                throw new IllegalArgumentException("Tipo de vehículo desconocido: " + clase);
        }
    }

    public static void main(String[] args) {
        Vehiculo original = new Turismo("1234ABC", Categoria.NORMAL, 2020, 5, 450.0);
        original.setDisponible(true);
        String serializado = original.toString();
        System.out.println("JSON original: " + serializado);
        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(serializado);
        System.out.println("Vehículo recuperado: " + recuperado);
        System.out.println("Tipo recuperado: " + recuperado.getTipo());
    }
}
