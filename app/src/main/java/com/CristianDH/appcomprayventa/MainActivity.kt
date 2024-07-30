package com.CristianDH.appcomprayventa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.CristianDH.appcomprayventa.Fragmentos.FragmentChats
import com.CristianDH.appcomprayventa.Fragmentos.FragmentCuenta
import com.CristianDH.appcomprayventa.Fragmentos.FragmentInicio
import com.CristianDH.appcomprayventa.Fragmentos.FragmentMisAnuncios
import com.CristianDH.appcomprayventa.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit   var  binding:ActivityMainBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
       comprobarSesion()

        verFragmentInicio()
        binding.bottomNV.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
            R.id.Item_Chats->{
                verFragmentChats()
                true
            }
            R.id.Item_Mis_Anuncios->{
                verFragmentMisAnuncios()
                true
            }
            R.id.Item_Cuenta->{
                verFragmentCuenta()
                true
            }

                else -> {
                    false
                }
            }
        }

    }
    private fun comprobarSesion(){
        if (firebaseAuth.currentUser==null){
           startActivity(Intent(this, OpcionesLogin::class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio(){
        binding.TituloRL.text="Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentInicio")
        fragmentTransition.commit()

    }
    private fun verFragmentChats(){
        binding.TituloRL.text="Chats"
        val fragment = FragmentChats()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentChats")
        fragmentTransition.commit()
    }
    private fun verFragmentMisAnuncios(){
        binding.TituloRL.text="Mis Anuncios"
        val fragment = FragmentMisAnuncios()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentMisAnuncios")
        fragmentTransition.commit()
    }
    private fun verFragmentCuenta(){
        binding.TituloRL.text="Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id, fragment, "FragmentCuenta")
        fragmentTransition.commit()
    }
}
