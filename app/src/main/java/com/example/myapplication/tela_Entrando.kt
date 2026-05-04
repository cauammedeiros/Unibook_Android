package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class tela_Entrando : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_tela_entrando)

            // 1. Recupera o tipo que veio lá do Login
            val tipoUsuario = intent.getStringExtra("TIPO_USUARIO")

            // 2. Localiza o botão de continuar (ajuste o ID conforme o seu XML)
            val btnContinuar = findViewById<Button>(R.id.btnEntrar)

            // 3. Configura o clique para decidir o destino
            btnContinuar.setOnClickListener {
                if (tipoUsuario == "admin") {
                    // Abre a tela de Administrador que você já tem no projeto
                    val intentAdm = Intent(this, AdmActivity::class.java)
                    startActivity(intentAdm)
                } else {
                    // Abre a HomeActivity dos usuários/alunos
                    val intentHome = Intent(this, HomeActivity::class.java)
                    startActivity(intentHome)
                }

                // Finaliza para não voltar no botão 'back'
                finish()
            }
        }
    }