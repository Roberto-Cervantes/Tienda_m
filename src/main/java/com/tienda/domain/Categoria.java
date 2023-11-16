/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

// @data de lombok lo que hace es crear implicitamente los getter and setters
@Data
// @Entity lo que significa es que esta clase va a tener acceso a una tabla porque es una entidad
@Entity
// Significa que esta clase va a mapear la tabla categoria de la base de datos
@Table(name="categoria")

// Se realiza un implements Serializable para que esta clase pueda guardar la información
public class Categoria implements Serializable{
    
    // Esta variable se debe llamar serialVersionUID ya que así la utiliza la base de datos
    // para el manego de la llave primaria
    private static final long serialVersionUID = 1L;
    
    // @Id es para indicar que esa variable va a ser la clave primaria en la tabla
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) indica que la generación de la numeración
    // la va a realizar la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column (name="idCategoria") significa que la variable va a ser referencia a la columna
    // id_categoria de la base de datos
    @Column (name="id_categoria")
    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;

    // esta anotación es para referenciar una relación de uno  a muchos en la base datos
    @OneToMany
    //esta anotación es para indicar que la columna id_categoria es la que hace 
    //la relación entre las tablas categorias y productos
    // indicar updatable=false indica que no se actualicen cascadas de filas
    @JoinColumn(name="id_categoria", updatable=false)
    // la variable es una lista de productos, ya que la relación es de uno a muchos   
    private List<Producto> productos;
    
}
