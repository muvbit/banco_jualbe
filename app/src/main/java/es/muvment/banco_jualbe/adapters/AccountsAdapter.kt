package es.muvment.banco_jualbe.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.muvment.banco_jualbe.R
import es.muvment.banco_jualbe.databinding.ItemAccountsBinding
import es.muvment.banco_jualbe.fragments.OnCuentaClickListener
import es.muvment.banco_jualbe.pojo.Cuenta

class AccountsAdapter (private var cuentasList: ArrayList<Cuenta>, private val listener: OnCuentaClickListener):RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_accounts, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cuentasList[position]
        holder.render(item, listener)
    }

    override fun getItemCount(): Int = cuentasList.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemAccountsBinding.bind(view)

        fun render(cuenta: Cuenta, listener: OnCuentaClickListener) {
            binding.run {
                tvCuenta.text = cuenta.getCuentaCompleta()
                tvSaldo.text = cuenta.getSaldoActual().toString() + " €"
                if (cuenta.getSaldoActual()!! < 0) tvSaldo.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        android.R.color.holo_red_dark
                    )
                )
                cvMain.setOnClickListener {
                    listener.onCuentaClick(cuenta)
                }
            }
        }
    }
}