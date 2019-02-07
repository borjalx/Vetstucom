package entities;
// Generated Jan 30, 2019 6:38:53 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Expedientes generated by hbm2java
 */
public class Expedientes  implements java.io.Serializable {


     private Integer id;
     private Usuarios usuarios;
     private String nombre;
     private String apellidos;
     private String dni;
     private String cp;
     private Date fechaAlta;
     private String telefono;
     private Integer NMascotas;

    public Expedientes() {
    }

    public Expedientes(Usuarios usuarios, String nombre, String apellidos, String dni, String cp, Date fechaAlta, String telefono, Integer NMascotas) {
       this.usuarios = usuarios;
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.dni = dni;
       this.cp = cp;
       this.fechaAlta = fechaAlta;
       this.telefono = telefono;
       this.NMascotas = NMascotas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Usuarios getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDni() {
        return this.dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getCp() {
        return this.cp;
    }
    
    public void setCp(String cp) {
        this.cp = cp;
    }
    public Date getFechaAlta() {
        return this.fechaAlta;
    }
    
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Integer getNMascotas() {
        return this.NMascotas;
    }
    
    public void setNMascotas(Integer NMascotas) {
        this.NMascotas = NMascotas;
    }

    @Override
    public String toString() {
        return "Expedientes con" + "ID=" + id + " ==> Matrícula usuario =" + usuarios.getMatricula() + ", Nombre=" + nombre + ", Apellidos=" + apellidos + ", DNI =" + dni + ", CP =" + cp + ", Fecha alta=" + fechaAlta + ", Telefono=" + telefono + ", Nº Mascotas=" + NMascotas + '}';
    }

    

}


