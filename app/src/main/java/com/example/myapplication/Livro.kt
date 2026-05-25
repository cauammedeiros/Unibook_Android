package com.example.myapplication

import com.google.firebase.firestore.PropertyName

/**
 * Modelo de dados para o Livro com mapeamento para o Firebase
 */
data class Livro(
    var id: String = "",

    @get:PropertyName("Titulo")
    @set:PropertyName("Titulo")
    @field:PropertyName("Titulo")
    var titulo: String = "",

    @get:PropertyName("Autor")
    @set:PropertyName("Autor")
    @field:PropertyName("Autor")
    var autor: String = "",

    @get:PropertyName("Genero")
    @set:PropertyName("Genero")
    @field:PropertyName("Genero")
    var genero: String = "",

    @get:PropertyName("Sinopse")
    @set:PropertyName("Sinopse")
    @field:PropertyName("Sinopse")
    var sinopse: String = "",

    @get:PropertyName("CapaUrl")
    @set:PropertyName("CapaUrl")
    @field:PropertyName("CapaUrl")
    var capaUrl: String = ""
)