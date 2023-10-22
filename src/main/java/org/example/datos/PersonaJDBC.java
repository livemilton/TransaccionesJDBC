package org.example.datos;


import org.example.domain.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaJDBC {

    //Conexion transaccion, extraer la conexion para usar una transaccion

    private Connection conexionTransaccional;

    //query para definir los llamados a la BBDD con parametros que se van a sustituir ?
    private static final String SQL_SELECT= "SELECT id_persona, nombre, apellido, email, telefono FROM persona";
    private static final String SQL_INSERT= "INSERT INTO persona(nombre,apellido,email,telefono) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE persona SET nombre=?, apellido=?, email=?, telefono=? WHERE id_persona=?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE id_persona=?";

    //Conexion transaccion, constructor vacio y con atributo de la clase
    public PersonaJDBC(){

    }

    public PersonaJDBC(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }

    //METODO PARA MODIFICAR EL REGISTRO EN LA BBDD
    public List<Persona> select() throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        Persona persona = null;
        //Agregar los objetos del rs a la lista
        List<Persona> personas = new ArrayList<Persona>();

        try {

            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs= stmt.executeQuery();
            while(rs.next()){
                int id_persona = rs.getInt("id_persona");
                String nombre= rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                persona = new Persona();
                persona.setId_persona(id_persona);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setEmail(email);
                persona.setTelefono(telefono);
                personas.add(persona);
            }

        }
        finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }

        }
        return personas;
    }

    //METODO PARA INSERTAR PERSONAS
    public int insert(Persona persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        //ResultSet rs = null;
        int rows = 0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4,persona.getTelefono());

            System.out.println("ejecutando query: "+ SQL_INSERT);
            rows= stmt.executeUpdate();
            System.out.println("Registros afectados: "+ rows);

        }

        finally {
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
    return rows;
    }

    //METODO PARA ACTUALIZAR REGISTROS DE LA DB
    //INICIALIZAR VARIABLES Y CONEXION
    public int update(Persona persona) throws SQLException {
        Connection conn =null;
        PreparedStatement stmt = null;
        int rows =0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5,persona.getId_persona());

            rows= stmt.executeUpdate();
            System.out.println("Registros actualizados: "+ rows);


        }
        finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }

        }
        return rows;
    }

    //METODO PARA ELIMINAR DE LA BBDD
    public int delete(Persona persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows=0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("Ejecutando query: "+ SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1,persona.getId_persona());
            rows=stmt.executeUpdate();
            System.out.println("Registros eliminados: "+ rows);


        }
        finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return rows;
    }

}
