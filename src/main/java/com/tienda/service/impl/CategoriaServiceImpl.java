/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service.impl;

import com.tienda.dao.CategoriaDao;
import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Se le indica una anotación que esta clase es un servicio
@Service
// debe implementar la interface CategoriaService, hacer click derecho y seleccionar 
// implements all abstract methods
public class CategoriaServiceImpl implements CategoriaService{

    // permite implementar un objeto sin tener que hacer new y con solo una instancia de él
    @Autowired 
    private CategoriaDao categoriaDao;
    
    @Override
    public List<Categoria> getCategorias(boolean activo) {
        // Eliminamos el Throw
        
        var categorias = categoriaDao.findAll();
        return categorias;
    }
    
}
