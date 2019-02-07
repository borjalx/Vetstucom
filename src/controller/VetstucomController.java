/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auxiliares.Auxiliares;
import entities.Expedientes;
import entities.Usuarios;
import exception.VetstucomException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import persistence.VetstucomDAO;

/**
 *
 * @author Borja S
 */
public class VetstucomController {
    
    public VetstucomDAO vtDAO = new VetstucomDAO();
    
    public boolean isMatriculaRight(String matricula) throws VetstucomException{
        boolean isIt = false;
        //tiene que constar de 4 dígitos
        if(matricula.length() != 4){
            //throw new VetstucomException("[ERROR : La matrícula debe tener 4 dígitos]");
            System.out.println("[ERROR : La matrícula debe tener 4 dígitos]");
        }
        //String nums = matricula.substring(0, 2);
        //String letras = matricula.substring(2, 4);
        //System.out.println("nums = " + nums);
        //System.out.println("letras = " + letras);
        
        Pattern pat = Pattern.compile("^[0-9]{2}[A-Za-z]{2}$");
        Matcher mat = pat.matcher(matricula);
        if (mat.matches()) {
            isIt = true;
        }else{
            //throw new VetstucomException("[ERROR : 2 números y 2 letras (ej. 01BA)]");
            System.out.println("[ERROR : 2 números y 2 letras (ej. 01BA)]");
        }
        
        //if()
        //los dos primeros deben ser números
        //los dos últimos deben ser letras
        
        return isIt;
    }
    
    //Función para solicitar una matrícula
    public String pedirMatricula(String mensaje) throws VetstucomException{
        boolean error = true;
        String matricula = "";
        do {            
            matricula = Auxiliares.pedirCadena(mensaje);
            
            if(isMatriculaRight(matricula)){
                error = false;
            }/*else{
                System.out.println("Matricula erronea");
            }*/
            
        } while (error);
        
        return matricula;
    }
    
    //Función para solicitar los datos para crear un Usuario que devulve un objeto Usuario
    public Usuarios createUser() throws VetstucomException{
        Usuarios user;
        
        String nombre = Auxiliares.pedirCadena("Nombre:");
        String apellidos = Auxiliares.pedirCadena("Apellidos: ");
        String dni = Auxiliares.pedirDNI("DNI: ");
        String matricula = pedirMatricula("Matricula: ");
        String pass = Auxiliares.pedirCadena("Password: ");
        int tipoUsu = Auxiliares.pedirNumeroRango("Tipo usuario (1.Auxiliar | 2.Veterinario)", 1, 2);
        //
        Date today = new Date();
        
        Set<Expedientes> expedientes = new HashSet<Expedientes>();
        
        user = new Usuarios(nombre, apellidos, dni, matricula, pass, tipoUsu, today, expedientes);
        return user;
    }
    
    //Función que devuelve un Usuarios a partir de la simulación de un login
    public Usuarios userLogin() throws VetstucomException{
        System.out.println("*** LOGIN ***");
        //Solicitamos la matricula
        String matricula = pedirMatricula("Matricula : ");
        //Solicitamos la contraseña
        String pass = Auxiliares.pedirCadena("Password : ");
        //Comprobamos si es correcto
        Usuarios u = vtDAO.getUserLogin(matricula, pass);
        
        if(u == null){
            throw new VetstucomException("[ERROR] Login incorrecto. Matricula o Password incorrect@");
        }
        updateUltimoAcceso(u, new Date());
        return u;
        
    }
    
    //Función que consulta todos los expedientes y los muestra por pantalla
    public void consultarExpedientes(Usuarios usuarioActual) throws VetstucomException{
        if(usuarioActual == null){
            throw new VetstucomException("[ERROR] El usuario debe estar logueado");
        }
        System.out.println("*** Consulta de expedientes ***");
        
        List<Expedientes> expedientes = vtDAO.getAllExpedientes();
        
        for (Expedientes expediente : expedientes) {
            System.out.println(expediente);
        }
        
    }
    
    //Función que actualiza el último acceso en la BBDD a partir de un Usuarios y un Date
    public void updateUltimoAcceso(Usuarios usuario, Date date) {
        System.out.println("[INFO] Último acceso actualizado, fecha = " + date); //BORRAR
        usuario.setUltimoAcceso(date);
        vtDAO.updateUser(usuario);
    }
}
