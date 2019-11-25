package com.example.teste.model

data class Indicacao(
    var indicId: String,
    var nome: String,
    var desc: String,
    var img: String
) {

    constructor() : this("", "", "", "")

    constructor(
        indicId: String,
        nome: String,
        desc: String
    ) : this(indicId, nome, desc, "")
}