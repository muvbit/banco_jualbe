package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.muvment.banco_jualbe.dao.MovimientoDAO
import es.muvment.banco_jualbe.databinding.FragmentMovementsBinding
import es.muvment.banco_jualbe.adapters.MovementsAdapter
import es.muvment.banco_jualbe.pojo.Cuenta
import es.muvment.banco_jualbe.pojo.Movimiento
import kotlin.collections.ArrayList

class MovementsFragment : Fragment() {
    lateinit var binding: FragmentMovementsBinding
    val args:MovementsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMovementsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cuenta: Cuenta = args.cuenta as Cuenta
        initRecyclerView(cuenta)
    }

    private fun initRecyclerView(cuenta :Cuenta){
        binding.run {
            movimientosRV.layoutManager=LinearLayoutManager(this@MovementsFragment.context)
            movimientosRV.adapter=MovementsAdapter(MovimientoDAO().getMovimientos(cuenta) as ArrayList<Movimiento>)
        }
    }

}