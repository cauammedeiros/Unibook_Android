package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class SolicitacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solicitacao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltarSolicitar)

        btnVoltar.setOnClickListener {
            finish()
        }

        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val edtCodigo = findViewById<android.widget.EditText>(R.id.Codigo)

        btnEnviar.setOnClickListener{
            val email = intent.getStringExtra("EMAIL_RECUPERACAO")
            val codigo = edtCodigo.text.toString()
            if (codigo == "1234") {
                val intent = Intent(this, RedefinirActivity::class.java)
                intent.putExtra("EMAIL_RECUPERACAO", email)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Código incorreto!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}