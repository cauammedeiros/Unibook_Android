package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import android.widget.EditText
import android.widget.Toast

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
                            fb.collection("Usuários").document(docId)
                                .update("senha", novaSenha)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                        }
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

}