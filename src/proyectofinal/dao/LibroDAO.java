/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

/**
 *
 * @author venga
 */
import proyectofinal.model.Libro;
import proyectofinal.util.dbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // ----------------------------------------------------------------------
    // INSERTAR
    // ----------------------------------------------------------------------
    public boolean insertar(Libro l) {
        String sql = "INSERT INTO libros (titulo, autor_id, categoria_id, anio, stock, destacado) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            if (l.getAutorId() > 0) ps.setInt(2, l.getAutorId()); else ps.setNull(2, Types.INTEGER);
            if (l.getCategoriaId() > 0) ps.setInt(3, l.getCategoriaId()); else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, l.getAnio());
            ps.setInt(5, l.getStock());
            ps.setInt(6, l.isDestacado() ? 1 : 0);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // ----------------------------------------------------------------------
    // ACTUALIZAR
    // ----------------------------------------------------------------------
    public boolean actualizar(Libro l) {
        String sql = "UPDATE libros SET titulo=?, autor_id=?, categoria_id=?, anio=?, stock=?, destacado=? WHERE id=?";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            if (l.getAutorId() > 0) ps.setInt(2, l.getAutorId()); else ps.setNull(2, Types.INTEGER);
            if (l.getCategoriaId() > 0) ps.setInt(3, l.getCategoriaId()); else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, l.getAnio());
            ps.setInt(5, l.getStock());
            ps.setInt(6, l.isDestacado() ? 1 : 0);
            ps.setInt(7, l.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // ----------------------------------------------------------------------
    // ELIMINAR
    // ----------------------------------------------------------------------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // ----------------------------------------------------------------------
    // MARCAR / DESMARCAR FAVORITO
    // ----------------------------------------------------------------------
    public boolean marcarFavorito(int id, boolean estado) {
        String sql = "UPDATE libros SET destacado=? WHERE id=?";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, estado ? 1 : 0);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // ----------------------------------------------------------------------
    // LISTAR TODOS LOS LIBROS + FILTRO
    // ----------------------------------------------------------------------
    public List<Libro> listar(String filtro) {
        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT id, titulo, autor_id, categoria_id, anio, stock, destacado "
                   + "FROM libros WHERE titulo LIKE ? ORDER BY id";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + (filtro == null ? "" : filtro) + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Libro l = new Libro();
                    l.setId(rs.getInt("id"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setAutorId(rs.getInt("autor_id"));
                    l.setCategoriaId(rs.getInt("categoria_id"));
                    l.setAnio(rs.getInt("anio"));
                    l.setStock(rs.getInt("stock"));
                    l.setDestacado(rs.getInt("destacado") == 1);

                    lista.add(l);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }


    // ----------------------------------------------------------------------
    // LISTAR ÚNICAMENTE DESTACADOS
    // ----------------------------------------------------------------------
    public List<Libro> listarDestacados() {
        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT id, titulo, autor_id, categoria_id, anio, stock, destacado "
                   + "FROM libros WHERE destacado = 1 ORDER BY id";

        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("id"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutorId(rs.getInt("autor_id"));
                l.setCategoriaId(rs.getInt("categoria_id"));
                l.setAnio(rs.getInt("anio"));
                l.setStock(rs.getInt("stock"));
                l.setDestacado(true);

                lista.add(l);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }
}

