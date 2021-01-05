
package Model;

public class TipoCliente {
    private int idtipo;
    private String tipo;
    private int descuento;

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    //CONSTRUCTORES
    public TipoCliente(int idtipo, String tipo, int descuento) {
        this.idtipo = idtipo;
        this.tipo = tipo;
        this.descuento = descuento;       
    }
    //
    public TipoCliente(int idTipo){
        this.idtipo = idTipo;
    }
    //listado
    public TipoCliente(int idtipo, String tipo) {
        this.idtipo = idtipo;
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return tipo+", descuento de: "+ descuento +"%";
    }
    
    
}
