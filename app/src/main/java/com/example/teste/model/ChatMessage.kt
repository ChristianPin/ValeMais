package com.example.teste.model


class ChatMessage(val id:String, val text:String, val fromId:String, val toId:String,val nomeToId: String, val imgToId: String, val telToId: String,val timestamp:Long, val tempoEnvio: String){
    constructor():this("", "", "", "", "", "", "",-1, "")
}