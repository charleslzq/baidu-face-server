package com.github.charleslzq.baiduface

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/face")
class TestController {

    @Autowired
    lateinit var baiduFaceApi: BaiduFaceApi

    @RequestMapping("/test")
    fun test() {
        val result = baiduFaceApi.detect(Image(Image.Type.FACE_TOKEN, "123"), 1, FaceSource.LIVE, *FaceField.values())
        print(result.toString())
    }
}