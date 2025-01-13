package es.muvment.banco_jualbe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import es.muvment.banco_jualbe.databinding.ActivityMainBinding
import es.muvment.banco_jualbe.databinding.NavHeaderBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navigationView:NavigationView
    private lateinit var navController: NavController
    var clienteId: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout=binding.mainDrawer
        navigationView=binding.navView
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController=navHostFragment.navController


        navigationView.setNavigationItemSelectedListener{menuItem->
            val clienteId=this.clienteId
            val bundle= Bundle().apply {
                putInt("id",clienteId as Int)
            }
            when(menuItem.itemId){
                R.id.nav_home-> {
                    navController.navigate(R.id.mainFragment, bundle)
                    true
                }
                R.id.nav_accounts->{
                    navController.navigate(R.id.globalPositionFragment, bundle)
                    true
                }
                R.id.nav_transfers->{
                    navController.navigate(R.id.transfersFragment, bundle)
                    true
                }
                R.id.nav_settings->{
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else->false
            }

        }

   }
    fun updateNavHeader(nombre:String, dni:String){
        val navHeader = binding.navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(navHeader)
        navHeaderBinding.navNombre.text =nombre
        navHeaderBinding.navNif.text =dni
    }

    // FALTA ACABAR EL DRAWER

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

}
