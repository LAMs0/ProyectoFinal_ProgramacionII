/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.service;

/**
 *
 * @author venga
 */


import proyectofinal.dao.LibroDAO;
import proyectofinal.model.Libro;
import java.util.List;

public class LibroService {
    private LibroDAO dao = new LibroDAO();

    public boolean crearLibro(Libro l) {
        if (l.getTitulo() == null || l.getTitulo().trim().isEmpty()) return false;
        return dao.insertar(l);
    }

    public boolean actualizarLibro(Libro l) {
        if (l.getId() <= 0) return false;
        if (l.getTitulo() == null || l.getTitulo().trim().isEmpty()) return false;
        return dao.actualizar(l);
    }

    public boolean eliminarLibro(int id) {
        if (id <= 0) return false;
        return dao.eliminar(id);
    }

    public List<Libro> listarLibros(String filtro) {
        return dao.listar(filtro);
    }
    
    public boolean marcarFavorito(int id, boolean estado){
        return dao.marcarFavorito(id, estado);
    }
    
    public List<Libro>obtenerLibrosDestacados(){
       return dao.listarDestacados();
    }
}
