/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;


import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

// se le indica que es una clase @Controller
@Controller
// esto indica que es un mapeo de consultas
// "/categoria" es el nombre del get que aparece en la pagina web al pasar por encima el mouse
@RequestMapping("/categoria")

public class CategoriaController {
    
    // permite implementar un objeto sin tener que hacer new y con solo una instancia de él
    @Autowired
    
    private CategoriaService  categoriaService;
    
    //Esto es un mapeo de un metodo GET (no de consultas como el de arriba)
    // "/listado" es el nombre del get que aparece en la pagina web al pasar por encima el mouse
    @GetMapping("/listado")
    public String listado (Model model){
        // con la variable false, se pretende que devuelva solo todas las categorias
        //  si se lecciona true, solo va a devolver las activas
        var  categorias = categoriaService.getCategorias(false);
        
        //se va a incorporar el arraylist para que pueda ser vista por una pagina HTML
        // "Categorias" es nombre de una variable 
        //categoria hace referencia a la variable indicada en var
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        
        //el return devuelve una ubicación de un archivo
        // categoria es una carpeta, listado es un archivo
        // Esta carpeta fue creada en el paquete templates de main/resources
        
        return "categoria/listado";
    }
    
    @GetMapping("/nuevo")
    public String categoriaNuevo(Categoria categoria) {
        return "/categoria/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar")
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "categoria", 
                            categoria.getIdCategoria()));
        }
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);
        return "/categoria/modifica";
    }
    
}
