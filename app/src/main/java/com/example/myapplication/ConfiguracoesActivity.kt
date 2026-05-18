package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfiguracoesActivity : BaseActivity() {
    private lateinit var prefs: PreferencesManager
    private var currentFontSize: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        prefs = PreferencesManager(this)
        currentFontSize = prefs.fontSize

        // Configuração do botão de voltar
        val btnVoltar = findViewById<ImageView>(R.id.btnBack)
        btnVoltar.setOnClickListener { finish() }

        // Elementos de Fonte
        val btnDiminuirFonte = findViewById<TextView>(R.id.btnDiminuirFonte)
        val btnAumentarFonte = findViewById<TextView>(R.id.btnAumentarFonte)
        val txtTamanhoFonte = findViewById<TextView>(R.id.txtTamanhoFonte)

        updateFontSizeUI(txtTamanhoFonte)

        btnDiminuirFonte.setOnClickListener {
            if (currentFontSize > 0.8f) {
                currentFontSize -= 0.1f
                updateFontSizeUI(txtTamanhoFonte)
            }
        }

        btnAumentarFonte.setOnClickListener {
            if (currentFontSize < 1.5f) {
                currentFontSize += 0.1f
                updateFontSizeUI(txtTamanhoFonte)
            }
        }

        // Elementos de Visual (Cores e Contraste)
        val btnPadrao = findViewById<Button>(R.id.btnPadrao)
        val btnContraste = findViewById<Button>(R.id.btnContraste)

        // Inicializar textos dos botões baseado no estado atual
        if (prefs.isDarkMode) {
            btnContraste.text = "Escuro"
            btnPadrao.text = "Padrão"
        } else {
            btnPadrao.text = "Claro"
            btnContraste.text = "Contraste"
        }

        btnPadrao.setOnClickListener {
            btnPadrao.text = "Claro"
            btnContraste.text = "Contraste"
            prefs.isDarkMode = false
            prefs.applyTheme()
            // Recriar para aplicar imediatamente se necessário, 
            // embora applyTheme com setDefaultNightMode geralmente faça isso.
        }

        btnContraste.setOnClickListener {
            btnContraste.text = "Escuro"
            btnPadrao.text = "Padrão"
            prefs.isDarkMode = true
            prefs.applyTheme()
        }

        // Botão Salvar
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.setOnClickListener {
            prefs.fontSize = currentFontSize
            // Para aplicar a fonte globalmente imediatamente, recarregamos a activity
            recreate()
        }
    }

    private fun updateFontSizeUI(textView: TextView) {
        val percentage = (currentFontSize * 100).toInt()
        textView.text = "$percentage%"
    }
}