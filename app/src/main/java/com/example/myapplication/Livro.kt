package com.example.myapplication

import com.google.firebase.firestore.PropertyName

/**
 * Modelo de dados para o Livro com mapeamento para o Firebase
 */
data class Livro(
    // ID agora é uma propriedade normal que preenchemos manualmente
    var id: String = "",

    @get:PropertyName("Titulo")
    @set:PropertyName("Titulo")
    var titulo: String = "",

    @get:PropertyName("Autor")
    @set:PropertyName("Autor")
    var autor: String = "",

    @get:PropertyName("Genero")
    @set:PropertyName("Genero")
    var genero: String = "",

    @get:PropertyName("Sinopse")
    @set:PropertyName("Sinopse")
    var sinopse: String = "",

    @get:PropertyName("CapaUrl")
    @set:PropertyName("CapaUrl")
    var capaUrl: String = ""
)