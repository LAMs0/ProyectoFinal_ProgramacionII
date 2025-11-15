/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

import proyectofinal.model.Autor;
import proyectofinal.util.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author venga
 */
public class AutorDao {
    
    public boolean insertar(Autor a){
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getNacionalidad());
            return ps.executeUpdate()> 0 ;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean actualizar(Autor a) {
        String sql = "UPDATE autores SET nombre=?, nacionalidad=? WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getNacionalidad());
            ps.setInt(3, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM autores WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Autor> listar(String filtro) {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, nacionalidad FROM autores WHERE nombre LIKE ? ORDER BY id";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (filtro == null ? "" : filtro) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Autor(rs.getInt("id"), rs.getString("nombre"), rs.getString("nacionalidad")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
