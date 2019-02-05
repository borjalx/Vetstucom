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
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Borja S
 */
public class VetstucomController {
    
    public boolean isMatriculaRight(String matricula) throws VetstucomException{
        boolean isIt = false;
        //tiene que constar de 4 dígitos
        if(matricula.length() != 4){
            throw new VetstucomException("[ERROR : La matrícula debe tener 4 dígitos]");
        }
        String nums = matricula.substring(0, 1);
        String letras = matricula.substring(2, 3);
        System.out.println("nums = " + nums);
        System.out.println("letras =" + letras);
        
        Pattern pat = Pattern.compile("^[0-9]{2}[A-Za-z]{2}$");
        Matcher mat = pat.matcher(matricula);
        if (mat.matches()) {
            isIt = true;
        }else{
            throw new VetstucomException("[ERROR : 2 números y 2 letras (ej. 01BA)]");
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
            }else{
                System.out.println("Matricula erronea");
            }
            
        } while (error);
        
        return matricula;
    }
    
    //Función para solicitar los datos para crear un Usuario que devulve un objeto Usuario
    public Usuarios createUser() throws VetstucomException{
        Usuarios user;
        /*ID int(11) AI PK 
NOMBRE varchar(25) 
APELLIDOS varchar(25) 
DNI varchar(12) 
MATRICULA varchar(6) 
PASS varchar(8) 
TIPO_USUARIO int(11) 
ULTIMO_ACCESO date*/
        //String id = ;
        
        String nombre = Auxiliares.pedirCadena("Nombre:");
        String apellidos = Auxiliares.pedirCadena("Apellidos: ");
        String dni = Auxiliares.pedirDNI("DNI: ");
        String matricula = pedirMatricula("Matricula: ");
        String pass = Auxiliares.pedirCadena("Password: ");
        int tipoUsu = Auxiliares.pedirNumeroRango("Tipo usuario (1.Auxiliar | 2.Veterinario)", 1, 2);
        //
        Date today = new Date();
        //SEGUIR CON EL SET
        Set<Expedientes> expedientes = new Set<Expedientes>();
        
        user = new Usuarios(nombre, apellidos, dni, matricula, pass, tipoUsu, today, expedientes);
        return user;
    }
}
