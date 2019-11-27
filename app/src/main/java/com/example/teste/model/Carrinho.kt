package com.example.teste.model

data class Carrinho(
    var clienteCpf: String,
    var prodId: String,
    var nome: String,
    var desc: String,
    var tam: String,
    var qtde: String,
    var valor: String,
    var categoria: String,
    var img: String
) {

    constructor() : this("", "", "", "", "", "", "", "", "")

    constructor(
        clienteCpf: String,
        prodId: String,
        nome: String,
        desc: String,
        tam: String,
        qtde: String,
        valor: String,
        categoria: String
    ) : this(clienteCpf, prodId, nome, desc, tam, qtde, valor, categoria, "")

}