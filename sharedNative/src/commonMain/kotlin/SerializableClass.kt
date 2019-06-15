package com.example.sharedNative

import kotlinx.serialization.Serializable

@Serializable
data class SerializableClass (val user: String? = "", val number: Int? = null)
