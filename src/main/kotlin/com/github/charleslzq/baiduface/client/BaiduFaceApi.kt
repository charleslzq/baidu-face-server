package com.github.charleslzq.baiduface.client

import com.baidu.aip.face.AipFace
import com.github.charleslzq.baiduface.client.io.*
import com.google.gson.Gson
import org.json.JSONObject

interface BaiduFaceApi {
    fun detect(image: Image, options: DetectOptions = DetectOptions()): BaiduResponse<DetectResult>
    fun search(image: Image, vararg groupId: String, userId: String? = null, options: SearchOptions = SearchOptions()): BaiduResponse<SearchResult>
    fun listGroup(options: PageOptions): BaiduResponse<GroupIdList>
    fun addGroup(groupId: String): BaiduResponse<*>
    fun deleteGroup(groupId: String): BaiduResponse<*>
    fun registerUser(image: Image, userMeta: UserMeta, options: ImageOptions = ImageOptions()): BaiduResponse<FaceOperationResult>
    fun updateUser(image: Image, userMeta: UserMeta, options: ImageOptions = ImageOptions()): BaiduResponse<FaceOperationResult>
    fun listUser(groupId: String, options: PageOptions = PageOptions()): BaiduResponse<UserIdList>
    fun copyUser(userId: String, srcGroup: String, dstGroup: String): BaiduResponse<*>
    fun getUser(groupId: String, userId: String): BaiduResponse<UserQueryResult>
    fun deleteUser(groupId: String, userId: String): BaiduResponse<*>
    fun listFace(groupId: String, userId: String): BaiduResponse<FaceListResult>
    fun deleteFace(groupId: String, userId: String, faceToken: String): BaiduResponse<*>
}

class BaiduFaceApiAdapter(
        private val client: AipFace,
        private val gson: Gson
) : BaiduFaceApi {
    override fun detect(image: Image, options: DetectOptions) = DetectResult.fromBaidu {
        options.check()
        client.detect(image.data, image.type.name, options.toHashmap())
    }

    override fun search(image: Image, vararg groupId: String, userId: String?, options: SearchOptions) = SearchResult.fromBaidu {
        options.check()
        client.search(image.data, image.type.name, groupId.joinToString(","), options.withUserId(userId))
    }

    override fun listGroup(options: PageOptions) = GroupIdList.fromBaidu {
        options.check()
        client.getGroupList(options.toHashmap())
    }

    override fun addGroup(groupId: String) = BaiduResponse.fromBaidu { client.groupAdd(groupId, hashMapOf()) }

    override fun deleteGroup(groupId: String) = BaiduResponse.fromBaidu { client.groupDelete(groupId, hashMapOf()) }

    override fun registerUser(image: Image, userMeta: UserMeta, options: ImageOptions) = FaceOperationResult.fromBaidu {
        options.check()
        client.addUser(image.data, image.type.name, userMeta.groupId, userMeta.userId, options.withUserData(userMeta))
    }

    override fun updateUser(image: Image, userMeta: UserMeta, options: ImageOptions) = FaceOperationResult.fromBaidu {
        options.check()
        client.updateUser(image.data, image.type.name, userMeta.groupId, userMeta.userId, options.withUserData(userMeta))
    }

    override fun listUser(groupId: String, options: PageOptions) = UserIdList.fromBaidu {
        options.check()
        client.getGroupUsers(groupId, options.toHashmap())
    }

    override fun copyUser(userId: String, srcGroup: String, dstGroup: String) = BaiduResponse.fromBaidu {
        client.userCopy(userId, hashMapOf(
                "src_group_id" to srcGroup,
                "dst_group_id" to dstGroup
        ))
    }

    override fun getUser(groupId: String, userId: String) = UserQueryResult.fromBaidu { client.getUser(userId, groupId, hashMapOf()) }

    override fun deleteUser(groupId: String, userId: String) = BaiduResponse.fromBaidu { client.deleteUser(groupId, userId, hashMapOf()) }

    override fun listFace(groupId: String, userId: String) = FaceListResult.fromBaidu { client.faceGetlist(userId, groupId, hashMapOf()) }

    override fun deleteFace(groupId: String, userId: String, faceToken: String) = BaiduResponse.fromBaidu { client.faceDelete(userId, groupId, faceToken, hashMapOf()) }

    private fun <T> FromJson<T>.fromBaidu(get: () -> JSONObject) = handleException {
        fromJson(gson, get)
    }

    private fun <T> handleException(handler: (Throwable) -> BaiduResponse<T> = this::defaultExceptionHandler, get: () -> BaiduResponse<T>): BaiduResponse<T> {
        return try {
            get()
        } catch (throwable: Throwable) {
            handler(throwable)
        }
    }

    private fun <T> defaultExceptionHandler(throwable: Throwable): BaiduResponse<T> = BaiduResponse.errResponse(throwable.localizedMessage)
}