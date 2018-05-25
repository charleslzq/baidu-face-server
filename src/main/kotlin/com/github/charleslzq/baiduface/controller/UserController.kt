package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${charleslzq.baidu.aipFace.basePath:/baidu-faces}/groups/{groupId}/users")
class UserController {

    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    @List
    fun list(
            @PathVariable groupId: String,
            @RequestParam(value = "start", required = false, defaultValue = "0") start: Int,
            @RequestParam(value = "length", required = false, defaultValue = "100") length: Int
    ) = baiduFaceApi.listUser(groupId, PageOptions(start, length))

    @Add
    fun add(
            @PathVariable groupId: String,
            @RequestBody userImage: RegisterImage,
            @RequestParam(value = "quality", required = false, defaultValue = "NONE") quality: QualityControl,
            @RequestParam(value = "liveness", required = false, defaultValue = "NONE") liveness: LivenessControl
    ) = baiduFaceApi.registerUser(userImage.image, UserMeta(groupId, userImage.userId, userImage.userInfo), ImageOptions(quality, liveness))

    @Update
    fun update(
            @PathVariable groupId: String,
            @PathVariable id: String,
            @RequestBody updateImage: UpdateImage,
            @RequestParam(value = "quality", required = false, defaultValue = "NONE") quality: QualityControl,
            @RequestParam(value = "liveness", required = false, defaultValue = "NONE") liveness: LivenessControl
    ) = baiduFaceApi.updateUser(updateImage.image, UserMeta(groupId, id, updateImage?.userInfo), ImageOptions(quality, liveness))

    @Get
    fun get(
            @PathVariable groupId: String,
            @PathVariable id: String
    ) = baiduFaceApi.getUser(groupId, id)

    @Delete
    fun delete(
            @PathVariable groupId: String,
            @PathVariable id: String
    ) = baiduFaceApi.deleteUser(groupId, id)

    data class RegisterImage(
            val image: Image = Image(),
            val userId: String = "",
            val userInfo: String? = null
    )

    data class UpdateImage(
            val image: Image = Image(),
            val userInfo: String? = null
    )
}