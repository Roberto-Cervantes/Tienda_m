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
// Significa que esta clase va a mapear la tabla rol de la base de datos
@Table(name="rol")

// Se realiza un implements Serializable para que esta clase pueda guardar la información
public class Rol implements Serializable{
    
    // Esta variable se debe llamar serialVersionUID ya que así la utiliza la base de datos
    // para el manego de la llave primaria
    private static final long serialVersionUID = 1L;
    
    // @Id es para indicar que esa variable va a ser la clave primaria en la tabla
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) indica que la generación de la numeración
    // la va a realizar la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column (name="idRol") significa que la variable va a ser referencia a la columna
    // id_rol de la base de datos
    @Column (name="id_rol")
    private Long idRol;
    private String nombre;
    
    //Esta es una foreign key, estamos haciendo referencia igual que en la primary key
    @Column (name="id_usuario")
    private Long idUsuario;
    
}
