package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RecuperacaoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacao)
        val fb = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltarRecuperar)

        btnVoltar.setOnClickListener {
            finish()
        }

        configurarBotaoTema()

        val btnSolicitar = findViewById<Button>(R.id.btnSolicitar)
        val edtEmail = findViewById<EditText>(R.id.txtEmail2)
        val txtErro = findViewById<TextView>(R.id.txtErroRecuperacao)

        btnSolicitar.setOnClickListener {
            val email = edtEmail.text.toString().trim()

            if (email.isEmpty()) {
                txtErro.text = "Preencha o campo de email!"
                txtErro.visibility = View.VISIBLE
            } else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            txtErro.visibility = View.GONE
                            val intent = Intent(this, RedefinirActivity::class.java)
                            intent.putExtra("EMAIL_RECUPERACAO", email)
                            startActivity(intent)
                        } else {
                            txtErro.text = "O email inserido não existe!"
                            txtErro.visibility = View.VISIBLE
                        }
                    }
                    .addOnFailureListener {
                        txtErro.text = "Erro ao conectar com o servidor."
                        txtErro.visibility = View.VISIBLE
                    }
            }
        }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}