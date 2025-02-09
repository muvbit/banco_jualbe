package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.muvment.banco_jualbe.R
import es.muvment.banco_jualbe.adapters.MovementsAdapter
import es.muvment.banco_jualbe.dao.MovimientoDAO
import es.muvment.banco_jualbe.databinding.FragmentMovementsBinding
import es.muvment.banco_jualbe.pojo.Cuenta
import es.muvment.banco_jualbe.pojo.Movimiento

class MovementsFragment : Fragment() {
    lateinit var binding: FragmentMovementsBinding
    val args: MovementsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovementsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cuenta: Cuenta = args.cuenta as Cuenta
        initRecyclerView(cuenta, 0)
        binding.bottomNavigationMovements.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.allMovements -> {
                    initRecyclerView(cuenta, 0)
                    true
                }

                R.id.inmovements -> {
                    initRecyclerView(cuenta, 1)
                    true
                }

                R.id.outmovements -> {
                    initRecyclerView(cuenta, 2)
                    true
                }

                else -> false
            }
        }
    }

    private fun initRecyclerView(cuenta: Cuenta, type: Int) {
        binding.run {
            movimientosRV.layoutManager = LinearLayoutManager(this@MovementsFragment.context)
            movimientosRV.adapter = MovementsAdapter(movementsList(cuenta, type))
        }
    }

    private fun movementsList(cuenta: Cuenta, tipo: Int): ArrayList<Movimiento> {
        val allMovimientos = MovimientoDAO().getMovimientos(cuenta) as ArrayList<Movimiento>
        return when (tipo) {
            1 -> allMovimientos.filter { it.getImporte() < 0 } as ArrayList<Movimiento>
            2 -> allMovimientos.filter { it.getImporte() > 0 } as ArrayList<Movimiento>
            else -> allMovimientos
        }
    }


}