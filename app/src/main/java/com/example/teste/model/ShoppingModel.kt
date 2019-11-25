package com.example.teste.model

class ShoppingModel {

    var shoppId: String? = null
    var nome: String? = null
    var endereco: String? = null
    var horaInicio: String? = null
    var horaFim: String? = null
    var dia: String? = null
    var img: String? = null

    constructor(){

    }

    constructor(
        shoppId: String?,
        nome: String?,
        endereco: String?,
        horaInicio: String?,
        horaFim: String?,
        dia: String?,
        img: String?
    ){
        this.shoppId = shoppId
        this.nome = nome
        this.endereco = endereco
        this.horaInicio = horaInicio
        this.horaFim = horaFim
        this.dia = dia
        this.img = img
    }
}