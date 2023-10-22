package org.example.test;

import org.example.datos.Conexion;
import org.example.datos.PersonaJDBC;
import org.example.domain.Persona;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class ManejoPersonas {

    public static void main(String[] args) {



        Connection conexion = null;

        //Obtener la conexion y manejar la excepcion
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            //objeto persona y pasar el objeto conexion del constructor Persona JDBC para transaccion
            PersonaJDBC personaJDBC = new PersonaJDBC(conexion);

            //sentencia Update , crear un objeto y realizar el update
            Persona cambioPersona = new Persona();
            cambioPersona.setId_persona(2);
            cambioPersona.setNombre("Karla Ivonne");
            cambioPersona.setApellido("Gomez");
            cambioPersona.setEmail("karla@hotmail.com");
            cambioPersona.setTelefono("3003778752");

            personaJDBC.update(cambioPersona);

            //Dentro de esta transaccion hacer el insert para una nueva persona
            Persona nuevaPersona = new Persona();
            nuevaPersona.setNombre("Ramiro");
            //Para manejar excepcion ejemplo
            //nuevaPersona.setApellido("Pastor1111111111111111111111111111111111111111111");
            nuevaPersona.setApellido("Riaños");
            nuevaPersona.setEmail("Ramiro@gmail.com");

            personaJDBC.insert(nuevaPersona);

            //si funciona le metodo hacer commit
            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
