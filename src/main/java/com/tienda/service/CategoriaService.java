/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

// Esta interface es para obtener el listado de categorias
public interface CategoriaService {
    public List<Categoria> getCategorias(boolean activo);
}
