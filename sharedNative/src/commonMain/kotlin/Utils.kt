package com.example.sharedNative
import kotlinx.serialization.json.*

fun getTextLabel() = "Hello Kotlin Function"

fun getUserName(user: CustomClass) = user.name

@kotlinx.serialization.UnstableDefault
fun SerializableClass.toJsonString(): String{
    return Json.stringify(SerializableClass.serializer(), this)
}