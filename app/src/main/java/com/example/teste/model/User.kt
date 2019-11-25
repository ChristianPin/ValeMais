package com.example.teste.model

data class User(
    var userId: String,
    var nome: String,
    var cpf: String,
    var tel: String,
    var nasc: String,
    var email: String,
    var senha: String,
    var img: String
) {
    constructor() : this("", "", "", "", "", "", "", "")

    constructor(
        userId: String,
        nome: String,
        cpf: String,
        tel: String,
        nasc: String,
        email: String,
        senha: String
    ) : this(userId, nome, cpf, tel, nasc, email, senha, "")
}