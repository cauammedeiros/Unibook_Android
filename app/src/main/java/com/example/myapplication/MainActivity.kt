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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fb = Firebase.firestore

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
            // 2. Verifica erro de senha especificamente para o Admin
            else if (email == "admin@unifor.br" && senha != "1234") {
                txtErro.text = "Administradores possuem senhas para logar na conta"
                txtErro.visibility = View.VISIBLE
            }
            // 3. Verifica erro de senha ou email para o Usuário Comum
            else if (email == "aluno@unifor.br" && senha != "aluno123") {
                txtErro.text = "Email ou senha incorretos!"
                txtErro.visibility = View.VISIBLE
            }
            // 4. Se passar por todos os testes, o login é bem-sucedido
            else if (email == "admin@unifor.br" || email == "aluno@unifor.br") {
                txtErro.visibility = View.GONE
                val intent = Intent(this, tela_Entrando::class.java)
                intent.putExtra("TIPO_USUARIO", if(email == "admin@unifor.br") "admin" else "aluno")
                startActivity(intent)
            }
            // 5. Caso o email nem exista no seu sistema manual
            else {
                txtErro.text = "Usuário não cadastrado!"
                txtErro.visibility = View.VISIBLE
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