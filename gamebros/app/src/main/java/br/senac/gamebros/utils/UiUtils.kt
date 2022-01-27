package br.senac.gamebros.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun mostrarToast(context: Context, msg: String){
    Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
}
fun mostrarDialogo (context : Context, msg: String){
    AlertDialog.Builder(context)
        .setMessage(msg)
        .setPositiveButton("OK", null)
        .create()
        .show()
}