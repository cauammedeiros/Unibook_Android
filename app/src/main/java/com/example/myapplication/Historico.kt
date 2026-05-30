package com.example.myapplication

import com.google.firebase.Timestamp

data class Historico(
    var id: String = "",
    val userId: String = "",
    val livroId: String = "",
    val titulo: String = "",
    val autor: String = "",
    val capaUrl: String = "",
    val data: Timestamp? = null,
    val tipoAcao: String = "",
    var status: String = "Ativo"
)