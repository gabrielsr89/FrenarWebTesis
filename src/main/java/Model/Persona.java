
package Model;

public class Persona {
    
    private int id;
    private String nombre;
    private String apellido;   
    private String telefono;
    private String tel_alternativo;
    private String email;
    private String pass;  
    
    private String domicilio;
    private String cp;   
    private Barrio barrio;
    private Localidad localidad;
    private Provincia provincia;


    //GET AND SET 
    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
    public Localidad getLocalidad() {
        return localidad;
    }
    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }
    public Provincia getProvincia() {
        return provincia;
    }
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    } 
     public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
     public Barrio getBarrio() {
        return barrio;
    }
    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }   
    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getTel_alternativo() {
        return tel_alternativo;
    }
    public void setTel_alternativo(String tel_alternativo) {
        this.tel_alternativo = tel_alternativo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    //CONSTRUCTORES
    //PARA PROVEEDORES
    public Persona(String telefono,             
            String email, 
            String domicilio, 
            String cp, 
            Barrio barrio, 
            Localidad localidad, 
            Provincia provincia) {
        this.telefono = telefono;       
        this.email = email;
        this.domicilio = domicilio;
        this.cp = cp;
        this.barrio = barrio;
        this.localidad = localidad;
        this.provincia = provincia;
    }
    
    
    public Persona(int idP, String nombre, String apellido, String email, String pass) {
        this.id = idP;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.pass = pass;
    }
    //CONSTRUCTOR PARA LISTAR BARRIOS NUEVOS
    public Persona(int idPersona, String nombre, String apellido, String email, String telefono, String telefonoAlter, Provincia prov, Localidad l, Barrio b) {
        this.id = idPersona;
        this.nombre= nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.tel_alternativo = telefonoAlter;
        this.provincia = prov;
        this.localidad = l;
        this.barrio = b;
    }
    
    //CREAR UNA PERSONA    
    public Persona(String nombre, String apellido,  String email, String pass, String telefono, String tel_alternativo, int id_barrio, String domicilio,String cp) {
        this.barrio = new Barrio(id_barrio);
        this.nombre = nombre;
        this.apellido = apellido;        
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.tel_alternativo = tel_alternativo;
        this.email = email;
        this.pass = pass;
        this.cp = cp;
    }
    //MODIFICAR
     public Persona(String nombre, String apellido,  String email, String pass, String telefono, String tel_alternativo,int id_provincia, int id_localidad, int id_barrio, String domicilio,String cp) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.barrio = new Barrio(id_barrio);
        this.localidad = new Localidad(id_localidad);
        this.provincia = new Provincia(id_provincia);
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.tel_alternativo = tel_alternativo;
        this.email = email;
        this.pass = pass;
    }
    //TRAER UNA PERSONA
    public Persona(Localidad localidad, 
            Provincia provincia, 
            Barrio barrio, 
            int id,
            String nombre, 
            String apellido, 
            String domicilio, 
            String telefono, 
            String tel_alternativo, 
            String email, 
            String pass,
            String cp) {
        this.localidad = localidad;
        this.provincia = provincia;
        this.barrio = barrio;
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;        
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.tel_alternativo = tel_alternativo;
        this.email = email;
        this.pass = pass;
        this.cp = cp;
       
    }
    //para listados de usuarios (ct,operadores,administradores)
    public Persona(int id_p, String nombre, String apellido, String email, String pass, String telefono, String telAlter){
        this.id = id_p;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.pass = pass;
        this.telefono = telefono;
        this.tel_alternativo = telAlter;    
    }
    public Persona(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    }
    //para hackear a la nasa y crear un tipo de usuario
    public Persona(int id) {
        this.id = id;
    }    
    //PARA RECUPERACIÓN DE PW
    public Persona(String pw, String nombre, String apellido){
        this.pass = pw;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    //MÉTODOS
    @Override
    public String toString() {
        return nombre + " " + apellido ;
    }
    
}
