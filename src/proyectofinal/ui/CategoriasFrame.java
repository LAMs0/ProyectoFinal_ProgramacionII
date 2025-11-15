/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */

import proyectofinal.model.Categoria;
import proyectofinal.service.CategoriaService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CategoriasFrame extends JFrame {
    private CategoriaService service = new CategoriaService();

    private JTextField txtId = new JTextField(5);
    private JTextField txtNombre = new JTextField(20);
    private JTextField txtFiltro = new JTextField(15);

    private JButton btnNuevo = new JButton("Nuevo");
    private JButton btnGuardar = new JButton("Guardar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnListar = new JButton("Listar");

    private JTable tbl = new JTable();
    private DefaultTableModel model;

    public CategoriasFrame() {
        setTitle("CRUD Categorías");
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtId.setEditable(false);
        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);

        JPanel top = new JPanel(new BorderLayout());
        JPanel actions = new JPanel();
        actions.add(btnNuevo); actions.add(btnGuardar); actions.add(btnModificar); actions.add(btnEliminar);
        JPanel filtroPanel = new JPanel();
        filtroPanel.add(new JLabel("Buscar:")); filtroPanel.add(txtFiltro); filtroPanel.add(btnListar);

        top.add(form, BorderLayout.CENTER);
        top.add(actions, BorderLayout.SOUTH);
        top.add(filtroPanel, BorderLayout.NORTH);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Nombre"}, 0) {
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
                    txtNombre.setText(model.getValueAt(row, 1).toString());
                }
            }
        });

        listar();
        setSize(600,350);
        setLocationRelativeTo(null);
    }

    private void limpiar() {
        txtId.setText(""); txtNombre.setText("");
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Nombre obligatorio"); return; }
        Categoria c = new Categoria();
        c.setNombre(nombre);
        if (service.crearCategoria(c)) {
            JOptionPane.showMessageDialog(this, "Categoría guardada");
            listar();
            limpiar();
        } else JOptionPane.showMessageDialog(this, "Error al guardar");
    }

    private void modificar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione una categoría"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Nombre obligatorio"); return; }
        Categoria c = new Categoria(id, nombre);
        if (service.actualizarCategoria(c)) {
            JOptionPane.showMessageDialog(this, "Categoría actualizada");
            listar();
            limpiar();
        } else JOptionPane.showMessageDialog(this, "Error al actualizar");
    }

    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione una categoría"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        int r = JOptionPane.showConfirmDialog(this, "¿Confirmar eliminación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (service.eliminarCategoria(id)) {
                JOptionPane.showMessageDialog(this, "Categoría eliminada");
                listar();
                limpiar();
            } else JOptionPane.showMessageDialog(this, "Error al eliminar");
        }
    }

    private void listar() {
        String filtro = txtFiltro.getText().trim();
        List<Categoria> lista = service.listarCategorias(filtro);
        model.setRowCount(0);
        for (Categoria c : lista) {
            model.addRow(new Object[]{c.getId(), c.getNombre()});
        }
    }
}
