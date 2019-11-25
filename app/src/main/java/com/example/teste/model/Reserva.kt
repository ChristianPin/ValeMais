package com.example.teste.model

data class Reserva(
    var clienteCpf: String,
    var clienteNome: String,
    var clienteImg: String,
    var clienteTel: String,
    var prodId: String,
    var prodNome: String,
    var prodDesc: String,
    var prodTam: String,
    var prodQtde: String,
    var prodValor: String,
    var prodCategoria: String,
    var prodImg: String,
    var shoppId: String,
    var shoppNome: String,
    var shoppEndereco: String,
    var shoppHoraInicio: String,
    var shoppHoraFim: String,
    var shoppDia: String,
    var shoppImg: String,
    var recebido: String
) {

    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

    constructor(
        clienteCpf: String,
        clienteNome: String,
        clienteImg: String,
        clienteTel: String,
        prodId: String,
        prodNome: String,
        prodDesc: String,
        prodTam: String,
        prodQtde: String,
        prodValor: String,
        prodCategoria: String,
        prodImg: String,
        shoppId: String,
        shoppNome: String,
        shoppEndereco: String,
        shoppHoraInicio: String,
        shoppHoraFim: String,
        shoppDia: String,
        shoppImg: String
    ) : this(clienteCpf, clienteNome, clienteImg, clienteTel, prodId, prodNome, prodDesc, prodTam, prodQtde, prodValor, prodCategoria, prodImg, shoppId, shoppNome, shoppEndereco, shoppHoraInicio, shoppHoraFim, shoppDia, shoppImg,"")
}