package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
// Actividad que muestra el resumen final de una compra hecha.
class ResumenCompraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_compra)

        // Obtener nombre de usuario desde SharedPreferences
        val sharedPrefs = getSharedPreferences("DatosUsuario", MODE_PRIVATE)
        val nombreGuardado = sharedPrefs.getString("nombre", "")
        val nombrePerfil = if (nombreGuardado.isNullOrBlank()) "Invitado" else nombreGuardado
        // Vistas
        val tituloExito = findViewById<TextView>(R.id.tituloExito)
        val contenedorProductos = findViewById<LinearLayout>(R.id.contenedorProductosResumen)
        val totalCompra = findViewById<TextView>(R.id.totalCompra)
        val textoTitularTarjeta = findViewById<TextView>(R.id.textoTitularTarjeta)
        val textoDireccion = findViewById<TextView>(R.id.textoDireccion)
        val btnVolver = findViewById<Button>(R.id.btnVolverCatalogoResumen)

        //¡Exito!
        tituloExito.text =getString(R.string.compra_exito, nombrePerfil)


        // Mostrar productos comprados
        val resumenTexto = intent.getStringExtra("resumen") ?: "No se encontraron productos"
        val productosSeparados = resumenTexto.split("\n")

        for (linea in productosSeparados) {
            // Cada producto en una línea
            val textoProducto = TextView(this).apply {
                text = linea
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.black))
            }
            // Separador entre productos
            val separador = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2
                ).apply {
                    setMargins(0, 12, 0, 12)
                }
                setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            }

            contenedorProductos.addView(textoProducto)
            contenedorProductos.addView(separador)
        }

        // Total de la compra
        val total = intent.getIntExtra("total", 0)
        totalCompra.text = getString(R.string.total_compra, total)

        // Datos del titular y tarjeta de la compra hecha
        val nombreTar = intent.getStringExtra("nombre") ?: "Desconocido"
        val tarjeta = intent.getStringExtra("tarjeta") ?: "0000000000000000"
        val ultimos4 = tarjeta.takeLast(4)
        val tarjetaFormateada = "************$ultimos4"

        textoTitularTarjeta.text = getString(R.string.tarjeta_formato, nombreTar, tarjetaFormateada)

        // Dirección registrada en perfil
        val direccion = intent.getStringExtra("direccion") ?: "No disponible"
        textoDireccion.text = getString(R.string.direccion_envioo, direccion)

        // botón para volver al catalogo
        btnVolver.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

