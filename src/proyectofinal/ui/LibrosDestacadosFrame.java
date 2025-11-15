/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */

import proyectofinal.service.LibroService;
import proyectofinal.model.Libro;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrosDestacadosFrame extends JFrame {

    private LibroService libroService = new LibroService();
    private JTable tablaDestacados;

    public LibrosDestacadosFrame() {
        setTitle("Mis Libros Destacados");
        setSize(700, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        // ------------------------------------------------------------
        // TABLA
        // ------------------------------------------------------------
        tablaDestacados = new JTable();
        JScrollPane scroll = new JScrollPane(tablaDestacados);
        scroll.setBounds(20, 20, 650, 250);
        add(scroll);

        // ------------------------------------------------------------
        // BOTÓN QUITAR DE FAVORITOS
        // ------------------------------------------------------------
        JButton btnQuitar = new JButton("Quitar de favoritos");
        btnQuitar.setBounds(20, 290, 180, 30);
        add(btnQuitar);

        btnQuitar.addActionListener(e -> quitarDeFavoritos());

        // Cargar datos al abrir
        cargarTabla();

        setVisible(true);
    }

    // ==============================================================
    // CARGAR SOLO LIBROS DESTACADOS
    // ==============================================================
    private void cargarTabla() {
        List<Libro> lista = libroService.obtenerLibrosDestacados();

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor ID", "Categoría ID", "Año", "Stock"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Libro l : lista) {
            modelo.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutorId(),
                    l.getCategoriaId(),
                    l.getAnio(),
                    l.getStock()
            });
        }

        tablaDestacados.setModel(modelo);
    }

    // ==============================================================
    // QUITAR LIBRO DE FAVORITOS DESDE ESTA VENTANA
    // ==============================================================
    private void quitarDeFavoritos() {
        int fila = tablaDestacados.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro.");
            return;
        }

        int id = (int) tablaDestacados.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Quitar este libro de favoritos?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            if (libroService.marcarFavorito(id, false)) {
                JOptionPane.showMessageDialog(this, "Libro removido de favoritos.");
                cargarTabla(); // refrescar la lista
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el libro.");
            }
        }
    }
}
