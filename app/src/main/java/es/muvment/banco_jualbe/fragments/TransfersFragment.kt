package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import es.muvment.banco_jualbe.dao.ClienteDAO
import es.muvment.banco_jualbe.dao.CuentaDAO
import es.muvment.banco_jualbe.databinding.FragmentTransfersBinding
import es.muvment.banco_jualbe.pojo.Cliente
import es.muvment.banco_jualbe.pojo.Cuenta

class TransfersFragment : Fragment() {
    lateinit var binding:FragmentTransfersBinding
    val args: TransfersFragmentArgs by navArgs()
    lateinit var cliente:Cliente
    lateinit var cuenta1Seleccionada:Cuenta
    lateinit var cuenta2Seleccionada:Cuenta

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentTransfersBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        val clienteBuscado=Cliente(args.id,"","","","","")
        cliente= ClienteDAO().search(clienteBuscado) as Cliente

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cuentas = CuentaDAO().getCuentas(cliente) as ArrayList<Cuenta>
        val misCuentas = cuentas.map { it.getCuentaCompleta() ?: "No existen cuentas" }

        val divisas = arrayOf("€", "$", "£")
        val adapterCuentas = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, misCuentas)
        val adapterDivisas = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, divisas)
        binding.run {

            var rdAjenaChecked = false
            btnEnviar.isEnabled=false
            rdCuentas.setOnCheckedChangeListener { _, checkedID ->
                if (checkedID == binding.rdCPropia.id) {
                    binding.spnCuenta2.visibility = View.VISIBLE
                    binding.txCuentaAjena.visibility = View.INVISIBLE
                    rdAjenaChecked = false
                } else {
                    rdAjenaChecked = true
                    binding.spnCuenta2.visibility = View.INVISIBLE
                    binding.txCuentaAjena.visibility = View.VISIBLE
                }

            }

            adapterCuentas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapterDivisas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnCuenta.adapter = adapterCuentas
            spnCuenta2.adapter = adapterCuentas
            spnDivisa.adapter = adapterDivisas

            spnCuenta.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cuenta1Seleccionada = cuentas[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            })

            spnCuenta2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cuenta2Seleccionada = cuentas[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            })
            var divisaSeleccionada = ""
            spnDivisa.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    divisaSeleccionada = divisas[position].toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            })

            fun checkFields(): Boolean {
                btnEnviar.isEnabled =
                    !rdAjenaChecked && !txCantidad.text.isNullOrEmpty() || rdAjenaChecked && !txCuentaAjena.text.isNullOrEmpty() && !txCantidad.text.isNullOrEmpty()
                return btnEnviar.isEnabled
            }

            txCuentaAjena.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && txCuentaAjena.text.isNullOrEmpty()) {
                    txCuentaAjena.setError("El campo no puede estar vacío.")
                }
                checkFields()
            }

            txCantidad.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && txCantidad.text.isNullOrEmpty()) {
                    txCantidad.setError("El campo no puede estar vacío")
                }
            }
            txCantidad.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    checkFields()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

            })

            btnEnviar.setOnClickListener {
                var todoBien = checkFields()
                if (!todoBien) {
                    snackbarMacker("Revise los campos antes de enviar")
                } else {
                    var mensaje = "La transferencia ha sido satisfactoria" +
                            "\nCuenta de origen:\n" + cuenta1Seleccionada.getCuentaCompleta() +
                            "\nCuenta destino:\n" + if (rdAjenaChecked) txCuentaAjena.text else cuenta2Seleccionada.getCuentaCompleta() +
                            "\nCantidad: " + txCantidad.text + divisaSeleccionada
                    AlertDialog.Builder(requireContext()).setMessage(mensaje)
                        .setPositiveButton("Aceptar") { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
            }


            btnBorrar.setOnClickListener {
                txCantidad.text?.clear()
                txCuentaAjena.text?.clear()
            }

        }


    }
    private fun snackbarMacker(mensaje: String) {
        val transferSnackbar = Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG)
        transferSnackbar.show()
    }


}