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

class RecuperacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacao)
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
            else if (email == "aluno@unifor.br" || email == "admin@unifor.br") {
                txtErro.visibility = View.GONE

                finish()
            }
            else {
                // Exatamente como no seu print: "O email inserido não existe!"
                txtErro.text = "O email inserido não existe!"
                txtErro.visibility = View.VISIBLE
            }
        }
    }
}