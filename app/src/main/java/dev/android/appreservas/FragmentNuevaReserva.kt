package dev.android.appreservas

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.RuntimeException


class FragmentNuevaReserva : Fragment() {

    interface ComunicadorFragmentos{
        fun enviarDatos( fechaReserva : String , numPersonas : String, disponibles: Int)
    }



    private var activityContenedor : ComunicadorFragmentos ?= null
    private lateinit var fechaReserva: EditText
    private lateinit var numPersonas : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_reserva, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is ComunicadorFragmentos){
            activityContenedor = context
        }else throw RuntimeException(
            context.toString() + "debe implementarse la interfaz Comunicador Fragmentos"
        )
    }

    override fun onDetach() {
        super.onDetach()
        //libera recursos cuando no se este usando
        activityContenedor = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btnConfirmar : Button = requireView().findViewById(R.id.btnConfirmar)

        btnConfirmar.setOnClickListener{
            if(comprobarCamposVacios()){
                if(arguments != null){
                    var numReservasDisponibles = requireArguments().getInt("disponibles")
                    activityContenedor!!.enviarDatos(fechaReserva.text.toString(), numPersonas.text.toString(), --numReservasDisponibles)
                    limpiarCampos()
                }
            }
        }


    }

    private fun comprobarCamposVacios(): Boolean {
        fechaReserva = requireView().findViewById(R.id.txtFechaReserva)
        numPersonas = requireView().findViewById(R.id.txtNumPersonas)

        if(fechaReserva.text.toString().isBlank() || numPersonas.text.toString().isBlank()){
            Toast.makeText(activity, "LLenar los campos solicitados!", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true;
    }

    private fun limpiarCampos(){
        fechaReserva.setText("")
        numPersonas.setText("")
    }
}