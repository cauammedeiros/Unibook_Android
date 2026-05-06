package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Sair da conta
        val btnLogout = findViewById<LinearLayout>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Sair")
                .setMessage("Deseja realmente sair da sua conta?")
                .setPositiveButton("Sim") { _, _ ->
                    // 1. Limpar SharedPreferences (se você usar para salvar o login)
                    val preferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    preferences.edit().clear().apply()

                    // 2. Ir para a tela de Login ou Splash
                    val intent = Intent(this, SplashActivity::class.java)

                    // 3. LIMPAR O HISTÓRICO DE TELAS
                    // Isso impede que o usuário volte para o Perfil após deslogar
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish() // Fecha a tela atual
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        // Editar Perfil
        val btnEditarPerfil = findViewById<Button>(R.id.btnEditarPerfil) // ID do botão azul na tela do meio

        btnEditarPerfil.setOnClickListener {
            // 1. Criar o Builder
            val builder = AlertDialog.Builder(this)

            // 2. Inflar o layout da esquerda (seu XML de edição)
            val view = layoutInflater.inflate(R.layout.activity_editar_perfil, null)

            // 3. Vincular o layout ao AlertDialog
            builder.setView(view)

            // 4. Criar o dialog
            val dialog = builder.create()

            // Deixar o fundo transparente para respeitar as bordas arredondadas do seu design
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            // 5. Mostrar o popup
            dialog.show()

            // Botão Voltar (Seta)
            val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
            btnVoltar.setOnClickListener {
                val intent = Intent (this, PerfilActivity::class.java)
                startActivity(intent)
            }

            // Botão Salvar Alterações
            val btnSalvar = findViewById<Button>(R.id.btnSalvarAlteracoes)
            btnSalvar.setOnClickListener {
                // Lógica de feedback para o usuário
                Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent (this, PerfilActivity::class.java)
                startActivity(intent)
            }
        }


        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        btnInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        // Botões
        val btnLivrosFav = findViewById<LinearLayout>(R.id.btnLivrosFav)
        val btnHistorico = findViewById<LinearLayout>(R.id.btnHistorico)
        val btnConfig = findViewById<LinearLayout>(R.id.btnConfig)

        btnLivrosFav.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoricoLivrosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracoesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }
    }
