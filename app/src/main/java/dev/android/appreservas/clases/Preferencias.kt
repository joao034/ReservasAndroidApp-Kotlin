package dev.android.appreservas.clases

import android.content.Context

class Preferencias(val context: Context) {
    //Clave de la BDD
    val SHARED_APP = "MyApp"
    val SHARED_ESTADO = "estado"

    //constantes usuario
    val SHARED_USUARIO = "usuario"
    val SHARED_CLAVE = "password"

    val almacenamiento = context.getSharedPreferences(SHARED_APP, Context.MODE_PRIVATE)

    fun guardarEstado( estado:Boolean ){
        almacenamiento.edit().putBoolean( SHARED_ESTADO , estado).apply()
    }

    fun devolverEstado():Boolean{
        return almacenamiento.getBoolean(SHARED_ESTADO, false)
    }

    fun guardarUsuario ( usuario: String) {
        almacenamiento.edit().putString( SHARED_USUARIO, usuario).apply()
    }

    fun devolverUsuario () : String? {
        return almacenamiento.getString( SHARED_USUARIO, "no existe")
    }

    fun guardarClave ( clave: String ) {
        almacenamiento.edit().putString( SHARED_CLAVE, clave).apply()
    }

    fun devolverClave () : String? {
        return almacenamiento.getString( SHARED_CLAVE, "no existe")
    }

}