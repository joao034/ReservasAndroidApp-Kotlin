package dev.android.appreservas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dev.android.appreservas.clases.Menu
import kotlinx.android.synthetic.main.activity_menu.*

data class MenuCafeteria(var nombre: String , var detalle: String)

class MenuActivity : AppCompatActivity() {

    val menuCafeteria = arrayListOf(
        MenuCafeteria("Hamburguesa Simple", "100 gramos de carne"),
        MenuCafeteria("Hamburguesa Doble", "200 gramos de carne"),
        MenuCafeteria("Empanadas de Pollo", "Relleno con salsa de la casa")
    )

    private lateinit var database: AppDatabase
    private lateinit var menu: Menu
    //captura datos de la bd
    private lateinit var menuLiveDatabase: LiveData<Menu>
    private val EDITAR = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        //crearLista()
        click()
        cargarListasMenus()
    }

    private fun crearLista(){
        val adaptador = Adaptador(applicationContext, menuCafeteria)
        this.lstMenu.adapter = adaptador
    }

    //Contexto es la ventana donde se va a llenar el listado
    private class Adaptador(context: Context, datos: ArrayList<MenuCafeteria>): BaseAdapter()
    {
        val datosMenu = datos
        val ctx =  context

        //Clase que hace referencia a la fila personalizada : Layout
        private inner class ViewHolder(){
            internal var nombre: TextView ?= null
            internal var detalle: TextView ?= null
            internal var flecha: ImageView ?= null
        }

        override fun getCount(): Int {
            return datosMenu.size
        }

        override fun getItem(position : Int): Any {
            return datosMenu[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, row: View?, parent: ViewGroup?): View {
            var viewHolder: ViewHolder
            var rowView = row

            if(rowView == null){
                //Hacer proceso de renderizacion
                viewHolder = ViewHolder()
                val inflater =  LayoutInflater.from(ctx)
                rowView = inflater.inflate(R.layout.fila_personalizada, parent, false)

                viewHolder.nombre = rowView.findViewById(R.id.txtNombre) as TextView
                viewHolder.detalle = rowView.findViewById(R.id.txtDetalle) as TextView
                viewHolder.flecha = rowView.findViewById(R.id.imgFlecha) as ImageView

                rowView.tag = viewHolder
            }else{
                viewHolder = rowView.tag as ViewHolder
            }
            //Rellenamos con los valores del arrayList
            //!! garantiza que los valores no sean nulos
            viewHolder.nombre!!.setText(datosMenu.get(position).nombre)
            viewHolder.detalle!!.setText(datosMenu.get(position).detalle)
            viewHolder.flecha!!.setImageResource(R.drawable.ic_flecha_derecha)

            return rowView!!
        }

    }

    private fun click(){
        this.btnNuevoMenu.setOnClickListener(){
            val intent = Intent(applicationContext, NuevoMenuActivity::class.java)
            startActivity(intent)
        }


    }

    private fun cargarListasMenus(){
        var listaMenus = emptyList<Menu>()
        val database = AppDatabase.getDatabase(this)
        database.menus().obtenerMenus().observe(this, Observer {
             listaMenus = it

            val adaptador = dev.android.appreservas.clases.MenuAdapter(this, listaMenus)
            lstMenu.adapter = adaptador
        })

        //Cargar detalle al dar click
        lstMenu.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetalleMenuActivity::class.java)
            intent.putExtra("id", listaMenus[position].idMenu)
            startActivity(intent)
        }
    }
}