package com.example.tarea3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



// Actividad encargada de mostrar los productos en el carrito,
class CarritoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        //Lista de productos.
        val listaProductosLayout = findViewById<LinearLayout>(R.id.listaProductos)
        //El total de toda la orden.
        val totalOrden = findViewById<TextView>(R.id.totalOrden)
        //Botón para volver al catalogo.
        val btnVolver = findViewById<Button>(R.id.btnVolverCatalogo)
        //Botón para continuar con la compra, donde ingresas tus datos bancarios.
        val btnContinuar = findViewById<Button>(R.id.btnContinuarCompra)

        // *** Función de extensión para convertir dp a px. ***
        fun Int.toPx(): Int = (this * resources.displayMetrics.density).toInt()

        // *** Función encargada de construir e ir actualizando la vista del carrito,
        // pues se muestran todos los productos añadidos con sus controles. ***
        fun actualizarVistaCarrito() {
            listaProductosLayout.removeAllViews()

            // Titulo del carrito
            val titulo = TextView(this).apply {
                text = "Carrito de compras"
                textSize = 22f
                setTextColor(resources.getColor(android.R.color.black))
                setPadding(0, 0, 0, 16)
            }
            listaProductosLayout.addView(titulo)

            // Mostramos cada producto en el carrito
            for (producto in Carrito.productosEnCarrito) {
                val container = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(8, 8, 8, 8)
                }

                // Nombre del producto
                val header = TextView(this).apply {
                    text = producto.nombre
                    textSize = 18f
                    setTextColor(resources.getColor(android.R.color.black))
                }

                // Layout para manejar las cantidades
                val cantidadLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    setPadding(0, 8, 0, 8)
                }

                // Cantidad de unidades que llevas de un producto
                val cantidad = TextView(this).apply {
                    text = producto.cantidad.toString()
                    setPadding(16, 0, 16, 0)
                    textSize = 18f
                }

                // Tamaño y margen de los botones
                val layoutParamsBoton = LinearLayout.LayoutParams(
                    70.toPx(), 35.toPx()
                ).apply {
                    setMargins(16.toPx(), 0, 16.toPx(), 0)
                }

                // Botón para disminuir las unidades de un producto
                val btnMenos = Button(this).apply {
                    text = "-"
                    setBackgroundColor(Color.parseColor("#00CCDD"))
                    setTextColor(Color.WHITE)
                    layoutParams = layoutParamsBoton
                    setOnClickListener {
                        //No se pueden disminuir menos de 1
                        if (producto.cantidad > 1) {
                            producto.cantidad--
                            actualizarVistaCarrito()
                        }
                    }
                }

                // Botón para aumentar las unidades de un producto
                val btnMas = Button(this).apply {
                    text = "+"
                    setBackgroundColor(Color.parseColor("#4F75FF")) // Verde claro
                    setTextColor(Color.WHITE)
                    layoutParams = layoutParamsBoton
                    setOnClickListener {
                        //No se pueden llevar mas de 10 productos
                        if (producto.cantidad < 10) {
                            producto.cantidad++
                            actualizarVistaCarrito()
                        }
                    }
                }

                // Botón para eliminar un prodcuto del carrito
                val btnEliminar = Button(this).apply {
                    text = "Eliminar"
                    setBackgroundColor(Color.parseColor("#6439FF")) // Gris
                    setTextColor(Color.WHITE)
                    layoutParams = layoutParamsBoton
                    setOnClickListener {
                        Carrito.eliminarProducto(producto.nombre)
                        actualizarVistaCarrito()
                    }
                }

                // Este sera un mensaje que anuncie las unidades que llevas de x producto
                val totalCantidad = TextView(this).apply {
                    text = "Llevas ${producto.cantidad} unidad(es) de este producto"
                    textSize = 16f
                    setTextColor(Color.DKGRAY)
                    setPadding(0, 8, 0, 8)
                }

                //Subtotal de cada producto.
                //Paso a de String a Int y luego la multiplicación
                val precioUnitario = producto.precio.replace(Regex("[^\\d]"), "").toInt()
                val subtotal = precioUnitario * producto.cantidad
                //Imprimimos
                val subtotalView = TextView(this).apply {
                    text = "Subtotal: ${producto.cantidad} x ${producto.precio} = $$subtotal MXN"
                    setTextColor(resources.getColor(android.R.color.black))
                }

                //Linea coqueta para separar los productos del carrito
                val separador = View(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 2
                    ).apply {
                        setMargins(0, 12, 0, 12)
                    }
                    setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                }

                // Hacemos el ensamble de todas las vistas
                cantidadLayout.addView(btnMenos)
                cantidadLayout.addView(cantidad)
                container.addView(totalCantidad)
                cantidadLayout.addView(btnMas)
                cantidadLayout.addView(btnEliminar)

                container.addView(header)
                container.addView(cantidadLayout)
                container.addView(subtotalView)
                container.addView(separador)

                listaProductosLayout.addView(container)
            }

            //Imprimimos el monto total a pagar
            totalOrden.text = "Total: $${Carrito.calcularTotal()} MXN"
        }

        //Se inicia la vista del carrito al abrir la pantalla
        actualizarVistaCarrito()

        //Boton para regresar al catalogo de productos
        btnVolver.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
            finish()
        }

        //Boton para continuar con la compra, que lleva a la pantalla siguiente
        btnContinuar.setOnClickListener {
            val intent = Intent(this, CompraActivity::class.java)
            startActivity(intent)
        }
    }
}
