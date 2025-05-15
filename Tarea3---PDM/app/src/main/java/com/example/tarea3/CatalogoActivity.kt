package com.example.tarea3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle

// Archivo que lleva la actividad de tod* el catalogo de productos
class CatalogoActivity : AppCompatActivity() {

    // Referencias al layout del menú lateral
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    // La lista de todos los productos disponibles para comprar con todos sus datos
    private val listaProductos = listOf(
        Producto(
            nombre = "Figura de Darth Vader",
            descripcion = "Tanto chicos como fans pueden recrear las batallas y misiones más importantes de la saga Star Wars con nuestras figuras. " +
                    "Con una extrema atención a los detalles, esta serie establece el estándar de calidad y el realismo que esperan los fanáticos de Star Wars. \n" +
                    "-Figura coleccionable de 6 pulgadas con múltiples puntos de articulación.\n" +
                    "-Piezas con diseño realista y finamente detalladas.\n" +
                    "Su precio es de $1,500 MXN.",
            precio = "$1500 MXN",
            imagenResId = R.drawable.vader
        ),
        Producto(
            nombre = "Figura de Iron Man",
            descripcion = "Meticulosamente elaborada en base a Tony Stark en la película End Game, la figura coleccionable única en su tipo presenta " +
                    "dos esculturas de cabeza intercambiables, incluida una escultura de cabeza recientemente desarrollada con daños de batalla y una cabeza de casco" +
                    " intercambiable con función de iluminación LED, la armadura de Iron Man se ha reproducido fielmente con colores rojos, dorados y gris carbón con " +
                    "daños de batalla y efectos de intemperie, y funciones de iluminación LED dispersas por toda la parte del cuerpo.\n" +
                    "Su precio es de $1,200 MXN.",
            precio = "$1200 MXN",
            imagenResId = R.drawable.iron
        ),
        Producto(
            nombre = "Dark Souls",
            descripcion = "Entonces llegó el Fuego.Deberias disfrutar del aclamado juego que definió el género con el que empezó todo. Gracias a una magnífica remasterización, " +
                    "podrás regresar a Lordran con unos impresionantes detalles en alta definición y a 60 fps.\n" +
                    "Deberias aprovechar este paquete, pues incluye el juego principal y el contenido descargable Artorias of the Abyss.\n" +
                    "Su precio es de 750 MXN",
            precio = "$750 MXN",
            imagenResId = R.drawable.souls
        ),
        Producto(
            nombre = "The Last of Us",
            descripcion = "Conoce la emocionante historia y a los entrañables personajes de The Last of Us, ganador de más de 200 premios del Juego del año.\n" +
                    "En una civilización devastada, donde los infectados y los empedernidos sobrevivientes proliferan, Joel, un protagonista cansado, es contratado para rescatar de " +
                    "contrabando a una niña de 14 años llamada Ellie de una zona de cuarentena militar. Sin embargo, lo que comienza como un pequeño trabajo pronto se transforma en una" +
                    " brutal travesía por todo el país. Incluye la historia completa para un solo jugador de The Last of Us y el capítulo de la precuela, Left Behind.\n" +
                    "Su precio es de 650 MXN",
            precio = "$650 MXN",
            imagenResId = R.drawable.last
        ),
        Producto(
            nombre = "Batman: The Killing Joke",
            descripcion = "Según el sonriente motor de locura y caos conocido como el Joker, eso es todo lo que separa a los cuerdos de los psicóticos. Liberado una vez más de los confines " +
                    "del Asilo Arkham, quiere demostrar su desquiciado punto de vista. Y para ello va a utilizar al mejor policía de Ciudad Gótica, el comisario Jim Gordon, y a su brillante y" +
                    " bella hija Barbara. Ahora Batman debe correr para detener a su archienemigo antes de que su reino de terror se cobre la vida de dos de los mejores amigos del Caballero Oscuro. \n" +
                    "Su precio es de 500 MXN",
            precio = "$500 MXN",
            imagenResId = R.drawable.joke
        ),
        Producto(
            nombre = "The Walking Dead: This Sorrowful Life",
            descripcion = "El mundo que conocíamos ha desaparecido. El mundo del comercio y la necesidad frívola ha sido sustituido por un mundo de supervivencia y responsabilidad. " +
                    "Una epidemia de proporciones apocalípticas ha arrasado el planeta, haciendo que los muertos se levanten y se alimenten de los vivos. En cuestión de meses, la sociedad se ha desmoronado: " +
                    "no hay gobierno, ni tiendas de comestibles, ni reparto de correo, ni televisión por cable. En un mundo gobernado por los muertos, los supervivientes se ven obligados a empezar a vivir.\n" +
                    "Su precio es de 500 MXN",
            precio = "$500 MXN",
            imagenResId = R.drawable.dead
        ),
        Producto(
            nombre = "Choque de Reyes",
            descripcion = "Choque de reyes es el segundo volumen de Canción de hielo y fuego, la monumental saga de fantasía épica del escritor George R. R. Martin. Después de la sospechosa muerte de Robert " +
                    "Baratheon, el monarca de los Siete Reinos, su hijo Joffrey ha sido impuesto por la fuerza, aunque quienes realmente gobiernan son su madre, un eunuco y un enano, como dice la voz del pueblo. " +
                    "Cuatro nobles se proclaman, a la vez, reyes legítimos, y las tierras de Poniente se estremecen entre guerras y traiciones. Y todo este horror se encuentra presidido por la más ominosa de las señales: un inmenso cometa color sangre suspendido en el cielo.\n" +
                    "Su precio es de 350 MXN",
            precio = "$350 MXN",
            imagenResId = R.drawable.reyes
        ),
        Producto(
            nombre = "El Último Deseo",
            descripcion = "Geralt de Rivia, brujo y mutante sobrehumano, se gana la vida como cazador de monstruos en una tierra de magia y maravilla: con sus dos espadas al hombro -la de acero para hombres, y la de plata para bestias- da cuenta de estriges, " +
                    "mantícoras, grifos, vampiros, quimeras y lobisomes, pero sólo cuando amenazan la paz. Irónico, cínico, descreído y siempre errante, sus pasos lo llevan de pueblo en pueblo ofreciendo sus servicios, hallando las más de las veces que los auténticos" +
                    " monstruos se esconden bajo rostros humanos. En su camino sorteará intrigas, elegirá el mal menor, debatirá cuestiones de precio, hollará el confín del mundo y realizará su último deseo...\n" +
                    "Su precio es de 250 MXN",
            precio = "$250 MXN",
            imagenResId = R.drawable.deseo
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        configurarMenuLateral()
        configurarProductosGrid()
        mostrarToastDeBienvenida()

    }

    // *** Función para configurar todas las funciones del menu lateral
    private fun configurarMenuLateral() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Se vinculan los elementos del DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Configura el ícono  para abrir/cerrar el menu lateral
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Maneja las opciones seleccionadas del menú lateral.
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> {
                    Toast.makeText(this, "Inicio seleccionado", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_perfil -> {
                    val intentPerfil = Intent(this, PerfilActivity::class.java)
                    intentPerfil.putExtra("nombre_usuario", intent.getStringExtra("nombre_usuario"))
                    intentPerfil.putExtra("correo_usuario", intent.getStringExtra("correo_usuario"))
                    startActivity(intentPerfil)
                }
                R.id.nav_carrito -> {
                    val intent = Intent(this, CarritoActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_videojuegos -> {
                    Toast.makeText(this, "Videojuegos seleccionados", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_figuras -> {
                    Toast.makeText(this, "Figuras seleccionadas", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_comics -> {
                    Toast.makeText(this, "Cómics seleccionados", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_libros -> {
                    Toast.makeText(this, "Libros seleccionados", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_cerrar -> {
                    Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    // Función que asocia un producto con su layout y lo manda a una nueva pantalla de detalles
    private fun configurarProductosGrid() {
        val layoutIds = listOf(
            R.id.producto1,
            R.id.producto2,
            R.id.producto3,
            R.id.producto4,
            R.id.producto5,
            R.id.producto6,
            R.id.producto7,
            R.id.producto8
        )

        layoutIds.forEachIndexed { index, id ->
            val layout = findViewById<LinearLayout>(id)
            layout.setOnClickListener {
                val intent = Intent(this, DetalleProductoActivity::class.java)
                intent.putExtra("producto", listaProductos[index])
                startActivity(intent)
            }
        }
    }

    // Función para enseñar un Toast de bienvenida solo la primera vez que abres la app
    private fun mostrarToastDeBienvenida() {
        val prefs = getSharedPreferences("DatosUsuario", MODE_PRIVATE)
        val mostrarToast = prefs.getBoolean("mostrarToast", false)
        if (mostrarToast) {
            val nombre = prefs.getString("nombre", "")
            val correo = prefs.getString("correo", "")
            val mensaje = if (nombre.isNullOrEmpty() && correo.isNullOrEmpty()) {
                "Ingreso como invitado"
            } else {
                "Bienvenido, $nombre"
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            prefs.edit().putBoolean("mostrarToast", false).apply()
        }
    }




    // Menu de 3 puntitos en la parte superior derecha.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_catalogo, menu)
        return true
    }


     // Maneja las acciones seleccionadas del menú de 3 puntitos.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_comprar -> {
                Toast.makeText(this, "Opción: Comprar", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_modificar -> {
                Toast.makeText(this, "Opción: Modificar Pedido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_cancelar -> {
                Toast.makeText(this, "Opción: Cancelar Pedido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_monitorear -> {
                Toast.makeText(this, "Opción: Monitorear mi pedido", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_dudas -> {
                Toast.makeText(this, "Opción: Dudas", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_comentarios -> {
                Toast.makeText(this, "Opción: Comentarios y/o quejas", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
