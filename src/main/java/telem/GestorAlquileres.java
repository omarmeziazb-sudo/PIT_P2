
package telem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class GestorAlquileres {

    private int contadorAlquileres = 0;
    private Alquiler[] alquileres = new Alquiler[1000];
    private Cliente[] clientes = new Cliente[10];
    private Vehiculo[] vehiculos = new Vehiculo[100];

    public GestorAlquileres() {
        inicializarClientes();
        inicializarVehiculos();
    }

    private void inicializarClientes() {
        clientes[0] = new Cliente("Clara Videncia", "001");
        clientes[1] = new Cliente("Aitor Menta", "002");
        clientes[2] = new Cliente("Lola Mento", "003");
        clientes[3] = new Cliente("Armando Guerra", "004");
        clientes[4] = new Cliente("Ana Letica", "005");
        clientes[5] = new Cliente("Carmelo Coton", "006");
    }

    private void inicializarVehiculos() {
        vehiculos[0] = new Turismo("TNA2020", Categoria.NORMAL, 2020, 5, 450.0);
        vehiculos[1] = new Motocicleta("MNA2021", Categoria.NORMAL, 2021, 200);
        vehiculos[2] = new VehiculoIndustrial("CNA2019", Categoria.NORMAL, 2019, 1400, 2);
        vehiculos[3] = new Turismo("TSA2024", Categoria.SUPERIOR, 2024, 5, 550.0);
        vehiculos[4] = new Motocicleta("MSA2021", Categoria.SUPERIOR, 2021, 500);
        vehiculos[5] = new VehiculoIndustrial("CSA2025", Categoria.SUPERIOR, 2025, 2400, 3);
        vehiculos[6] = new Turismo("TEA2026", Categoria.ESPECIAL, 2026, 7, 650.0);
        vehiculos[7] = new Motocicleta("MEA2026", Categoria.ESPECIAL, 2026, 1000);
        vehiculos[8] = new VehiculoIndustrial("CEA2025", Categoria.ESPECIAL, 2025, 4400, 5);

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                vehiculo.setDisponible(true);
            }
        }
    }

    // EJERCICIO 5: PERSISTENCIA JSON

    public void guardarDatos(String fichero) throws IOException {
        if (fichero == null || fichero.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del fichero no puede estar vacío.");
        }

        JSONObject datos = new JSONObject();
        JSONArray datosClientes = new JSONArray();
        JSONArray datosVehiculos = new JSONArray();
        JSONArray datosAlquileres = new JSONArray();

        for (Cliente cliente : clientes) {
            if (cliente != null) {
                datosClientes.put(new JSONObject(cliente.toString()));
            }
        }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                datosVehiculos.put(new JSONObject(vehiculo.toString()));
            }
        }

        for (int i = 0; i < contadorAlquileres; i++) {
            if (alquileres[i] != null) {
                datosAlquileres.put(new JSONObject(alquileres[i].toString()));
            }
        }

        datos.put("clientes", datosClientes);
        datos.put("vehiculos", datosVehiculos);
        datos.put("alquileres", datosAlquileres);

        Path ruta = Paths.get(fichero);
        Path directorio = ruta.toAbsolutePath().getParent();

        if (directorio != null) {
            Files.createDirectories(directorio);
        }

        Files.write(ruta, datos.toString(2).getBytes(StandardCharsets.UTF_8));
    }

    public void cargarDatos(String fichero) throws IOException {
        if (fichero == null || fichero.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del fichero no puede estar vacío.");
        }

        Path ruta = Paths.get(fichero);
        String contenido = new String(Files.readAllBytes(ruta), StandardCharsets.UTF_8);
        JSONObject datos = new JSONObject(contenido);

        JSONArray datosClientes = datos.getJSONArray("clientes");
        JSONArray datosVehiculos = datos.getJSONArray("vehiculos");
        JSONArray datosAlquileres = datos.getJSONArray("alquileres");

        Cliente[] nuevosClientes = new Cliente[Math.max(10, datosClientes.length())];
        Vehiculo[] nuevosVehiculos = new Vehiculo[Math.max(100, datosVehiculos.length())];
        Alquiler[] nuevosAlquileres = new Alquiler[Math.max(1000, datosAlquileres.length())];

        for (int i = 0; i < datosClientes.length(); i++) {
            nuevosClientes[i] = new Cliente(datosClientes.getJSONObject(i).toString());
        }

        for (int i = 0; i < datosVehiculos.length(); i++) {
            nuevosVehiculos[i] = FactoriaVehiculos.getVehiculo(datosVehiculos.getJSONObject(i).toString());
        }

        for (int i = 0; i < datosAlquileres.length(); i++) {
            nuevosAlquileres[i] = new Alquiler(datosAlquileres.getJSONObject(i).toString());
        }

        clientes = nuevosClientes;
        vehiculos = nuevosVehiculos;
        alquileres = nuevosAlquileres;
        contadorAlquileres = datosAlquileres.length();

        for (int i = 0; i < contadorAlquileres; i++) {
            if (alquileres[i] != null && !alquileres[i].getVehiculo().isDisponible()) {
                actualizarDisponibilidadEnTodosLosObjetos(alquileres[i].getVehiculo().getMatricula(), false);
            }
        }
    }

    // EJERCICIO 6: GESTIÓN DE VEHÍCULOS

    public Vehiculo[] obtenerVehiculos(String tipo) {
        return obtenerVehiculos(tipo, null);
    }

    public Vehiculo[] obtenerVehiculos(String tipo, Categoria categoria) {
        if (tipo == null) {
            return new Vehiculo[0];
        }

        List<Vehiculo> resultado = new ArrayList<>();

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null && tipo.equals(vehiculo.getTipo())) {
                if (categoria == null || categoria == vehiculo.getCategoria()) {
                    resultado.add(vehiculo);
                }
            }
        }

        return resultado.toArray(new Vehiculo[0]);
    }

    // GESTIÓN DE ALQUILERES

    public void agregarAlquiler(Alquiler alquiler) {
        if (alquiler == null) {
            throw new IllegalArgumentException("El alquiler no puede ser null.");
        }

        if (contadorAlquileres == alquileres.length) {
            alquileres = Arrays.copyOf(alquileres, alquileres.length * 2);
        }

        alquileres[contadorAlquileres] = alquiler;
        contadorAlquileres++;

        actualizarDisponibilidadEnTodosLosObjetos(alquiler.getVehiculo().getMatricula(), false);
    }

    public Alquiler[] obtenerAlquileres() {
        return Arrays.copyOf(alquileres, contadorAlquileres);
    }

    public Alquiler[] obtenerAlquileres(Cliente cliente) {
        if (cliente == null) {
            return new Alquiler[0];
        }

        List<Alquiler> resultado = new ArrayList<>();

        for (int i = 0; i < contadorAlquileres; i++) {
            Alquiler alquiler = alquileres[i];

            if (alquiler != null && Objects.equals(cliente.getDni(), alquiler.getCliente().getDni())) {
                resultado.add(alquiler);
            }
        }

        return resultado.toArray(new Alquiler[0]);
    }

    boolean esClienteFrecuente(Cliente cliente, int umbral) {
        if (cliente == null || umbral <= 0) {
            return false;
        }

        int numeroAlquileres = obtenerAlquileres(cliente).length;
        return numeroAlquileres >= umbral;
    }

    public boolean devolverVehiculo(Cliente cliente, String matricula) {
        if (cliente == null || matricula == null) {
            return false;
        }

        for (int i = 0; i < contadorAlquileres; i++) {
            Alquiler alquiler = alquileres[i];

            if (alquiler != null && Objects.equals(cliente.getDni(), alquiler.getCliente().getDni()) && matricula.equals(alquiler.getVehiculo().getMatricula()) && !alquiler.getVehiculo().isDisponible()) {
                actualizarDisponibilidadEnTodosLosObjetos(matricula, true);
                return true;
            }
        }

        return false;
    }

    private void actualizarDisponibilidadEnTodosLosObjetos(String matricula, boolean disponible) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null && matricula.equals(vehiculo.getMatricula())) {
                vehiculo.setDisponible(disponible);
            }
        }

        for (int i = 0; i < contadorAlquileres; i++) {
            Alquiler alquiler = alquileres[i];

            if (alquiler != null && matricula.equals(alquiler.getVehiculo().getMatricula())) {
                alquiler.getVehiculo().setDisponible(disponible);
            }
        }
    }

    // INTERFAZ DE USUARIO

    public void menuPrincipal() {
        while (true) {
            System.out.println("Gestión de Alquileres");
            System.out.println("¿Desea salir de la aplicación? (S/N)");

            char salir = KeyBoard.readChar();

            if (salir == 'S' || salir == 's') {
                System.out.println("Hasta pronto.");
                return;
            }

            Cliente cliente = identificarCliente();

            if (cliente == null) {
                System.out.println("Debe identificarse correctamente o salir de la aplicación.");
                continue;
            }

            System.out.println("Sr./Sra. " + cliente.getNombre() + ", introduzca una opción:");
            System.out.println("(1) Seleccionar vehículo para alquilar");
            System.out.println("(2) Entregar un vehículo alquilado");
            System.out.println("(3) Listar su histórico de alquileres");
            System.out.println("(4) Guardar datos en fichero JSON");
            System.out.println("(5) Recuperar datos de fichero JSON");

            int opcion = KeyBoard.readInt();

            switch (opcion) {
                case 1:
                    Vehiculo vehiculo = seleccionarVehiculo(cliente);

                    if (vehiculo == null) {
                        break;
                    }

                    System.out.println("¿Cuántos días desea alquilar el vehículo?");
                    int dias = KeyBoard.readInt();

                    if (dias <= 0) {
                        System.out.println("La duración debe ser de al menos un día.");
                        break;
                    }

                    LocalDate inicio = LocalDate.now();
                    LocalDate fin = inicio.plusDays(dias - 1L);
                    boolean usuarioFrecuente = esClienteFrecuente(cliente, 3);
                    Alquiler nuevoAlquiler = new Alquiler(vehiculo, cliente, inicio, fin);

                    agregarAlquiler(nuevoAlquiler);

                    PoliticasDePrecios politica = usuarioFrecuente ? PoliticasDePrecios.ClienteFrecuente : PoliticasDePrecios.Normal;
                    int precio = nuevoAlquiler.calcularPrecioAlquiler(inicio, fin, politica, vehiculo);

                    System.out.println("Alquiler realizado correctamente.");
                    System.out.println("Política aplicada: " + politica);
                    System.out.println("Precio total: " + precio + " euros");
                    break;

                case 2:
                    devolverVehiculoDesdeMenu(cliente);
                    break;

                case 3:
                    listarAlquileres(cliente);
                    break;

                case 4:
                    System.out.println("Introduzca el nombre o ruta del fichero JSON:");
                    String ficheroGuardar = KeyBoard.readString();

                    try {
                        guardarDatos(ficheroGuardar);
                        System.out.println("Datos guardados correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al guardar los datos: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Introduzca el nombre o ruta del fichero JSON:");
                    String ficheroCargar = KeyBoard.readString();

                    try {
                        cargarDatos(ficheroCargar);
                        System.out.println("Datos recuperados correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al recuperar los datos: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private void devolverVehiculoDesdeMenu(Cliente cliente) {
        Alquiler[] historial = obtenerAlquileres(cliente);
        List<Alquiler> activos = new ArrayList<>();
        Set<String> matriculas = new HashSet<>();

        for (Alquiler alquiler : historial) {
            String matricula = alquiler.getVehiculo().getMatricula();

            if (!alquiler.getVehiculo().isDisponible() && matriculas.add(matricula)) {
                activos.add(alquiler);
            }
        }

        if (activos.isEmpty()) {
            System.out.println("No tiene vehículos pendientes de devolución.");
            return;
        }

        System.out.println("Vehículos pendientes de devolución:");

        for (int i = 0; i < activos.size(); i++) {
            System.out.println(i + " ---> " + activos.get(i));
        }

        System.out.println("Introduzca el número del vehículo que desea devolver:");
        int seleccion = KeyBoard.readInt();

        if (seleccion < 0 || seleccion >= activos.size()) {
            System.out.println("Selección no válida.");
            return;
        }

        String matricula = activos.get(seleccion).getVehiculo().getMatricula();

        if (devolverVehiculo(cliente, matricula)) {
            System.out.println("Vehículo devuelto correctamente.");
        } else {
            System.out.println("No se ha podido registrar la devolución.");
        }
    }

    public Cliente identificarCliente() {
        System.out.println("Bienvenido al Gestor de Alquileres");

        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                System.out.println(i + " ---> " + clientes[i].getNombre() + " - " + clientes[i].getDni());
            }
        }

        System.out.println("Identifíquese con su número de cliente (-1 para salir):");
        int numeroCliente = KeyBoard.readInt();

        if (numeroCliente == -1) {
            return null;
        }

        if (numeroCliente < 0 || numeroCliente >= clientes.length || clientes[numeroCliente] == null) {
            System.out.println("Número de cliente inválido.");
            return null;
        }

        return clientes[numeroCliente];
    }

    public Vehiculo seleccionarVehiculo(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        System.out.println("Estimado/a " + cliente.getNombre());
        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Turismo");
        System.out.println("2. Motocicleta");
        System.out.println("3. Vehículo Industrial");

        int opcionTipo = KeyBoard.readInt();
        String tipo;

        switch (opcionTipo) {
            case 1:
                tipo = "Turismo";
                break;
            case 2:
                tipo = "Motocicleta";
                break;
            case 3:
                tipo = "VehiculoIndustrial";
                break;
            default:
                System.out.println("Tipo de vehículo no válido.");
                return null;
        }

        System.out.println("Seleccione la categoría:");
        System.out.println("1. Normal");
        System.out.println("2. Superior");
        System.out.println("3. Especial");
        System.out.println("4. Cualquiera");

        int opcionCategoria = KeyBoard.readInt();
        Categoria categoria;

        switch (opcionCategoria) {
            case 1:
                categoria = Categoria.NORMAL;
                break;
            case 2:
                categoria = Categoria.SUPERIOR;
                break;
            case 3:
                categoria = Categoria.ESPECIAL;
                break;
            case 4:
                categoria = null;
                break;
            default:
                System.out.println("Categoría no válida.");
                return null;
        }

        Vehiculo[] candidatos = obtenerVehiculos(tipo, categoria);
        List<Vehiculo> disponibles = new ArrayList<>();

        for (Vehiculo vehiculo : candidatos) {
            if (vehiculo.isDisponible()) {
                disponibles.add(vehiculo);
            }
        }

        if (disponibles.isEmpty()) {
            System.out.println("No hay vehículos disponibles para esa selección.");
            return null;
        }

        System.out.println("Vehículos disponibles:");

        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println(i + " ---> " + disponibles.get(i));
        }

        System.out.println("Introduzca el número del vehículo:");
        int seleccion = KeyBoard.readInt();

        if (seleccion < 0 || seleccion >= disponibles.size()) {
            System.out.println("Selección de vehículo no válida.");
            return null;
        }

        return disponibles.get(seleccion);
    }

    public void listarAlquileres() {
        System.out.println("Listado de todos los alquileres");

        for (int i = 0; i < contadorAlquileres; i++) {
            System.out.println(alquileres[i]);
        }

        System.out.println();
    }

    public void listarAlquileres(Cliente cliente) {
        System.out.println("Histórico de alquileres de " + cliente.getNombre());

        Alquiler[] historial = obtenerAlquileres(cliente);

        if (historial.length == 0) {
            System.out.println("Este cliente todavía no tiene alquileres.");
        }

        for (Alquiler alquiler : historial) {
            System.out.println(alquiler);
        }

        System.out.println();
    }

    public void listarVehiculos() {
        System.out.println("Listado de vehículos");

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                System.out.println(vehiculo);
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {
        GestorAlquileres app = new GestorAlquileres();
        app.menuPrincipal();
    }
}
