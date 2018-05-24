package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.PageOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${charleslzq.baidu.aipFace.basePath:/baidu-faces}/groups")
class UserGroupController {
    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    @List
    fun list(
            @RequestParam(value = "start", required = false, defaultValue = "0") start: Int,
            @RequestParam(value = "length", required = false, defaultValue = "100") length: Int
    ) = baiduFaceApi.listGroup(PageOptions(start, length))

    @Add
    fun add(@RequestBody groupId: String) = baiduFaceApi.addGroup(groupId)

    @Delete
    fun delete(@PathVariable("id") id: String) = baiduFaceApi.deleteGroup(id)
}