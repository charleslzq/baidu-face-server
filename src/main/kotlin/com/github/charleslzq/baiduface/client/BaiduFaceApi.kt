package com.github.charleslzq.baiduface.client

import com.baidu.aip.face.AipFace
import com.google.gson.Gson

interface BaiduFaceApi {
    fun detect(image: Image, options: DetectOptions = DetectOptions()): BaiduResponse<DetectResult>
    fun search(image: Image, vararg groupId: String, options: SearchOptions = SearchOptions()): BaiduResponse<SearchResult>
    fun verify(image: Image, userId: String, options: SearchOptions = SearchOptions()): BaiduResponse<SearchResult>
    fun listGroup(options: PageOptions): BaiduResponse<GroupIdList>
    fun addGroup(groupId: String): BaiduResponse<*>
    fun deleteGroup(groupId: String): BaiduResponse<*>
    fun registerUser(image: Image, userMeta: UserMeta, options: RegisterOptions = RegisterOptions()): BaiduResponse<FaceOperationResult>
    fun updateUser(image: Image, userMeta: UserMeta, options: UpdateOptions = UpdateOptions()): BaiduResponse<FaceOperationResult>
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
    override fun detect(image: Image, options: DetectOptions): BaiduResponse<DetectResult> {
        options.check()
        val rawObject = client.detect(image.data, image.type.name, options.toHashmap())
        return DetectResult.fromJsonObject(gson, rawObject)
    }

    override fun search(image: Image, vararg groupId: String, options: SearchOptions): BaiduResponse<SearchResult> {
        options.check()
        val rawObject = client.search(image.data, image.type.name, groupId.joinToString(","), options.toHashmap())
        return SearchResult.fromJsonObject(gson, rawObject)
    }

    override fun verify(image: Image, userId: String, options: SearchOptions): BaiduResponse<SearchResult> {
        options.check()
        val rawObject = client.search(image.data, image.type.name, "", options.withUserId(userId))
        return SearchResult.fromJsonObject(gson, rawObject)
    }

    override fun listGroup(options: PageOptions): BaiduResponse<GroupIdList> {
        options.check()
        val rawObject = client.getGroupList(options.toHashmap())
        return GroupIdList.fromJsonObject(gson, rawObject)
    }

    override fun addGroup(groupId: String) = BaiduResponse.fromJsonObject(gson, client.groupAdd(groupId, hashMapOf()))

    override fun deleteGroup(groupId: String) = BaiduResponse.fromJsonObject(gson, client.groupDelete(groupId, hashMapOf()))

    override fun registerUser(image: Image, userMeta: UserMeta, options: RegisterOptions): BaiduResponse<FaceOperationResult> {
        options.check()
        val rawObject = client.addUser(image.data, image.type.name, userMeta.groupId, userMeta.userId, options.withUserData(userMeta))
        return FaceOperationResult.fromJsonObject(gson, rawObject)
    }

    override fun updateUser(image: Image, userMeta: UserMeta, options: UpdateOptions): BaiduResponse<FaceOperationResult> {
        options.check()
        val rawObject = client.updateUser(image.data, image.type.name, userMeta.groupId, userMeta.userId, options.withUserData(userMeta))
        return FaceOperationResult.fromJsonObject(gson, rawObject)
    }

    override fun listUser(groupId: String, options: PageOptions): BaiduResponse<UserIdList> {
        options.check()
        val rawObject = client.getGroupUsers(groupId, options.toHashmap())
        return UserIdList.fromJsonObject(gson, rawObject)
    }

    override fun copyUser(userId: String, srcGroup: String, dstGroup: String) = BaiduResponse.fromJsonObject(gson, client.userCopy(userId, hashMapOf(
            "src_group_id" to srcGroup,
            "dst_group_id" to dstGroup
    )))

    override fun getUser(groupId: String, userId: String) = UserQueryResult.fromJsonObject(gson, client.getUser(userId, groupId, hashMapOf()))

    override fun deleteUser(groupId: String, userId: String) = BaiduResponse.fromJsonObject(gson, client.deleteUser(groupId, userId, hashMapOf()))

    override fun listFace(groupId: String, userId: String) = FaceListResult.fromJsonObject(gson, client.faceGetlist(userId, groupId, hashMapOf()))

    override fun deleteFace(groupId: String, userId: String, faceToken: String) = BaiduResponse.fromJsonObject(gson, client.faceDelete(userId, groupId, faceToken, hashMapOf()))
}