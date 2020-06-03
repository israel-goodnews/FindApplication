package com.israel.myapplication.modelclasses

class Chat {
     private var sender:String = ""
    private var message:String = ""
    private var receiver:String = ""
    private var isseen = false
    private var url:String = ""
    private var messageId:String = ""

     constructor()
    constructor(
        sender: String,
        message: String,
        receiver: String,
        isseen: Boolean,
        url: String,
        messageId: String
    ) {
        this.sender = sender
        this.message = message
        this.receiver = receiver
        this.isseen = isseen
        this.url = url
        this.messageId = messageId
    }
        //SENDER
    fun getSender():String?{
        return sender
    }

    fun setSender(sender: String?){
        this.sender = sender!!
    }

    //MESSAGE
    fun getMessage():String?{
        return message
    }

    fun setMessage(message: String?){
        this.message = message!!
    }

    //RECEIVER
    fun getReceiver():String?{
        return receiver
    }

    fun setReceiver(receiver: String?){
        this.receiver = receiver!!
    }

    //ISSEEN
    fun isIsseen():Boolean?{
        return isseen
    }

    fun setIsseen(isseen: Boolean){
        this.isseen = isseen!!
    }

    //URL
    fun getUrl():String?{
        return url
    }

    fun setUrl(url: String?){
        this.url = url!!
    }

    //MESSAGEID
    fun getMessageId():String?{
        return messageId
    }

    fun setMessageId(messageId: String?){
        this.messageId = messageId!!
    }
}