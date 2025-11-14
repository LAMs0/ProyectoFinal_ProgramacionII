/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.dao;

/**
 *
 * @author venga
 */
import proyectofinal.model.AcercaDe;
import proyectofinal.util.dbConnection;
import java.sql.*;

public class AcercaDeDAO {

    public AcercaDe obtenerPrimero() {
        String sql = "SELECT id, carne, nombres, numero_carne, foto_path, proyecto, version, fecha FROM acerca_de LIMIT 1";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                AcercaDe a = new AcercaDe();
                a.setId(rs.getInt("id"));
                a.setCarne(rs.getString("carne"));
                a.setNombres(rs.getString("nombres"));
                a.setNumeroCarne(rs.getString("numero_carne"));
                a.setFotoPath(rs.getString("foto_path"));
                a.setProyecto(rs.getString("proyecto"));
                a.setVersion(rs.getString("version"));
                a.setFecha(rs.getDate("fecha"));
                return a;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertar(AcercaDe a) {
        String sql = "INSERT INTO acerca_de (carne, nombres, numero_carne, foto_path, proyecto, version, fecha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getCarne());
            ps.setString(2, a.getNombres());
            ps.setString(3, a.getNumeroCarne());
            ps.setString(4, a.getFotoPath());
            ps.setString(5, a.getProyecto());
            ps.setString(6, a.getVersion());
            ps.setDate(7, a.getFecha());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(AcercaDe a) {
        String sql = "UPDATE acerca_de SET carne=?, nombres=?, numero_carne=?, foto_path=?, proyecto=?, version=?, fecha=? WHERE id=?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getCarne());
            ps.setString(2, a.getNombres());
            ps.setString(3, a.getNumeroCarne());
            ps.setString(4, a.getFotoPath());
            ps.setString(5, a.getProyecto());
            ps.setString(6, a.getVersion());
            ps.setDate(7, a.getFecha());
            ps.setInt(8, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
