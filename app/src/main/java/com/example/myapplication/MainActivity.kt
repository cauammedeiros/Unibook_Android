package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        btnContinuar.setOnClickListener {
            val intent = Intent(this, tela_Entrando::class.java)
            startActivity(intent)
       }

        val btnEsqueceuSenha = findViewById<TextView>(R.id.txtEsqueceuSenha)

        btnEsqueceuSenha.setOnClickListener {
            val intent = Intent(this, RecuperacaoActivity::class.java)
            startActivity(intent)
        }

        val btnCadastrar = findViewById<TextView>(R.id.txtCadastrar)

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}