package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Sair da conta
        findViewById<LinearLayout>(R.id.btnLogout).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Sair")
                .setMessage("Deseja realmente sair da sua conta?")
                .setPositiveButton("Sim") { _, _ ->
                    val preferences = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                    preferences.edit().clear().apply()

                    val intent = Intent(this, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        // Abrir tela de edição
        findViewById<Button>(R.id.btnEditarPerfil).setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }

        // Configuração do menu inferior e botões
        configurarBotoes()
    }

    // Carregar dados no onResume garante que a tela atualize após editar o perfil
    override fun onResume() {
        super.onResume()
        carregarDadosUsuario()
    }

    private fun carregarDadosUsuario() {
        val nomeView = findViewById<TextView>(R.id.txtNomeUsuario)
        val emailView = findViewById<TextView>(R.id.txtEmail)
        
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val emailLogado = sharedPref.getString("USER_EMAIL", "") ?: ""

        if (emailLogado == "aluno@unifor.br") {
            nomeView.text = "Aluno Unifor (Teste)"
            emailView.text = emailLogado
        } else if (emailLogado.isNotEmpty()) {
            Firebase.firestore.collection("Usuários")
                .whereEqualTo("email", emailLogado)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val doc = documents.documents[0]
                        nomeView.text = doc.getString("nome")
                        emailView.text = doc.getString("email")
                    }
                }
        }
    }

    private fun configurarBotoes() {
        // Menu de Navegação
        findViewById<LinearLayout>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_library).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_chatbot).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_search).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        // Cores do menu atual
        findViewById<ImageView>(R.id.iv_profile).setColorFilter(Color.parseColor("#2196F3"))
        findViewById<TextView>(R.id.tv_profile).setTextColor(Color.parseColor("#2196F3"))

        // Outros botões
        findViewById<LinearLayout>(R.id.btnLivrosFav).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.btnHistorico).setOnClickListener {
            startActivity(Intent(this, HistoricoLivrosActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.btnConfig).setOnClickListener {
            startActivity(Intent(this, ConfiguracoesActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }
}
