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
        
        // ID sincronizado com activity_visualizar_perfil.xml
        val txtNomeUsuario = findViewById<TextView>(R.id.txtNomeUsuario)

        val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Nome do Usuário"
        txtNomeUsuario.text = nomeUsuario

        btnVoltar.setOnClickListener {
            finish()
        }

        btnVerificarMulta.setOnClickListener {
            val intent = Intent(this, VerificarMultasActivity::class.java)
            intent.putExtra("USER_NAME", nomeUsuario)
            startActivity(intent)
        }
    }
}
