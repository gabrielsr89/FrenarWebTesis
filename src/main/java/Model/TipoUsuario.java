
package Model;

public class TipoUsuario {
    private int id_tipousuario;
    private String usuario;

    public int getId_tipousuario() {
        return id_tipousuario;
    }
    public void setId_tipousuario(int id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    //CONSTRUCTORES
    //TRAER TIPOS DE USUARIOS
    public TipoUsuario(int id_tipousuario, String usuario) {
        this.id_tipousuario = id_tipousuario;
        this.usuario = usuario;
    }
    //PARA USAR EN CLASE USUARIO Y CREAR UN USUARIO NUEVO
    public TipoUsuario(int id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }
    //TO STRING

    @Override
    public String toString() {
        return  usuario ;
    }
    
    
}
