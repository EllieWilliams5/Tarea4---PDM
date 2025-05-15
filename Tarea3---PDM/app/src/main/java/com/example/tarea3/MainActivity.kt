package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
// Pantalla de registro
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.registro)

        //Botón de ingreso
        val btnEntrar = findViewById<Button>(R.id.btnEntrarTienda)

        btnEntrar.setOnClickListener {
            val editNombre = findViewById<EditText>(R.id.editNombre)
            val editCorreo = findViewById<EditText>(R.id.editCorreo)

            val nombreIngresado = editNombre.text.toString().trim()
            val correoIngresado = editCorreo.text.toString().trim()

            // Guardar los datos temporalmente
            val prefs = getSharedPreferences("DatosUsuario", MODE_PRIVATE)
            prefs.edit().apply {
                putString("nombre", nombreIngresado)
                putString("correo", correoIngresado)
                putBoolean("mostrarToast", true)
                apply()
            }

            // Nos movemos al catalogo
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
