package br.senac.gamebros.utils

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import br.senac.gamebros.R

class LoadingDialog(val frag: Fragment) {
    private lateinit var isDialog: AlertDialog

    fun startLoading(){
        val inflater = frag.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        val builder = AlertDialog.Builder(frag.context)

        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}

