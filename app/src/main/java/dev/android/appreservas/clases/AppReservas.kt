package dev.android.appreservas.clases

import android.app.Application

//Preferencias compartidas en toda la aplicacion
class AppReservas : Application(){

    //variable global que se usa en toda la app
    companion object{
        lateinit var preferencias : Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        preferencias = Preferencias( applicationContext )
    }
}