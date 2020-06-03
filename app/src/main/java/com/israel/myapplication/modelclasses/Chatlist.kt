package com.israel.myapplication.modelclasses

class Chatlist {
    private var id: String = ""

    constructor()

    constructor(id: String) {
        this.id = id
    }

    //ID
    fun getId():String?{
        return id
    }

    fun setId(url: String?){
        this.id = id!!
    }
}