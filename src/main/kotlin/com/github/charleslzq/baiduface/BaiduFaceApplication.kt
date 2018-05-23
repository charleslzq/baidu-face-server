package com.github.charleslzq.baiduface

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

fun main(vararg args: String) {
    SpringApplication.run(BaiduFaceApplication::class.java, *args)
}

@SpringBootApplication
open class BaiduFaceApplication

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
@Import(AipFaceClientConfiguration::class)
annotation class EnableBaiduFaceServer