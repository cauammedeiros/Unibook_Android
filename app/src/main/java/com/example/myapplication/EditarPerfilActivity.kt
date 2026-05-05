package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        // Botão Voltar (Seta)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()// Retorna para a tela anterior (Perfil)
        }

        // Botão Salvar Alterações
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAlteracoes)
        btnSalvar.setOnClickListener {
            // Lógica de feedback para o usuário
            Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}