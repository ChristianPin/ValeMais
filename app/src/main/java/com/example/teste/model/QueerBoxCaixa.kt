package com.example.teste.model

data class QueerBoxCaixa(
    var id:String,
    var nome: String,
    var qtde: String,
    var img: String,
    var tipo: String
){
    constructor() : this("", "", "", "", "")

    constructor(
        id : String,
        nome: String,
        qtde: String,
        img: String
    ) : this(id, nome, qtde, img, "")
}