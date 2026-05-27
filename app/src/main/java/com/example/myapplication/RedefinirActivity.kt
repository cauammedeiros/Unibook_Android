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
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
            } else if (!isSenhaValida(novaSenha)) {
                Toast.makeText(this, "A senha não atende aos requisitos!", Toast.LENGTH_SHORT).show()
            } else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val docId = documents.documents[0].id
                            val senhaHash = hashSenha(novaSenha)
                            fb.collection("Usuários").document(docId)
                                .update("senha", senhaHash)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro ao conectar com o servidor.", Toast.LENGTH_SHORT).show()
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

    private fun hashSenha(senha: String): String {
        val bytes = java.security.MessageDigest.getInstance("SHA-256").digest(senha.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
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
