package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PerfilActivity : BaseActivity() {

    private val db = Firebase.firestore
    private lateinit var sharedPref: android.content.SharedPreferences
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        userId = sharedPref.getString("USER_ID", "") ?: ""

        // Configuração dos botões e navegação
        configurarBotoes()
        configurarBotaoTema()

        // Botão de Logout (Sincronizado com o ImageView btnLogoutIcon do XML)
        findViewById<ImageView>(R.id.btnLogoutIcon)?.setOnClickListener {
            exibirDialogLogout()
        }

        // Abrir tela de edição
        findViewById<Button>(R.id.btnEditarPerfil)?.setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }
    }

    private fun exibirDialogLogout() {
        AlertDialog.Builder(this)
            .setTitle("Sair")
            .setMessage("Deseja realmente sair da sua conta?")
            .setPositiveButton("Sim") { _, _ ->
                sharedPref.edit().clear().apply()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarDadosUsuario()
    }

    private fun carregarDadosUsuario() {
        val nomeView = findViewById<TextView>(R.id.txtNomeUsuario)
        val emailView = findViewById<TextView>(R.id.txtEmail)

        if (userId.isNotEmpty()) {
            db.collection("Usuários").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        nomeView.text = document.getString("nome")
                        emailView.text = document.getString("email")
                    }
                }
        }
    }

    private fun configurarBotoes() {
        // Menu de Navegação (layout_bottom_nav)
        findViewById<LinearLayout>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_library)?.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_chatbot)?.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.nav_search)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }

        // Destaque da aba atual (Perfil)
        findViewById<ImageView>(R.id.iv_profile)?.setColorFilter(Color.parseColor("#5B7FFF"))
        findViewById<TextView>(R.id.tv_profile)?.setTextColor(Color.parseColor("#5B7FFF"))

        // Outras Ações Rápidas
        findViewById<LinearLayout>(R.id.btnLivrosFav)?.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.btnHistorico)?.setOnClickListener {
            startActivity(Intent(this, HistoricoLivrosActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
        findViewById<LinearLayout>(R.id.btnConfig)?.setOnClickListener {
            startActivity(Intent(this, ConfiguracoesActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }
}
