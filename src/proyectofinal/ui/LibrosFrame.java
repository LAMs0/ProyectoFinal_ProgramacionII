/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */
import proyectofinal.model.Libro;
import proyectofinal.service.LibroService;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrosFrame extends JFrame {

    private LibroService libroService = new LibroService();

    private JTable tablaLibros;
    private JTextField txtTitulo, txtAnio, txtStock, txtBuscar;
    private JComboBox<String> cbAutor, cbCategoria;

    public LibrosFrame() {

        setTitle("Gestión de Libros");
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);

        // ------------------------------------------------------------
        // CAMPOS DE FORMULARIO
        // ------------------------------------------------------------
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 20, 100, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(120, 20, 200, 25);
        add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor ID:");
        lblAutor.setBounds(20, 60, 100, 25);
        add(lblAutor);

        cbAutor = new JComboBox<>();
        cbAutor.setBounds(120, 60, 200, 25);
        add(cbAutor);

        JLabel lblCategoria = new JLabel("Categoría ID:");
        lblCategoria.setBounds(20, 100, 100, 25);
        add(lblCategoria);

        cbCategoria = new JComboBox<>();
        cbCategoria.setBounds(120, 100, 200, 25);
        add(cbCategoria);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(20, 140, 100, 25);
        add(lblAnio);

        txtAnio = new JTextField();
        txtAnio.setBounds(120, 140, 200, 25);
        add(txtAnio);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(20, 180, 100, 25);
        add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(120, 180, 200, 25);
        add(txtStock);
                // ------------------------------------------------------------
        // BOTONES CRUD
        // ------------------------------------------------------------
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(350, 20, 120, 30);
        add(btnGuardar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 60, 120, 30);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(350, 100, 120, 30);
        add(btnEliminar);

        // ------------------------------------------------------------
        // BOTÓN FAVORITO (NUEVA FUNCIÓN DEL EXAMEN FINAL)
        // ------------------------------------------------------------
        JButton btnFavorito = new JButton("Marcar / Quitar Favorito");
        btnFavorito.setBounds(500, 20, 200, 30);
        add(btnFavorito);

        // ------------------------------------------------------------
        // BUSQUEDA
        // ------------------------------------------------------------
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(500, 60, 100, 25);
        add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(560, 60, 180, 25);
        add(txtBuscar);

        JButton btnBuscar = new JButton("Filtrar");
        btnBuscar.setBounds(560, 95, 100, 30);
        add(btnBuscar);

        // ------------------------------------------------------------
        // TABLA
        // ------------------------------------------------------------
        tablaLibros = new JTable();
        JScrollPane scroll = new JScrollPane(tablaLibros);
        scroll.setBounds(20, 260, 850, 280);
        add(scroll);

        // ------------------------------------------------------------
        // EVENTOS DE BOTONES
        // ------------------------------------------------------------
        btnGuardar.addActionListener(e -> guardarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscar.addActionListener(e -> cargarTabla());
        btnFavorito.addActionListener(e -> marcarFavorito());

        // Cargar datos iniciales
        cargarTabla();

        setVisible(true);
    }
    // ==============================================================
    // GUARDAR LIBRO
    // ==============================================================
    private void guardarLibro() {
        try {
            Libro l = new Libro();
            l.setTitulo(txtTitulo.getText());
            l.setAutorId(Integer.parseInt(cbAutor.getSelectedItem().toString()));
            l.setCategoriaId(Integer.parseInt(cbCategoria.getSelectedItem().toString()));
            l.setAnio(Integer.parseInt(txtAnio.getText()));
            l.setStock(Integer.parseInt(txtStock.getText()));
            l.setDestacado(false); // nuevos libros no están destacados

            if (libroService.crearLibro(l)) {
                JOptionPane.showMessageDialog(this, "Libro registrado correctamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar libro.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    // ==============================================================
    // ACTUALIZAR LIBRO
    // ==============================================================
    private void actualizarLibro() {
        int fila = tablaLibros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para actualizar.");
            return;
        }

        try {
            Libro l = new Libro();
            l.setId((int) tablaLibros.getValueAt(fila, 0));
            l.setTitulo(txtTitulo.getText());
            l.setAutorId(Integer.parseInt(cbAutor.getSelectedItem().toString()));
            l.setCategoriaId(Integer.parseInt(cbCategoria.getSelectedItem().toString()));
            l.setAnio(Integer.parseInt(txtAnio.getText()));
            l.setStock(Integer.parseInt(txtStock.getText()));

            // recuperar estado actual
            l.setDestacado((boolean) tablaLibros.getValueAt(fila, 6));

            if (libroService.actualizarLibro(l)) {
                JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el libro.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Revise los campos.");
        }
    }

    // ==============================================================
    // ELIMINAR LIBRO
    // ==============================================================
    private void eliminarLibro() {
        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro para eliminar.");
            return;
        }

        int id = (int) tablaLibros.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (libroService.eliminarLibro(id)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el libro.");
            }
        }
    }

    // ==============================================================
    // MARCAR / QUITAR FAVORITO
    // ==============================================================
    private void marcarFavorito() {
        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro.");
            return;
        }

        int id = (int) tablaLibros.getValueAt(fila, 0);
        boolean estadoActual = (boolean) tablaLibros.getValueAt(fila, 6);

        boolean nuevoEstado = !estadoActual;

        if (libroService.marcarFavorito(id, nuevoEstado)) {
            JOptionPane.showMessageDialog(this,
                    nuevoEstado ? "Libro agregado a favoritos." : "Libro removido de favoritos.");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar favorito.");
        }
    }

    // ==============================================================
    // CARGAR TABLA COMPLETA
    // ==============================================================
    private void cargarTabla() {

        List<Libro> lista = libroService.listarLibros(
                txtBuscar != null ? txtBuscar.getText() : ""
        );

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor ID", "Categoría ID", "Año", "Stock", "Favorito"}, 0) {

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 6 ? Boolean.class : String.class;
            }

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
                    l.getStock(),
                    l.isDestacado()
            });
        }

        tablaLibros.setModel(modelo);
    }
}


