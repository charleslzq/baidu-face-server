package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.BaiduResponse
import com.github.charleslzq.baiduface.client.io.DetectOptions
import com.github.charleslzq.baiduface.client.io.DetectResult
import com.github.charleslzq.baiduface.client.io.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/face")
@Profile("local")
class TestController {

    @Autowired
    lateinit var baiduFaceApi: BaiduFaceApi

    @RequestMapping("/detect")
    fun detect(): BaiduResponse<DetectResult> {
        val result = baiduFaceApi.detect(Image(
                Image.Type.URL, "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=329095528,1972228230&fm=200&gp=0.jpg"
        ), DetectOptions.requireAllFields())
        return result
    }
}