package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltarRedefinir)

        btnVoltar.setOnClickListener {
            finish()
        }

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)
        val txtErro = findViewById<TextView>(R.id.txtErroCadastro)
        val edtNome = findViewById<EditText>(R.id.txtNome)
        val edtEmail = findViewById<EditText>(R.id.txtEmail)
        val edtSenha = findViewById<EditText>(R.id.txtSenha)

        btnCadastrar.setOnClickListener {
            val nome = edtNome.text.toString()
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                txtErro.text = "Preencha todos os campos!"
                txtErro.visibility = View.VISIBLE
            }
            else if (email == "aluno@unifor.br") { // Simulação de e-mail já existente
                txtErro.text = "O email inserido já está cadastrado!"
                txtErro.visibility = View.VISIBLE
            }
            else if (!isSenhaValida(senha)) {
                txtErro.text = "A senha deve ter no mínimo 8 caracteres, um número e uma letra maiúscula!"
                txtErro.visibility = View.VISIBLE
            }
            else {
                // Sucesso: Vai para a tela Entrando que você acabou de configurar
                txtErro.visibility = View.GONE
                val intent = Intent(this, tela_Entrando::class.java)
                intent.putExtra("TIPO_USUARIO", "aluno")
                startActivity(intent)
                finish()
            }
        }

        val btnTemConta = findViewById<TextView>(R.id.txtTemLogin)

        btnTemConta.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun isSenhaValida(senha: String): Boolean {
        val temOitoDigitos = senha.length >= 8
        val temNumero = senha.any { it.isDigit() }
        val temMaiuscula = senha.any { it.isUpperCase() }

        return temOitoDigitos && temNumero && temMaiuscula
    }
}