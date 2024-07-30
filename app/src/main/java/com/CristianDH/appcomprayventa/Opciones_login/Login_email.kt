package com.CristianDH.appcomprayventa.Opciones_login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.CristianDH.appcomprayventa.R
import com.CristianDH.appcomprayventa.Registro_email
import com.CristianDH.appcomprayventa.databinding.ActivityLoginEmailBinding

class Login_email : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@Login_email, Registro_email::class.java))
        }
    }
}