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
import java.util.Objects;
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

    public boolean isMatriculaRight(String matricula) throws VetstucomException {
        boolean isIt = false;
        //tiene que constar de 4 dígitos
        if (matricula.length() != 4) {
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
        } else {
            //throw new VetstucomException("[ERROR : 2 números y 2 letras (ej. 01BA)]");
            System.out.println("[ERROR : 2 números y 2 letras (ej. 01BA)]");
        }

        //if()
        //los dos primeros deben ser números
        //los dos últimos deben ser letras
        return isIt;
    }

    //Función para solicitar una matrícula
    public String pedirMatricula(String mensaje) throws VetstucomException {
        boolean error = true;
        String matricula = "";
        do {
            matricula = Auxiliares.pedirCadena(mensaje);

            if (isMatriculaRight(matricula)) {
                error = false;
            }/*else{
                System.out.println("Matricula erronea");
            }*/

        } while (error);

        return matricula;
    }

    //Función para solicitar los datos para crear un Usuario que devulve un objeto Usuario
    public Usuarios createUser(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() != 0) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }
        Usuarios user;
        System.out.println("*** ALTA USUARIO ***");
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
        Usuarios aux = vtDAO.getUsuarioByMatricula(user.getMatricula());
        //PROBAR DE NUEVO ESTA PARTE - CREO QUE LA HE ARREGLADO PERO TENIA HAMBRE, BON PROFIT
        if (aux != null) {
            throw new VetstucomException("Ya existe un usuario con esa matricula");
        }
        vtDAO.insertUsuario(user);
        return user;
    }

    //Función que elimina un usuario a partir de un Usuarios administrador
    public void bajaUsuario(Usuarios usuario) throws VetstucomException {
        //Es el usuario admin - Ya lo comprobamos en el consultarUsuarios()
        /*if(usuario.getTipoUsuario() != 0){
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }
         */
        //mostramos usuarios
        List<Usuarios> usuarios = vtDAO.getAllUsuariosNoAdmin();
        if (usuarios.size() <= 0) {
            System.out.println("[INFO] - No hay usuarios disponibles para dar de baja");
        } else {
            mostrarUsuarios(usuarios);
            //pedimos que seleccione uno de la lista
            int nUsers = usuarios.size();

            int nUsu = Auxiliares.pedirNumeroRango("Nº posición Usuario: ", 0, nUsers-1);
            //eliminamos el usuario
            vtDAO.deleteUsuarioByID(usuarios.get(nUsu).getId());
            //avisamos a cliente que se ha borrado correctamente
            System.out.println("[INFO] - Usuario dado de baja");
        }
    }

    //Función que muestra los ususarios de un List<Usuarios> y sus posiciones en el List
    public void mostrarUsuarios(List<Usuarios> usuarios) {
        System.out.println("*** USUARIOS ***");
        int n = 0;
        for (Usuarios user : usuarios) {
            System.out.println("Nº usuario = " + n + " > " + user.toString());
            n++;
        }
        System.out.println("****************");
    }

    //Función que consulta los usuariosActuales
    public void consultarUsuarios(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() != 0) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }
        List<Usuarios> usuarios = vtDAO.getAllUsuarios();
        mostrarUsuarios(usuarios);
    }
    
    //Función que modifica un Usuario de la BBDD
    public void editarUsuario(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() != 0) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }

        List<Usuarios> users = vtDAO.getAllUsuarios();
        mostrarUsuarios(users);
        int nUsers = users.size();

        int nUser = Auxiliares.pedirNumeroRango("Nº Usuario: ", 0, nUsers-1);

        Usuarios u = users.get(nUser);
        //Nombre actual, Apellidos actual y pass actual y comprobación
        String nuevoNom = Auxiliares.pedirCadena("(Nombre actual : " + u.getNombre()+ ") Nuevo nombre: ");
        String nuevoApe = Auxiliares.pedirCadena("(Apellidos actuales : " + u.getApellidos()+ ") Nuevos apellidos: ");
        String contra = Auxiliares.pedirCadena("Password actual del usuario a editar : ");
        if(!Objects.equals(contra, u.getPass())){
            System.out.println("contraseña:" + u.getPass() + " |entras:" + contra);
            throw new VetstucomException("[ERROR] - Password erronea");
        }
        String nuevaPass = Auxiliares.pedirCadena("Nueva Password: ");
        String compPass = Auxiliares.pedirCadena("Repetir nueva Password: ");

        if(!Objects.equals(nuevaPass, compPass)){
            throw new VetstucomException("[ERROR] - Error con la nueva contraseña");
        }
        
        u.setNombre(nuevoNom);
        u.setApellidos(nuevoApe);
        u.setPass(nuevaPass);

        vtDAO.updateUser(u);

        System.out.println("[INFO] - Usuario actualizado");
    }

    //Función que devuelve un Usuarios a partir de la simulación de un login
    public Usuarios userLogin() throws VetstucomException {
        System.out.println("*** LOGIN ***");
        //Solicitamos la matricula
        String matricula = pedirMatricula("Matricula : ");
        //Solicitamos la contraseña
        String pass = Auxiliares.pedirCadena("Password : ");
        //Comprobamos si es correcto
        Usuarios u = vtDAO.getUserLogin(matricula, pass);

        if (u == null) {
            throw new VetstucomException("[ERROR] Login incorrecto. Matricula o Password incorrect@");
        }
        updateUltimoAcceso(u, new Date());
        return u;

    }

    //Función que consulta todos los expedientes y los muestra por pantalla
    public void consultarExpedientes(Usuarios usuarioActual) throws VetstucomException {
        if (usuarioActual == null) {
            throw new VetstucomException("[ERROR] El usuario debe estar logueado");
        }
        System.out.println("*** Consulta de expedientes ***");

        mostrarExpedientes(vtDAO.getAllExpedientes());

    }

    //Función que actualiza el último acceso en la BBDD a partir de un Usuarios y un Date
    public void updateUltimoAcceso(Usuarios usuario, Date date) {
        System.out.println("[INFO] Último acceso actualizado, fecha = " + date); //BORRAR
        usuario.setUltimoAcceso(date);
        vtDAO.updateUser(usuario);
    }

    //Función que crea un Expediente y lo inserta en la BBDD
    public void altaExpediente(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() == 1) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }

        Expedientes expediente;

        String nombre = Auxiliares.pedirCadena("Nombre:");
        String apellidos = Auxiliares.pedirCadena("Apellidos: ");
        String dni = Auxiliares.pedirDNI("DNI: ");
        String cp = Auxiliares.pedirCadena("CP: ");
        Date fechaAlta = new Date();
        //String telefono = Auxiliares.pedirNTelefono("Número teléfono: ");
        String telefono = Integer.toString(Auxiliares.pedirNumero("Número de teléfono: "));
        int nMascotas = Auxiliares.pedirNumero("Número de mascotas: ");

        Expedientes exp = new Expedientes(usuario, nombre, apellidos, dni, cp, fechaAlta, telefono, nMascotas);

        vtDAO.insertExpediente(exp);

        System.out.println("[INFO] Expediente dado de alta correctamente");
    }

    //Funcion que muestra todos los expedientes disponibles
    public void mostrarExpedientes(List<Expedientes> expedientes) {
        System.out.println("*** EXPEDIENTES ***");
        //BORRAR - List<Expedientes> expedientes = vtDAO.getAllExpedientes();
        int x = 0;
        for (Expedientes expediente : expedientes) {
            System.out.println("Nº exp = " + x + ">" + expediente);
            x++;
        }
        System.out.println("*******************");
    }

    //Función que da de baja a un usuario
    public void bajaExpediente(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() == 1) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }

        List<Expedientes> expedientes = vtDAO.getAllExpedientes();

        mostrarExpedientes(expedientes);
        int nExpedientes = expedientes.size();

        int nExp = Auxiliares.pedirNumeroRango("Nº posición Expediente: ", 0, nExpedientes-1);

        vtDAO.deleteExpedienteByID(expedientes.get(nExp).getId());

        System.out.println("[INFO] - Expediente dado de baja");
    }

    //Función que modifica un Expediente de la BBDD
    public void editarExpediente(Usuarios usuario) throws VetstucomException {
        if (usuario.getTipoUsuario() == 1) {
            throw new VetstucomException("[ERROR] No tienes permisos para ejecutar esta acción");
        }

        List<Expedientes> exp = vtDAO.getAllExpedientes();
        mostrarExpedientes(exp);
        int nExpedientes = vtDAO.getAllExpedientes().size();

        int nExp = Auxiliares.pedirNumeroRango("Nº Expediente: ", 1, nExpedientes);

        Expedientes e = exp.get(nExp);
        //CP actual, telefono actual y nmascotas actual
        String nuevoCP = Auxiliares.pedirCadena("(CP actual : " + e.getCp() + ") Nuevo CP: ");
        String nuevoTel = Auxiliares.pedirCadena("(Teléfono actual : " + e.getTelefono() + ") Nuevo teléfono: ");
        int nuevoNM = Auxiliares.pedirNumero("(Nº mascotas actual : " + e.getNMascotas() + ") Nuevo nº mascotas: ");

        e.setCp(nuevoCP);
        e.setTelefono(nuevoTel);
        e.setNMascotas(nuevoNM);

        vtDAO.updateExpediente(e);

        System.out.println("[INFO] - Expediente actualizado");
    }

}
