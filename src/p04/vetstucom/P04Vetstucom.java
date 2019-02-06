/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p04.vetstucom;

import controller.VetstucomController;
import entities.Usuarios;
import exception.VetstucomException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.HibernateUtil;

/**
 *
 * @author Borja S
 */
public class P04Vetstucom {

    private static Usuarios usuarioActual;
    private static VetstucomController vstController = new VetstucomController();
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //instrucción para limitar el log de Hibernate
//        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        try {
            usuarioActual = vstController.userLogin();
            System.out.println("Usuario tipo = " + usuarioActual.getTipoUsuario());
            int tipoUsu = usuarioActual.getTipoUsuario();
            switch(tipoUsu){
                case 0:
                    System.out.println("Usuario Administrador");
                    //int op = 9;
                    break;
                case 1:
                    System.out.println("Usuario Auxiliar");
                    //int op = 9;
                    break;
                case 2:
                    System.out.println("Usuario Veterinario");
                    //int op = 9;
                    break;
                default:
                    break;
            }
            
        } catch (VetstucomException ex) {
            System.out.println(ex.getMessage());
        }
        HibernateUtil.cerrar();
    }
    
    //Función que muestra el menú del Auxiliar
    public void menuAuxiliar(){
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes");
        System.out.println("0. Salir");
        System.out.println("************");
    }
    //Función que muestra el menú del Veterinario
    public void menuVeterinario(){
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes");
        System.out.println("2. Alta expedientes");
        System.out.println("3. Baja expedientes");
        System.out.println("4. Editar expedientes");
        System.out.println("0. Salir");
        System.out.println("************");
    }
    //Función que muestra el menú del administrador
    public void menuAdministrador(){
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes");
        System.out.println("2. Alta expedientes");
        System.out.println("3. Baja expedientes");
        System.out.println("4. Editar expedientes");
        System.out.println("5. Alta usuarios");
        System.out.println("6. Baja usuarios");
        System.out.println("7. Editar usuarios");
        System.out.println("8. Consultar usuarios");
        System.out.println("0. Salir");
        System.out.println("************");
    }
    
}
