package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class tela_Entrando : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_entrando)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 1. Ache o novo botão pelo ID que você criou
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

// 2. Configure o clique dele
        btnEntrar.setOnClickListener {
            // 3. Mude o destino (SegundaClasse::class.java) para a tela que você quer abrir
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}