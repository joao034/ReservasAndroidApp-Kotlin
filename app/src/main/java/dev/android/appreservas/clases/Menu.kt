package dev.android.appreservas.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "menu")
class Menu (
    var nombre: String,
    var detalle: String,
    var precio: Double,
    val imagen: Int,
    @PrimaryKey(autoGenerate = true)
    var idMenu: Int = 0): Serializable
{
}