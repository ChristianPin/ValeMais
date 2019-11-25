package com.example.teste.model

data class Shopping(
    var shoppId: String,
    var nome: String,
    var endereco: String,
    var horaInicio: String,
    var horaFim: String,
    var dia: String,
    var img: String
) {

    constructor() : this("", "", "", "", "", "", "")

    constructor(
        shoppId: String,
        nome: String,
        endereco: String,
        horaInicio: String,
        horaFim: String,
        dia: String
    ) : this(shoppId, nome, endereco, horaInicio, horaFim, dia, "")
}