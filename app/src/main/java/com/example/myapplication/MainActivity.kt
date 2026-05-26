package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity() {
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

            if (email.isEmpty() || senha.isEmpty()) {
                txtErro.setText(R.string.err_campos_vazios)
                txtErro.visibility = View.VISIBLE
            }

            else if (email == "admin@unifor.br" || email == "aluno@unifor.br") {
                val senhaCorreta = if (email == "admin@unifor.br") "1234" else "aluno123"
                if (senha == senhaCorreta) {
                    val tipoUsuario = if (email == "admin@unifor.br") "admin" else "aluno"
                    val nomeUsuario = if (tipoUsuario == "admin") "Administrador" else "Aluno Teste"

//                    val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
//                    val editor = sharedPref.edit()
//                    editor.putString("USER_EMAIL", email)
//                    editor.putString("USER_NAME", nomeUsuario)
//                    editor.apply()

                    txtErro.visibility = View.GONE
                    val intent = Intent(this, tela_Entrando::class.java)
                    intent.putExtra("TIPO_USUARIO", tipoUsuario)
                    startActivity(intent)
                } else {
                    txtErro.setText(R.string.err_login_incorreto)
                    txtErro.visibility = View.VISIBLE
                }
            }
            else {
                fb.collection("Usuários")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val userDoc = documents.documents[0]
                            val senhaNoBanco = userDoc.getString("senha")
                            val tipoUsuario = userDoc.getString("tipo") ?: "aluno"

                            if (senhaNoBanco == hashSenha(senha)) {
                                val nomeNoBanco = userDoc.getString("nome") ?: "Usuário"
                                val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                editor.putString("USER_ID", userDoc.id)
//                                editor.putString("USER_EMAIL", email)
//                                editor.putString("USER_NAME", nomeNoBanco)
                                editor.apply()
                                txtErro.visibility = View.GONE
                                val intent = Intent(this, tela_Entrando::class.java)
                                intent.putExtra("TIPO_USUARIO", tipoUsuario)
                                startActivity(intent)
                            } else {
                                txtErro.setText(R.string.err_login_incorreto)
                                txtErro.visibility = View.VISIBLE
                            }
                        } else {
                            txtErro.setText(R.string.err_login_incorreto)
                            txtErro.visibility = View.VISIBLE
                        }
                    }
                    .addOnFailureListener {
                        txtErro.setText(R.string.err_servidor)
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

    private fun hashSenha(senha: String): String {
        val bytes = java.security.MessageDigest.getInstance("SHA-256").digest(senha.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}