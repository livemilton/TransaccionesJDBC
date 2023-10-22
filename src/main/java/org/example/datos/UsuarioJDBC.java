package org.example.datos;

import org.example.domain.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioJDBC {

    //Conexion transaccion, extraer la conexion para usar una transaccion
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT id_usuario, username, password FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?,?)";
    private static  final String SQL_UPDATE = "UPDATE usuario SET username=?, password=? WHERE id_usuario=?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=?";

    //Conexion transaccion, constructor vacio y con atributo de la clase

    public UsuarioJDBC(){}

    public UsuarioJDBC(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }



    //METODO PARA QUERY SELECT
    public List<Usuario> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt= null;
        ResultSet rs= null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs=stmt.executeQuery();
            while(rs.next()){
                int id_usuario = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String password = rs.getString("password");

                usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setUsername(username);
                usuario.setPassword(password);

                usuarios.add(usuario);
            }



        }finally {
            Conexion.close(stmt);
            Conexion.close(rs);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return usuarios;
    }

    //METODO PARA QUERY INSERT

    public int insert(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;


            try {
                //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
                conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setString(1,usuario.getUsername());
                stmt.setString(2, usuario.getPassword());

                System.out.println("ejecutando query: "+ SQL_INSERT);
                rows = stmt.executeUpdate();
                System.out.println("Registros afectados: "+ rows);


            }finally {
                Conexion.close(stmt);
                //validacion para cerrar la transaccion
                if (this.conexionTransaccional == null){
                    Conexion.close(conn);
                }
            }
            return rows;
    }

    //METODO PARA QUERY UPDATE
    public int update(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt= null;
        int rows =0;


        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("ejecutando quey: "+ SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3,usuario.getId_usuario());

            rows = stmt.executeUpdate();
            System.out.println("Registros actualizado: "+ rows);


        }finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return rows;

    }

    //METODO PARA QUERY DELETE
    public int delete(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt =null;
        int rows =0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("Ejecutando Query: " + SQL_DELETE);
            stmt= conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getId_usuario());
            rows = stmt.executeUpdate();
            System.out.println("Registros elimiandos: " + rows);



        }finally {
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
    return rows;
    }

}
