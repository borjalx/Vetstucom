/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entities.Usuarios;
import exception.VetstucomException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Borja S
 */
public class VetstucomDAO {

    Session sesion;
    Transaction tx;

    public VetstucomDAO() {
        sesion = HibernateUtil.createSessionFactory().openSession();
    }

    //Función que añade un Usuario a la BBDD
    public void insertUsuario(Usuarios u) throws VetstucomException {
        Usuarios aux = getUsuarioByMatricula(u.getNombre());
        if (aux != null) {
            throw new VetstucomException("Ya existe un usuario con esa matricula");
        }
        sesion.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
        sesion.save(u);
        tx.commit();
        sesion.close();
    }

    //--------------------\\
    //-FUNCIONES USUARIOS-||
    //--------------------//
    
    //Función que devuelve el número de usuarios que hay en la BBDD
    
    //Función que devuelve un Usuario a partir de un Id
    public Usuarios getUsuarioById(String id) {
        return (Usuarios) sesion.get(Usuarios.class, id);
    }

    //Función que devuelve un Usuario a partir de un nombre
    public Usuarios getUsuarioByMatricula(String matricula) {
        Usuarios u = new Usuarios();
        sesion = HibernateUtil.createSessionFactory().openSession();
        String sentencia = "FROM Usuarios WHERE matricula = " + matricula;
        Query q = sesion.createQuery(sentencia);
        //List lista = q.list();
        sesion.close();
        //return lista.get(0);
        return u;

    }

}
