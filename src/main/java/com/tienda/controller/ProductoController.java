/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.controller;


import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
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
// "/producto" es el nombre del get que aparece en la pagina web al pasar por encima el mouse
@RequestMapping("/producto")

public class ProductoController {
    
    // Autowired permite implementar un objeto sin tener que hacer new 
    // y con solo una instancia de él
    @Autowired
    
    private ProductoService  productoService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    //Esto es un mapeo de un metodo GET (no de consultas como el de arriba)
    // "/listado" es el nombre del get que aparece en la pagina web al pasar por encima el mouse
    @GetMapping("/listado")
    public String listado (Model model){
        // con la variable false, se pretende que devuelva solo todas las productos
        //  si se lecciona true, solo va a devolver las activas
        var  productos = productoService.getProductos(false);
        
        //se va a incorporar el arraylist para que pueda ser vista por una pagina HTML
        // "Productos" es nombre de una variable 
        //producto hace referencia a la variable indicada en var
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        
        // Devuelve el listado de categorias
        // para incorporarlo al html en el select
        var  categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        
        //el return devuelve una ubicación de un archivo
        // producto es una carpeta, listado es un archivo
        // Esta carpeta fue creada en el paquete templates de main/resources
        
        return "producto/listado";
    }
    
    @GetMapping("/nuevo")
    public String productoNuevo(Producto producto) {
        return "/producto/modifica";
    }

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;
    
    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {        
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile, 
                            "producto", 
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);
        return "/producto/modifica";
    }
    
}
