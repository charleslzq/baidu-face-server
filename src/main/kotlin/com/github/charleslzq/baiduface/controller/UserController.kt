package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.PageOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/baidu-faces/groups/{groupId}/users")
class UserController {

    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    @List
    fun list(
            @PathVariable groupId: String,
            @RequestParam(value = "start", required = false, defaultValue = "0") start: Int,
            @RequestParam(value = "length", required = false, defaultValue = "100") length: Int
    ) = baiduFaceApi.listUser(groupId, PageOptions(start, length))

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


    //todo 注册,更新, 拷贝
}