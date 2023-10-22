package org.example.test;

import org.example.datos.Conexion;
import org.example.datos.UsuarioJDBC;
import org.example.domain.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoUsuarios {

    public static void main(String[] args) {

        Connection conexion = null;

        //Obtener la conexion y manejar la excepcion
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            UsuarioJDBC usuarioJDBC = new UsuarioJDBC();

            //Ejecutando el listado de usuarios
            List<Usuario> usuarios = usuarioJDBC.select();
            for(Usuario usuario: usuarios){
                System.out.println("Usuario:" + usuario);
            }

            //Insertamos un nuevo  usuario

            Usuario usuario= new Usuario("Pepito.Perez","1234");
            usuarioJDBC.insert(usuario);

            //si funciona le metodo hacer commit
            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");

            //Modificar un usuario existente
            /*
            Usuario usuario = new Usuario(3, "Hilda.Muñoz", "1234");
            usuarioJDBC.update(usuario);
            */
            //Eliminar un usuario existente
            //usuarioJDBC.delete(new Usuario(3));



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
