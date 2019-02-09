/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p04.vetstucom;

import auxiliares.Auxiliares;
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
        try {
            //instrucción para limitar el log de Hibernate
//        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
//try {
            usuarioActual = vstController.userLogin();
        } catch (VetstucomException ex) {
            System.out.println(ex.getMessage());
        }
        int tipoUsu = usuarioActual.getTipoUsuario();
        int op = 9;

        do {
            menuCompleto();
            op = Auxiliares.pedirNumeroRango("Selecciona una opción = ", 0, 9);
            switch (op) {
                case 1:
                    try {
                        vstController.consultarExpedientes(usuarioActual);
                    } catch (VetstucomException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    try {
                        vstController.altaExpediente(usuarioActual);
                    } catch (VetstucomException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 3:
                    try {
                        vstController.bajaExpediente(usuarioActual);
                    } catch (VetstucomException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 4:
                    try {
                        vstController.editarExpediente(usuarioActual);
                        
                    } catch (VetstucomException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Hasta la vista!");
                    break;
            }
        } while (op != 0);

        /*
            switch(tipoUsu){
                case 0:
                    System.out.println("Usuario Administrador");
                    //int op = 9;
                    do {                        
                        
                    } while (op != 0);
                    break;
                case 1:
                    System.out.println("Usuario Auxiliar");
                    //int op = 9;
                    
                    break;
                case 2:
                    System.out.println("Usuario Veterinario");
                    //int op = 9;
                    do {                        
                        
                    } while (op != 0);
                    break;
                default:
                    break;
            }*/
        //} catch (VetstucomException ex) {
        //System.out.println(ex.getMessage());
        //}
        HibernateUtil.cerrar();
    }

    /* ELIMINAR
    //Función que muestra el menú del Auxiliar
    public static void menuAuxiliar() {
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes");
        System.out.println("0. Salir");
        System.out.println("************");
    }

    //Función que muestra el menú del Veterinario
    public static void menuVeterinario() {
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes");
        System.out.println("2. Alta expedientes");
        System.out.println("3. Baja expedientes");
        System.out.println("4. Editar expedientes");
        System.out.println("0. Salir");
        System.out.println("************");
    }*/

    //Función que muestra el menú del administrador
    public static void menuCompleto() {
        System.out.println("*** MENÚ ***");
        System.out.println("1. Consultar expedientes (Todos)");
        System.out.println("2. Alta expedientes (Veter./Admin.)");
        System.out.println("3. Baja expedientes (Veter./Admin.)");
        System.out.println("4. Editar expedientes (Veter./Admin.)");
        System.out.println("5. Alta usuarios (Admin.)");
        System.out.println("6. Baja usuarios (Admin.)");
        System.out.println("7. Editar usuarios (Admin.)");
        System.out.println("8. Consultar usuarios (Admin.)");
        System.out.println("0. Salir");
        System.out.println("************");
    }

}
