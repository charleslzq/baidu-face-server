package com.github.charleslzq.baiduface

import com.baidu.aip.face.AipFace
import com.google.gson.Gson

interface BaiduFaceApi {
    fun detect(image: Image, maxCount: Int = 1, source: FaceSource = FaceSource.LIVE, vararg fields: FaceField): DetectResponse
}

class BaiduFaceApiAdapter(
        private val client: AipFace,
        private val gson: Gson
): BaiduFaceApi {
    override fun detect(image: Image, maxCount: Int, source: FaceSource, vararg fields: FaceField): DetectResponse {
        return gson.fromJson(
                client.detect(image.data, image.type.name, hashMapOf(
                        "face_type" to source.name,
                        "max_face_num" to maxCount.toString()
                ).apply {
                    if (fields.isNotEmpty()) {
                        put("face_field", fields.string())
                    }
                }).toString(), DetectResponse::class.java)!!
    }

    private fun Array<out FaceField>.string() = joinToString(",") {
        it.keyword
    }
}