package com.example.myapplication

/**
 * Modelo de dados para representar uma multa de usuário
 */
data class Multa(
    val id: String,
    val nomeUsuario: String,
    val valorMulta: Double,
    val diasAtraso: Int,
    val isPago: Boolean,
    val nomeLivro: String = "" // Opcional: nome do livro atrasado
) {
    /**
     * Retorna o valor da multa formatado como string
     * Exemplo: "R$ 15,00"
     */
    fun getValorFormatado(): String {
        return "R$ %.2f".format(valorMulta)
    }

    /**
     * Retorna o texto do status (Pago ou Não Pago)
     */
    fun getStatusTexto(): String {
        return if (isPago) "Pago" else "Não Pago"
    }

    /**
     * Retorna o texto completo do status para exibição
     * Exemplo: "Status: Pago" ou "Status: Não Pago"
     */
    fun getStatusCompleto(): String {
        return "Status: ${getStatusTexto()}"
    }

    /**
     * Retorna o texto sobre o atraso do livro
     * Exemplo: "Livro atrasado há 2 dias"
     */
    fun getTextoAtraso(): String {
        return if (diasAtraso == 1) {
            "Livro atrasado há 1 dia"
        } else {
            "Livro atrasado há $diasAtraso dias"
        }
    }
}
