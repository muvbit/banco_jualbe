package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import es.muvment.banco_jualbe.MainActivity
import es.muvment.banco_jualbe.dao.ClienteDAO
import es.muvment.banco_jualbe.dao.CuentaDAO
import es.muvment.banco_jualbe.databinding.ActivityMainBinding
import es.muvment.banco_jualbe.databinding.FragmentMainBinding
import es.muvment.banco_jualbe.pojo.Cliente

class MainFragment : Fragment() {

    val args:MainFragmentArgs by navArgs()
    lateinit var binding: FragmentMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: Int = args.id
        val clienteBuscado = Cliente(id, "", "", "", "", "")
        val cliente = ClienteDAO().search(clienteBuscado) as Cliente

        if (cliente != null) {
            binding.run {
                // Configura el botón menuToggle para abrir/cerrar el Drawer
                menuToggle.setOnClickListener {
                    (activity as? MainActivity)?.openDrawer()
                }

                saldoActual.text = "********* €"

                swSaldo.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        saldoActual.text = "********* €"
                    } else saldoActual.text =
                        CuentaDAO().getSaldoCuentasCliente(cliente).toString() + " €"
                }


                cardViewTransferencias.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTransfersFragment(cliente.getId())
                    )
                }

                cardViewConfiguracion.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToSettingsFragment()
                    )
                }

                cardViewCuentas.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToGlobalPositionFragment(cliente.getId())
                    )
                }

                imgLogout.setOnClickListener {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
                }

                tvNombre.text = cliente.getNombre()+" "+cliente.getApellidos()

                // Configura el Header del Drawer

            }
        } else {
            Log.e("MainFragment", "No se pudo convertir el objeto a Cliente")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }
    }
/*
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Log.d("MainActivity", "Clicked Home")
            }
            R.id.nav_accounts -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToGlobalPositionFragment(args.id))
            }
            R.id.nav_settings -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment(args.id))
            }
            R.id.nav_transfers -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToTransfersFragment(args.id))
            }
        }
        bindingMainActivity.mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }
*/

}