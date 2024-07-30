package com.CristianDH.appcomprayventa

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.CristianDH.appcomprayventa.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Pattern

class Registro_email : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroEmailBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener {
            validarInfo()
        }

    }

    private var email = ""
    private var password = ""
    private var r_password = ""
    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()
        r_password = binding.EtRPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EtEmail.error = "Correo invalido"
            binding.EtEmail.requestFocus()

        } else if (email.isEmpty()) {
            binding.EtEmail.error = "Ingrese un email"
            binding.EtEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.EtPassword.error = "Ingrese una contraseña"
            binding.EtPassword.requestFocus()
        } else if (r_password.isEmpty()) {
            binding.EtRPassword.error = "repita la contraseña"
            binding.EtRPassword.requestFocus()
        } else if (password != r_password) {
            binding.EtRPassword.error = "Las contraseñas no coinciden"
            binding.EtRPassword.requestFocus()
        } else {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta...")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBaseDatos()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registro el usuario devido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun llenarInfoBaseDatos() {
        progressDialog.setMessage("Guardando informacion")

        val tiempo = Constantes.obtenerTiempoDis()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario=firebaseAuth.uid

        val hashMap= HashMap<String, Any> ()
        hashMap["nombres"]=""
        hashMap["CodigoTelefono"]=""
        hashMap["telefono"]=""
        hashMap["urlImagnePerfil"]=""
        hashMap["proveedor"]="Email"
        hashMap["escribiendo"]=""
        hashMap["tiempo"]=tiempo
        hashMap["online"]=true
        hashMap["email"]="${emailUsuario}"
        hashMap["uid"]="${uidUsuario}"
        hashMap["fechaNacimiento"]=""

        val ref=FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"No se pudo guardar la informacion devido a ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }
}