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
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PerfilActivity : BaseActivity() {

    private val db = Firebase.firestore
    private lateinit var sharedPref: android.content.SharedPreferences
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        userId = sharedPref.getString("USER_ID", "") ?: ""

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
        carregarEmprestimoAtivo()
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

    private fun carregarEmprestimoAtivo() {
        val cardEmprestimo = findViewById<View>(R.id.cardEmprestimo)
        val txtNomeLivro = findViewById<TextView>(R.id.txtNomeLivroCard)
        val txtDevolucao = findViewById<TextView>(R.id.txtDevolucaoCard)
        val txtTempo = findViewById<TextView>(R.id.txtTempoCard)

        if (userId.isEmpty()) return

        db.collection("Historico")
            .whereEqualTo("userId", userId)
            .whereEqualTo("tipoAcao", "Empréstimo")
            .orderBy("data", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val doc = documents.documents[0]
                    val titulo = doc.getString("titulo") ?: getString(R.string.livro_desconhecido)
                    val dataEmprestimo = doc.getTimestamp("data")?.toDate()

                    if (dataEmprestimo != null) {
                        // Calcula data de devolução (14 dias depois)
                        val cal = Calendar.getInstance()
                        cal.time = dataEmprestimo
                        cal.add(Calendar.DAY_OF_YEAR, 14)
                        val dataDevolucao = cal.time

                        // Formatação para exibição
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val dataFormatada = sdf.format(dataDevolucao)

                        // Cálculo de dias restantes
                        val hoje = Date()
                        val diff = dataDevolucao.time - hoje.time
                        val diasRestantes = TimeUnit.MILLISECONDS.toDays(diff)

                        // Atualiza UI usando strings.xml com placeholders
                        txtNomeLivro.text = titulo
                        txtDevolucao.text = getString(R.string.label_devolver_em, dataFormatada)
                        
                        if (diasRestantes >= 0) {
                            txtTempo.text = getString(R.string.label_tempo_restante, diasRestantes.toInt())
                            txtTempo.setTextColor(if (diasRestantes <= 2) Color.RED else Color.parseColor("#757575"))
                        } else {
                            txtTempo.text = getString(R.string.status_atrasado)
                            txtTempo.setTextColor(Color.RED)
                        }
                        
                        cardEmprestimo.visibility = View.VISIBLE
                    }
                } else {
                    cardEmprestimo.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                cardEmprestimo.visibility = View.GONE
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
