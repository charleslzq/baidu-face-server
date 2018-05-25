package com.github.charleslzq.baiduface.client.io

import com.baidu.aip.face.MatchRequest

data class Image(
        val type: Type = Type.BASE64,
        val data: String = ""
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

data class UserMeta(
        val groupId: String,
        val userId: String,
        val userInfo: String? = null
)

enum class QualityControl {
    NONE,
    LOW,
    NORMAL,
    HIGH
}

enum class LivenessControl {
    NONE,
    LOW,
    NORMAL,
    HIGH
}

data class MatchReq(
        val image: Image = Image(),
        val source: FaceSource = FaceSource.LIVE,
        val quality: QualityControl = QualityControl.NONE,
        val liveness: LivenessControl = LivenessControl.NONE
) {
    fun toMatchRequest() = MatchRequest(
            image.data,
            image.type.name,
            source.name,
            quality.name,
            liveness.name
    )
}

