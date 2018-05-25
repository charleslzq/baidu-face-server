package com.github.charleslzq.baiduface.controller

import com.github.charleslzq.baiduface.client.BaiduFaceApi
import com.github.charleslzq.baiduface.client.io.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${charleslzq.baidu.aipFace.basePath:/baidu-faces}/images")
class ImageController {

    @Autowired
    private lateinit var baiduFaceApi: BaiduFaceApi

    @RequestMapping(value = ["detect"], method = [RequestMethod.POST])
    fun detect(
            @RequestBody image: Image,
            @RequestParam(name = "maxCount", required = false, defaultValue = "1") maxCount: Int,
            @RequestParam(name = "source", required = false, defaultValue = "LIVE") source: FaceSource,
            @RequestParam(name = "fields", required = false, defaultValue = "") fields: Array<FaceField>,
            @RequestParam(name = "complete", required = false, defaultValue = "false") complete: Boolean
    ) = baiduFaceApi.detect(image, DetectOptions(maxCount, source, (if (complete) FaceField.values() else fields).toList()))

    @RequestMapping(value = ["search"], method = [RequestMethod.POST])
    fun search(
            @RequestBody image: Image,
            @RequestParam("groups") groups: Array<String>,
            @RequestParam(name = "userId", required = false) userId: String?,
            @RequestParam(name = "maxUser", required = false, defaultValue = "1") maxUser: Int,
            @RequestParam(name = "quality", required = false, defaultValue = "NONE") quality: QualityControl,
            @RequestParam(name = "liveness", required = false, defaultValue = "NONE") liveness: LivenessControl
    ) = if (groups.isEmpty()) {
        BaiduResponse.errResponse("Need to which group(s) should be searched")
    } else {
        baiduFaceApi.search(image,
                groupId = *groups,
                userId = userId,
                options = SearchOptions(maxUser, quality, liveness)
        )
    }

    @RequestMapping(value = ["match"], method = [RequestMethod.POST])
    fun match(
            @RequestBody images: Array<MatchReq>
    ) = baiduFaceApi.match(*images)
}