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
        val result = baiduFaceApi.detect(Image(
                Image.Type.URL, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=329095528,1972228230&fm=200&gp=0.jpg"
        ), 1, FaceSource.LIVE, *FaceField.values())
        print(result.toString())
    }
}