/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.model;

/**
 *
 * @author venga
 */
public class Libro {
    private int id;
    private String titulo;
    private int autorId;
    private int categoriaId;
    private int anio;
    private int stock;
    private boolean destacado;
    
    public Libro(){}

    //constructor
    public Libro(int id, String titulo, int autorId, int categoriaId, int anio, int stock, boolean destacado) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
        this.categoriaId = categoriaId;
        this.anio = anio;
        this.stock = stock;
        this.destacado = destacado;
    }
    
    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public boolean isDestacado(){
        return destacado;
    }
    
    public void setDestacado(boolean destacado){
        this.destacado = destacado;
    }
    
    
}
