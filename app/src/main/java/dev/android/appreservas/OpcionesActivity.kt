package dev.android.appreservas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dev.android.appreservas.clases.AppReservas.Companion.preferencias
import kotlinx.android.synthetic.main.activity_opciones.*

class OpcionesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)
        clickBotones()
        if(preferencias.devolverEstado()){
            setearUsuarioBienvenida()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_superior, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when( item.itemId ){
            R.id.opCerrarSesion -> {
                preferencias.almacenamiento.edit().clear().apply()//Vaciar todas las configuraciones de la BD
                /*preferencias.guardarEstado(false)
                preferencias.guardarUsuario("")
                preferencias.guardarUsuario("")*/
                this.finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clickBotones(){
        this.btnMenu.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        this.btnReservas2.setOnClickListener(){
            val intent = Intent(this, ReservasActivity::class.java)
            startActivity(intent)
        }

        this.btnBusqueda.setOnClickListener(){
            val intent = Intent(this, BusquedasActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setearUsuarioBienvenida(){
        val usuarioBienvenido = preferencias.devolverUsuario()
        //this.txtUsuario.setText("Bienvenido: ${usuarioBienvenido}" )
    }

}