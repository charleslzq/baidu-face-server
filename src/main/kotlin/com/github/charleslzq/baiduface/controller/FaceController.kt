package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${charleslzq.baidu.aipFace.basePath:/baidu-faces}/groups/{groupId}/users/{userId}/faces")
class FaceController {

    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    @List
    fun list(
            @PathVariable groupId: String,
            @PathVariable userId: String
    ) = baiduFaceApi.listFace(groupId, userId)

    @Delete
    fun delete(
            @PathVariable groupId: String,
            @PathVariable userId: String,
            @PathVariable id: String
    ) = baiduFaceApi.deleteFace(groupId, userId, id)
}