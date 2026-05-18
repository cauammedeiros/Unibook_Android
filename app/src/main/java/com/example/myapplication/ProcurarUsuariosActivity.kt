package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProcurarUsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_usuarios)

        // Botão Voltar
        findViewById<ImageButton>(R.id.btnVoltar)?.setOnClickListener {
            finish()
        }

        // Inicialização do RecyclerView de usuários
        val rvUsuarios = findViewById<RecyclerView>(R.id.rvUsuarios)
        rvUsuarios?.layoutManager = LinearLayoutManager(this)
        
        // Aqui você definiria um UsuárioAdapter futuramente
        // rvUsuarios?.adapter = UsuarioAdapter(listaUsuarios)
    }
}
