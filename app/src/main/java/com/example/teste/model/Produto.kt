package com.example.teste.model

data class Produto(
    var prodId: String,
    var nome: String,
    var desc: String,
    var tam: String,
    var qtde: String,
    var valor: String,
    var categoria: String,
    var img: String
) {

    constructor() : this("", "", "", "", "", "", "", "")

    constructor(
        prodId: String,
        nome: String,
        desc: String,
        tam: String,
        qtde: String,
        valor: String,
        categoria: String
    ) : this(prodId, nome, desc, tam, qtde, valor, categoria, "")
}