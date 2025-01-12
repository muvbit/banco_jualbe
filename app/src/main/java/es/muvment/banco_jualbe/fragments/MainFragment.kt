package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.navigation.NavigationView
import es.muvment.banco_jualbe.R
import es.muvment.banco_jualbe.dao.ClienteDAO
import es.muvment.banco_jualbe.databinding.FragmentMainBinding
import es.muvment.banco_jualbe.databinding.NavHeaderBinding
import es.muvment.banco_jualbe.pojo.Cliente

class MainFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    val args:MainFragmentArgs by navArgs()
    lateinit var binding: FragmentMainBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(layoutInflater)
        drawerLayout=binding.main
        val toolbar=binding.toolbar
        val toggle=ActionBarDrawerToggle(requireActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START)
                    }
                }


                navView.setNavigationItemSelectedListener(this@MainFragment)

                cardViewTransferencias.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToTransfersFragment(cliente.getId())
                    )
                }

                cardViewConfiguracion.setOnClickListener {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToSettingsFragment(cliente.getId())
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
                val navHeader = navView.getHeaderView(0)
                val navHeaderBinding = NavHeaderBinding.bind(navHeader)
                navHeaderBinding.navNombre.text = cliente.getNombre()
                navHeaderBinding.navNif.text = cliente.getNif()
            }
        } else {
            Log.e("MainFragment", "No se pudo convertir el objeto a Cliente")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }
    }

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
        binding.main.closeDrawer(GravityCompat.START)
        return true
    }

}