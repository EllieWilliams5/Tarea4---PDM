package com.example.tarea3

import java.io.Serializable

//Clase que representa un producto.
//Implementa Serializable para poder enviarse entre Actividades mediante Intents.
data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val imagenResId: Int,
    var cantidad: Int = 1
) : Serializable

