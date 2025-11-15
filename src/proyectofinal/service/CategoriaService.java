/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.service;

/**
 *
 * @author venga
 */
import proyectofinal.dao.CategoriaDAO;
import proyectofinal.model.Categoria;
import java.util.List;

public class CategoriaService {
    private CategoriaDAO dao = new CategoriaDAO();

    public boolean crearCategoria(Categoria c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) return false;
        return dao.insertar(c);
    }

    public boolean actualizarCategoria(Categoria c) {
        if (c.getId() <= 0) return false;
        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) return false;
        return dao.actualizar(c);
    }

    public boolean eliminarCategoria(int id) {
        if (id <= 0) return false;
        return dao.eliminar(id);
    }

    public List<Categoria> listarCategorias(String filtro) {
        return dao.listar(filtro);
    }
}
