package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        val fb = Firebase.firestore
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
                txtErro.setText(R.string.err_campos_vazios)
                txtErro.visibility = View.VISIBLE
            }
            else if (email == "aluno@unifor.br" || email == "admin@unifor.br") {
                txtErro.setText(R.string.err_email_reservado)
                txtErro.visibility = View.VISIBLE
            }
            else if (!isSenhaValida(senha)) {
                txtErro.setText(R.string.msg_requisitos_senha)
                txtErro.visibility = View.VISIBLE
            }
            else {
                // Busca no Firebase Firestore para verificar se o email já existe
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            // Criar novo usuário no banco
                            val novoUsuario = hashMapOf(
                                "nome" to nome,
                                "email" to email,
                                "senha" to senha,
                                "tipo" to "aluno"
                            )

                            fb.collection("Usuários")
                                .add(novoUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, R.string.msg_cadastro_sucesso, Toast.LENGTH_SHORT).show()
                                    txtErro.visibility = View.GONE
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("TIPO_USUARIO", "aluno")
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener {
                                    txtErro.setText(R.string.err_salvar_dados)
                                    txtErro.visibility = View.VISIBLE
                                }
                        } else {
                            // Email já cadastrado
                            txtErro.setText(R.string.err_email_cadastrado)
                            txtErro.visibility = View.VISIBLE
                        }
                    }
                    .addOnFailureListener {
                        txtErro.setText(R.string.err_servidor)
                        txtErro.visibility = View.VISIBLE
                    }
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