package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Configuração para a tela não ficar por baixo da barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- 1. CONFIGURAÇÃO DOS LIVROS (AÇÃO) ---
        val listaDeAcao = listOf(
            Livro("O Hobbit", R.layout.item_livro), // Substitua pelos seus drawables
            Livro("Duna", R.layout.item_livro),
            Livro("Star Wars", R.layout.item_livro)
        )

        val rvAcao = findViewById<RecyclerView>(R.id.rvAcao)
        rvAcao.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAcao.adapter = LivroAdapter(listaDeAcao)

        // Efeito de imã para os livros de Ação
        val snapAcao = LinearSnapHelper()
        snapAcao.attachToRecyclerView(rvAcao)


        // --- 2. CONFIGURAÇÃO DOS LIVROS (SUSPENSE) ---
        val listaDeSuspense = listOf(
            Livro("Sherlock", R.layout.item_livro),
            Livro("Drácula", R.layout.item_livro),
            Livro("Psicose", R.layout.item_livro)
        )

        val rvSuspense = findViewById<RecyclerView>(R.id.rvSuspense)
        rvSuspense.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSuspense.adapter = LivroAdapter(listaDeSuspense)

        // Efeito de imã para os livros de Suspense
        val snapSuspense = LinearSnapHelper()
        snapSuspense.attachToRecyclerView(rvSuspense)


        // --- 3. MENU DE NAVEGAÇÃO (BOTTOM NAVIGATION) ---
        /*val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> true // Já estamos aqui
                R.id.menu_perfil -> {
                    //startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                R.id.menu_busca -> {
                    // Se tiver uma tela de busca, abra aqui
                    true
                }
                else -> false
            }
        }
        */

        // --- 4. BOTÕES DE TEMA (MODO CLARO/ESCURO) ---
        val btnLight = findViewById<ImageButton>(R.id.btnLightMode)
        val btnNight = findViewById<ImageButton>(R.id.btnNightMode)

        btnLight.setOnClickListener {
            // Lógica para mudar para tema claro
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        btnNight.setOnClickListener {
            // Lógica para mudar para tema escuro
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}