package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class EditarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var livroId: String? = null

    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtAutor: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtSinopse: EditText
    private lateinit var btnSalvar: Button
    private lateinit var txtErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro) // Usando o mesmo layout de criar

        // Ajustando o título da tela para "Editar"
        findViewById<TextView>(R.id.txtTituloCriar).text = "Editar Livro"

        // Inicialização
        imgCapa = findViewById(R.id.imgCapaLivro)
        edtUrlCapa = findViewById(R.id.edtUrlCapa)
        edtNome = findViewById(R.id.edtNome)
        edtAutor = findViewById(R.id.edtAutor)
        edtGenero = findViewById(R.id.edtGenero)
        edtSinopse = findViewById(R.id.edtSinopse)
        btnSalvar = findViewById(R.id.btnCriar)
        btnSalvar.text = "Salvar Alterações"
        txtErro = findViewById(R.id.txtErro)

        findViewById<View>(R.id.btnVoltar).setOnClickListener { finish() }

        // Pegar dados do Intent (se houver)
        // Aqui você carregaria os dados do Firebase para preencher os campos
        
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString().trim()
                if (url.isNotEmpty()) {
                    Glide.with(this).load(url).placeholder(R.drawable.logo_nome).into(imgCapa)
                }
            }
        }

        btnSalvar.setOnClickListener {
            // Lógica de update no Firebase
            Toast.makeText(this, "Funcionalidade de salvar em desenvolvimento", Toast.LENGTH_SHORT).show()
        }
    }
}
