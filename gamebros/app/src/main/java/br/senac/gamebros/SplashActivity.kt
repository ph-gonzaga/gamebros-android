package br.senac.gamebros

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senac.gamebros.BottomNavigationActivity
import android.content.Intent
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent: android.content.Intent = Intent(this, BottomNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)

    }
}