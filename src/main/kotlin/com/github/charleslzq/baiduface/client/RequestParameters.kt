package com.github.charleslzq.baiduface.client

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

interface BaiduRequestOptions {
    fun check() {}
    fun toHashmap(): HashMap<String, String>
}

interface BaiduRequestOptionsWithUserInfo : BaiduRequestOptions {
    fun withUserData(userMeta: UserMeta) = toHashmap().apply {
        userMeta.userInfo?.let { put("user_info", it) }
    }
}

data class DetectOptions(
        val maxCount: Int = 1,
        val source: FaceSource = FaceSource.LIVE,
        val fields: List<FaceField> = emptyList()
) : BaiduRequestOptions {
    override fun check() {
        if (maxCount !in 1..10) {
            throw IllegalArgumentException("Illegal maxCount, expect to be in 1 to 10, actual: $maxCount")
        }
    }

    override fun toHashmap() = hashMapOf(
            "face_type" to source.name,
            "max_face_num" to maxCount.toString()
    ).apply {
        if (fields.isNotEmpty()) {
            put("face_field", fields.string())
        }
    }

    private fun List<FaceField>.string() = joinToString(",") {
        it.keyword
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

data class SearchOptions(
        val maxUser: Int = 1,
        val quality: QualityControl = QualityControl.NONE,
        val liveness: LivenessControl = LivenessControl.NONE
) : BaiduRequestOptions {

    override fun check() {
        if (maxUser !in 1..20) {
            throw IllegalArgumentException("Illegal maxCount, expect to be in 1 to 20, actual: $maxUser")
        }
    }

    override fun toHashmap() = hashMapOf(
            "max_user_num" to maxUser.toString(),
            "quality_control" to quality.name,
            "liveness_control" to liveness.name
    )

    fun withUserId(userId: String) = toHashmap().apply {
        put("user_id", userId)
    }
}

data class UserMeta(
        val groupId: String,
        val userId: String,
        val userInfo: String? = null
)

data class RegisterOptions(
        val quality: QualityControl = QualityControl.NONE,
        val liveness: LivenessControl = LivenessControl.NONE
) : BaiduRequestOptionsWithUserInfo {
    override fun toHashmap() = hashMapOf(
            "quality_control" to quality.name,
            "liveness_control" to liveness.name
    )

}

data class UpdateOptions(
        val quality: QualityControl = QualityControl.NONE,
        val liveness: LivenessControl = LivenessControl.NONE,
        val autoCreate: Boolean = false
) : BaiduRequestOptionsWithUserInfo {
    override fun toHashmap(): HashMap<String, String> = hashMapOf(
            "quality_control" to quality.name,
            "liveness_control" to liveness.name
    ).apply {
        if (autoCreate) {
            put("action_type", "replace")
        }
    }

}

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

data class PageOptions(
        val start: Int = 0,
        val length: Int = 100
) : BaiduRequestOptions {
    override fun check() {
        if (start < 0) {
            throw IllegalArgumentException("Illegal Start, suppose to be positive, actual: $start")
        }
        if (length !in 1..100) {
            throw IllegalArgumentException("Illegal Start, suppose to be in range 1 to 100, actual: $length")
        }
    }

    override fun toHashmap() = hashMapOf(
            "start" to start.toString(),
            "length" to length.toString()
    )

}

