/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.ui;

/**
 *
 * @author venga
 */
import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame(String username) {
        setTitle("Menú Principal - Usuario: " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar mb = new JMenuBar();
        JMenu mArchivo = new JMenu("Archivo");
        JMenuItem miCerrar = new JMenuItem("Cerrar sesión");
        JMenuItem miSalir = new JMenuItem("Salir");
        miCerrar.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        miSalir.addActionListener(e -> System.exit(0));
        mArchivo.add(miCerrar);
        mArchivo.addSeparator();
        mArchivo.add(miSalir);

        JMenu mAyuda = new JMenu("Ayuda");
        JMenuItem miAcerca = new JMenuItem("Acerca de");
        miAcerca.addActionListener(e -> new AcercaDeFrame().setVisible(true));
        mAyuda.add(miAcerca);

        mb.add(mArchivo);
        mb.add(mAyuda);
        setJMenuBar(mb);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JButton bAutores = new JButton("CRUD Autores");
        JButton bCategorias = new JButton("CRUD Categorías");
        JButton bLibros = new JButton("CRUD Libros");
        gbc.gridx = 0; gbc.gridy = 0; p.add(bAutores, gbc);
        gbc.gridx = 1; p.add(bCategorias, gbc);
        gbc.gridx = 2; p.add(bLibros, gbc);

        bAutores.addActionListener(e -> new AutoresFrame().setVisible(true));
        bCategorias.addActionListener(e -> new CategoriasFrame().setVisible(true));
        bLibros.addActionListener(e -> new LibrosFrame().setVisible(true));

        add(p, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
}
