package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmprestimoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish() // Volta para a tela de detalhes do livro
        }

        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val checkTermos = findViewById<CheckBox>(R.id.checkTermos) // Use o ID da sua CheckBox
        val txtErro = findViewById<TextView>(R.id.txtErroTermos)

        btnConfirmar.setOnClickListener {
            if (checkTermos.isChecked) {
                // Se estiver marcado, esconde o erro e segue o jogo
                txtErro.visibility = View.GONE

                // Coloque aqui a sua lógica de sucesso (ex: ir para outra tela ou salvar no banco)
                Toast.makeText(this, "Empréstimo realizado!", Toast.LENGTH_SHORT).show()
            } else {
                // Se NÃO estiver marcado, mostra a mensagem vermelha
                txtErro.visibility = View.VISIBLE
            }
        }

        configurarMenuNavegacao()
    }

    private fun configurarMenuNavegacao() {
        findViewById<LinearLayout>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_library).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_chatbot).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_search).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_profile).setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }
}