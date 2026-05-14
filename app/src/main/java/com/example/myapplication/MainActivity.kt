package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fb = Firebase.firestore

        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val edtEmail = findViewById<EditText>(R.id.CampoEmail)
        val edtSenha = findViewById<EditText>(R.id.CampoSenha)

        btnContinuar.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()
            val txtErro = findViewById<TextView>(R.id.txtMensagemErro)

            // 1. Verifica se os campos estão vazios
            if (email.isEmpty() || senha.isEmpty()) {
                txtErro.text = "Preencha todos os campos!"
                txtErro.visibility = View.VISIBLE
            }
            // 2. Verifica Logins Hardcoded (Segurança/Fallback)
            else if (email == "admin@unifor.br" || email == "aluno@unifor.br") {
                val senhaCorreta = if (email == "admin@unifor.br") "1234" else "aluno123"
                if (senha == senhaCorreta) {
                    txtErro.visibility = View.GONE
                    val intent = Intent(this, tela_Entrando::class.java)
                    intent.putExtra("TIPO_USUARIO", if (email == "admin@unifor.br") "admin" else "aluno")
                    startActivity(intent)
                } else {
                    txtErro.text = if (email == "admin@unifor.br") "Administradores possuem senhas para logar na conta" else "Email ou senha incorretos!"
                    txtErro.visibility = View.VISIBLE
                }
            }
            // 3. Busca no Firebase Firestore
            else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val userDoc = documents.documents[0]
                            val senhaNoBanco = userDoc.getString("senha")
                            val tipoUsuario = userDoc.getString("tipo") ?: "aluno"

                            if (senhaNoBanco == senha) {
                                val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                               sharedPref.edit().putString("USER_EMAIL", email).apply()
                                txtErro.visibility = View.GONE
                                val intent = Intent(this, tela_Entrando::class.java)
                                intent.putExtra("TIPO_USUARIO", tipoUsuario)
                                startActivity(intent)
                            } else {
                                txtErro.text = "Senha incorreta!"
                                txtErro.visibility = View.VISIBLE
                            }
                        } else {
                            txtErro.text = "Usuário não cadastrado!"
                            txtErro.visibility = View.VISIBLE
                        }
                    }
                    .addOnFailureListener {
                        txtErro.text = "Erro ao conectar com o servidor."
                        txtErro.visibility = View.VISIBLE
                    }
            }
        }

        val btnEsqueceuSenha = findViewById<TextView>(R.id.txtEsqueceuSenha)

        btnEsqueceuSenha.setOnClickListener {
            val intent = Intent(this, RecuperacaoActivity::class.java)
            startActivity(intent)
        }

        val btnCadastrar = findViewById<TextView>(R.id.txtCadastrar)

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}