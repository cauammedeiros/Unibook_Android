package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VisualizarPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_perfil)

        // 1. Vincula os componentes da tela pelos IDs corretos
        val edtNome = findViewById<TextView>(R.id.txtNomeUsuario)
        val edtEmail = findViewById<TextView>(R.id.txtEmail)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val btnVerificarMulta = findViewById<Button>(R.id.btnVerificarMulta)

        // 2. Configura a ação de voltar para fechar a tela
        btnVoltar?.setOnClickListener {
            finish()
        }

        // 3. Recupera os dados enviados pela ProcurarUsuariosActivity
        val nomeUsuario = intent.getStringExtra("USUARIO_NOME")
        val emailUsuario = intent.getStringExtra("USUARIO_EMAIL")

        // 4. Preenche os campos da tela com os dados do usuário clicado
        if (nomeUsuario != null) {
            edtNome.setText(nomeUsuario)
        }
        if (emailUsuario != null) {
            edtEmail.setText(emailUsuario)
        }

        btnVerificarMulta.setOnClickListener{
            val intent = Intent(this, VerificarMultasActivity::class.java)
            intent.putExtra("USER_NAME", nomeUsuario)
            startActivity(intent)
        }
    }
}