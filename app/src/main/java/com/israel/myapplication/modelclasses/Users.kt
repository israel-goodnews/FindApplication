package com.israel.myapplication.modelclasses

class Users {

    private var uid: String = ""
    private var username: String = ""
    private var profile: String = ""
    private var cover: String = ""
    private var status: String = ""
    private var search: String = ""
    private var facebook: String = ""
    private var instagram: String = ""
    private var website: String = ""


    constructor()
    constructor(
        uid: String,
        username: String,
        profile: String,
        cover: String,
        status: String,
        search: String,
        facebook: String,
        instagram: String,
        website: String
    ) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.status = status
        this.search = search
        this.facebook = facebook
        this.instagram = instagram
        this.website = website
    }

    //SETTER AND GETTER UID
    fun getUID(): String?{
        return uid
    }
    fun setUID(uid: String){
        this.uid = uid
    }

    //SETTER AND GETTER USERNAME
    fun getUserName(): String?{
        return username
    }
    fun setUserName(username: String){
        this.username = username
    }


    //SETTER AND GETTER PROFILE
    fun getProfile(): String?{
        return profile
    }
    fun setProfile(profile: String){
        this.profile = profile
    }

    //SETTER AND GETTER COVER
    fun getCover(): String?{
        return cover
    }
    fun setCover(cover: String){
        this.cover = cover
    }

    //SETTER AND GETTER STATUS
    fun getStatus(): String?{
        return status
    }
    fun setStatus(status: String){
        this.status = status
    }

    //SETTER AND GETTER SEARCH
    fun getSearch(): String?{
        return search
    }
    fun setSearch(search: String){
        this.search = search
    }


    //SETTER AND GETTER UID
    fun getFacebook(): String?{
        return facebook
    }
    fun setFacebook(facebook: String){
        this.facebook = facebook
    }

    //SETTER AND GETTER UID
    fun getInstagram(): String?{
        return instagram
    }
    fun setInstagram(instagram: String){
        this.instagram = instagram
    }

    //SETTER AND GETTER UWEBSITE
    fun getWebsite(): String?{
        return website
    }
    fun setWebsite(website: String){
        this.website = website
    }

}