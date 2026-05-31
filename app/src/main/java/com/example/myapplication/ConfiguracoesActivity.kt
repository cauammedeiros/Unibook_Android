package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

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

        // Inicializar textos e cores dos botões baseado no estado atual
        updateThemeButtonsUI(btnPadrao, btnContraste)

        btnPadrao.setOnClickListener {
            prefs.isDarkMode = false
            prefs.applyTheme()
            updateThemeButtonsUI(btnPadrao, btnContraste)
        }

        btnContraste.setOnClickListener {
            prefs.isDarkMode = true
            prefs.applyTheme()
            updateThemeButtonsUI(btnPadrao, btnContraste)
        }

        // Botão Salvar
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.setOnClickListener {
            prefs.fontSize = currentFontSize
            android.widget.Toast.makeText(this, "Configurações salvas com sucesso!", android.widget.Toast.LENGTH_SHORT).show()
            // Para aplicar a fonte globalmente imediatamente, recarregamos a activity
            recreate()
        }
    }

    private fun updateFontSizeUI(textView: TextView) {
        val percentage = (currentFontSize * 100).toInt()
        textView.text = "$percentage%"
    }

    private fun updateThemeButtonsUI(btnPadrao: Button, btnContraste: Button) {
        btnPadrao.text = "Padrão"
        btnContraste.text = "Contraste"

        if (prefs.isDarkMode) {
            btnPadrao.setBackgroundResource(R.drawable.btn_branco_borda)
            btnPadrao.setTextColor(resources.getColor(R.color.texto_principal, theme))
            btnContraste.setBackgroundResource(R.drawable.btn_azul)
            btnContraste.setTextColor(resources.getColor(R.color.white, theme))
        } else {
            btnPadrao.setBackgroundResource(R.drawable.btn_azul)
            btnPadrao.setTextColor(resources.getColor(R.color.white, theme))
            btnContraste.setBackgroundResource(R.drawable.btn_branco_borda)
            btnContraste.setTextColor(resources.getColor(R.color.texto_principal, theme))
        }
    }
}
