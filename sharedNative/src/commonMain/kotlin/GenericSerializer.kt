package com.example.sharedNative

import kotlinx.serialization.*
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.SerialClassDescImpl

data class CheckedData<T : Any>(
    val data: T,
    val checkSum: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CheckedData<*>

        if (data != other.data) return false
        if (!checkSum.contentEquals(other.checkSum)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + checkSum.contentHashCode()
        return result
    }
}

@Serializable data class StringCheckData(@Serializable(with = CheckedDataSerializer::class) val data: CheckedData<String>)
@Serializable data class IntData(@Serializable(with = CheckedDataSerializer::class) val data: CheckedData<Int>)

@Serializer(forClass = CheckedData::class)
class CheckedDataSerializer<T : Any>(val dataSerializer: KSerializer<T>) : KSerializer<CheckedData<T>> {
    override val descriptor: SerialDescriptor = object : SerialClassDescImpl("") {
        init {
            addElement("data")
            addElement("checkSum")
        }
    }
    override fun serialize(encoder: Encoder, obj: CheckedData<T>) {
        val out = encoder.beginStructure(descriptor)
        out.encodeSerializableElement(descriptor, 0, dataSerializer, obj.data)
        out.encodeStringElement(descriptor, 1, HexConverter.printHexBinary(obj.checkSum))
        out.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): CheckedData<T> {
        val inp = decoder.beginStructure(descriptor)
        lateinit var data: T
        lateinit var sum: ByteArray
        loop@ while (true) {
            when (val i = inp.decodeElementIndex(descriptor)) {
                CompositeDecoder.READ_DONE -> break@loop
                0 -> data = inp.decodeSerializableElement(descriptor, i, dataSerializer)
                1 -> sum = HexConverter.parseHexBinary(inp.decodeStringElement(descriptor, i))
                else -> throw SerializationException("Unknown index $i")
            }
        }
        inp.endStructure(descriptor)
        return CheckedData(data, sum)
    }
}


