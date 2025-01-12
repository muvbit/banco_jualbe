package es.muvment.banco_jualbe.fragments

import es.muvment.banco_jualbe.pojo.Cuenta

interface OnCuentaClickListener {
    fun onCuentaClick(cuenta: Cuenta)
}