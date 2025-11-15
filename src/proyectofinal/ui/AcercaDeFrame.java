/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */
import proyectofinal.dao.AcercaDeDAO;
import proyectofinal.model.AcercaDe;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.sql.Date;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AcercaDeFrame extends JFrame {
    private JTextField txtCarne = new JTextField(20);
    private JTextField txtNombres = new JTextField(30);
    private JTextField txtNumeroCarne = new JTextField(20);
    private JButton btnCargar = new JButton("Cargar foto");
    private JButton btnGuardar = new JButton("Guardar en BD");
    private JLabel lblFoto = new JLabel();
    private String fotoPath = null;
    private AcercaDeDAO dao = new AcercaDeDAO();
    private AcercaDe current = null;

    public AcercaDeFrame() {
        setTitle("Acerca de");
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; top.add(new JLabel("Carné:"), gbc);
        gbc.gridx = 1; top.add(txtCarne, gbc);
        gbc.gridx = 0; gbc.gridy = 1; top.add(new JLabel("Nombres:"), gbc);
        gbc.gridx = 1; top.add(txtNombres, gbc);
        gbc.gridx = 0; gbc.gridy = 2; top.add(new JLabel("Número carné:"), gbc);
        gbc.gridx = 1; top.add(txtNumeroCarne, gbc);
        gbc.gridx = 0; gbc.gridy = 3; top.add(btnCargar, gbc);
        gbc.gridx = 1; top.add(btnGuardar, gbc);
        add(top, BorderLayout.NORTH);

        lblFoto.setPreferredSize(new Dimension(220,220));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(lblFoto, BorderLayout.CENTER);

        btnCargar.addActionListener(e -> seleccionarFoto());
        btnGuardar.addActionListener(e -> guardarEnBD());

        cargarDesdeBD();

        pack();
        setLocationRelativeTo(null);
    }

    private void seleccionarFoto() {
        JFileChooser fc = new JFileChooser();
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                Path destDir = Paths.get("images");
                if (!Files.exists(destDir)) Files.createDirectories(destDir);
                Path dest = destDir.resolve(System.currentTimeMillis() + "_" + f.getName());
                Files.copy(f.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                fotoPath = dest.toString();
                mostrarImagen(fotoPath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void mostrarImagen(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
            lblFoto.setIcon(icon);
        } catch (IOException ex) {
            lblFoto.setText("No se puede cargar imagen");
            ex.printStackTrace();
        }
    }

    private void guardarEnBD() {
        String carne = txtCarne.getText().trim();
        String nombres = txtNombres.getText().trim();
        String numero = txtNumeroCarne.getText().trim();
        if (carne.isEmpty() || nombres.isEmpty()) { JOptionPane.showMessageDialog(this, "Carné y nombres son obligatorios"); return; }
        try {
            if (current == null) {
                AcercaDe a = new AcercaDe();
                a.setCarne(carne);
                a.setNombres(nombres);
                a.setNumeroCarne(numero);
                a.setFotoPath(fotoPath);
                a.setProyecto("Proyecto Final Programación II");
                a.setVersion("1.0");
                a.setFecha(new Date(System.currentTimeMillis()));
                if (dao.insertar(a)) {
                    JOptionPane.showMessageDialog(this, "Información guardada");
                    cargarDesdeBD();
                } else JOptionPane.showMessageDialog(this, "Error al guardar");
            } else {
                current.setCarne(carne);
                current.setNombres(nombres);
                current.setNumeroCarne(numero);
                current.setFotoPath(fotoPath);
                current.setProyecto("Proyecto Final Programación II");
                current.setVersion("1.0");
                current.setFecha(new Date(System.currentTimeMillis()));
                if (dao.actualizar(current)) {
                    JOptionPane.showMessageDialog(this, "Información actualizada");
                    cargarDesdeBD();
                } else JOptionPane.showMessageDialog(this, "Error al actualizar");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void cargarDesdeBD() {
        current = dao.obtenerPrimero();
        if (current != null) {
            txtCarne.setText(current.getCarne());
            txtNombres.setText(current.getNombres());
            txtNumeroCarne.setText(current.getNumeroCarne());
            fotoPath = current.getFotoPath();
            if (fotoPath != null && !fotoPath.trim().isEmpty()) mostrarImagen(fotoPath);
        }
    }
}
