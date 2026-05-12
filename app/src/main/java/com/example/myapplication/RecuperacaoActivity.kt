package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RecuperacaoActivity : AppCompatActivity() {
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

        val btnSolicitar = findViewById<Button>(R.id.btnSolicitar)
        val edtEmail = findViewById<EditText>(R.id.txtEmail2)
        val txtErro = findViewById<TextView>(R.id.txtErroRecuperacao)

        btnSolicitar.setOnClickListener {
            val email = edtEmail.text.toString()

            if (email.isEmpty()) {
                txtErro.text = "Preencha o campo de email!"
                txtErro.visibility = View.VISIBLE
            }
            // Simulação de e-mails cadastrados no sistema do Unibook
            else if (email == "aluno@unifor.br") {
                txtErro.visibility = View.GONE
                val intent = Intent(this, SolicitacaoActivity::class.java)
                startActivity(intent)
            } else if (email == "admin@unifor.br") {
                txtErro.visibility = View.GONE
                // Admin pode ter fluxo direto ou o mesmo, manteremos o finish ou redirecionamento se desejar
                finish()
            } else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            txtErro.visibility = View.GONE
                            val intent = Intent(this, SolicitacaoActivity::class.java)
                            intent.putExtra("EMAIL_RECUPERACAO", email)
                            startActivity(intent)
                        } else {
                            txtErro.text = "O email inserido não existe !"
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
}