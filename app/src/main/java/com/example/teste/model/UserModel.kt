package com.example.teste.model

class UserModel {

    var userId: String? = null
    var nome: String? = null
    var cpf: String? = null
    var tel: String? = null
    var nasc: String? = null
    var email: String? = null
    var senha: String? = null
    var img: String? = null

    constructor(){

    }

    constructor(
        userId: String?,
        nome: String?,
        cpf: String?,
        tel: String?,
        nasc: String?,
        email: String?,
        senha: String?,
        img: String?
    ) {
        this.userId = userId
        this.nome = nome
        this.cpf = cpf
        this.tel = tel
        this.nasc = nasc
        this.email = email
        this.senha = senha
        this.img = img
    }


}