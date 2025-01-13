package es.muvment.banco_jualbe.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import es.muvment.banco_jualbe.MainActivity
import es.muvment.banco_jualbe.R
import es.muvment.banco_jualbe.bd.MiBancoOperacional
import es.muvment.banco_jualbe.databinding.FragmentLoginBinding
import es.muvment.banco_jualbe.pojo.Cliente


class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val banco = MiBancoOperacional.getInstance(requireContext())
        var clientePrueba: Cliente

        binding.entrar.setOnClickListener {
            val usuario = binding.user.editText?.text.toString()
            val contraseña = binding.password.editText?.text.toString()
            if (usuario.isEmpty()) {
                showSnackbar("El usuario no puede estar vacío.")
                return@setOnClickListener
            }
            if (contraseña.isEmpty()) {
                showSnackbar("La contraseña no puede estar vacía.")
                return@setOnClickListener
            }
            clientePrueba = Cliente(0, usuario, "", "", contraseña, "")
            var cliente = banco?.login(clientePrueba)
            if (cliente != null) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment(cliente.getId()))

                val mainActivity=activity as? MainActivity
                mainActivity?.clienteId=cliente.getId()
                mainActivity?.updateNavHeader("${cliente.getNombre() + " "+cliente.getApellidos()}", cliente.getNif() as String)

            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage("El nombre de usuario y/o contraseña no coinciden.")
                    .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }.show()
                return@setOnClickListener
            }
        }


        binding.tvNuevoCliente.setOnClickListener {
            val action =
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNewClientFragment())
        }



        binding.salir.setOnClickListener {
            requireActivity().finishAffinity() // CERRAMOS LA APLICACIÓN
        }
    }

    private fun showSnackbar(mensaje: String) {
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()
    }

}