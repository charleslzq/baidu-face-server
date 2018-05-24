package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${charleslzq.baidu.aipFace.basePath:/baidu-faces}/images")
class ImageController {

    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    // todo 检测,搜索,对比认证
}