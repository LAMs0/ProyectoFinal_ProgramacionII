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
import proyectofinal.model.Autor;
import proyectofinal.model.Categoria;
import proyectofinal.service.LibroService;
import proyectofinal.service.AutorService;
import proyectofinal.service.CategoriaService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrosFrame extends JFrame {
    private LibroService service = new LibroService();
    private AutorService autorService = new AutorService();
    private CategoriaService categoriaService = new CategoriaService();

    private JTextField txtId = new JTextField(5);
    private JTextField txtTitulo = new JTextField(20);
    private JComboBox<ComboItem> cmbAutor = new JComboBox<>();
    private JComboBox<ComboItem> cmbCategoria = new JComboBox<>();
    private JTextField txtAnio = new JTextField(5);
    private JTextField txtStock = new JTextField(5);
    private JTextField txtFiltro = new JTextField(15);

    private JButton btnNuevo = new JButton("Nuevo");
    private JButton btnGuardar = new JButton("Guardar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnListar = new JButton("Listar");

    private JTable tbl = new JTable();
    private DefaultTableModel model;

    public LibrosFrame() {
        setTitle("CRUD Libros");
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("ID:"), gbc);
        txtId.setEditable(false); gbc.gridx = 1; form.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; form.add(txtTitulo, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1; form.add(cmbAutor, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1; form.add(cmbCategoria, gbc);
        gbc.gridx = 0; gbc.gridy = 4; form.add(new JLabel("Año:"), gbc);
        gbc.gridx = 1; form.add(txtAnio, gbc);
        gbc.gridx = 0; gbc.gridy = 5; form.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1; form.add(txtStock, gbc);

        JPanel actions = new JPanel();
        actions.add(btnNuevo); actions.add(btnGuardar); actions.add(btnModificar); actions.add(btnEliminar);

        JPanel top = new JPanel(new BorderLayout());
        JPanel filtroPanel = new JPanel();
        filtroPanel.add(new JLabel("Buscar:")); filtroPanel.add(txtFiltro); filtroPanel.add(btnListar);

        top.add(form, BorderLayout.CENTER);
        top.add(actions, BorderLayout.SOUTH);
        top.add(filtroPanel, BorderLayout.NORTH);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Título","AutorID","CategoriaID","Año","Stock"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tbl.setModel(model);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> listar());

        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbl.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(model.getValueAt(row, 0).toString());
                    txtTitulo.setText(model.getValueAt(row, 1).toString());
                    selectComboByValue(cmbAutor, Integer.parseInt(model.getValueAt(row,2).toString()));
                    selectComboByValue(cmbCategoria, Integer.parseInt(model.getValueAt(row,3).toString()));
                    txtAnio.setText(model.getValueAt(row,4).toString());
                    txtStock.setText(model.getValueAt(row,5).toString());
                }
            }
        });

        cargarCombos();
        listar();

        setSize(900,450);
        setLocationRelativeTo(null);
    }

    private void cargarCombos() {
        cmbAutor.removeAllItems();
        cmbCategoria.removeAllItems();
        List<Autor> autores = autorService.listarAutores("");
        for (Autor a : autores) cmbAutor.addItem(new ComboItem(a.getId(), a.getNombre()));
        List<Categoria> cats = categoriaService.listarCategorias("");
        for (Categoria c : cats) cmbCategoria.addItem(new ComboItem(c.getId(), c.getNombre()));
    }

    private void selectComboByValue(JComboBox<ComboItem> combo, int value) {
        for (int i=0;i<combo.getItemCount();i++) {
            if (combo.getItemAt(i).getId() == value) {
                combo.setSelectedIndex(i); return;
            }
        }
    }

    private void limpiar() {
        txtId.setText(""); txtTitulo.setText(""); txtAnio.setText(""); txtStock.setText("");
        if (cmbAutor.getItemCount()>0) cmbAutor.setSelectedIndex(0);
        if (cmbCategoria.getItemCount()>0) cmbCategoria.setSelectedIndex(0);
    }

    private void guardar() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) { JOptionPane.showMessageDialog(this, "Título obligatorio"); return; }
        int autorId = cmbAutor.getSelectedItem() == null ? 0 : ((ComboItem)cmbAutor.getSelectedItem()).getId();
        int catId = cmbCategoria.getSelectedItem() == null ? 0 : ((ComboItem)cmbCategoria.getSelectedItem()).getId();
        int anio = 0, stock = 0;
        try { anio = Integer.parseInt(txtAnio.getText().trim().isEmpty() ? "0" : txtAnio.getText().trim()); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Año inválido"); return; }
        try { stock = Integer.parseInt(txtStock.getText().trim().isEmpty() ? "0" : txtStock.getText().trim()); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Stock inválido"); return; }
        Libro l = new Libro();
        l.setTitulo(titulo); l.setAutorId(autorId); l.setCategoriaId(catId); l.setAnio(anio); l.setStock(stock);
        if (service.crearLibro(l)) { JOptionPane.showMessageDialog(this, "Libro guardado"); listar(); limpiar(); } else JOptionPane.showMessageDialog(this, "Error al guardar");
    }

    private void modificar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione un libro"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) { JOptionPane.showMessageDialog(this, "Título obligatorio"); return; }
        int autorId = cmbAutor.getSelectedItem() == null ? 0 : ((ComboItem)cmbAutor.getSelectedItem()).getId();
        int catId = cmbCategoria.getSelectedItem() == null ? 0 : ((ComboItem)cmbCategoria.getSelectedItem()).getId();
        int anio = 0, stock = 0;
        try { anio = Integer.parseInt(txtAnio.getText().trim().isEmpty() ? "0" : txtAnio.getText().trim()); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Año inválido"); return; }
        try { stock = Integer.parseInt(txtStock.getText().trim().isEmpty() ? "0" : txtStock.getText().trim()); } catch (NumberFormatException e) { JOptionPane.showMessageDialog(this, "Stock inválido"); return; }
        Libro l = new Libro(id, titulo, autorId, catId, anio, stock);
        if (service.actualizarLibro(l)) { JOptionPane.showMessageDialog(this, "Libro actualizado"); listar(); limpiar(); } else JOptionPane.showMessageDialog(this, "Error al actualizar");
    }

    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione un libro"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        int r = JOptionPane.showConfirmDialog(this, "¿Confirmar eliminación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (service.eliminarLibro(id)) { JOptionPane.showMessageDialog(this, "Libro eliminado"); listar(); limpiar(); } else JOptionPane.showMessageDialog(this, "Error al eliminar");
        }
    }

    private void listar() {
        String filtro = txtFiltro.getText().trim();
        List<Libro> lista = service.listarLibros(filtro);
        model.setRowCount(0);
        for (Libro l : lista) {
            model.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutorId(), l.getCategoriaId(), l.getAnio(), l.getStock()});
        }
    }

    // helper para combo
    private static class ComboItem {
        private int id; private String value;
        public ComboItem(int id, String value) { this.id = id; this.value = value; }
        public int getId() { return id; }
        public String toString() { return value; }
    }
}
