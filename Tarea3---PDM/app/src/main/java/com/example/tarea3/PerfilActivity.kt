package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
// Pantalla para el perfil de usuario
class PerfilActivity : AppCompatActivity() {

    //UI
    private lateinit var textDireccion: TextView
    private lateinit var btnEditarDireccion: Button

    //Constantes pref
    private val PREFS = "DatosUsuario"
    private val DIRECCION_KEY = "direccion"
    private val DIRECCION_DEFAULT = "1007 Mountain Drive, Gotham"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Referencias de las vistas de nombre y correo del registro
        val textNombre = findViewById<TextView>(R.id.textNombre)
        val textCorreo = findViewById<TextView>(R.id.textCorreo)

        //Botones
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnVolverCatalogo = findViewById<Button>(R.id.btnVolverCatalogo)

        // Cargar datos del usuario en el registro
        val sharedPrefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        val nombre = sharedPrefs.getString("nombre", "") ?: ""
        val correo = sharedPrefs.getString("correo", "") ?: ""

        // Si no hay datos de regitro, entonces enseñamos los genericos
        textNombre.text = if (nombre.isNotBlank()) nombre else getString(R.string.invited_user)
        textCorreo.text = if (correo.isNotBlank()) correo else getString(R.string.no_email)

        // Mostramos la dirección registrada o el default
        textDireccion = findViewById(R.id.textDireccion)
        btnEditarDireccion = findViewById(R.id.btnEditarDireccion)
        val direccion = sharedPrefs.getString(DIRECCION_KEY, DIRECCION_DEFAULT)
        textDireccion.text = "Dirección de envio: $direccion"

        //Boton para editar la dirección registrada
        btnEditarDireccion.setOnClickListener {
            val editText = EditText(this)
            editText.setText(direccion)

            AlertDialog.Builder(this)
                .setTitle("Modificar Dirección")
                .setView(editText)
                .setPositiveButton(getString(R.string.guardar)) { _, _ ->
                    val nuevaDireccion = editText.text.toString()
                    sharedPrefs.edit().putString(DIRECCION_KEY, nuevaDireccion).apply()
                    textDireccion.text = getString(R.string.direccion_envio, nuevaDireccion)
                }
                .setNegativeButton(getString(R.string.cancelar), null)
                .show()
        }


        // botón de editar perfil
        btnEditar.setOnClickListener {
            Toast.makeText(this, getString(R.string.funcion_no_implementada), Toast.LENGTH_SHORT).show()
        }

        // boton de volver al catalogo
        btnVolverCatalogo.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Boton de cerrar sesión, regresa al registro
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
