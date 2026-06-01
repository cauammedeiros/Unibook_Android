package com.example.myapplication

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FirebaseFirestore

class EditarPerfilActivity : BaseActivity() {

    private val db = FirebaseFirestore.getInstance()

     private lateinit var sharedPref: SharedPreferences
     private var userId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        userId = sharedPref.getString("USER_ID", "") ?: ""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAlteracoes)
        val edtNome = findViewById<EditText>(R.id.edtNomeUsuario)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)

        btnVoltar.setOnClickListener {
            finish()
        }
        btnSalvar.setOnClickListener {
            val novoNome = edtNome.text.toString().trim()
            val novoEmail = edtEmail.text.toString().trim()

            if (validarCampos(novoNome, novoEmail, edtNome, edtEmail)) {
                salvarAlteracoesNoFirebase(novoNome, novoEmail)
            }
        }
        carregarDadosAtuais(edtNome, edtEmail)
    }

    private fun validarCampos(nome: String, email: String, edtNome: EditText, edtEmail: EditText): Boolean {
        var isValid = true

        if (nome.isEmpty()) {
            edtNome.error = getString(R.string.err_campos_vazios)
            isValid = false
        }

        if (email.isEmpty()) {
            edtEmail.error = getString(R.string.err_campos_vazios)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.error = getString(R.string.err_email_invalido)
            isValid = false
        }



        return isValid
    }


    private fun carregarDadosAtuais(edtNome: EditText, edtEmail: EditText){
        if(userId.isNotEmpty()){
            db.collection("Usuários").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if(document != null && document.exists()){
                        edtNome.setText(document.getString("nome"))
                        edtEmail.setText(document.getString("email"))
                    }
                    }
        }
    }


    private fun salvarAlteracoesNoFirebase(nome: String, email: String) {
        if(userId.isEmpty()){
            return
        }
        val dados = mapOf(
            "nome" to nome,
            "email" to email
        )

        db.collection("Usuários").document(userId)
            .update(dados)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener { e ->
            }
    }
}