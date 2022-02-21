package dev.android.appreservas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detalle_menu.*
import kotlinx.android.synthetic.main.activity_nuevo_menu.*
import kotlinx.android.synthetic.main.fila_personalizada.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleMenuActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var menu : dev.android.appreservas.clases.Menu
    private lateinit var menuLiveData : LiveData<dev.android.appreservas.clases.Menu>
    private val SELECCIONAR = 2
    private var uriImagen: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_menu)
        cargarMenu()
        cargarImagen()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.acciones_menu, menu)
        return true
    }

    private fun cargarMenu(){
        database = AppDatabase.getDatabase(this)
        val idMenu = intent.getIntExtra("id", 0)

        menuLiveData = database.menus().obtenreMenu(idMenu)
        menuLiveData.observe(this, Observer {
            if( it != null ){
                menu = it

                this.txtMenuNombreDetalle.setText(menu.nombre)
                this.txtDetalleMenuDetalle.setText(menu.detalle)
                this.txtDetalleMenuPrecio.setText("${menu.precio}")

                //settear imagen
                val uriImagen = ControladorImagen.obtenerUriImagen(this, idMenu.toLong())
                imgDetalleMenu.setImageURI(uriImagen)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECCIONAR && resultCode == Activity.RESULT_OK ->{
                uriImagen = data!!.data
                imgDetalleMenu.setImageURI(uriImagen)
            }
        }
    }

    private fun cargarImagen(){
        imgDetalleMenu.setOnClickListener{
            ControladorImagen.seleccionarFotoGaleria(this,  SELECCIONAR)
        }
    }

    private fun editarMenu(){
        val idMenu = intent.getIntExtra("id", 0)

        val nombre = txtMenuNombreDetalle.text.toString()
        val detalle = txtDetalleMenuDetalle.text.toString()
        val precio = txtDetalleMenuPrecio.text.toString()

        menu.idMenu = idMenu
        menu.nombre = nombre
        menu.detalle = detalle
        menu.precio = precio.toDouble()

        CoroutineScope(Dispatchers.IO).launch {
            database.menus().actualizarMenu(menu)
            uriImagen?.let{
                /*val intent = Intent()
                intent.data = it
                setResult(Activity.RESULT_OK, intent)*/
                ControladorImagen.guardarImagen(this@DetalleMenuActivity, idMenu.toLong(), it)
            }
            this@DetalleMenuActivity.finish()
        }
    }

    private fun eliminarMenu() {
        CoroutineScope(Dispatchers.IO).launch {
            database.menus().elimnarMenu(menu)
            this@DetalleMenuActivity.finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.opEditarMenu -> {
                editarMenu()
                return true
            }

            R.id.opEliminarMenu -> {
                eliminarMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}