/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */
import proyectofinal.model.Autor;
import proyectofinal.service.AutorService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AutoresFrame extends JFrame {
    private AutorService service = new AutorService();

    private JTextField txtId = new JTextField(5);
    private JTextField txtNombre = new JTextField(20);
    private JTextField txtNacionalidad = new JTextField(20);
    private JTextField txtFiltro = new JTextField(15);

    private JButton btnNuevo = new JButton("Nuevo");
    private JButton btnGuardar = new JButton("Guardar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnListar = new JButton("Listar");

    private JTable tbl = new JTable();
    private DefaultTableModel model;

    public AutoresFrame() {
        setTitle("CRUD Autores");
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("ID:"), gbc);
        txtId.setEditable(false); gbc.gridx = 1; form.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; form.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Nacionalidad:"), gbc);
        gbc.gridx = 1; form.add(txtNacionalidad, gbc);

        JPanel actions = new JPanel();
        actions.add(btnNuevo); actions.add(btnGuardar); actions.add(btnModificar); actions.add(btnEliminar);

        JPanel top = new JPanel(new BorderLayout());
        JPanel filtroPanel = new JPanel();
        filtroPanel.add(new JLabel("Buscar:")); filtroPanel.add(txtFiltro); filtroPanel.add(btnListar);
        top.add(form, BorderLayout.CENTER);
        top.add(actions, BorderLayout.SOUTH);
        top.add(filtroPanel, BorderLayout.NORTH);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID","Nombre","Nacionalidad"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tbl.setModel(model);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Listeners
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
                    txtNacionalidad.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        listar();
        setSize(700,400);
        setLocationRelativeTo(null);
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtNacionalidad.setText("");
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String nac = txtNacionalidad.getText().trim();
        if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Nombre obligatorio"); return; }
        Autor a = new Autor();
        a.setNombre(nombre);
        a.setNacionalidad(nac);
        if (service.crearAutor(a)) {
            JOptionPane.showMessageDialog(this, "Autor guardado");
            listar();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar autor");
        }
    }

    private void modificar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione un autor"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        String nombre = txtNombre.getText().trim();
        String nac = txtNacionalidad.getText().trim();
        if (nombre.isEmpty()) { JOptionPane.showMessageDialog(this, "Nombre obligatorio"); return; }
        Autor a = new Autor(id, nombre, nac);
        if (service.actualizarAutor(a)) {
            JOptionPane.showMessageDialog(this, "Autor actualizado");
            listar();
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar autor");
        }
    }

    private void eliminar() {
        if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Seleccione un autor"); return; }
        int id = Integer.parseInt(txtId.getText().trim());
        int r = JOptionPane.showConfirmDialog(this, "¿Confirmar eliminación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (service.eliminarAutor(id)) {
                JOptionPane.showMessageDialog(this, "Autor eliminado");
                listar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar autor");
            }
        }
    }

    private void listar() {
        String filtro = txtFiltro.getText().trim();
        List<Autor> lista = service.listarAutores(filtro);
        model.setRowCount(0);
        for (Autor a : lista) {
            model.addRow(new Object[]{a.getId(), a.getNombre(), a.getNacionalidad()});
        }
    }
}
