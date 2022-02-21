package dev.android.appreservas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.android.appreservas.clases.AppReservas.Companion.preferencias
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoginClick()
        if (revisarEstado()) {
            startActivity(Intent(this, OpcionesActivity::class.java))
        }
    }

    private fun btnLoginClick() {
        btnLogin.setOnClickListener() {
            validarUsuario()
        }
    }

    private fun revisarEstado(): Boolean {
        return preferencias.devolverEstado()
    }

    private fun validarUsuario() {
        if ((edtUsername.text.isBlank() || edtPassword.text.isBlank())) {
            Toast.makeText(this, "Ingresar usuario y contraseña", Toast.LENGTH_SHORT).show()
        } else {
            if (swRecuerdame.isChecked()) {
                preferencias.guardarUsuario(edtUsername.text.toString())
                preferencias.guardarClave(edtPassword.text.toString())
            }
            preferencias.guardarEstado(swRecuerdame.isChecked())
            val intent = Intent(this, OpcionesActivity::class.java)
            startActivity(intent)
        }
    }
}