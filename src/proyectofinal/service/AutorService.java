/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.service;

/**
 *
 * @author venga
 */
import proyectofinal.dao.AutorDao;
import proyectofinal.model.Autor;
import java.util.List;

public class AutorService {
    private AutorDao dao = new AutorDao();

    public boolean crearAutor(Autor a) {
        if (a.getNombre() == null || a.getNombre().trim().isEmpty()) return false;
        return dao.insertar(a);
    }

    public boolean actualizarAutor(Autor a) {
        if (a.getId() <= 0) return false;
        if (a.getNombre() == null || a.getNombre().trim().isEmpty()) return false;
        return dao.actualizar(a);
    }

    public boolean eliminarAutor(int id) {
        if (id <= 0) return false;
        return dao.eliminar(id);
    }

    public List<Autor> listarAutores(String filtro) {
        return dao.listar(filtro);
    }
}
