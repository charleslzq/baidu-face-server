package com.github.charleslzq.baiduface.client.io

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

interface FromJson<D> {
    val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<D>>
    fun fromJson(gson: Gson, getter: () -> JSONObject) = fromJsonObject(gson, getter())
    fun fromJsonObject(gson: Gson, json: JSONObject) = convertJsonObject(json, convertUseGson(gson))
    fun convertJsonObject(json: JSONObject, convert: (JSONObject, TypeToken<BaiduResponse<D>>) -> BaiduResponse<D>) = convert(json, RESPONSE_TYPE_TOKEN)
    fun convertUseGson(gson: Gson): (JSONObject, TypeToken<BaiduResponse<D>>) -> BaiduResponse<D> = { json, typeToken ->
        gson.fromJson(json.toString(), typeToken.type)
    }
}

data class BaiduResponse<D>(
        val result: D?,
        @SerializedName("log_id") val logId: String,
        @SerializedName("error_code") val errorCode: String,
        @SerializedName("error_msg") val errorMsg: String,
        val timestamp: Long
) {
    companion object : FromJson<Any> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<Any>> = object : TypeToken<BaiduResponse<Any>>() {}
        fun <T> errResponse(errMsg: String, errCode: String = "-1") = BaiduResponse<T>(
                result = null,
                logId = "",
                errorCode = errCode,
                errorMsg = errMsg,
                timestamp = System.currentTimeMillis()
        )
    }
}

data class DetectResult(
        @SerializedName("face_num") val count: Int,
        @SerializedName("face_list") val faceList: List<DetectedFace>
) {
    companion object : FromJson<DetectResult> {
        override val RESPONSE_TYPE_TOKEN = object : TypeToken<BaiduResponse<DetectResult>>() {}
    }
}

data class SearchResult(
        @SerializedName("face_token") val faceToken: String,
        @SerializedName("user_list") val userList: List<UserSearchResult>
) {
    companion object : FromJson<SearchResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<SearchResult>> = object : TypeToken<BaiduResponse<SearchResult>>() {}
    }
}

data class UserSearchResult(
        @SerializedName("group_id") val groupId: String,
        @SerializedName("user_id") val userId: String,
        @SerializedName("user_info") val userInfo: String,
        val score: Float
)

data class FaceOperationResult(
        @SerializedName("face_token") val faceToken: String,
        val location: FaceLocation
) {
    companion object : FromJson<FaceOperationResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<FaceOperationResult>> = object : TypeToken<BaiduResponse<FaceOperationResult>>() {}
    }
}

data class UserQueryResult(
        @SerializedName("user_list") val userList: List<QueriedUser>
) {
    companion object : FromJson<UserQueryResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<UserQueryResult>> = object : TypeToken<BaiduResponse<UserQueryResult>>() {}
    }
}

data class FaceListResult(
        @SerializedName("face_list") val faceList: List<FaceListItem>
) {
    companion object : FromJson<FaceListResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<FaceListResult>> = object : TypeToken<BaiduResponse<FaceListResult>>() {}
    }
}

data class UserIdList(
        @SerializedName("user_id_list") val userIdList: List<String>
) {
    companion object : FromJson<UserIdList> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<UserIdList>> = object : TypeToken<BaiduResponse<UserIdList>>() {}
    }
}

data class GroupIdList(
        @SerializedName("group_id_list") val groupIdList: List<String>
) {
    companion object : FromJson<GroupIdList> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<GroupIdList>> = object : TypeToken<BaiduResponse<GroupIdList>>() {}
    }
}

data class MatchResult(
        val score: Float,
        @SerializedName("face_list") val faceList: List<FaceToken>
) {
    companion object : FromJson<MatchResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<MatchResult>> = object : TypeToken<BaiduResponse<MatchResult>>() {}
    }
}

data class VerifyResult(
        @SerializedName("face_liveness") val liveness: Float,
        val thresholds: VerifyThreshold,
        @SerializedName("face_list") val faceList: List<DetectedFace>
) {
    companion object : FromJson<VerifyResult> {
        override val RESPONSE_TYPE_TOKEN: TypeToken<BaiduResponse<VerifyResult>> = object : TypeToken<BaiduResponse<VerifyResult>>() {}
    }
}