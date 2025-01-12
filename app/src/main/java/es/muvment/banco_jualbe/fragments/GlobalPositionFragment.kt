package es.muvment.banco_jualbe.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import es.muvment.banco_jualbe.dao.ClienteDAO
import es.muvment.banco_jualbe.dao.CuentaDAO
import es.muvment.banco_jualbe.databinding.FragmentGlobalPositionBinding
import es.muvment.banco_jualbe.adapters.AccountsAdapter
import es.muvment.banco_jualbe.pojo.Cliente
import es.muvment.banco_jualbe.pojo.Cuenta

class GlobalPositionFragment : Fragment(), OnCuentaClickListener {
    lateinit var binding: FragmentGlobalPositionBinding
    val args:MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGlobalPositionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clienteBuscado=Cliente(args.id,"","","","","")
        val cliente:Cliente=ClienteDAO().search(clienteBuscado) as Cliente
        initRecyclerView(cliente)
    }

    private fun initRecyclerView(c:Cliente){
        binding.run {
            cuentasRV.layoutManager=LinearLayoutManager(this@GlobalPositionFragment.context)
            cuentasRV.adapter=AccountsAdapter(CuentaDAO().getCuentas(c) as ArrayList<Cuenta>, this@GlobalPositionFragment)
        }
    }

    override fun onCuentaClick(cuenta: Cuenta) {
        val action=findNavController().navigate(GlobalPositionFragmentDirections.actionGlobalPositionFragmentToMovementsFragment(cuenta))
    }

}