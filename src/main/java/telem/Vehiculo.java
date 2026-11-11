
package telem;

import org.json.JSONObject;

public abstract class Vehiculo {

    private String matricula;
    private Categoria categoria;
    private int annio;
    private boolean disponible;

    public Vehiculo(String matricula, Categoria categoria, int annio) {
        this.matricula = matricula;
        this.categoria = categoria;
        this.annio = annio;
    }
    public Vehiculo(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        this.matricula = json.getString("matricula");
        this.categoria = Categoria.valueOf(json.getString("categoria"));
        this.annio = json.getInt("annio");
        this.disponible = json.getBoolean("disponible");
    }

    public String getMatricula() {
        return matricula;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getAnnio() {
        return annio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public abstract String getTipo();

    public abstract int getPrecioBase();

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
