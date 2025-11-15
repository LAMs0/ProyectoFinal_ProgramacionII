/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */

import proyectofinal.util.dbConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser = new JTextField(20);
    private JPasswordField txtPass = new JPasswordField(20);
    private JButton btnLogin = new JButton("Ingresar");

    public LoginFrame() {
        setTitle("Login - Proyecto Final");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; add(txtUser, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; add(btnLogin, gbc);

        btnLogin.addActionListener(e -> autenticar());

        pack();
        setLocationRelativeTo(null);
    }

    private void autenticar() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña");
            return;
        }
        try (Connection con = dbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT password_hash, rol, estado FROM usuarios WHERE username = ?")) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password_hash");
                    int estado = rs.getInt("estado");
                    if (estado != 1) {
                        JOptionPane.showMessageDialog(this, "Usuario inactivo");
                        return;
                    }
                    if (hash.equals(sha256(pass))) {
                        JOptionPane.showMessageDialog(this, "Login exitoso");
                        new MainMenuFrame(user).setVisible(true);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no existe");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private String sha256(String base) {
        try{
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
