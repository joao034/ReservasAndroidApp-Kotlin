package dev.android.appreservas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.appreservas.databinding.ActivityReservasBinding

class ReservasActivity : AppCompatActivity(), FragmentNuevaReserva.ComunicadorFragmentos {

    lateinit var binding : ActivityReservasBinding
    var NUM_RESERVAS_DISPONIBLES = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        binding = ActivityReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickBotones()
    }

    private fun clickBotones(){
        //Fragmento Nuevo
        binding.btnNuevaReserva.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("disponibles", NUM_RESERVAS_DISPONIBLES)
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentNuevaReserva()
            fragmento.arguments = bundle
            transaccion.replace(R.id.contendor, fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }

        //Fragmento Disponibilidad
        binding.btnDisponibilidad.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("disponibles", NUM_RESERVAS_DISPONIBLES)
            val transaccion = supportFragmentManager.beginTransaction()
            val fragmento = FragmentDisponibilidadReservas()
            fragmento.arguments = bundle
            transaccion.replace(R.id.contendor, fragmento)
            transaccion.addToBackStack(null)
            transaccion.commit()
        }
    }

    override fun enviarDatos(fechaReserva: String, numPersonas: String, disponibles: Int) {
        val reserva = arrayOf(fechaReserva, numPersonas)
        val texto = "Reserva para ${reserva.get(1)} personas, para el día ${reserva.get(0)}"
        binding.txtReservaConfirmada.setText(texto)
        NUM_RESERVAS_DISPONIBLES = disponibles
    }


}