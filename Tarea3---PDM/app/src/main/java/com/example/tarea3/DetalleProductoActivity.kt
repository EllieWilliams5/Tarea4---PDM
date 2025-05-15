package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetalleProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val imagen = findViewById<ImageView>(R.id.imagenProducto)
        val nombre = findViewById<TextView>(R.id.nombreProducto)
        val descripcion = findViewById<TextView>(R.id.descripcionProducto)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarCarrito)

        val btnSumar = findViewById<Button>(R.id.btnSumar)
        val btnRestar = findViewById<Button>(R.id.btnRestar)
        val textCantidad = findViewById<TextView>(R.id.textCantidad)

        var cantidad = 1

        btnSumar.setOnClickListener {
            if (cantidad < 10) {
                cantidad++
                textCantidad.text = cantidad.toString()
            } else {
                Toast.makeText(this, "Solo puedes añadir hasta 10 unidades", Toast.LENGTH_SHORT).show()
            }
        }

        btnRestar.setOnClickListener {
            if (cantidad > 1) {
                cantidad--
                textCantidad.text = cantidad.toString()
            } else {
                Toast.makeText(this, "No puedes seleccionar menos de 1", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregar.setOnClickListener {
            val producto = intent.getSerializableExtra("producto") as Producto
            producto.cantidad = cantidad
            Carrito.agregarProducto(producto)

            Toast.makeText(this, "$cantidad producto(s) añadido(s) al carrito", Toast.LENGTH_SHORT).show()
            // Aquí irá más adelante la lógica para guardar en el carrito
            finish()
        }

        val btnVolverCatalogo = findViewById<Button>(R.id.btnVolverCatalogo)
        btnVolverCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
            finish()
        }


        val producto = intent.getSerializableExtra("producto") as? Producto

        if (producto != null) {
            val nombre = findViewById<TextView>(R.id.nombreProducto)
            val descripcion = findViewById<TextView>(R.id.descripcionProducto)
            val imagen = findViewById<ImageView>(R.id.imagenProducto)

            nombre.text = producto.nombre
            descripcion.text = producto.descripcion
            imagen.setImageResource(producto.imagenResId)
        }
    }
}