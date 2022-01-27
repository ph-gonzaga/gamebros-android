package br.senac.gamebros.services

import android.content.Context
import android.content.Intent
import android.util.Log
import br.senac.gamebros.BottomNavigationActivity
import br.senac.gamebros.api.RetrofitInstance.login
import br.senac.gamebros.model.User
import br.senac.gamebros.views.home.HomeFragment

class SharedPrefManager private constructor(private val context: Context){

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: User
        get() {
            val sharedPreferences = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id", 0),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("token", null)
            )
        }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(ARQUIVO_LOGIN, Context.MODE_PRIVATE)
        //Log.e(">>>> TOKEN >>>> ", sharedPreferences.getString("token", null).toString())
        login.fazerLogout("Bearer " + sharedPreferences.getString("token", null).toString())
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        var i: Intent = Intent(context, BottomNavigationActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    companion object {
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }
}