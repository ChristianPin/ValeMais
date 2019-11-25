package com.example.teste.model

class ReservaModel {

    var clienteCpf: String? = null
    var clienteNome: String? = null
    var clienteImg: String? = null
    var clienteTel: String? = null
    var prodId: String? = null
    var prodNome: String? = null
    var prodDesc: String? = null
    var prodTam: String? = null
    var prodQtde: String? = null
    var prodValor: String? = null
    var prodCategoria: String? = null
    var prodImg: String? = null
    var shoppId: String? = null
    var shoppNome: String? = null
    var shoppEndereco: String? = null
    var shoppHoraInicio: String? = null
    var shoppHoraFim: String? = null
    var shoppDia: String? = null
    var shoppImg: String? = null

    constructor(){

    }

    constructor(
        clienteCpf: String?,
        clienteNome: String?,
        clienteImg: String?,
        clienteTel: String?,
        prodId: String?,
        prodNome: String?,
        prodDesc: String?,
        prodTam: String?,
        prodQtde: String?,
        prodValor: String?,
        prodCategoria: String?,
        prodImg: String?,
        shoppId: String?,
        shoppNome: String?,
        shoppEndereco: String?,
        shoppHoraInicio: String?,
        shoppHoraFim: String?,
        shoppDia: String?,
        shoppImg: String?
    ) {
        this.clienteCpf = clienteCpf
        this.clienteNome = clienteNome
        this.clienteImg = clienteImg
        this.clienteTel = clienteTel
        this.prodId = prodId
        this.prodNome = prodNome
        this.prodDesc = prodDesc
        this.prodTam = prodTam
        this.prodQtde = prodQtde
        this.prodValor = prodValor
        this.prodCategoria = prodCategoria
        this.prodImg = prodImg
        this.shoppId = shoppId
        this.shoppNome = shoppNome
        this.shoppEndereco = shoppEndereco
        this.shoppHoraInicio = shoppHoraInicio
        this.shoppHoraFim = shoppHoraFim
        this.shoppDia = shoppDia
        this.shoppImg = shoppImg
    }
}