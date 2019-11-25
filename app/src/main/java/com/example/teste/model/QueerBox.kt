package com.example.teste.model

data class QueerBox(
    var prodId: String,
    var nome: String,
    var desc: String,
    var tam: String,
    var qtde: String,
    var valor: String,
    var categoria: String,
    var img: String,
    var tipo: String,
    var id:String
){
    constructor() : this("", "", "", "", "", "", "", "", "","")

    constructor(
        prodId: String,
        nome: String,
        desc: String,
        tam: String,
        qtde: String,
        valor: String,
        categoria: String,
        img: String,
        id : String
    ) : this(prodId, nome, desc, tam, qtde, valor, categoria, img, "",id)
}