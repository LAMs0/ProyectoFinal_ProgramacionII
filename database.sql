/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  venga
 * Created: 14 nov. 2025
 */
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Tabla autores
CREATE TABLE IF NOT EXISTS autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100) NOT NULL
);

-- Tabla categorias
CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla libros
CREATE TABLE IF NOT EXISTS libro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor_id INT NOT NULL,
    categoria_id INT NOT NULL,
    anio INT NOT NULL,
    stock INT NOT NULL,

    FOREIGN KEY (autor_id) REFERENCES autor(id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Tabla usuarios para Login
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50),
    estado TINYINT DEFAULT 1
);

-- Insert de usuario admin (password: admin123)
INSERT INTO usuarios (username, password_hash, rol, estado)
VALUES ('admin', SHA2('admin123', 256), 'Administrador', 1);

-- Datos iniciales
INSERT INTO autor(nombre, nacionalidad) VALUES
('Gabriel García Márquez', 'Colombia'),
('Mario Vargas Llosa', 'Perú');

INSERT INTO categoria(nombre) VALUES
('Novela'),
('Ciencia ficción');

INSERT INTO libro(titulo, autor_id, categoria_id, anio, stock) VALUES
('Cien Años de Soledad', 1, 1, 1967, 10),
('La Ciudad y Los Perros', 2, 1, 1963, 8);

