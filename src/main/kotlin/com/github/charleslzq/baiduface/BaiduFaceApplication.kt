package com.github.charleslzq.baiduface

import com.github.charleslzq.baiduface.controller.FaceController
import com.github.charleslzq.baiduface.controller.UserController
import com.github.charleslzq.baiduface.controller.UserGroupController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
open class BaiduFaceApplication {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            SpringApplication.run(BaiduFaceApplication::class.java, *args)
        }
    }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Import(UserGroupController::class, UserController::class, FaceController::class)
annotation class EnableBaiduFaceController