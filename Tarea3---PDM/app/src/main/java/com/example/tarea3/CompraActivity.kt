package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// Pantalla donde el usuario ingresa un nombre y datos de tarjeta
// con la que se realizara la compra
class CompraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)


        // Elementos de la vista
        val btnFinalizar = findViewById<Button>(R.id.btnFinalizarCompra)
        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editTarjeta = findViewById<EditText>(R.id.editTarjeta)
        val btnVolverCarrito = findViewById<Button>(R.id.btnVolverCarrito)

        // Botón para volver al carrito, osea por si quieres modificar el carrito
        btnVolverCarrito.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Botón para finalizar la compra
        btnFinalizar.setOnClickListener {
            val nombre = editNombre.text.toString().ifEmpty { "Invitado" }
            val tarjeta = editTarjeta.text.toString().ifEmpty { "0000000000000000" }

            // Construir la lista de los productos antes de limpiar el carrito
            val productos = Carrito.productosEnCarrito.map {
                Triple(it.nombre, it.cantidad, it.precio)
            }

            // Texto que ira en el resumen: Prod, precio, subtotal
            val resumenTexto = productos.joinToString("\n") { prod ->
                val precioUnit = prod.third.replace(Regex("[^\\d]"), "").toInt()
                val subtotal = precioUnit * prod.second
                "${prod.first} - ${prod.second} x ${prod.third} = $$subtotal MXN"
            }

            val total = Carrito.calcularTotal()

            // Obtenemos la dirección que esta definida en el perfil
            val direccion = getSharedPreferences("DatosUsuario", MODE_PRIVATE)
                .getString("direccion", "No disponible") ?: "No disponible"

            // Intent para pasar los datos al resumen
            val intent = Intent(this, ResumenCompraActivity::class.java).apply {
                putExtra("total", total)
                putExtra("resumen", resumenTexto)
                putExtra("nombre", nombre)
                putExtra("tarjeta", tarjeta)
                putExtra("direccion", direccion)
            }

            // Ahora sí, limpiar el carrito :')
            Carrito.limpiarCarrito()

            startActivity(intent)
            finish()
        }
    }
}
