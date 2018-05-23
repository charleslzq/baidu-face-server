package com.github.charleslzq.baiduface


data class Image(
        val type: Type,
        val data: String
) {
    enum class Type {
        BASE64,
        URL,
        FACE_TOKEN
    }
}

enum class FaceField(val keyword: String) {
    AGE("age"),
    BEAUTY("beauty"),
    EXPRESSION("expression"),
    FACE_SHAPE("faceshape"),
    GENDER("gender"),
    GLASSES("glasses"),
    LANDMARK("landmark"),
    RACE("race"),
    QUALITY("quality"),
    FACE_TYPE("facetype"),
    PARSING("parsing");
}

enum class FaceSource {
    LIVE,
    IDCARD,
    WATERMARK,
    CERT
}
