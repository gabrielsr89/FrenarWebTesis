
package Model;

public class Auto {
    private int id_auto;
    private String patente;
    private int id_cliente;
    private int id_modelo;    
    private int id_marca;
    private String marca;
    private String modelo;
    //GET & SET
    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    
    //CONSTRUCTORES
    
    
    public Auto(String patente, int id_modelo) {
        this.patente = patente;
        this.id_modelo = id_modelo;
    }    

    public Auto(int id_auto, String patente, int id_cliente, int id_modelo) {
        this.id_auto = id_auto;
        this.patente = patente;
        this.id_cliente = id_cliente;
        this.id_modelo = id_modelo;
    }    

    public Auto(String patente, int id_cliente, int id_modelo) {
        this.patente = patente;
        this.id_cliente = id_cliente;
        this.id_modelo = id_modelo;
    }
    //para listado por cliente
    public Auto(int id, String patente, String marca, String modelo) {
        this.id_auto = id;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
    }
    //para listar todos los autos de todos los clientes para nuevo presupuesto
    public Auto(int id, String patente, String marca, String modelo, int id_cliente) {
        this.id_auto = id;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.id_cliente = id_cliente;
    }
    //mod auto
    public Auto(int id_auto,String patente,int id_cliente,int id_modelo, int id_marca) {
        this.id_auto = id_auto;
        this.patente = patente;
        this.id_marca = id_marca;
        this.id_modelo = id_modelo;
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return marca + " " + modelo +" " + patente;
    }
    
    
    
    
    
}
