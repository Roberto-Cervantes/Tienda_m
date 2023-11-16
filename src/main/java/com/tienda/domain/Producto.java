/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

// @data de lombok lo que hace es crear implicitamente los getter and setters
@Data
// @Entity lo que significa es que esta clase va a tener acceso a una tabla porque es una entidad
@Entity
// Significa que esta clase va a mapear la tabla producto de la base de datos
@Table(name="producto")

// Se realiza un implements Serializable para que esta clase pueda guardar la información
public class Producto implements Serializable{
    
    // Esta variable se debe llamar serialVersionUID ya que así la utiliza la base de datos
    // para el manego de la llave primaria
    private static final long serialVersionUID = 1L;
    
    // @Id es para indicar que esa variable va a ser la clave primaria en la tabla
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) indica que la generación de la numeración
    // la va a realizar la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column (name="idProducto") significa que la variable va a ser referencia a la columna
    // id_producto de la base de datos
    @Column (name="id_producto")
    private Long idProducto;
    //private Long idCategoria; (como esta la anotación abajo, no se puede manipular de forma directa
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private boolean activo;

    // esta anotación hace referencia a la relación de la base de datos, se utiliza
    // cuando en dos tablas hay una relación muchos a uno
    @ManyToOne
    // esta anotación indica que el atributo que va a enlazar ambas tablas 
    // va a ser id_categoria
    @JoinColumn(name="id_categoria")
    
    // esta variable indica que dentro de este objeto (producto) hay 
    // un objeto categoria, los cuales van a estar enlazados por id_categoria
    Categoria categoria;
    
}
