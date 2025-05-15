package com.example.tarea3

// Clase para gestionar los productos que se añaden al carrito. Creo que queda mejor
object Carrito {

    // Lista de los productos añadidos al carrito
    val productosEnCarrito = mutableListOf<Producto>()

    // *** Función para agregar un producto a nuestro carrito. ***
    fun agregarProducto(producto: Producto) {
        //Sirve para ver si un producto ya esta en el carrito.
        val existente = productosEnCarrito.find { it.nombre == producto.nombre }
        // Si el producto ya esta en el carro, solo se suma la cantidad de los que llevas.
        if (existente != null) {
            existente.cantidad += producto.cantidad
            //Limito la cantidad de unidades que puedes llevar hasta 10
            if (existente.cantidad > 10) existente.cantidad = 10
        } else {
            productosEnCarrito.add(producto)
        }
    }

    // *** Función para eliminar un producto de nustro carrito. ***
    fun eliminarProducto(nombre: String) {
        productosEnCarrito.removeIf { it.nombre == nombre }
    }

    // *** Función para calcular el total de la orden sumando el subtotal de cada producto ***
    fun calcularTotal(): Int {
        return productosEnCarrito.sumOf {
            // Convertir String a Int.
            val precioLimpio = it.precio.replace(Regex("[^\\d]"), "").toInt()
            // Multiplicamos el precio por las unidades que llevamos.
            precioLimpio * it.cantidad
        }
    }

    // *** Función para limpiar el carrito por completo.
    fun limpiarCarrito() {
        productosEnCarrito.clear()
    }
}

