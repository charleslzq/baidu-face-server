package com.github.charleslzq.baiduface.client.io

import com.google.gson.annotations.SerializedName

data class DetectedFace(
        @SerializedName("face_token") val faceToken: String,
        val location: FaceLocation,
        @SerializedName("face_probability") val faceProbability: Double,
        val angle: FaceAngle,
        val age: Double?,
        val beauty: Double?,
        val expression: DetectedItem<Expression>?,
        @SerializedName("face_shape") val shape: DetectedItem<Shape>?,
        val gender: DetectedItem<Gender>?,
        val glasses: DetectedItem<Glasses>?,
        val race: DetectedItem<Race>?,
        @SerializedName("face_type") val faceType: DetectedItem<FaceType>?,
        val landmark: List<FacePoint>?,
        val landmark72: List<FacePoint>?,
        val quality: Quality?,
        @SerializedName("parsing_info") val parsingInfo: String?
)

data class FacePoint(
        val x: Double,
        val y: Double
)

data class FaceLocation(
        val left: Double,
        val top: Double,
        val width: Double,
        val height: Double,
        val rotation: Int
)

data class FaceAngle(
        val yaw: Double,
        val pitch: Double,
        val roll: Double
)

data class DetectedItem<T : Enum<T>>(
        val type: T,
        val probability: Double
)

enum class Expression {
    @SerializedName("none")
    NONE,
    @SerializedName("smile")
    SMILE,
    @SerializedName("laugh")
    LAUGH
}

enum class Shape {
    @SerializedName("square")
    SQUARE,
    @SerializedName("triangle")
    TRIANGLE,
    @SerializedName("oval")
    OVAL,
    @SerializedName("heart")
    HEART,
    @SerializedName("round")
    ROUND
}

enum class Gender {
    @SerializedName("male")
    MALE,
    @SerializedName("female")
    FEMALE
}

enum class Glasses {
    @SerializedName("none")
    NONE,
    @SerializedName("common")
    COMMON,
    @SerializedName("sun")
    SUN
}

enum class Race {
    @SerializedName("yellow")
    YELLOW,
    @SerializedName("white")
    WHITE,
    @SerializedName("black")
    BLACK,
    @SerializedName("arabs")
    ARABS
}

enum class FaceType {
    @SerializedName("human")
    HUMAN,
    @SerializedName("cartoon")
    CARTOON
}

data class Quality(
        val occlusion: Occlusion?,
        val blur: Double,
        val illumination: Double,
        val completeness: Int
)

data class Occlusion(
        @SerializedName("left_eye") val leftEye: Double,
        @SerializedName("right_eye") val rightEye: Double,
        val nose: Double,
        val mouth: Double,
        @SerializedName("left_cheek") val leftCheek: Double,
        @SerializedName("right_cheek") val rightCheek: Double,
        val chin: Double
)

data class QueriedUser(
        @SerializedName("group_id") val groupId: String,
        @SerializedName("user_info") val userInfo: String
)

data class FaceListItem(
        @SerializedName("face_token") val faceToken: String,
        @SerializedName("ctime") val createTime: String
)

data class FaceToken(
        @SerializedName("face_token") val faceToken: String
)

data class VerifyThreshold(
        @SerializedName("frr_1e-4") val err10000th: Float,
        @SerializedName("frr_1e-3") val err1000th: Float,
        @SerializedName("frr_1e-2") val err100th: Float
)