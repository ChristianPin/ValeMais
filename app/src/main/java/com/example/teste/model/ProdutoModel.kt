package com.example.teste.model

class ProdutoModel {

    var prodId: String? = null
    var nome: String? = null
    var desc: String? = null
    var tam: String? = null
    var qtde: String? = null
    var valor: String? = null
    var categoria: String? = null
    var img: String? = null

    constructor(){

    }

    constructor(
        prodId: String?,
        nome: String?,
        desc: String?,
        tam: String?,
        qtde: String?,
        valor: String?,
        categoria: String?,
        img: String?
    ) {
        this.prodId = prodId
        this.nome = nome
        this.desc = desc
        this.tam = tam
        this.qtde = qtde
        this.valor = valor
        this.categoria = categoria
        this.img = img
    }

}