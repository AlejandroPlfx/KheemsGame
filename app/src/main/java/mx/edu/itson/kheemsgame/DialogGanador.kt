import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import mx.edu.itson.kheemsgame.R

class DialogGanador : AppCompatDialogFragment() {

    private lateinit var tvGanador: TextView
    private lateinit var etNombre: EditText
    private lateinit var btnGuardar: Button
    private var listener: DialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_ganador, null)

        tvGanador = view.findViewById(R.id.tv_ganador)
        etNombre = view.findViewById(R.id.et_nombre)
        btnGuardar = view.findViewById(R.id.btn_guardar)

        val nombreGanador = arguments?.getString("nombre")
        tvGanador.text = "¡Felicidades $nombreGanador, ganaste!"

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            if (nombre.isNotEmpty()) {
                listener?.guardarPuntuacion(nombre, 0)
                dismiss()
            } else {
                etNombre.error = "Por favor ingrese su nombre"
            }
        }

        builder.setView(view)

        return builder.create()
    }

    interface DialogListener {
        fun guardarPuntuacion(nombre: String, puntuacion: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " debe implementar DialogListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
