package com.github.charleslzq.baiduface.client.io

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

    companion object {
        @JvmStatic
        fun requireAllFields(options: DetectOptions = DetectOptions()) = options.copy(fields = FaceField.values().toList())
    }
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

data class ImageOptions(
        val quality: QualityControl = QualityControl.NONE,
        val liveness: LivenessControl = LivenessControl.NONE
) : BaiduRequestOptionsWithUserInfo {
    override fun toHashmap() = hashMapOf(
            "quality_control" to quality.name,
            "liveness_control" to liveness.name
    )

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