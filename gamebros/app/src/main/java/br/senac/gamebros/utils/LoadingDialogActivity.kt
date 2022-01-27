package br.senac.gamebros.utils

import android.app.Activity
import android.app.AlertDialog
import br.senac.gamebros.R

class LoadingDialogActivity(val activity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun startLoading(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)
        val builder = AlertDialog.Builder(activity)

        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss(){
        isDialog.dismiss()
    }
}