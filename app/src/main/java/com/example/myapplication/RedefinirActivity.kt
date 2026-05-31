package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RedefinirActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_redefinir)
        
        val fb = Firebase.firestore
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura o botão de alternar tema (Sol/Lua)
        configurarBotaoTema()
        
        // Configura o botão de voltar
        findViewById<android.widget.ImageButton>(R.id.btnVoltarRedefinir).setOnClickListener { 
            finish() 
        }

        val btnRedefinir = findViewById<Button>(R.id.btnRedefinir)
        btnRedefinir.setOnClickListener {
            val email = intent.getStringExtra("EMAIL_RECUPERACAO")
            val novaSenha = findViewById<EditText>(R.id.txtNovaSenha).text.toString()
            val repetirSenha = findViewById<EditText>(R.id.txtRepetirSenha).text.toString()

            if (novaSenha != repetirSenha) {
            } else if (!isSenhaValida(novaSenha)) {
            } else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val docId = documents.documents[0].id
                            fb.collection("Usuários").document(docId)
                                .update("senha", novaSenha)
                                .addOnSuccessListener {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                        } else {
                        }
                    }
                    .addOnFailureListener {
                    }
            }
        }
    }

    private fun isSenhaValida(senha: String): Boolean {
        val temOitoDigitos = senha.length >= 8
        val temNumero = senha.any { it.isDigit() }
        val temMaiuscula = senha.any { it.isUpperCase() }
        return temOitoDigitos && temNumero && temMaiuscula
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema) ?: return
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        
        // Define o ícone inicial: Sol se estiver no modo escuro, Lua se estiver no claro
        btnTema.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}
