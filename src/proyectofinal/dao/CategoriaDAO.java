/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

/**
 *
 * @author venga
 */

import proyectofinal.model.Categoria;
import proyectofinal.util.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    public boolean insertar(Categoria c) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Categoria c) {
        String sql = "UPDATE categorias SET nombre=? WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Categoria> listar(String filtro) {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM categorias WHERE nombre LIKE ? ORDER BY id";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + (filtro == null ? "" : filtro) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
