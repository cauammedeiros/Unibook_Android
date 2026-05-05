package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VisualizarPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_perfil)

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val btnVerificarMulta = findViewById<Button>(R.id.btnVerificarMulta)
        val txtNomeLabel = findViewById<TextView>(R.id.txtNomeLabel)

        // Simulação: Pegar nome vindo da busca ou usar padrão
        val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Nome do Usuário 1"
        txtNomeLabel.text = "Nome do Usuário : $nomeUsuario"

        btnVoltar.setOnClickListener {
            finish()
        }

        btnVerificarMulta.setOnClickListener {
            // RF26: Navegar para a tela de multas
            val intent = Intent(this, VerificarMultasActivity::class.java)
            intent.putExtra("USER_NAME", nomeUsuario) // Opcional: passa o nome para filtrar
            startActivity(intent)
        }
    }
}

