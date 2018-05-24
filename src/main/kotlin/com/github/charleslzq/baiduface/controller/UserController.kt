package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.PageOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/groups/{groupId}/users")
class UserController {

    @Autowired
    lateinit var baiduFaceApi: BaiduFaceApi

    @List
    fun list(
            @PathVariable("groupId") groupId: String,
            @RequestParam(value = "start", required = false, defaultValue = "0") start: Int,
            @RequestParam(value = "length", required = false, defaultValue = "100") length: Int
    ) = baiduFaceApi.listUser(groupId, PageOptions(start, length))

    @Get
    fun get(
            @PathVariable("groupId") groupId: String,
            @PathVariable("userId") userId: String
    ) = baiduFaceApi.getUser(groupId, userId)

    @Delete
    fun delete(
            @PathVariable("groupId") groupId: String,
            @PathVariable("id") userId: String
    ) = baiduFaceApi.deleteUser(groupId, userId)


    //todo 注册,更新, 拷贝
}