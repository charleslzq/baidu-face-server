package com.github.charleslzq.baiduface

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

data class BaiduResponse<D>(
        val result: D?,
        @SerializedName("log_id") val logId: String,
        @SerializedName("error_code") val errorCode: String,
        @SerializedName("error_msg") val errorMsg: String,
        val timestamp: Long
)

interface FromJson<D> {
    val typeToken: TypeToken<BaiduResponse<D>>
    fun fromJsonObject(json: JSONObject, gson: Gson): BaiduResponse<D> {
        return gson.fromJson<BaiduResponse<D>>(json.toString(), DetectResponse.typeToken.type)
    }
}