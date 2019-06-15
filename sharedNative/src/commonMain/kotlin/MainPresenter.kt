package com.example.sharedNative

import kotlinx.serialization.json.Json

class MainPresenter(){

    var user: CustomClass? = null

    var delegate: PresenterInterface? = null

    fun getUserIdWithInterface(){
        user?.let {
            if (it.id < 2000) {
                delegate?.userId(it.id)
            } else {
                delegate?.userId(99999999)
            }
        } ?: delegate?.error()


       /* if (user != null) {
            if (user!!.id < 2000) {
                delegate?.userId(user!!.id)
            } else {
                delegate?.userId(99999999)
            }
        } else delegate?.error()*/


    }
    @kotlinx.serialization.UnstableDefault
    fun toData(json: String): SerializableClass{
        return Json.nonstrict.parse(SerializableClass.serializer(), json)
    }

}
interface PresenterInterface{
    fun userId(userId: Int)
    fun error()
}